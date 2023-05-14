
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

import com.libman.R;

public class signin_screen extends AppCompatActivity {
    private EditText edittextPassword, editTextNis;
    private TextView btn_signUp;
    private Button btn_signIn;
    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_screen);
        editTextNis = findViewById(R.id.edt_nis);
        edittextPassword = findViewById(R.id.edt_pw);
        btn_signUp = findViewById(R.id.btn_daftar);
        btn_signIn = findViewById(R.id.btn_masuk);
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

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signup_screen.class);
                startActivity(intent);
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), dashboard.class);
                startActivity(intent);
            }
        });
    }
}