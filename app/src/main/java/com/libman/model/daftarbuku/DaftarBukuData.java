package com.libman.model.daftarbuku;

import com.google.gson.annotations.SerializedName;

public class DaftarBukuData {

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("penerbit")
	private String penerbit;

	@SerializedName("id_buku")
	private int idBuku;

	@SerializedName("semester")
	private String semester;

	@SerializedName("tahun_terima")
	private String tahunTerima;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("judul_buku")
	private String judulBuku;

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

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