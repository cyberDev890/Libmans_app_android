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
import com.libman.model.history.HistoryData;
import com.libman.sesion.SesionManager;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapterList extends RecyclerView.Adapter<HistoryAdapterList.ViewHolder>implements Filterable {
    SesionManager sesionManager;

    private List<HistoryData> historyDataListdaftar;
    private List<HistoryData> filteredList;

    public HistoryAdapterList() {
        this.historyDataListdaftar = new ArrayList<>();
        this.filteredList = new ArrayList<>();

    }

    public void setData(List<HistoryData> historyDataListdaftar) {
        this.historyDataListdaftar = historyDataListdaftar;
        this.filteredList = historyDataListdaftar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history_peminjaman, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryData historyData = filteredList.get(position);
        holder.txtJudul.setText(historyData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + historyData.getSemester());
        holder.txtTgl.setText("Dikembalikan pada tanggal: " + historyData.getTanggalPengembalian());

        // Load image using Glide or any other image loading library
        String imageUrl = endpointUrl.BASE_URL_IMAGE + historyData.getGambar();
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
                intent.putExtra("id_buku", historyData.getId_buku());
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
        TextView txtJudul, txtSemester, txtTgl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.listImageHistory);
            txtJudul = itemView.findViewById(R.id.listJudulHistory);
            txtSemester = itemView.findViewById(R.id.txt_semesteHistory);
            txtTgl = itemView.findViewById(R.id.liststatusHistory);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keyword = charSequence.toString().toLowerCase().trim();
                List<HistoryData> filteredData = new ArrayList<>();

                if (keyword.isEmpty()) {
                    filteredData = historyDataListdaftar;
                } else {
                    for (HistoryData data : historyDataListdaftar) {
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
                filteredList = (List<HistoryData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
