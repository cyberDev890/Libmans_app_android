package com.libman.api;

import com.libman.model.daftar_favorite.DaftarFavorit;
import com.libman.model.daftar_favorite.TambahFavorit;
import com.libman.model.daftarbuku.DaftarBuku;
import com.libman.model.daftarkelas.DaftarKelas;
import com.libman.model.history.History;
import com.libman.model.login.Login;
import com.libman.model.memerlukan_tindakan.Tindakan;
import com.libman.model.profile.Profile;
import com.libman.model.register.Register;
import com.libman.model.sendToken.FcmToken;

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
            @Field("password") String password,
            @Field("fcmToken") String fcmToken
    );

    @FormUrlEncoded
    @POST("register")
        // Sesuaikan dengan path ke login.php pada API Anda
    Call<Register> registerResponse(
            @Field("NIS") String nis,
            @Field("nama_siswa") String nama_siswa,
            @Field("password") String password,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("notelp") String notelp,
            @Field("id_data_kelas") int id_data_kelas,
            @Field("gambar") String gambar);

    @Multipart
    @POST("profile")
        // Ganti dengan endpoint PHP Anda
    Call<Profile> profileResponse(
            @Part("NIS") RequestBody nis,
            @Part("id_data_kelas") RequestBody idDataKelas,
            @Part("notelp") RequestBody noTelp,
            @Part("jenis_kelamin") RequestBody jenisKelamin,
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

    @FormUrlEncoded
    @POST("tambahfavorit")
    Call<TambahFavorit> tambahfavorit(
            @Field("NIS") String nis,
            @Field("nama_siswa") String nama_siswa,
            @Field("nama_buku") String nama_buku,
            @Field("id_buku") String id_buku
    );

    @GET("daftarbuku")
    Call<DaftarBuku> getDaftarBuku();

    @GET("daftarkelas")
    Call<DaftarKelas> getDaftarKelas();

    @FormUrlEncoded
    @POST("sendToken")
    Call<FcmToken> fcmToken(
            @Field("NIS") String nis,
            @Field("fcmToken") String fcmToken
    );

}
