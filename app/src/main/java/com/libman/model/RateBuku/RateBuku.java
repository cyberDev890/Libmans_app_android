package com.libman.model.RateBuku;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RateBuku{

	@SerializedName("data")
	private List<RateBukuData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(List<RateBukuData> data){
		this.data = data;
	}

	public List<RateBukuData> getData(){
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