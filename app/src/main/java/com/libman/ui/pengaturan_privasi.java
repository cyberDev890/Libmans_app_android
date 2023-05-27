package com.libman.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.libman.R;
import com.libman.api.ApiClient;
import com.libman.api.ApiInterface;
import com.libman.api.endpointUrl;
import com.libman.model.daftarkelas.DaftarKelas;
import com.libman.model.daftarkelas.DataItem;
import com.libman.model.profile.Profile;
import com.libman.sesion.SesionManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pengaturan_privasi extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 3;
    private static final int GALLERY_REQUEST_CODE = 4;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private ImageView imageView;
    private FloatingActionButton pickImageButton;
    private SesionManager sesionManager;
    private EditText edt_NIS, edt_noTelp;
    private TextView txt_Nama;
    private Spinner spinner_jk, spinner_kelas;
    private Button btn_simpan;
    private ApiInterface apiInterface;
    private Uri imageUri;
    private List<DataItem> dataItemslist = new ArrayList<>();
    private DataItem dataItem;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengaturan_privasi, container, false);
        imageView = view.findViewById(R.id.img_profileupdate);
        pickImageButton = view.findViewById(R.id.btn_camera);
        btn_simpan = view.findViewById(R.id.btn_simpanProfile);
        txt_Nama = view.findViewById(R.id.txt_namaProfile);
        edt_NIS = view.findViewById(R.id.edt_nisProfile);
        edt_noTelp = view.findViewById(R.id.edt_notelpProfile);
        spinner_kelas = view.findViewById(R.id.dropdownKelasProfile);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        spinner_jk = view.findViewById(R.id.dropdownJenisProfile);
        sesionManager = new SesionManager(getActivity().getApplicationContext());
        String NIS = sesionManager.getUserDetail().get(SesionManager.NIS);
        edt_NIS.setText(NIS);
        edt_NIS.setEnabled(false);
        String telp = sesionManager.getUserDetail().get(SesionManager.Notelp);
        edt_noTelp.setText(telp);
        String nama = sesionManager.getUserDetail().get(SesionManager.Nama_siswa);
        txt_Nama.setText(nama);
        String jk = sesionManager.getUserDetail().get(SesionManager.Jenis_kelamin);
        spinner_jk.setPrompt(jk);
        String kelas = sesionManager.getUserDetail().get(SesionManager.id_data_kelas);
        spinner_kelas.setPrompt(kelas);


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
                .into(imageView);
        // Mengambil daftar tingkatan yang ada
        ArrayList<String> JenisKelamin = getJk();
        // Membuat adapter untuk Spinner dengan daftar tingkatan
        ArrayAdapter<String> getJkAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, JenisKelamin);
        getJkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Mengatur adapter ke Spinner
        spinner_jk.setAdapter(getJkAdapter);
        getDaftarKelas();


        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nis = edt_NIS.getText().toString().trim();
                String noTelp = edt_noTelp.getText().toString().trim();
                String tingkatan = spinner_kelas.getSelectedItem().toString().trim();
                String jk = spinner_jk.getSelectedItem().toString().trim();
                String nama = txt_Nama.getText().toString().trim();
                Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();


                // Mengubah Bitmap menjadi byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();


                // Membuat RequestBody untuk file gambar
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("gambar", "profile.jpg", requestBody);

                // Membuat RequestBody untuk data siswa
                RequestBody nisBody = RequestBody.create(MediaType.parse("text/plain"), nis);
                RequestBody idDataKelasBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(dataItem.getIdDataKelas()));
                RequestBody noTelpBody = RequestBody.create(MediaType.parse("text/plain"), noTelp);
                RequestBody jkBody = RequestBody.create(MediaType.parse("text/plain"), jk);

                // Mengirim permintaan ke API menggunakan Retrofit
                Call<Profile> call = apiInterface.profileResponse(nisBody, idDataKelasBody, noTelpBody, jkBody, filePart);
                call.enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful()) {
                            Profile apiResponse = response.body();
                            if (apiResponse != null && apiResponse.isStatus()) {
                                // Berhasil memperbarui profil siswa
                                Toast.makeText(getActivity(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Gagal memperbarui profil siswa
                                Toast.makeText(getActivity(), "Gagal memperbarui profil siswa", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Gagal memperbarui profil siswa
                            Toast.makeText(getActivity(), "Gagal memperbarui profil siswa", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        // Gagal memperbarui profil siswa
                        Toast.makeText(getActivity(), "Gagal memperbarui profil siswa: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ImagePicker.with(pengaturan_privasi.this)
                            .crop()                    //Crop image(Optional), Check Customization for more option
                            .compress(1024)            //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                            .start();
            }
        });

        // Tambahkan kode berikut untuk mengatur ImageView dengan gambar yang dipilih
        if (imageUri != null) {
            imageView.setImageURI(imageUri);
        }

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        imageUri = uri;
                        imageView.setImageURI(imageUri);
                    }
                }
            } else {
                Toast.makeText(getActivity(), "Camera access denied", Toast.LENGTH_SHORT).show();
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        imageUri = uri;
                        imageView.setImageURI(imageUri);
                    }
                }
            } else {
                Toast.makeText(getActivity(), "Gallery access denied", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    //untuk mengambil data kelas dari server
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
                        ArrayAdapter<String> kelasAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, kelasList);
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
                    Toast.makeText(getActivity(), "Failed to retrieve class list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DaftarKelas> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to retrieve class list: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//untuk mengambil data jenis kelamin hardcode
    private ArrayList<String> getJk() {
        // Mendapatkan daftar tingkatan dari sumber data Anda (misalnya, API atau database)
        ArrayList<String> daftarJk = new ArrayList<>();
        daftarJk.add("Laki-Laki");
        daftarJk.add("Perempuan");

        // ...
        return daftarJk;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ImagePicker.REQUEST_CODE) {
                Uri uri = data.getData();
                imageView.setImageURI(uri);
            }
        }
    }
}
