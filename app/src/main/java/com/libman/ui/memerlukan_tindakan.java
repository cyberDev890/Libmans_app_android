package com.libman.ui;

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
import com.libman.controller.daftarFavoritAdapter;
import com.libman.controller.tindakanAdapter;
import com.libman.model.daftar_favorite.DaftarFavorit;
import com.libman.model.daftar_favorite.DaftarFavoritData;
import com.libman.model.memerlukan_tindakan.Tindakan;
import com.libman.model.memerlukan_tindakan.TindakanData;
import com.libman.sesion.SesionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class memerlukan_tindakan extends Fragment {

    private RecyclerView rvTindakan;
    SesionManager sesionManager;
    private String _Nis;
    private tindakanAdapter TindakanAdapter;
    private TextView cariTindakan;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LottieAnimationView animationView;
    private ProgressBar pbData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memerlukan_tindakan, container, false);

        sesionManager = new SesionManager(getActivity());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        rvTindakan = view.findViewById(R.id.rv_tindakan);
        cariTindakan = view.findViewById(R.id.edt_cariTindakan);
        swipeRefreshLayout = view.findViewById(R.id.refreshertindakan);
        animationView=view.findViewById(R.id.lottie_emptyTindakan);
        animationView.setScale(0.2f);
        rvTindakan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        TindakanAdapter = new tindakanAdapter();
        rvTindakan.setAdapter(TindakanAdapter);
        // Fetch history data
        cariTindakan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TindakanAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });

        fetchTindakanData(NIS);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true); // menampilkan progress bar
                fetchTindakanData(NIS);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;

    }
    private void fetchTindakanData(String nis ) {
        animationView.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Tindakan> call = apiInterface.tindakan(nis);
        call.enqueue(new Callback<Tindakan>() {
            @Override
            public void onResponse(Call<Tindakan> call, Response<Tindakan> response) {
                if (response.isSuccessful()) {
                    Tindakan tindakan = response.body();
                    if (tindakan != null && tindakan.isStatus()) {
                        List<TindakanData> tindakanList = tindakan.getData();
                        TindakanAdapter.setData(tindakanList);
                        TindakanAdapter.notifyDataSetChanged();
                        animationView.setVisibility(View.INVISIBLE);

                    } else {
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tindakan> call, Throwable t) {
                animationView.setVisibility(View.INVISIBLE);

            }
        });


    }

}