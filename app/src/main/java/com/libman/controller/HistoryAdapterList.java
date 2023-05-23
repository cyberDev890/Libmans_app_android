package com.libman.controller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.model.history.HistoryData;
import com.libman.sesion.SesionManager;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapterList extends RecyclerView.Adapter<HistoryAdapterList.ViewHolder> {
    SesionManager sesionManager;

    private List<HistoryData> historyDataListdaftar;

    public HistoryAdapterList() {
        this.historyDataListdaftar = new ArrayList<>();
    }

    public void setData(List<HistoryData> historyDataListdaftar) {
        this.historyDataListdaftar = historyDataListdaftar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history_peminjaman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryData historyData = historyDataListdaftar.get(position);
        holder.txtJudul.setText(historyData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + historyData.getSemester());
        holder.txtTgl.setText("Dikembalikan pada tanggal: "+historyData.getTanggalPengembalian());

        // Load image using Glide or any other image loading library
        String imageUrl = "https://laravel.yoganova.my.id/assets/upload/" + historyData.getGambar();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);

//        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Navigate to detail book page, passing the book ID
//                Intent intent = new Intent(v.getContext(), detail_buku.class);
//                intent.putExtra("id_buku", historyData.getId_buku());
//                v.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return historyDataListdaftar.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       ImageView imgBuku;
        TextView txtJudul, txtSemester, txtTgl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.listImageHistory);
            txtJudul = itemView.findViewById(R.id.listJudulHistory);
            txtSemester = itemView.findViewById(R.id.txt_semesteHistory);
            txtTgl = itemView.findViewById(R.id.liststatusHistory);
        }
    }
}
