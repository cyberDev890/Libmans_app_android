package com.libman.controller;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.model.history.HistoryData;
import com.libman.ui.detail_buku;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
private  final Context context;

    private List<HistoryData> historyDataList;

    public HistoryAdapter(Context context) {
        this.context = context;
        this.historyDataList = new ArrayList<>();
    }

    public void setData(List<HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_buku, parent, false);


        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String judul = historyDataList.get(position).getJudulBuku();
        String semester = historyDataList.get(position).getSemester();
        String penerbit = historyDataList.get(position).getPenerbit();
        String jumlah = historyDataList.get(position).getJumlah();
        HistoryData historyData = historyDataList.get(position);
        holder.txtJudul.setText(historyData.getJudulBuku());
        holder.txtSemester.setText("Semester: " + historyData.getSemester());
        holder.txtTgl.setText(historyData.getTanggalPengembalian());
        String linkgbr = historyDataList.get(position).getGambar();

        // Load image using Glide or any other image loading library
        String imageUrl = "https://laravel.yoganova.my.id/assets/upload/" + linkgbr;
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .override(1500, 1500)
                .into(holder.imgBuku);

        holder.imgBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_detail_buku);
                ImageView book = dialog.findViewById(R.id.img_bukuDetail);
                TextView txtJudul = dialog.findViewById(R.id.Judul_bukuDetail);
                TextView txtSemester = dialog.findViewById(R.id.semester_detail);
                TextView txtPenerbit = dialog.findViewById(R.id.penerbit_detail);
                TextView txtJumlah = dialog.findViewById(R.id.jumlah_bukuDetail);
                txtJumlah.setText(jumlah);
                txtPenerbit.setText(penerbit);
                txtSemester.setText(semester);
                txtJudul.setText(judul);

                Glide.with(dialog.getContext())
                        .load(imageUrl)
                        .override(1500, 1500)
                        .into(book);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.getWindow().setGravity(Gravity.CENTER);
                // Navigate to detail book page, passing the book ID
//                Intent intent = new Intent(v.getContext(), detail_buku.class);
//                intent.putExtra("id_buku", historyData.getId_buku());
//                intent.putExtra("judul_buku", historyData.getJudulBuku());
//                intent.putExtra("gambar", historyData.getGambar());
//                intent.putExtra("tanggal_pengembalian", historyData.getTanggalPengembalian());
//
//                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return historyDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imgBuku;
        TextView txtJudul, txtSemester, txtTgl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBuku = itemView.findViewById(R.id.img_buku);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtSemester = itemView.findViewById(R.id.txt_semester);
            txtTgl = itemView.findViewById(R.id.txt_tgl);
        }
    }
}
