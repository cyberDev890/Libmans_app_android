package com.libman.api;

import com.libman.model.login.Login;

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
}
