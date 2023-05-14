package com.libman;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.libman.controller.card_buku;

import java.util.ArrayList;

public class list_bukuAdapter extends RecyclerView.Adapter<list_bukuAdapter.ViewHolder> {
    ArrayList<card_buku> items;
    Context context;

    public list_bukuAdapter(ArrayList<card_buku> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_buku, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_judul.setText(items.get(position).getJudul());
        holder.txt_semester.setText(items.get(position).getSemester());
        int drawableResourceId = context.getResources().getIdentifier(items.get(position).getImgUrl(), "drawable", context.getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).transform(new GranularRoundedCorners(30, 30, 0, 0)).into(holder.img_buku);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_judul, txt_semester;
        ImageView img_buku;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_judul = itemView.findViewById(R.id.txt_judul);
            txt_semester = itemView.findViewById(R.id.txt_semester);
            img_buku = itemView.findViewById(R.id.img_buku);
        }
    }

}
