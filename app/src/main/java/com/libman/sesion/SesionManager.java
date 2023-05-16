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
    public static final String Tingkatan = "tingkatan";
    public static final String Kelas = "kelas";
    public static final String Jenis_kelamin = "jenis_kelamin";
    public static final String Notelp = "notelp";
    public static final String Gambar = "gambar";

    public SesionManager(Context context) {
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSesion(LoginData user) {
        editor.putBoolean(ISLOGIN, true);
        editor.putString(NIS, user.getNIS());
        editor.putString(Nama_siswa, user.getNamaSiswa());
        editor.putString(Tingkatan, user.getTingkatan());
        editor.putString(Kelas, user.getKelas());
        editor.putString(Jenis_kelamin, user.getJenisKelamin());
        editor.putString(Notelp, user.getNotelp());
        editor.putString(Gambar, user.getGambar().toString());
        editor.commit();
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(NIS, sharedPreferences.getString(NIS, null));
        user.put(Nama_siswa, sharedPreferences.getString(Nama_siswa, null));
        user.put(Tingkatan, sharedPreferences.getString(Tingkatan, null));
        user.put(Kelas, sharedPreferences.getString(Kelas, null));
        user.put(Jenis_kelamin, sharedPreferences.getString(Jenis_kelamin, null));
        user.put(Notelp, sharedPreferences.getString(Notelp, null));
        user.put(Gambar, sharedPreferences.getString(Gambar, null));
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
