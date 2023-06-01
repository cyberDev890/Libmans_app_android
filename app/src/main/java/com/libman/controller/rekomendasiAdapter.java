package com.libman.controller;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.api.endpointUrl;
import com.libman.model.RateBuku.RateBukuData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class rekomendasiAdapter extends RecyclerView.Adapter<rekomendasiAdapter.ViewHolder> {
    private  final Context context;

    private List<RateBukuData> rateBukuDataList;

    public rekomendasiAdapter(Context context) {
        this.context = context;
        this.rateBukuDataList = new ArrayList<>();
    }

    public void setData(List<RateBukuData> RateBukuDataList) {
        this.rateBukuDataList = RateBukuDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_rekomendasi, parent, false);


        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RateBukuData rateBukuData = rateBukuDataList.get(position);
        String judul = rateBukuDataList.get(position).getJudulBuku();
        String semester = rateBukuDataList.get(position).getSemester();
        String jumlah = rateBukuDataList.get(position).getPersentasePeminjaman();

        holder.txtJudul.setText(rateBukuData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + rateBukuData.getSemester());
        holder.txtRate.setText("Rating: " + rateBukuData.getPersentasePeminjaman());
        String linkgbr = rateBukuDataList.get(position).getGambar();

        // Load image using Glide or any other image loading library
        String imageUrl = endpointUrl.BASE_URL_IMAGE + linkgbr;
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);

        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked book data

                // Create an intent to navigate to the detail book page
                Intent intent = new Intent(v.getContext(), detail_buku.class);
                intent.putExtra("id_buku", rateBukuData.getIdBuku());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rateBukuDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imgBuku;
        TextView txtJudul, txtSemester, txtRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.img_bukuRekomendasi);
            txtJudul = itemView.findViewById(R.id.txt_judulRekomendasi);
            txtSemester = itemView.findViewById(R.id.txt_semesterRekomendasi);
            txtRate = itemView.findViewById(R.id.Rate);
        }
    }
}
