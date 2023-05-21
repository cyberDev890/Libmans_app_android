package com.libman.model.daftar_favorite;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DaftarFavorit{

	@SerializedName("data")
	private List<DaftarFavoritData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DaftarFavoritData> data){
		this.data = data;
	}

	public List<DaftarFavoritData> getData(){
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

	public boolean getStatus(){
		return status;
	}
}