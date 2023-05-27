package libman.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.model.daftarkelas.DaftarKelas;
import com.libman.model.daftarkelas.DataItem;
import com.libman.model.register.Register;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup_screen extends AppCompatActivity {
    private Button btn_signUptoSignIn;
    private EditText edt_nis, edt_nama, edt_password, edt_notelp, edt_gambar, edt_passwordkonfirmasi;
    private Spinner spinner_tingkatan, spinner_kelas, spinner_jk;
    private String Nis, Nama, Password, Notelp, Gambar, Kelas, Jk, Passwordkonfirmasi;
    ApiInterface apiInterface;
    private List<DataItem> dataItemslist = new ArrayList<>();

    private DataItem dataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        btn_signUptoSignIn = findViewById(R.id.btn_daftartoLogin);
        edt_nis = findViewById(R.id.edt_nisSingIn);
        edt_nama = findViewById(R.id.edt_namaSingIn);
        edt_password = findViewById(R.id.edt_passwordSingin);
        edt_passwordkonfirmasi = findViewById(R.id.edtKonfirmasiPassword);
        edt_notelp = findViewById(R.id.edt_telpSingIn);
        edt_gambar = findViewById(R.id.edt_gambar);
        spinner_kelas = findViewById(R.id.dropdownKelassignIn);
        spinner_jk = findViewById(R.id.dropdownJKsignIn);

        btn_signUptoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nis = edt_nis.getText().toString().trim();
                Nama = edt_nama.getText().toString().trim();
                Password = edt_password.getText().toString().trim();
                Passwordkonfirmasi = edt_passwordkonfirmasi.getText().toString().trim();
                Kelas = spinner_kelas.getSelectedItem().toString().trim();
                Jk = spinner_jk.getSelectedItem().toString().trim();
                Notelp = edt_notelp.getText().toString().trim();
                Gambar = edt_gambar.getText().toString().trim();
                if (Nama.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field nama", Toast.LENGTH_SHORT).show();
                } else if (Nis.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field NIS", Toast.LENGTH_SHORT).show();
                } else if (Kelas.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field kelas", Toast.LENGTH_SHORT).show();
                } else if (Notelp.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field Notelp", Toast.LENGTH_SHORT).show();
                } else if (Jk.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field jenis kelamin", Toast.LENGTH_SHORT).show();
                } else if (Password.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi field no password", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(Passwordkonfirmasi)) {
                    Toast.makeText(signup_screen.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                } else if (Nis.isEmpty() && Nama.isEmpty() && Password.isEmpty()
                        && Kelas.isEmpty() && Jk.isEmpty() &&
                        Notelp.isEmpty() && Gambar.isEmpty()) {
                    Toast.makeText(signup_screen.this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
                } else {
                    register(Nis, Nama, Password, Jk, Notelp, Gambar, dataItem.getIdDataKelas());
                }
            }
        });

        // Retrieve class list from the server
        getDaftarKelas();
    }

    private void getDaftarKelas() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DaftarKelas> daftarKelasCall = apiInterface.getDaftarKelas();
        daftarKelasCall.enqueue(new Callback<DaftarKelas>() {
            @Override
            public void onResponse(Call<DaftarKelas> call, Response<DaftarKelas> response) {
                if (response.isSuccessful()) {
                    DaftarKelas daftarKelas = response.body();
                    if (daftarKelas != null && daftarKelas.getData() != null) {
                        dataItemslist = daftarKelas.getData();
                        List<String> kelasList = new ArrayList<>();
                        for (DataItem dataItem : dataItemslist) {
                            kelasList.add(dataItem.getTingkatan() + " " + dataItem.getKelas());
                        }
                        ArrayAdapter<String> kelasAdapter = new ArrayAdapter<>(signup_screen.this, android.R.layout.simple_spinner_item, kelasList);
                        kelasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_kelas.setAdapter(kelasAdapter);
                        spinner_kelas.setOnItemSelectedListener(
                               new AdapterView.OnItemSelectedListener() {
                                   @Override
                                   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                       dataItem = dataItemslist.get(i);
                                   }

                                   @Override
                                   public void onNothingSelected(AdapterView<?> adapterView) {
                                       dataItem = dataItemslist.get(0);
                                   }
                               }
                        );
                    }
                } else {
                    Toast.makeText(signup_screen.this, "Failed to retrieve class list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DaftarKelas> call, Throwable t) {
                Toast.makeText(signup_screen.this, "Failed to retrieve class list: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String NIS, String nama, String password, String Jk, String noTelp, String gambar, int idDatakelas) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> registerCall = apiInterface.registerResponse(NIS, nama, password, Jk, noTelp, idDatakelas, gambar);
        registerCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                    Toast.makeText(signup_screen.this, response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup_screen.this, signin_screen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(signup_screen.this, response.body().getMassage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(signup_screen.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
