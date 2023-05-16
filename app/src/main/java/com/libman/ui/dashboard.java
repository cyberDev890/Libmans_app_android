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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

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
        navigationView = findViewById(R.id.nav_view);
        fragmentR(new home());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.icon));
        drawerLayout.addDrawerListener(toggle);
//        sesionManager = new SesionManager(dashboard.this);
//        if (!sesionManager.isLogin()) {
//        moveTologin();
//        }
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
                        toolbar1.setText("      Tentang Aplikasi");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.ic_logout:
                        // tambahkan kode untuk logout dari aplikasi di sini
                        Intent intent = new Intent(dashboard.this, signin_screen.class);
                        startActivity(intent);
                        finish(); // tambahkan kode untuk mengakhiri activity
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
