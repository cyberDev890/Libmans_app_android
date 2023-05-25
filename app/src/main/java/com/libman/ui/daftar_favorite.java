package com.libman.ui;

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
import com.libman.controller.HistoryAdapterList;
import com.libman.controller.daftarFavoritAdapter;
import com.libman.model.daftar_favorite.DaftarFavorit;
import com.libman.model.daftar_favorite.DaftarFavoritData;
import com.libman.model.daftarbuku.DaftarBuku;
import com.libman.model.history.History;
import com.libman.model.history.HistoryData;
import com.libman.sesion.SesionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftar_favorite extends Fragment {

    private RecyclerView rvDaftarfavorit;
    SesionManager sesionManager;
    private String _Nis;
    private daftarFavoritAdapter daftarFavoritAdapter;

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
        View view = inflater.inflate(R.layout.fragment_daftar_favorite, container, false);
        sesionManager = new SesionManager(getActivity());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        Toast.makeText(getActivity(), NIS, Toast.LENGTH_SHORT).show();
        rvDaftarfavorit = view.findViewById(R.id.rv_daftar_favorit);
        rvDaftarfavorit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        daftarFavoritAdapter = new daftarFavoritAdapter();
        rvDaftarfavorit.setAdapter(daftarFavoritAdapter);
        // Fetch history data
        fetchHistoryData(NIS);

        return view;
    }

    private void fetchHistoryData(String nis ) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DaftarFavorit> call = apiInterface.daftarFavorit(nis);

       call.enqueue(new Callback<DaftarFavorit>() {
           @Override
           public void onResponse(Call<DaftarFavorit> call, Response<DaftarFavorit> response) {
               if (response.isSuccessful()) {
                   DaftarFavorit daftarFavorit = response.body();
                   if (daftarFavorit != null && daftarFavorit.getStatus()) {
                       List<DaftarFavoritData> DatafavoritList = daftarFavorit.getData();
                       daftarFavoritAdapter.setData(DatafavoritList);
                       daftarFavoritAdapter.notifyDataSetChanged();
                   } else {
                       Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                   }
               } else {
                   Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<DaftarFavorit> call, Throwable t) {

           }
       });
    }
}