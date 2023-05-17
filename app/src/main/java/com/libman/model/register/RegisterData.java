package com.libman.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName("notelp")
	private String notelp;

	@SerializedName("password")
	private String password;

	@SerializedName("tingkatan")
	private String tingkatan;

	@SerializedName("nama_siswa")
	private String namaSiswa;

	@SerializedName("kelas")
	private String kelas;

	@SerializedName("NIS")
	private String nIS;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("gambar")
	private String gambar;

	public void setNotelp(String notelp){
		this.notelp = notelp;
	}

	public String getNotelp(){
		return notelp;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setTingkatan(String tingkatan){
		this.tingkatan = tingkatan;
	}

	public String getTingkatan(){
		return tingkatan;
	}

	public void setNamaSiswa(String namaSiswa){
		this.namaSiswa = namaSiswa;
	}

	public String getNamaSiswa(){
		return namaSiswa;
	}

	public void setKelas(String kelas){
		this.kelas = kelas;
	}

	public String getKelas(){
		return kelas;
	}

	public void setNIS(String nIS){
		this.nIS = nIS;
	}

	public String getNIS(){
		return nIS;
	}

	public void setJenisKelamin(String jenisKelamin){
		this.jenisKelamin = jenisKelamin;
	}

	public String getJenisKelamin(){
		return jenisKelamin;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}
}