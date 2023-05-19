package com.libman.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.libman.sesion.SesionManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class dashboard extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    SesionManager sesionManager;
    ImageView img_view;
    TextView txt_name, txt_nis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        TextView toolbar1 = findViewById(R.id.toolbar_title);
        sesionManager = new SesionManager(this);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        txt_name = headerView.findViewById(R.id.txt_nama_profile);
        String nama = sesionManager.getUserDetail().get(SesionManager.Nama_siswa);
        txt_name.setText(nama);
        ImageView imgProfile = headerView.findViewById(R.id.img_profile);
// Dapatkan URL gambar profil dari sesionManager
        String baseUrl = "http://192.168.1.12/api_libman/uploads/";
        String gambarUrl = sesionManager.getUserDetail().get(SesionManager.Gambar);
        String fullUrl = baseUrl + gambarUrl;

// Konfigurasikan RequestOptions untuk memberikan nilai awalan URL
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person);

// Muat gambar menggunakan Glide dengan RequestOptions
        Glide.with(this)
                .load(fullUrl)
                .apply(options)
                .into(imgProfile);
        fragmentR(new home());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.icon));
        drawerLayout.addDrawerListener(toggle);
        sesionManager = new SesionManager(dashboard.this);
        if (!sesionManager.isLogin()) {
            moveTologin();
        }
        System.out.println("gambarUrl = " + gambarUrl);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.ic_home:
                        fragmentR(new home());
                        toolbar1.setText("");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_privasi:
                        fragmentR(new pengaturan_privasi());
                        toolbar1.setText("Pengaturan Privasi");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_tindakan:
                        fragmentR(new memerlukan_tindakan());
                        toolbar1.setText("Memerlukan Tindakan");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_favorite:
                        fragmentR(new daftar_favorite());
                        toolbar1.setText("Daftar Favorite");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_history:
                        fragmentR(new history_peminjaman());
                        toolbar1.setText("History Peminjaman");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_cs:
                        fragmentR(new costumer_servis());
                        toolbar1.setText("Customer Service");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ic_tentang:
                        fragmentR(new tentang_aplikasi());
                        toolbar1.setText("Tentang Aplikasi");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.ic_logout:
                        sesionManager.logoutSession();
                        moveTologin();
                        break;


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }

    private void moveTologin() {
        Intent intent = new Intent(dashboard.this, signin_screen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
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
        fragmentTransaction.replace(R.id.framelayout_main, fragment);
        fragmentTransaction.commit();
    }
}
