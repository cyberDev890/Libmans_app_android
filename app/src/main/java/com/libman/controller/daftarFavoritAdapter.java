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
import com.libman.model.daftar_favorite.DaftarFavoritData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class daftarFavoritAdapter extends RecyclerView.Adapter<daftarFavoritAdapter.ViewHolder> {

    private List<DaftarFavoritData> DaftarFavoritDataList;

    public daftarFavoritAdapter() {
        this.DaftarFavoritDataList = new ArrayList<>();
    }

    public void setData(List<DaftarFavoritData> DaftarFavoritDataList) {
        this.DaftarFavoritDataList = DaftarFavoritDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_daftar_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DaftarFavoritData daftarFavoritData = DaftarFavoritDataList.get(position);
        holder.txtJudul.setText(daftarFavoritData.getNamaBuku());
        holder.txtSemester.setText("Semester: " + daftarFavoritData.getSemester());
        holder.txtPenerbit.setText("Penerbit: " + daftarFavoritData.getPenerbit());
        holder.txtTahun.setText("Tahun Terbit: " + daftarFavoritData.getTahunTerima());

        // Load image using Glide or any other image loading library
        String imageUrl = "https://0ece-103-176-143-44.ngrok-free.app/uploads/" + daftarFavoritData.getGambar();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);
        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to detail book page, passing the book ID
                Intent intent = new Intent(v.getContext(), detail_buku.class);
                intent.putExtra("id_buku", daftarFavoritData.getId_buku());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return DaftarFavoritDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBuku;
        TextView txtJudul, txtSemester, txtTahun,txtPenerbit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.listImageDaftarFavorit);
            txtJudul = itemView.findViewById(R.id.listJudulDaftarFavorit);
            txtSemester = itemView.findViewById(R.id.txt_semesterDaftarFavorit);
            txtTahun = itemView.findViewById(R.id.tahunDaftarFavorit);
            txtPenerbit = itemView.findViewById(R.id.penerbitDaftarFavorit);
        }
    }
}
