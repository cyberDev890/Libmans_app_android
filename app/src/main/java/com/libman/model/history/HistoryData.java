package com.libman.model.history;

import com.google.gson.annotations.SerializedName;

public class HistoryData {

    @SerializedName("id_buku")
    private int id_buku;
    @SerializedName("semester")
    private String semester;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("penerbit")
    private String penerbit;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("judul_buku")
    private String judulBuku;
    @SerializedName("tanggal_peminjaman")
    private String tanggal_peminjaman;

    @SerializedName("tanggal_pengembalian")
    private String tanggalPengembalian;

    public void setId_buku(int id_buku) {
        this.id_buku = id_buku;
    }

    public int getId_buku() {
        return id_buku;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setTanggalPengembalian(String tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public String getTanggalPengembalian() {
        return tanggalPengembalian;
    }   public void setTanggalPeminjaman(String tanggal_peminjaman) {
        this.tanggal_peminjaman = tanggal_peminjaman;
    }

    public String getTanggalPeminjaman() {
        return tanggal_peminjaman;
    }
}