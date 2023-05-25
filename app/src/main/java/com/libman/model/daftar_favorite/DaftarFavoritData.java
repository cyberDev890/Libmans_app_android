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
	private String id_buku;

	@SerializedName("gambar")
	private String gambar;

	public void setNamaBuku(String namaBuku){
		this.namaBuku = namaBuku;
	}

	public String getNamaBuku(){
		return namaBuku;
	}

	public void setPenerbit(String penerbit){
		this.penerbit = penerbit;
	}

	public String getPenerbit(){
		return penerbit;
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

	public void setId_buku(String id_buku){
		this.id_buku = id_buku;
	}

	public String getId_buku(){
		return id_buku;
	}
}