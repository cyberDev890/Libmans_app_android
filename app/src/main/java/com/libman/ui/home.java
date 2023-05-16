package com.libman.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.libman.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import com.libman.controller.card_buku;
import com.libman.list_bukuAdapter;

public class home extends Fragment {
    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<Integer> image = new ArrayList<>();

    private RecyclerView.Adapter adapterListbuku;
    private RecyclerView rvListbuku;
    private ImageView img_more;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initRecyclerView(view);
        img_more = view.findViewById(R.id.ic_more);

        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with 'daftar_buku' fragment
                Fragment daftarBukuFragment = new daftar_buku();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.framelayout_main, daftarBukuFragment)
                        .commit();
            }
        });
        return view;
    }
    private void initRecyclerView(View view) {
        ArrayList<card_buku> items = new ArrayList<>();
        items.add(new card_buku("Pemrograman Berorientasi Objek", "Semester 1", "buku1"));
        items.add(new card_buku("Pemrograman Berorientasi Objek", "Semester 1", "buku2"));
        items.add(new card_buku("Pemrograman Berorientasi Objek", "Semester 1", "buku3"));
        items.add(new card_buku("Pemrograman Berorientasi Objek", "Semester 1", "buku4"));
        items.add(new card_buku("Pemrograman Berorientasi Objek", "Semester 1", "buku5"));
        items.add(new card_buku("Pemrograman Berorientasi Objek", "Semester 1", "buku6"));
        // Get the view root from onCreateView()
        rvListbuku = view.findViewById(R.id.view1);
        rvListbuku.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterListbuku = new list_bukuAdapter(items);
        rvListbuku.setAdapter(adapterListbuku);
    }
}