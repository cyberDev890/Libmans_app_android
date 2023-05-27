package com.libman.sesion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.libman.model.login.LoginData;

import java.util.HashMap;

public class SesionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    public static final String ISLOGIN = "islogin";
    public static final String NIS = "NIS";
    public static final String Nama_siswa = "nama_siswa";
    public static final String Password = "password";
    public static final String Jenis_kelamin = "jenis_kelamin";
    public static final String Notelp = "notelp";
    public static final String Api_token = "api_token";
    public static final String Gambar = "gambar";
    public static final String Update = "updated_at";
    public static final String id_data_kelas = "id_data_kelas";

    public SesionManager(Context context) {
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSesion(LoginData user) {
        editor.putBoolean(ISLOGIN, true);
        editor.putInt(NIS, user.getNIS());
        editor.putString(Nama_siswa, user.getNamaSiswa());
        editor.putString(Jenis_kelamin, user.getJenisKelamin());
        editor.putString(Api_token, user.getApiToken());
        editor.putString(Update, user.getUpdatedAt());
        editor.putInt(id_data_kelas, user.getIdDataKelas());
        editor.putString(Notelp, user.getNotelp());
        editor.putString(Gambar, (String) user.getGambar());
        editor.commit();
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NIS, String.valueOf(sharedPreferences.getInt(NIS, 0)));
        user.put(Nama_siswa, sharedPreferences.getString(Nama_siswa, null));
        user.put(Jenis_kelamin, sharedPreferences.getString(Jenis_kelamin, null));
        user.put(Api_token, sharedPreferences.getString(Api_token, null));
        user.put(Update, sharedPreferences.getString(Jenis_kelamin, null));
        user.put(Notelp, sharedPreferences.getString(Notelp, null));
        user.put(id_data_kelas, String.valueOf(sharedPreferences.getInt(id_data_kelas, 0)));
        user.put(Gambar, sharedPreferences.getString((String) Gambar, null));
        return user;
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(ISLOGIN, false);
    }


}
