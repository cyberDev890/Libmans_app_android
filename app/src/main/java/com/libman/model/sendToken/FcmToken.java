package com.libman.model.sendToken;

import com.google.gson.annotations.SerializedName;

public class FcmToken{

	@SerializedName("data")
	private fcmTokenData fcmTokenData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(fcmTokenData fcmTokenData){
		this.fcmTokenData = fcmTokenData;
	}

	public fcmTokenData getData(){
		return fcmTokenData;
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