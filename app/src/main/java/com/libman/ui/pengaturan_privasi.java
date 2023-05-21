package com.libman.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.net.Uri;
import android.provider.MediaStore;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.model.profile.Profile;
import com.libman.sesion.SesionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pengaturan_privasi extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 3;
    private static final int GALLERY_REQUEST_CODE = 4;

    private ImageView imageView;
    private FloatingActionButton pickImageButton;
    private SesionManager sesionManager;
    private EditText edt_NIS, edt_noTelp;
    private TextView txt_Nama;
    private Spinner spinner_jk, spinner_kelas;
    private Button btn_simpan;
    private ApiInterface apiInterface;
    private Uri imageUri;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengaturan_privasi, container, false);
        imageView = view.findViewById(R.id.img_profileupdate);
        pickImageButton = view.findViewById(R.id.btn_camera);
        btn_simpan = view.findViewById(R.id.btn_simpanProfile);
        txt_Nama = view.findViewById(R.id.txt_namaProfile);
        edt_NIS = view.findViewById(R.id.edt_nisProfile);
        edt_noTelp = view.findViewById(R.id.edt_notelpProfile);
        spinner_kelas = view.findViewById(R.id.dropdownKelasProfile);
        spinner_jk = view.findViewById(R.id.dropdownJenisProfile);
        sesionManager = new SesionManager(getActivity().getApplicationContext());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        edt_NIS.setText(NIS);
        edt_NIS.setEnabled(false);
        String telp = sesionManager.getUserDetail().get(SesionManager.Notelp);
        edt_noTelp.setText(telp);
        String nama = sesionManager.getUserDetail().get(SesionManager.Nama_siswa);
        txt_Nama.setText(nama);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // Mengambil daftar tingkatan yang ada
        ArrayList<String> daftarKelas = getDaftarTingkatan();
        // Membuat adapter untuk Spinner dengan daftar tingkatan
        ArrayAdapter<String> kelasAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, daftarKelas);
        kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Mengatur adapter ke Spinner
        spinner_kelas.setAdapter(kelasAdapter);

        // Mengambil daftar tingkatan yang ada
        ArrayList<String> JenisKelamin = getJk();
        // Membuat adapter untuk Spinner dengan daftar tingkatan
        ArrayAdapter<String> getJkAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, JenisKelamin);
        getJkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Mengatur adapter ke Spinner
        spinner_jk.setAdapter(getJkAdapter);

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Image Source");
                builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                // Memilih gambar dari kamera
                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    pickImageFromCamera();
                                } else {
                                    // Jika izin belum diberikan, minta izin
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
                                }
                                break;
                            case 1:
                                // Memilih gambar dari galeri
                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    pickImageFromGallery();
                                } else {
                                    // Jika izin belum diberikan, minta izin
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
                                }
                                break;
                        }
                    }
                });
                builder.show(); // Menampilkan AlertDialog
            }
        });

// Method untuk mengambil gambar dari kamera


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nis = edt_NIS.getText().toString().trim();
                String noTelp = edt_noTelp.getText().toString().trim();
                String tingkatan = spinner_kelas.getSelectedItem().toString().trim();
                String jk = spinner_jk.getSelectedItem().toString().trim();
                String nama = txt_Nama.getText().toString().trim();
                Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                // Membuat objek RequestBody untuk mengirim data-data lain
                RequestBody nisBody = RequestBody.create(MediaType.parse("text/plain"), nis);
                RequestBody noTelpBody = RequestBody.create(MediaType.parse("text/plain"), noTelp);
                RequestBody tingkatanBody = RequestBody.create(MediaType.parse("text/plain"), tingkatan);
                RequestBody jkBody = RequestBody.create(MediaType.parse("text/plain"), jk);
                RequestBody namaBody = RequestBody.create(MediaType.parse("text/plain"), nama);
                RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), imageBytes);

                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageBody);
                Log.d("POTO", jkBody.toString());
                // Mengirim request ke server menggunakan Retrofit

                Call<Profile> call = apiInterface.profileResponse(nisBody, tingkatanBody, jkBody,noTelpBody,  imagePart);
                call.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            // Tanggapan sukses dari server
                            Profile profile = response.body();
                            Toast.makeText(getActivity(), "Profil berhasil disimpan", Toast.LENGTH_SHORT).show();
                        } else {
                            // Tanggapan gagal dari server
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        // Kegagalan dalam melakukan request
                        Toast.makeText(getActivity(), "Gagal menyimpan profil: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    private ArrayList<String> getDaftarTingkatan() {
        // Mendapatkan daftar tingkatan dari sumber data Anda (misalnya, API atau database)
        ArrayList<String> daftarTingkatan = new ArrayList<>();
        daftarTingkatan.add("1");
        daftarTingkatan.add("2");
        daftarTingkatan.add("3");
        daftarTingkatan.add("4");
        daftarTingkatan.add("5");
        daftarTingkatan.add("6");
        daftarTingkatan.add("7");
        daftarTingkatan.add("8");
        daftarTingkatan.add("9");
        daftarTingkatan.add("10");
        daftarTingkatan.add("11");
        daftarTingkatan.add("12");
        daftarTingkatan.add("13");
        daftarTingkatan.add("14");
        daftarTingkatan.add("15");
        daftarTingkatan.add("16");
        daftarTingkatan.add("17");
        // ...
        return daftarTingkatan;
    }

    private ArrayList<String> getJk() {
        // Mendapatkan daftar tingkatan dari sumber data Anda (misalnya, API atau database)
        ArrayList<String> daftarJk = new ArrayList<>();
        daftarJk.add("Laki-Laki");
        daftarJk.add("Perempuan");

        // ...
        return daftarJk;
    }


    private void pickImageFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    // Method untuk mengambil gambar dari galeri
    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                // Memilih gambar dari kamera
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                // Mengirim data gambar ke API (lakukan implementasi sendiri)
                // sendImageToApi(photo);
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                // Memilih gambar dari galeri
                Uri selectedImage = data.getData();
                try {
                    Bitmap photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    imageView.setImageBitmap(photo);
                    // Mengirim data gambar ke API (lakukan implementasi sendiri)
                    // sendImageToApi(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }








    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin akses penyimpanan diberikan, lanjutkan untuk memilih gambar
                pickImageFromCamera(); // Ganti dengan metode yang sesuai (pickImageFromGallery() atau pickImageFromCamera())
            } else {
                Toast.makeText(getActivity(), "Izin akses penyimpanan ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }






}
