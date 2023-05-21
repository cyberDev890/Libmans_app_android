
package com.libman.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.model.register.Register;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup_screen extends AppCompatActivity {
    private Button btn_signUptoSignIn;
    private EditText edt_nis, edt_nama, edt_password, edt_notelp, edt_gambar, edt_passwordkonfirmasi;
    private Spinner spinner_tingkatan, spinner_kelas, spinner_jk;
    private String Nis, Nama, Password, Notelp, Gambar, Kelas, Jk, Passwordkonfirmasi;
    ApiInterface apiInterface;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        btn_signUptoSignIn = findViewById(R.id.btn_daftartoLogin);
        edt_nis = findViewById(R.id.edt_nisSingIn);
        edt_nama = findViewById(R.id.edt_namaSingIn);
        edt_password = findViewById(R.id.edt_passwordSingin);
        edt_passwordkonfirmasi = findViewById(R.id.edtKonfirmasiPassword);
        edt_notelp = findViewById(R.id.edt_telpSingIn);
        edt_gambar = findViewById(R.id.edt_gambar);
        spinner_tingkatan = findViewById(R.id.dropdownJenisProfile);
        spinner_kelas = findViewById(R.id.dropdownKelassignIn);
        spinner_jk = findViewById(R.id.dropdownJKsignIn);
        // Mengambil daftar tingkatan yang ada
        ArrayList<String> kelas = getKelas();
        // Membuat adapter untuk Spinner dengan daftar tingkatan
        ArrayAdapter<String> getKelasAdapter = new ArrayAdapter<>(signup_screen.this, android.R.layout.simple_spinner_item, kelas);
        getKelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Mengatur adapter ke Spinner
        spinner_kelas.setAdapter(getKelasAdapter);



        btn_signUptoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nis = edt_nis.getText().toString().trim();
                Nama = edt_nama.getText().toString().trim();
                Password = edt_password.getText().toString().trim();
                Passwordkonfirmasi = edt_passwordkonfirmasi.getText().toString().trim();
                Kelas = spinner_kelas.getSelectedItem().toString().trim();
                Jk = spinner_jk.getSelectedItem().toString().trim();
                Notelp = edt_notelp.getText().toString().trim();
                Gambar = edt_gambar.getText().toString().trim();
                if (Nama.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field nama", Toast.LENGTH_SHORT).show();
                } else if (Nis.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field NIS", Toast.LENGTH_SHORT).show();
                } else if (Kelas.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field kelas", Toast.LENGTH_SHORT).show();
                } else if (Notelp.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field Notelp", Toast.LENGTH_SHORT).show();
                } else if (Jk.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field jenis kelamin", Toast.LENGTH_SHORT).show();
                } else if (Password.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field no password", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(Passwordkonfirmasi)) {
                    Toast.makeText(signup_screen.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                } else if (Nis.isEmpty() && Nama.isEmpty() && Password.isEmpty()
                      && Kelas.isEmpty() && Jk.isEmpty() &&
                        Notelp.isEmpty() && Gambar.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
                } else {
                    register(Nis, Nama, Password, Jk, Notelp, Gambar,Kelas);
                }
            }
        });
    }

    private ArrayList<String> getKelas() {
        // Mendapatkan daftar tingkatan dari sumber data Anda (misalnya, API atau database)
        ArrayList<String> daftarKelas = new ArrayList<>();
        daftarKelas.add("1");
        daftarKelas.add("2");
        daftarKelas.add("3");
        daftarKelas.add("4");
        daftarKelas.add("5");
        daftarKelas.add("6");
        daftarKelas.add("7");
        daftarKelas.add("8");
        daftarKelas.add("9");
        daftarKelas.add("10");
        daftarKelas.add("11");
        daftarKelas.add("12");
        daftarKelas.add("13");
        daftarKelas.add("14");
        daftarKelas.add("15");
        daftarKelas.add("16");
        daftarKelas.add("17");
        // ...
        return daftarKelas;
    }
    private void register(String NIS, String nama, String password,String Jk, String noTelp, String gambar ,String idDatakelas) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> registerCall = apiInterface.registerResponse(NIS,nama,password,Jk,noTelp,gambar,idDatakelas);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    Toast.makeText(signup_screen.this, response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup_screen.this, signin_screen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(signup_screen.this, response.body().getMassage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(signup_screen.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}