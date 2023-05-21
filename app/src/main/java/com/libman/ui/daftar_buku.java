package com.libman.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.controller.daftarBukuAdapter;
import com.libman.model.daftarbuku.DaftarBukuData;
import com.libman.model.daftarbuku.DaftarBuku;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftar_buku extends Fragment {

    private daftarBukuAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daftar_buku, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_DaftarBuku);
        adapter = new daftarBukuAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        loadDataBuku();

        return view;
    }

    private void loadDataBuku() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DaftarBuku> call = apiInterface.getDaftarBuku();
        call.enqueue(new Callback<DaftarBuku>() {
            @Override
            public void onResponse(@NonNull Call<DaftarBuku> call, @NonNull Response<DaftarBuku> response) {
                if (response.isSuccessful()) {
                    DaftarBuku daftarBuku = response.body();
                    if (daftarBuku != null && daftarBuku.getData() != null) {
                        List<DaftarBukuData> dataBuku = daftarBuku.getData();
                        adapter.setData(dataBuku);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DaftarBuku> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Gagal mengambil data buku", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
