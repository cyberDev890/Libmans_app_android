package com.libman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class dashboard extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView ivMenu;
    private ImageView ivMenu2;
    private RecyclerView rvMenu;
    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<Integer> image = new ArrayList<>();
    private drawerAdapater adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);
        drawerLayout = findViewById(R.id.drawer_layout);
        ivMenu = findViewById(R.id.ivMenu);
        rvMenu = findViewById(R.id.rvMenu);

        arrayList.clear();

        arrayList.add("Memerlukan Tindakan");
        arrayList.add("Daftar favorite");
        arrayList.add("Costumer Service");
        arrayList.add("History Peminjaman");
        arrayList.add("Tentang Aplikasi");
        arrayList.add("Keluar");


        image.add(R.drawable.privasi);
        image.add(R.drawable.tindakan);
        image.add(R.drawable.daftar_favorite);
        image.add(R.drawable.history);
        image.add(R.drawable.cs);
        image.add(R.drawable.tentang_aplikasi);
        image.add(R.drawable.logout);

        adapter = new drawerAdapater(this, arrayList, image);

        rvMenu.setLayoutManager(new LinearLayoutManager(this));


        rvMenu.setAdapter(adapter);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}