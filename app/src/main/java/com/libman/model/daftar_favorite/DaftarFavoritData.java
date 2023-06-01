package com.libman.model.daftar_favorite;

import com.google.gson.annotations.SerializedName;

public class DaftarFavoritData {

    @SerializedName("nama_buku")
    private String namaBuku;

    @SerializedName("penerbit")
    private String penerbit;

    @SerializedName("semester")
    private String semester;

    @SerializedName("tahun_terima")
    private String tahunTerima;
    @SerializedName("id_buku")
    private int id_buku;

    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("deskripsi")
    private String deskripsi;

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setTahunTerima(String tahunTerima) {
        this.tahunTerima = tahunTerima;
    }

    public String getTahunTerima() {
        return tahunTerima;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
    }

    public void setId_buku(int id_buku) {
        this.id_buku = id_buku;
    }

    public int getId_buku() {
        return id_buku;
    }
}