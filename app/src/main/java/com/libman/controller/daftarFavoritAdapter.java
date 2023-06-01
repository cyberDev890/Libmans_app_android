package com.libman.controller;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.api.endpointUrl;
import com.libman.model.daftar_favorite.DaftarFavoritData;
import com.libman.model.daftarbuku.DaftarBukuData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;


public class daftarFavoritAdapter extends RecyclerView.Adapter<daftarFavoritAdapter.ViewHolder> implements Filterable {

    private List<DaftarFavoritData> DaftarFavoritDataList;
    private List<DaftarFavoritData> filteredList;


    public daftarFavoritAdapter() {
        this.DaftarFavoritDataList = new ArrayList<>();
        this.filteredList = new ArrayList<>();

    }

    public void setData(List<DaftarFavoritData> DaftarFavoritDataList) {
        this.DaftarFavoritDataList = DaftarFavoritDataList;
        this.filteredList = DaftarFavoritDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_daftar_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DaftarFavoritData daftarFavoritData = filteredList.get(position);
        holder.txtJudul.setText(daftarFavoritData.getNamaBuku());
        holder.txtSemester.setText("Semester: " + daftarFavoritData.getSemester());
        holder.txtPenerbit.setText("Penerbit: " + daftarFavoritData.getPenerbit());
        holder.txtTahun.setText("Tahun Terbit: " + daftarFavoritData.getTahunTerima());

        String imageUrl = endpointUrl.BASE_URL_IMAGE + daftarFavoritData.getGambar();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);
        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detail_buku.class);
                intent.putExtra("id_buku", daftarFavoritData.getId_buku());
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keyword = charSequence.toString().toLowerCase().trim();
                List<DaftarFavoritData> filteredData = new ArrayList<>();

                if (keyword.isEmpty()) {
                    filteredData = DaftarFavoritDataList;
                } else {
                    for (DaftarFavoritData data : DaftarFavoritDataList) {
                        if (data.getNamaBuku().toLowerCase().contains(keyword)) {
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
                filteredList = (List<DaftarFavoritData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
