package com.libman.api;

import com.libman.model.login.Login;
import com.libman.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login2.php")
        // Sesuaikan dengan path ke login.php pada API Anda
    Call<Login> loginResponse(
            @Field("NIS") String nis,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("register2.php")
        // Sesuaikan dengan path ke login.php pada API Anda
    Call<Register> registerResponse(
            @Field("NIS") String nis,
            @Field("nama_siswa") String nama_siswa,
            @Field("password") String password,
            @Field("tingkatan") String tingkatan,
            @Field("kelas") String kelas,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("notelp") String notelp,
            @Field("gambar") String gambar);

}
