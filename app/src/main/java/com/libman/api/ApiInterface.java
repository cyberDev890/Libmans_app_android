package com.libman.api;

import com.libman.model.daftar_favorite.DaftarFavorit;
import com.libman.model.daftarbuku.DaftarBuku;
import com.libman.model.history.History;
import com.libman.model.login.Login;
import com.libman.model.memerlukan_tindakan.Tindakan;
import com.libman.model.profile.Profile;
import com.libman.model.register.Register;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
        // Sesuaikan dengan path ke login pada API Anda
    Call<Login> loginResponse(
            @Field("NIS") String nis,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
        // Sesuaikan dengan path ke login.php pada API Anda
    Call<Register> registerResponse(
            @Field("NIS") String nis,
            @Field("nama_siswa") String nama_siswa,
            @Field("password") String password,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("notelp") String notelp,
            @Field("id_data_kelas") String id_data_kelas,
            @Field("gambar") String gambar);

    @Multipart
    @POST("profile")
        // Ganti dengan endpoint PHP Anda
    Call<Profile> profileResponse(
            @Part("NIS") RequestBody NIS,
            @Part("id_data_kelas") RequestBody idDataKelas,
            @Part("jenis_kelamin") RequestBody jenisKelamin,
            @Part("notelp") RequestBody noTelp,
            @Part MultipartBody.Part gambar
    );

    @FormUrlEncoded
    @POST("history")
    Call<History> history(
            @Field("NIS") String nis);
    @FormUrlEncoded
    @POST("daftarfavorit")
    Call<DaftarFavorit> daftarFavorit(
            @Field("NIS") String nis);


    @FormUrlEncoded
    @POST("tindakan")
    Call<Tindakan> tindakan(
            @Field("NIS") String nis);

    @GET("daftarbuku")
    Call<DaftarBuku> getDaftarBuku();

}
