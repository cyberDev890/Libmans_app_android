package com.libman.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.model.history.HistoryData;

public class detail_buku extends AppCompatActivity {
    private ImageView imgBuku;
    private TextView txtJudul, txtJumlah, txtPengarang,txtSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);
        imgBuku = findViewById(R.id.img_bukuDetail);
        txtJudul = findViewById(R.id.Judul_bukuDetail);
        txtJumlah = findViewById(R.id.jumlah_buku);
        txtSemester = findViewById(R.id.semester_detail);
        txtPengarang = findViewById(R.id.penerbit_detail);

        // Get the historyData object from the intent
        Intent intent = getIntent();
        if (intent.hasExtra("historyData")) {
            HistoryData historyData = intent.getParcelableExtra("historyData");
            // Set the book information to the views
            txtJudul.setText(historyData.getJudulBuku());
            txtSemester.setText(historyData.getSemester());
            txtPengarang.setText(historyData.getPenerbit());

            // Load image using Glide or any other image loading library
            String imageUrl = "https://5c87-103-176-143-44.ngrok-free.app/api/uploads/";
            Glide.with(this)
                    .load(imageUrl)
                    .override(1500, 1500)
                    .into(imgBuku);
        }
    }
        }




