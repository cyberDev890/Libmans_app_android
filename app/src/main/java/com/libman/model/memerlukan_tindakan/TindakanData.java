package com.libman.model.memerlukan_tindakan;

import com.google.gson.annotations.SerializedName;

public class TindakanData {

	@SerializedName("penerbit")
	private String penerbit;

	@SerializedName("id_buku")
	private String idBuku;

	@SerializedName("semester")
	private String semester;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("judul_buku")
	private String judulBuku;

	@SerializedName("id_pengembalian")
	private String idPengembalian;

	@SerializedName("tanggal_pengembalian")
	private String tanggalPengembalian;

	@SerializedName("tahun_terima")
	private String tahun_terima;

	public void setPenerbit(String penerbit){
		this.penerbit = penerbit;
	}

	public String getPenerbit(){
		return penerbit;
	}

	public void setIdBuku(String idBuku){
		this.idBuku = idBuku;
	}

	public String getIdBuku(){
		return idBuku;
	}

	public void setSemester(String semester){
		this.semester = semester;
	}

	public String getSemester(){
		return semester;
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

	public void setIdPengembalian(String idPengembalian){
		this.idPengembalian = idPengembalian;
	}

	public String getIdPengembalian(){
		return idPengembalian;
	}

	public void setTanggalPengembalian(String tanggalPengembalian){
		this.tanggalPengembalian = tanggalPengembalian;
	}

	public String getTanggalPengembalian(){
		return tanggalPengembalian;
	}

	public void setTahunterima(String tahun_terima){
		this.tahun_terima = tahun_terima;
	}

	public String getTahunterima(){
		return tahun_terima;
	}
}