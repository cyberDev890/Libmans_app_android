package com.libman.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.libman.R;

import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

public class dashboard extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private ImageView ivMenu;
    private ImageView ivMenu2;
    private RecyclerView rvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        fragmentR(new home());
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.icon));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.ic_privasi:
                        fragmentR(new pengaturan_privasi());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_tindakan:
                        fragmentR(new memerlukan_tindakan());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_favorite:
                        fragmentR(new daftar_favorite());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_history:
                        fragmentR(new history_peminjaman());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_cs:
                        fragmentR(new costumer_servis());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_tentang:
                        fragmentR(new tentang_aplikasi());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.ic_logout:
                        // tambahkan kode untuk logout dari aplikasi di sini
                        Intent intent = new Intent(dashboard.this, signin_screen.class);
                        startActivity(intent);
                        finish(); // mengakhiri dashboard activity agar tidak bisa kembali ke tampilan dashboard setelah logout
                        break;


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void fragmentR(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
}
