package com.libman.model.history;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class History{

	@SerializedName("data")
	private List<HistoryData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<HistoryData> data){
		this.data = data;
	}

	public List<HistoryData> getData(){
		return data;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}