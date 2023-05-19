package com.libman.model.profile;

import com.google.gson.annotations.SerializedName;

public class Profile{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private Boolean status;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(Boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}