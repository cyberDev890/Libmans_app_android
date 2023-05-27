package libman.model.memerlukan_tindakan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tindakan{

	@SerializedName("data")
	private List<TindakanData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(List<TindakanData> data){
		this.data = data;
	}

	public List<TindakanData> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}