package com.libman.model.history;

import com.google.gson.annotations.SerializedName;

public class HistoryData {

    @SerializedName("id_buku")
    private String id_buku;
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

    @SerializedName("tanggal_pengembalian")
    private String tanggalPengembalian;

    public void setId_buku(String id_buku) {
        this.id_buku = id_buku;
    }

    public String getId_buku() {
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
    }
}