package com.libman.ui;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.controller.daftarFavoritAdapter;
import com.libman.model.daftar_favorite.DaftarFavorit;
import com.libman.model.daftar_favorite.DaftarFavoritData;
import com.libman.sesion.SesionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftar_favorite extends Fragment {

    private RecyclerView rvDaftarfavorit;
    SesionManager sesionManager;
    private String _Nis;
    private TextView cariFavorite;
    private daftarFavoritAdapter daftarFavoritAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
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
        View view = inflater.inflate(R.layout.fragment_daftar_favorite, container, false);
        sesionManager = new SesionManager(getActivity());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        rvDaftarfavorit = view.findViewById(R.id.rv_daftar_favorit);
        cariFavorite=view.findViewById(R.id.cariFavorite);
        swipeRefreshLayout = view.findViewById(R.id.refresherFavorit);
        animationView=view.findViewById(R.id.lottie_emptyfavorit);
        rvDaftarfavorit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        daftarFavoritAdapter = new daftarFavoritAdapter();
        rvDaftarfavorit.setAdapter(daftarFavoritAdapter);
        // Fetch history data

        cariFavorite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                daftarFavoritAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
        fetchFavoritData(NIS);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true); // menampilkan progress bar
                fetchFavoritData(NIS);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void fetchFavoritData(String nis ) {
        animationView.setVisibility(View.VISIBLE);

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
                       animationView.setVisibility(View.INVISIBLE);
                   } else {
                       Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                   }
               } else {
                   Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<DaftarFavorit> call, Throwable t) {
               animationView.setVisibility(View.INVISIBLE);
           }
       });
    }
}