
package com.libman.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.model.login.Login;
import com.libman.model.login.LoginData;
import com.libman.model.register.Register;
import com.libman.model.register.RegisterData;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup_screen extends AppCompatActivity {
    private Button btn_signUptoSignIn;
    private EditText edt_nis, edt_nama, edt_password, edt_notelp, edt_gambar, edt_passwordkonfirmasi;
    private Spinner spinner_tingkatan, spinner_kelas, spinner_jk;
    private String Nis, Nama, Password, Notelp, Gambar, Tingkatan, Kelas, Jk, Passwordkonfirmasi;
    ApiInterface apiInterface;

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
        spinner_tingkatan = findViewById(R.id.dropdownTingkatansingIn);
        spinner_kelas = findViewById(R.id.dropdownKelassignIn);
        spinner_jk = findViewById(R.id.dropdownJKsignIn);
        btn_signUptoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nis = edt_nis.getText().toString().trim();
                Nama = edt_nama.getText().toString().trim();
                Password = edt_password.getText().toString().trim();
                Passwordkonfirmasi = edt_passwordkonfirmasi.getText().toString().trim();
                Tingkatan = spinner_tingkatan.getSelectedItem().toString().trim();
                Kelas = spinner_kelas.getSelectedItem().toString().trim();
                Jk = spinner_jk.getSelectedItem().toString().trim();
                Notelp = edt_notelp.getText().toString().trim();
                Gambar = edt_gambar.getText().toString().trim();
                if (Nama.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field nama", Toast.LENGTH_SHORT).show();
                } else if (Nis.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field NIS", Toast.LENGTH_SHORT).show();
                } else if (Tingkatan.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field password", Toast.LENGTH_SHORT).show();
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
                } else if (Nis.isEmpty() && Nama.isEmpty() && Password.isEmpty() &&
                        Tingkatan.isEmpty() && Kelas.isEmpty() && Jk.isEmpty() &&
                        Notelp.isEmpty() && Gambar.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
                } else {
                    register(Nis, Nama, Password, Tingkatan, Kelas, Jk, Notelp, Gambar);
                }
            }
        });
    }

    private void register(String NIS, String nama, String password, String tingkatan, String kelas, String Jk, String noTelp, String gambar) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> registerCall = apiInterface.registerResponse(NIS, nama, password, tingkatan, kelas, Jk, noTelp, gambar);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    RegisterData Logindata = response.body().getRegisterData();
                    Toast.makeText(signup_screen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup_screen.this, signin_screen.class);
                    startActivity(intent);
                    finish();
//                    sesionManager = new SesionManager(signin_screen.this);
////                    sesionManager.createLoginSesion(Logindata);
                } else {
                    Toast.makeText(signup_screen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(signup_screen.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}