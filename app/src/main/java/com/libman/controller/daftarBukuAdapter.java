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
import com.libman.model.daftarbuku.DaftarBukuData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class daftarBukuAdapter extends RecyclerView.Adapter<daftarBukuAdapter.ViewHolder> {

    private List<DaftarBukuData> DaftarBukuDataList;

    public daftarBukuAdapter() {
        this.DaftarBukuDataList = new ArrayList<>();
    }

    public void setData(List<DaftarBukuData> DaftarBukuDataList) {
        this.DaftarBukuDataList = DaftarBukuDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_daftar_buku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DaftarBukuData daftarbukuData = DaftarBukuDataList.get(position);
        holder.txtJudul.setText(daftarbukuData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + daftarbukuData.getSemester());
        holder.txtPenerbit.setText("Penerbit: " + daftarbukuData.getPenerbit());
        holder.txtJumlah.setText("Tersedia: " + daftarbukuData.getJumlah());
        holder.txtTahun.setText("Tahun Terbit: " + daftarbukuData.getTahunTerima());

        // Load image using Glide or any other image loading library
        String imageUrl = "https://0ece-103-176-143-44.ngrok-free.app/uploads/" + daftarbukuData.getGambar();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);

        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to detail book page, passing the book ID
                Intent intent = new Intent(v.getContext(), detail_buku.class);
                intent.putExtra("id_buku", daftarbukuData.getIdBuku());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DaftarBukuDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBuku;
        TextView txtJudul, txtSemester, txtTahun,txtPenerbit,txtJumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.listImageDaftarBuku);
            txtJudul = itemView.findViewById(R.id.listJudulDaftarBuku);
            txtSemester = itemView.findViewById(R.id.txt_semesterDaftarBuku);
            txtTahun = itemView.findViewById(R.id.tahunDaftarbuku);
            txtPenerbit = itemView.findViewById(R.id.penerbitDaftarbuku);
            txtJumlah= itemView.findViewById(R.id.jumlahDaftarBuku);

        }
    }
}
