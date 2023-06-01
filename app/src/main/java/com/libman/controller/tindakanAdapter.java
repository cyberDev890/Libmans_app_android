package com.libman.controller;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.api.endpointUrl;
import com.libman.model.daftar_favorite.DaftarFavoritData;
import com.libman.model.memerlukan_tindakan.TindakanData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class tindakanAdapter extends RecyclerView.Adapter<tindakanAdapter.ViewHolder>implements Filterable {

    private List<TindakanData> tindakanDataList;
    private List<TindakanData> filteredList;

    public tindakanAdapter() {
        this.tindakanDataList = new ArrayList<>();
        this.filteredList = new ArrayList<>();

    }

    public void setData(List<TindakanData> tindakanDataList) {
        this.tindakanDataList = tindakanDataList;
        this.filteredList = tindakanDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tindakan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TindakanData tindakanData = filteredList.get(position);
        holder.txtJudul.setText(tindakanData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + tindakanData.getSemester());
        holder.txtPenerbit.setText("Penerbit: " + tindakanData.getPenerbit());
        holder.txtTahun.setText("Tahun Terbit: " + tindakanData.getTahunterima());
        holder.txtTanggal.setText("Status: kembalikan sebelum " + tindakanData.getTanggalPengembalian());

        // Load image using Glide or any other image loading library
        String imageUrl = endpointUrl.BASE_URL_IMAGE + tindakanData.getGambar();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);
        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to detail book page, passing the book ID
                Intent intent = new Intent(v.getContext(), detail_buku.class);
                intent.putExtra("id_buku", tindakanData.getIdBuku());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBuku;
        TextView txtJudul, txtSemester, txtTahun,txtPenerbit,txtTanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.listImageTindakan);
            txtJudul = itemView.findViewById(R.id.listJudulTindakan);
            txtSemester = itemView.findViewById(R.id.txt_semesterTindakan);
            txtTahun = itemView.findViewById(R.id.tahunTindakan);
            txtPenerbit = itemView.findViewById(R.id.penerbitTindakan);
            txtTanggal = itemView.findViewById(R.id.jumlahTindakan);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keyword = charSequence.toString().toLowerCase().trim();
                List<TindakanData> filteredData = new ArrayList<>();

                if (keyword.isEmpty()) {
                    filteredData = tindakanDataList;
                } else {
                    for (TindakanData data : tindakanDataList) {
                        if (data.getJudulBuku().toLowerCase().contains(keyword)) {
                            filteredData.add(data);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (List<TindakanData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
