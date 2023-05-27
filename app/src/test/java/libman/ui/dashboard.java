package libman.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.libman.R;
import com.libman.api.endpointUrl;
import com.libman.sesion.SesionManager;

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
        askNotificationPermission();

        ImageView imgProfile = headerView.findViewById(R.id.img_profile);
// Dapatkan URL gambar profil dari sesionManager
        String baseUrl = endpointUrl.BASE_URL_IMAGE ;
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
                    case R.id.ic_daftarbuku:
                        fragmentR(new daftar_buku());
                        toolbar1.setText("Daftar Buku");
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
    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });
}
