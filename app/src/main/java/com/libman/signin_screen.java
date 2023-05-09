
package com.libman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class signin_screen extends AppCompatActivity {
    private EditText edittextPassword, editTextNim;
    private TextView textView5;
    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_screen);
        edittextPassword = findViewById(R.id.editTextPassword);
        textView5 = findViewById(R.id.textView5);
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

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signup_screen.class);
                startActivity(intent);
            }
        });
    }
}