package com.libman.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.controller.HistoryAdapter;
import com.libman.controller.HistoryAdapterList;
import com.libman.model.history.History;
import com.libman.model.history.HistoryData;
import com.libman.sesion.SesionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class history_peminjaman extends Fragment {
    private RecyclerView rvHistory;
    SesionManager sesionManager;
    private String _Nis;
    private HistoryAdapterList historyAdapterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_peminjaman, container, false);
        sesionManager = new SesionManager(getActivity());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        Toast.makeText(getActivity(), NIS, Toast.LENGTH_SHORT).show();
        rvHistory = view.findViewById(R.id.rv_HistoryPeminjaman);
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        historyAdapterList = new HistoryAdapterList();
        rvHistory.setAdapter(historyAdapterList);
        // Fetch history data
        fetchHistoryData(NIS);
        return view;
    }

    private void checkReturnDate(String returnDate, String dueDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date returnDateObj = dateFormat.parse(returnDate);
            Date dueDateObj = dateFormat.parse(dueDate);

            if (returnDateObj != null && dueDateObj != null) {
                if (returnDateObj.after(dueDateObj)) {
                    long diff = returnDateObj.getTime() - dueDateObj.getTime();
                    long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    if (diffDays > 7) {
                        // Implement your action for exceeding return date by more than 7 days
                        Toast.makeText(getActivity(), "Anda melewati batas pengembalian buku. Anda akan dikenai denda.", Toast.LENGTH_SHORT).show();
                        // Add code to apply appropriate action (e.g., charging a fine, sending notification, etc.)
                    } else {
                        // Implement your action for exceeding return date within 7 days
                        long remainingDays = 7 - diffDays;
                        Toast.makeText(getActivity(), "Anda belum mengembalikan buku. Silakan mengembalikan dalam waktu " + remainingDays + " hari.", Toast.LENGTH_SHORT).show();                        // Add code to apply appropriate action (e.g., sending a reminder, etc.)
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void fetchHistoryData(String nis) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<History> call = apiInterface.history(nis);

        call.enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                if (response.isSuccessful()) {
                    History history = response.body();
                    if (history != null && history.isSuccess()) {
                        List<HistoryData> historyDataList = history.getData();

                        // Check return date for each history data
                        for (HistoryData historyData : historyDataList) {
                            checkReturnDate(historyData.getTanggalPeminjaman(), historyData.getTanggalPengembalian());
                        }

                        historyAdapterList.setData(historyDataList);
                        historyAdapterList.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {

            }
        });
    }
}
