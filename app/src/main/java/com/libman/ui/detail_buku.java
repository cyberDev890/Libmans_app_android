package com.libman.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.model.daftar_favorite.DaftarFavorit;
import com.libman.model.daftar_favorite.DaftarFavoritData;
import com.libman.model.daftar_favorite.TambahFavorit;
import com.libman.model.daftarbuku.DaftarBuku;
import com.libman.model.daftarbuku.DaftarBukuData;
import com.libman.api.endpointUrl;
import com.libman.model.history.History;
import com.libman.model.history.HistoryData;
import com.libman.model.memerlukan_tindakan.Tindakan;
import com.libman.model.memerlukan_tindakan.TindakanData;
import com.libman.sesion.SesionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_buku extends AppCompatActivity {
    private ImageView imgBuku;
    SesionManager sesionManager;
    private TextView btnFavorit;
    private TextView txtJudul, txtjudul2,txtJumlah, txtPengarang, txtSemester,txtDeskripsi;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        imgBuku = findViewById(R.id.img_bukuDetail);
        txtJudul = findViewById(R.id.Judul_bukuDetail);
        txtJumlah = findViewById(R.id.jumlah_bukuDetail);
        txtSemester = findViewById(R.id.semester_detail);
        txtPengarang = findViewById(R.id.penerbit_detail);
        btnFavorit = findViewById(R.id.tambahkan_favorite);
        txtjudul2 = findViewById(R.id.Judul_bukuDetail2);
        txtDeskripsi= findViewById(R.id.txt_deskripsi);
        txtDeskripsi.setMovementMethod(new ScrollingMovementMethod());
        txtDeskripsi.setScrollbarFadingEnabled(false);
        sesionManager = new SesionManager(this);
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        loadBookDetailsDaftarBuku();
        loadBookDetailsHistoryCard(NIS);
        loadBookDetailsFavorit(NIS);
        loadBookDetailsTindakan(NIS);

        //Create the Dialog here
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.show_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(detail_buku.this, daftar_favorite.class);
              startActivity(intent);
                dialog.dismiss();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nis = sesionManager.getUserDetail().get(SesionManager.NIS);
                String nama_siswa = sesionManager.getUserDetail().get(SesionManager.Nama_siswa);
                String nama_buku = txtJudul.getText().toString();
                int id_buku = getIntent().getIntExtra("id_buku", -1);
                TambahFavorit(nis, nama_siswa, nama_buku, String.valueOf(id_buku));
                dialog.show(); // Showing the dialog here


            }
        });
    }

    private void TambahFavorit(String nis, String nama_siswa, String nama_buku, String id_buku) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TambahFavorit> call = apiInterface.tambahfavorit(nis, nama_siswa, nama_buku, id_buku);

        call.enqueue(new Callback<TambahFavorit>() {
            @Override
            public void onResponse(Call<TambahFavorit> call, Response<TambahFavorit> response) {
                if (response.isSuccessful()) {
                    TambahFavorit tambahFavorit = response.body();
                    if (tambahFavorit != null) {
                        Toast.makeText(detail_buku.this, tambahFavorit.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.show(); // Showing the dialog here
                    }
                } else {
                    Toast.makeText(detail_buku.this, "Gagal menambahkan buku ke favorit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TambahFavorit> call, Throwable t) {
                Toast.makeText(detail_buku.this, "Gagal menambahkan buku ke favorit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBookDetailsDaftarBuku() {
        Intent intent = getIntent();
        if (intent.hasExtra("id_buku")) {
            int bookId = intent.getIntExtra("id_buku", -1);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<DaftarBuku> call = apiInterface.getDaftarBuku();
            call.enqueue(new Callback<DaftarBuku>() {
                @Override
                public void onResponse(@NonNull Call<DaftarBuku> call, @NonNull Response<DaftarBuku> response) {
                    if (response.isSuccessful()) {
                        DaftarBuku daftarBuku = response.body();
                        if (daftarBuku != null && daftarBuku.getData() != null) {
                            List<DaftarBukuData> dataBuku = daftarBuku.getData();
                            for (DaftarBukuData bookData : dataBuku) {
                                if (bookData.getIdBuku() == bookId) {
                                    setBookDetailsDaftarBuku(bookData);
                                    return;
                                }
                            }
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<DaftarBuku> call, @NonNull Throwable t) {
                    Toast.makeText(detail_buku.this, "Gagal memuat detail buku", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setBookDetailsDaftarBuku(DaftarBukuData bookData) {
        txtJudul.setText(bookData.getJudulBuku());
        txtjudul2.setText(bookData.getJudulBuku());
        txtSemester.setText(" Semester: " + bookData.getSemester());
        txtPengarang.setText(" Penerbit: " + bookData.getPenerbit());
        txtDeskripsi.setText(bookData.getDeskripsi());
        txtJumlah.setText(bookData.getJumlah());

        String imageUrl = endpointUrl.BASE_URL_IMAGE + bookData.getGambar();
        Glide.with(this)
                .load(imageUrl)
                .override(1500, 1500)
                .into(imgBuku);
    }

    private void loadBookDetailsHistoryCard(String nis) {
        Intent intent = getIntent();
        if (intent.hasExtra("id_buku")) {
            int bookId = intent.getIntExtra("id_buku", -1);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<History> call = apiInterface.history(nis);
            call.enqueue(new Callback<History>() {
                @Override
                public void onResponse(@NonNull Call<History> call, @NonNull Response<History> response) {
                    if (response.isSuccessful()) {
                        History daftarHistory = response.body();
                        if (daftarHistory != null && daftarHistory.getData() != null) {
                            List<HistoryData> dataHistory = daftarHistory.getData();
                            for (HistoryData bookData : dataHistory) {
                                if (bookData.getId_buku() == bookId) {
                                    setBookDetailsHistoryCard(bookData);
                                    return;
                                }
                            }
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<History> call, @NonNull Throwable t) {
                    Toast.makeText(detail_buku.this, "Gagal memuat detail buku", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setBookDetailsHistoryCard(HistoryData bookDataHistory) {
        txtJudul.setText(bookDataHistory.getJudulBuku());
        txtjudul2.setText(bookDataHistory.getJudulBuku());
        txtSemester.setText(" Semester: " + bookDataHistory.getSemester());
        txtPengarang.setText(" Penerbit: " + bookDataHistory.getPenerbit());
        txtDeskripsi.setText(bookDataHistory.getDeskripsi());
        txtJumlah.setText(bookDataHistory.getJumlah());

        String imageUrl = endpointUrl.BASE_URL_IMAGE + bookDataHistory.getGambar();
        Glide.with(this)
                .load(imageUrl)
                .override(1500, 1500)
                .into(imgBuku);
    }

    private void loadBookDetailsFavorit(String nis) {
        Intent intent = getIntent();
        if (intent.hasExtra("id_buku")) {
            int bookId = intent.getIntExtra("id_buku", -1);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<DaftarFavorit> call = apiInterface.daftarFavorit(nis);
            call.enqueue(new Callback<DaftarFavorit>() {
                @Override
                public void onResponse(@NonNull Call<DaftarFavorit> call, @NonNull Response<DaftarFavorit> response) {
                    if (response.isSuccessful()) {
                        DaftarFavorit daftarFavorit = response.body();
                        if (daftarFavorit != null && daftarFavorit.getData() != null) {
                            List<DaftarFavoritData> dataFavorit = daftarFavorit.getData();
                            for (DaftarFavoritData bookData : dataFavorit) {
                                if (bookData.getId_buku() == bookId) {
                                    setBookDetailsFavorit(bookData);
                                    return;
                                }
                            }
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<DaftarFavorit> call, @NonNull Throwable t) {
                    Toast.makeText(detail_buku.this, "Gagal memuat detail buku", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setBookDetailsFavorit(DaftarFavoritData bookDatafavorit) {
        txtJudul.setText(bookDatafavorit.getNamaBuku());
        txtjudul2.setText(bookDatafavorit.getNamaBuku());
        txtSemester.setText(" Semester: " + bookDatafavorit.getSemester());
        txtPengarang.setText(" Penerbit: " + bookDatafavorit.getPenerbit());
        txtJumlah.setText(bookDatafavorit.getJumlah());
        txtDeskripsi.setText(bookDatafavorit.getDeskripsi());

        String imageUrl = endpointUrl.BASE_URL_IMAGE + bookDatafavorit.getGambar();
        Glide.with(this)
                .load(imageUrl)
                .override(1500, 1500)
                .into(imgBuku);
    }

    private void loadBookDetailsTindakan(String nis) {
        Intent intent = getIntent();
        if (intent.hasExtra("id_buku")) {
            int bookId = intent.getIntExtra("id_buku", -1);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Tindakan> call = apiInterface.tindakan(nis);
            call.enqueue(new Callback<Tindakan>() {
                @Override
                public void onResponse(Call<Tindakan> call, Response<Tindakan> response) {
                    if (response.isSuccessful()) {
                        Tindakan daftarTindakan = response.body();
                        if (daftarTindakan != null && daftarTindakan.getData() != null) {
                            List<TindakanData> dataFavorit = daftarTindakan.getData();
                            for (TindakanData bookData : dataFavorit) {
                                if (bookData.getIdBuku() == bookId) {
                                    setBookDetailsTindakan(bookData);
                                    return;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Tindakan> call, Throwable t) {

                }
            });
        }
    }

    private void setBookDetailsTindakan(TindakanData bookDataTindakan) {
        txtJudul.setText(bookDataTindakan.getJudulBuku());
        txtjudul2.setText(bookDataTindakan.getJudulBuku());
        txtSemester.setText(" Semester: " + bookDataTindakan.getSemester());
        txtPengarang.setText(" Penerbit: " + bookDataTindakan.getPenerbit());
        txtJumlah.setText(bookDataTindakan.getJumlah());
        txtDeskripsi.setText(bookDataTindakan.getDeskripsi());

        String imageUrl = endpointUrl.BASE_URL_IMAGE + bookDataTindakan.getGambar();
        Glide.with(this)
                .load(imageUrl)
                .override(1500, 1500)
                .into(imgBuku);
    }
}
