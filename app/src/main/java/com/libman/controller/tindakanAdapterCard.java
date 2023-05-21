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
import com.libman.model.memerlukan_tindakan.TindakanData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class tindakanAdapterCard extends RecyclerView.Adapter<tindakanAdapterCard.ViewHolder> {

    private List<TindakanData> tindakanDataList;

    public tindakanAdapterCard() {
        this.tindakanDataList = new ArrayList<>();
    }

    public void setData(List<TindakanData> tindakanDataList) {
        this.tindakanDataList = tindakanDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kotak_tindakan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TindakanData tindakanData = tindakanDataList.get(position);
        holder.txtJudul.setText(tindakanData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + tindakanData.getSemester());
        holder.txtTanggal.setText("Status: kembalikan sebelum " + tindakanData.getTanggalPengembalian());

    }

    @Override
    public int getItemCount() {
        return Math.min(tindakanDataList.size(), 2);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtJudul, txtSemester, txtTanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txt_judultindakan);
            txtSemester = itemView.findViewById(R.id.semesterTindakan);
            txtTanggal = itemView.findViewById(R.id.txt_status);
        }
    }
}
