package libman.model.daftarbuku;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DaftarBuku{

	@SerializedName("data")
	private List<DaftarBukuData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setData(List<DaftarBukuData> data){
		this.data = data;
	}

	public List<DaftarBukuData> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}