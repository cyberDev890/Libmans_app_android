

package com.libman.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libman.R;


public class costumer_servis extends Fragment {


    public costumer_servis() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customerservice_screen, container, false);
//        text = findViewById(R.id.text2);
//        text.setText("Jika mengalami kendala dalam penggunaan aplikasi\n" +
//                "anda dapat menghubungi beberapa CS berikut :\n\n" +
//                "No :\n" +
//                "+62 856 9555 0432 ( GG Project Company )\n" +
//                " \n\n" +
//                "Website :\n" +
//                "www.ggproject.com\n" +
//                "\n\n" +
//                "Email :\n" +
//                "ggproject@gmail.com");
//        String[] para = text.getText().toString().split("\r\r ");


    }
}