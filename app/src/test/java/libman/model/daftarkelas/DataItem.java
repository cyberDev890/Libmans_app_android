package libman.model.daftarkelas;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("tingkatan")
	private String tingkatan;

	@SerializedName("id_data_kelas ")
	private int idDataKelas;

	@SerializedName("kelas")
	private String kelas;

	public void setTingkatan(String tingkatan){
		this.tingkatan = tingkatan;
	}

	public String getTingkatan(){
		return tingkatan;
	}

	public void setIdDataKelas(int idDataKelas){
		this.idDataKelas = idDataKelas;
	}

	public int getIdDataKelas(){
		return idDataKelas;
	}

	public void setKelas(String kelas){
		this.kelas = kelas;
	}

	public String getKelas(){
		return kelas;
	}
}