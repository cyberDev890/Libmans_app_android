package com.libman.model.login;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("LoginData")
	private LoginData LoginData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setLoginData(LoginData LoginData){
		this.LoginData = LoginData;
	}

	public LoginData getLoginData(){
		return LoginData;
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