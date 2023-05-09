package com.libman;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class tentang_aplikasi extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tentangaplikasi_screen);
        text = findViewById(R.id.text1);
        text.setText("Libman School merupakan sebuah aplikasi manajemen perpustakaan yang berfungsi untuk memudahkan penggunanya untuk mengatur segala aktivitas di dalam perpustakaan secara cepat dan efisien.  Libman School berasal dari pengertian dari keseluruhan fungsi aplikasi ini, " +
                "Kata awal “Lib” kami ambil dari kata “Library” yang berarti perpustakaan dan “Man” kami ambil dari kata “Management” " +
                "yang berarti pengelolaan. \n\n" +
                "Aplikasi ini kami rancang dengan dua jenis versi yaitu mobile dan website, " +
                "Keduanya memiliki fungsi berbeda namun saling melengkapi. " +
                "Website digunakan oleh admin untuk melakukan manajemen dan mengolah data dalam perpustakaan, " +
                "sedangkan Mobile diperuntukan untuk pengguna kalangan umum untuk melakukan " +
                "transasksi peminjaman dan pengembalian buku \n\n" +
                "Selain fitur utama dalam website yang disebutkan di atas, kami juga menyediakan " +
                "bebagai fitur yang dapat memudahkan dan mempercepat kineja admin dalam mengelola data " +
                "seperti eksport dan import dari file PDF, Ecel, atau TXT, Scan Barcode cerdas menggunakan " +
                "kartu perpustakaan dalam melakukan Transaksi, Selain itu admin juga dapat mengedit database " +
                "secara real time dengan mudah dan cepat. Dalam versi mobile juga memiliki berbagai fitur " +
                "yang menarik dan mudah digunakan oleh pengguna, seperti fitur pemberithuan memerlukan " +
                "tindakan yang berfungsi untuk memperlihatkan pengguna mengenai status batas waktu " +
                "peminjaman agar pengguna tidak melewati batas peminjaman buku. Selain itu juga ada " +
                "fitur tambah favorite yang berfungsi untuk menambahkan buku ke daftar favorite sebagai " +
                "pengingat buku yang akan dipinjam di kemudian hari tanpa harus mencarinya kembali.");
        String[] para = text.getText().toString().split("\r\r ");
        Toast.makeText(tentang_aplikasi.this, "" + para.length, Toast.LENGTH_LONG).show();
    }
}
