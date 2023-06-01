package com.libman.model.RateBuku;

import com.google.gson.annotations.SerializedName;

public class RateBukuData {

	@SerializedName("penerbit")
	private String penerbit;

	@SerializedName("id_buku")
	private int idBuku;

	@SerializedName("semester")
	private String semester;

	@SerializedName("persentase_peminjaman")
	private String persentasePeminjaman;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("tahun_terima")
	private String tahunTerima;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("judul_buku")
	private String judulBuku;

	public void setPenerbit(String penerbit){
		this.penerbit = penerbit;
	}

	public String getPenerbit(){
		return penerbit;
	}

	public void setIdBuku(int idBuku){
		this.idBuku = idBuku;
	}

	public int getIdBuku(){
		return idBuku;
	}

	public void setSemester(String semester){
		this.semester = semester;
	}

	public String getSemester(){
		return semester;
	}

	public void setPersentasePeminjaman(String persentasePeminjaman){
		this.persentasePeminjaman = persentasePeminjaman;
	}

	public String getPersentasePeminjaman(){
		return persentasePeminjaman;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setTahunTerima(String tahunTerima){
		this.tahunTerima = tahunTerima;
	}

	public String getTahunTerima(){
		return tahunTerima;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setJudulBuku(String judulBuku){
		this.judulBuku = judulBuku;
	}

	public String getJudulBuku(){
		return judulBuku;
	}
}