
package com.libman.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.model.login.Login;
import com.libman.model.login.LoginData;
import com.libman.sesion.SesionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signin_screen extends AppCompatActivity implements View.OnClickListener {
    private EditText edittextPassword, editTextNis;
    private TextView btn_signUp;
    private Button btn_signIn;
    private boolean passwordVisible;
    ApiInterface apiInterface;
    SesionManager sesionManager;
    String Nis, Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_screen);
        editTextNis = findViewById(R.id.edt_nis);
        edittextPassword = findViewById(R.id.edt_pw);
        btn_signUp = findViewById(R.id.btn_daftar);
        btn_signIn = findViewById(R.id.btn_masuk);
        btn_signUp.setOnClickListener(this);
        btn_signIn.setOnClickListener(this);
        edittextPassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final int inType = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= edittextPassword.getRight() - edittextPassword.getCompoundDrawables()[inType].getBounds().width()) {
                        int selection = edittextPassword.getSelectionEnd();
                        if (passwordVisible) {
                            edittextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
                            edittextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            edittextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                            edittextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;

                        }
                        edittextPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_masuk:
                Nis = editTextNis.getText().toString();
                Password = edittextPassword.getText().toString();
                login(Nis, Password);
                break;

            case R.id.btn_daftar:
                Intent intent = new Intent(this, signup_screen.class);
                startActivity(intent);
                break;
        }

    }

    private void login(String nis, String password) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> loginCall = apiInterface.loginResponse(nis, password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body() != null && response.isSuccessful() && response.body().getStatus()) {
                    LoginData logindata = response.body().getData();
                    sesionManager = new SesionManager(signin_screen.this);
                    sesionManager.createLoginSesion(logindata);
                    Toast.makeText(signin_screen.this, response.body().getData().getNamaSiswa(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signin_screen.this, dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(signin_screen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(signin_screen.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}