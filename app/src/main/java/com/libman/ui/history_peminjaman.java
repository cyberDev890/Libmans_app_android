package com.libman.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView cariHistory;
    private LottieAnimationView animationView;

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
        rvHistory = view.findViewById(R.id.rv_HistoryPeminjaman);
        cariHistory = view.findViewById(R.id.edt_cariHistory);
        swipeRefreshLayout = view.findViewById(R.id.refresherHistory);
        animationView = view.findViewById(R.id.lottie_emptyHistory);
        animationView.setScale(0.2f);
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        historyAdapterList = new HistoryAdapterList();
        rvHistory.setAdapter(historyAdapterList);
        cariHistory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                historyAdapterList.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });

        // Fetch history data
        fetchHistoryData(NIS);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true); // menampilkan progress bar
                fetchHistoryData(NIS);
                swipeRefreshLayout.setRefreshing(false);

            }
        });

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
                        Toast.makeText(getActivity(), "Perhatikan Status Pengembalian untuk mencegah sanksi keterlambatan", Toast.LENGTH_SHORT).show();
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
        animationView.setVisibility(View.VISIBLE);
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
                        animationView.setVisibility(View.INVISIBLE);

                    } else {
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                animationView.setVisibility(View.INVISIBLE);

            }
        });
    }
}
