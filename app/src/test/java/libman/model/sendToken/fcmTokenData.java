package libman.model.sendToken;

import com.google.gson.annotations.SerializedName;

public class fcmTokenData {

	@SerializedName("id_data_kelas")
	private int idDataKelas;

	@SerializedName("notelp")
	private String notelp;

	@SerializedName("password")
	private String password;

	@SerializedName("nama_siswa")
	private String namaSiswa;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("NIS")
	private int nIS;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("fcmToken")
	private String fcmToken;

	@SerializedName("gambar")
	private String gambar;

	public void setIdDataKelas(int idDataKelas){
		this.idDataKelas = idDataKelas;
	}

	public int getIdDataKelas(){
		return idDataKelas;
	}

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

	public void setNamaSiswa(String namaSiswa){
		this.namaSiswa = namaSiswa;
	}

	public String getNamaSiswa(){
		return namaSiswa;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setApiToken(String apiToken){
		this.apiToken = apiToken;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void setNIS(int nIS){
		this.nIS = nIS;
	}

	public int getNIS(){
		return nIS;
	}

	public void setJenisKelamin(String jenisKelamin){
		this.jenisKelamin = jenisKelamin;
	}

	public String getJenisKelamin(){
		return jenisKelamin;
	}

	public void setFcmToken(String fcmToken){
		this.fcmToken = fcmToken;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}
}