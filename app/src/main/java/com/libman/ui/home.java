package com.libman.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.libman.R;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.controller.HistoryAdapter;
import com.libman.controller.tindakanAdapterCard;
import com.libman.model.history.History;
import com.libman.model.history.HistoryData;
import com.libman.model.memerlukan_tindakan.Tindakan;
import com.libman.model.memerlukan_tindakan.TindakanData;
import com.libman.sesion.SesionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends Fragment {

    private RecyclerView recyclerView;
    private String _nis;
    private HistoryAdapter historyAdapter;
    private tindakanAdapterCard TindakanAdapterCard;

    private RecyclerView.Adapter adapterListbuku, adapterTindakanbuku;
    private RecyclerView rvListbuku, rvTindakanbuku;
    private ImageView img_more;
    SesionManager sesionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sesionManager = new SesionManager(getActivity());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        Toast.makeText(getActivity(), NIS, Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_more = view.findViewById(R.id.ic_more);
        rvListbuku = view.findViewById(R.id.rv_HistoryHome);
        rvListbuku.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        historyAdapter = new HistoryAdapter();
        rvListbuku.setAdapter(historyAdapter);
        //batas
        rvTindakanbuku = view.findViewById(R.id.rv_tindakanHome);
        rvTindakanbuku.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        TindakanAdapterCard = new tindakanAdapterCard();
        rvTindakanbuku.setAdapter(TindakanAdapterCard);
        // Fetch history data
        fetchTindakanData(NIS);
        fetchHistoryData(NIS);

        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with 'daftar_buku' fragment
                Fragment daftarBukuFragment = new daftar_buku();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_main, daftarBukuFragment)
                        .commit();
            }
        });
        return view;
    }

    private void fetchHistoryData(String nis) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<History> call = apiInterface.history(nis); //

        call.enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                if (response.isSuccessful()) {
                    History history = response.body();
                    if (history != null && history.isSuccess()) {
                        List<HistoryData> historyDataList = history.getData();
                        historyAdapter.setData(historyDataList);
                        historyAdapter.notifyDataSetChanged();
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

    private void fetchTindakanData(String nis) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Tindakan> call = apiInterface.tindakan(nis); //
        call.enqueue(new Callback<Tindakan>() {
            @Override
            public void onResponse(Call<Tindakan> call, Response<Tindakan> response) {
                if (response.isSuccessful()) {
                    Tindakan tindakan = response.body();
                    if (tindakan != null && tindakan.isStatus()) {
                        List<TindakanData> tindakanList = tindakan.getData();
                        TindakanAdapterCard.setData(tindakanList);
                        TindakanAdapterCard.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tindakan> call, Throwable t) {

            }

        });

    }


}