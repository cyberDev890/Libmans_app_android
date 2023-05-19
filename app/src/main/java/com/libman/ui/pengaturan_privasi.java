package com.libman.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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

import java.io.File;
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

    private ImageView imageView;
    private FloatingActionButton pickImageButton;
    private SesionManager sesionManager;
    private EditText edt_NIS, edt_noTelp;
    private TextView txt_Nama;
    private Spinner spinner_jk, spinner_kelas;
    private Button btn_simpan;
    private ApiInterface apiInterface;
    private Uri imageUri;

    public pengaturan_privasi() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @SuppressLint("MissingInflatedId")
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

        // Mengambil daftar tingkatan yang ada
        ArrayList<String> daftarTingkatan = getDaftarTingkatan();
        // Membuat adapter untuk Spinner dengan daftar tingkatan
        ArrayAdapter<String> tingkatanAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, daftarTingkatan);
        tingkatanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Mengatur adapter ke Spinner
        spinner_jk.setAdapter(tingkatanAdapter);

        // Mengambil daftar tingkatan yang ada
        ArrayList<String> JenisKelamin = getJk();
        // Membuat adapter untuk Spinner dengan daftar tingkatan
        ArrayAdapter<String> getJkAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, JenisKelamin);
        getJkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Mengatur adapter ke Spinner
        spinner_kelas.setAdapter(getJkAdapter);

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PICK_IMAGE_REQUEST);
                }
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanProfile();
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
        ArrayList<String> daftarJk= new ArrayList<>();
        daftarJk.add("Laki-Laki");
        daftarJk.add("Perempuan");

        // ...
        return daftarJk;
    }

    private void pickImage() {
        ImagePicker.with(pengaturan_privasi.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            // Mengambil URI gambar yang dipilih
            imageUri = data.getData();
            // Menampilkan gambar ke ImageView
            imageView.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(getActivity(), "Izin akses penyimpanan ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void simpanProfile() {
        if (imageUri != null) {
            // Mengambil path file gambar dari URI
            String imagePath = getRealPathFromURI(imageUri);
            // Membuat objek File dari path gambar
            File imageFile = new File(imagePath);

            // Mengecek apakah file gambar ada dan valid
            if (imageFile.exists() && imageFile.isFile()) {
                // Membuat objek RequestBody untuk mengirim file gambar
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

                // Mengambil data-data lain yang akan disimpan dalam profile
                String nis = edt_NIS.getText().toString().trim();
                String noTelp = edt_noTelp.getText().toString().trim();
                String tingkatan = spinner_jk.getSelectedItem().toString().trim();
                String kelas = spinner_kelas.getSelectedItem().toString().trim();

                // Membuat objek RequestBody untuk mengirim data-data lain
                RequestBody nisBody = RequestBody.create(MediaType.parse("text/plain"), nis);
                RequestBody noTelpBody = RequestBody.create(MediaType.parse("text/plain"), noTelp);
                RequestBody tingkatanBody = RequestBody.create(MediaType.parse("text/plain"), tingkatan);
                RequestBody kelasBody = RequestBody.create(MediaType.parse("text/plain"), kelas);

                // Mengirim request ke server menggunakan Retrofit
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<Profile> call = apiInterface.profileResponse(nisBody, noTelpBody, tingkatanBody, kelasBody, imagePart);
                call.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Tanggapan sukses dari server
                            Profile profile = response.body();
                            Toast.makeText(getActivity(), "Profil berhasil disimpan", Toast.LENGTH_SHORT).show();
                        } else {
                            // Tanggapan gagal dari server
                            Toast.makeText(getActivity(), "Gagal mengunggah profil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        // Kegagalan dalam melakukan request
                        Toast.makeText(getActivity(), "Gagal mengunggah profil: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getActivity(), "File gambar tidak valid", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;

        try {
            cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return filePath;
    }

}
