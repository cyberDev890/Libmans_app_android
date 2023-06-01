package com.libman.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.libman.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.controller.HistoryAdapter;
import com.libman.controller.rekomendasiAdapter;
import com.libman.controller.tindakanAdapterCard;
import com.libman.model.RateBuku.RateBuku;
import com.libman.model.RateBuku.RateBukuData;
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
    private rekomendasiAdapter rekomendasiAdapter;
    private tindakanAdapterCard TindakanAdapterCard;

    private RecyclerView.Adapter adapterListbuku, adapterTindakanbuku;
    private RecyclerView rvListbuku, rvTindakanbuku,rvRekomendasi;
    private ImageView img_more;
    private TextView tv_more;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LottieAnimationView animationView1,animationView2,animationView3;
    SesionManager sesionManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sesionManager = new SesionManager(getActivity());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_more = view.findViewById(R.id.ic_more);
        tv_more = view.findViewById(R.id.lihat_lainnya);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        animationView1=view.findViewById(R.id.lottie_emptytindakanCard);
        animationView2=view.findViewById(R.id.lottie_emptyHistorycard);
        animationView3=view.findViewById(R.id.lottie_emptyrekomendasiCard);
        animationView1.setScale(0.2f);
        animationView2.setScale(0.2f);
        animationView3.setScale(0.2f);
        rvListbuku = view.findViewById(R.id.rv_HistoryHome);
        rvListbuku.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        historyAdapter = new HistoryAdapter(getContext());
        rvListbuku.setAdapter(historyAdapter);
        //batas
        rvTindakanbuku = view.findViewById(R.id.rv_tindakanHome);
        rvTindakanbuku.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        TindakanAdapterCard = new tindakanAdapterCard();
        rvTindakanbuku.setAdapter(TindakanAdapterCard);
        //batas
        rvRekomendasi = view.findViewById(R.id.rv_Rekomendasi);
        rvRekomendasi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rekomendasiAdapter = new rekomendasiAdapter(getContext());
        rvRekomendasi.setAdapter(rekomendasiAdapter);
        // Fetch history data
        fetchTindakanData(NIS);
        fetchHistoryData(NIS);
        loadRekomendasi();
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with 'daftar_buku' fragment
                Fragment daftarTindakanFragment = new memerlukan_tindakan();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_main, daftarTindakanFragment)
                        .commit();
            }
        });
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true); // menampilkan progress bar
                fetchTindakanData(NIS);
                fetchHistoryData(NIS);
                loadRekomendasi();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view; 
    }

    private void fetchHistoryData(String nis) {
        animationView2.setVisibility(View.VISIBLE);
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
                        animationView2.setVisibility(View.INVISIBLE);


                    } else {
                        Toast.makeText(getActivity(), "Ayo pinjam buku sekarang", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                animationView2.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void fetchTindakanData(String nis) {
        animationView1.setVisibility(View.VISIBLE);
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
                        animationView1.setVisibility(View.INVISIBLE);

                    } else {

                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Tindakan> call, Throwable t) {
                animationView1.setVisibility(View.INVISIBLE);

            }

        });

    }
    private void loadRekomendasi() {
        animationView3.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RateBuku> call = apiInterface.getStatistikbuku();
        call.enqueue(new Callback<RateBuku>() {
            @Override
            public void onResponse(Call<RateBuku> call, Response<RateBuku> response) {
                if (response.isSuccessful()) {
                    RateBuku rateBuku = response.body();
                    if (rateBuku != null && rateBuku.getData()!= null) {
                        List<RateBukuData> rateBukudata = rateBuku.getData();
                        rekomendasiAdapter.setData(rateBukudata);
                        rekomendasiAdapter.notifyDataSetChanged();
                        animationView3.setVisibility(View.INVISIBLE);
                    }
                }else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RateBuku> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                animationView3.setVisibility(View.INVISIBLE);

            }
        });
    }


}