package com.libman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class customer_service extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customerservice_screen);
        text = findViewById(R.id.text2);
        text.setText("Jika mengalami kendala dalam penggunaan aplikasi\n" +
                "anda dapat menghubungi beberapa CS berikut :\n\n" +
                "No :\n" +
                "+62 856 9555 0432 ( GG Project Company )\n" +
                " \n\n" +
                "Website :\n" +
                "www.ggproject.com\n" +
                "\n\n" +
                "Email :\n" +
                "ggproject@gmail.com");
        String[] para = text.getText().toString().split("\r\r ");
        Toast.makeText(customer_service.this, "" + para.length, Toast.LENGTH_LONG).show();
    }
}