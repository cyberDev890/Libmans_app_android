package com.libman.controller;

import java.io.Serializable;

public class card_buku implements Serializable {
    private String judul;
    private String semester;
    private String imgUrl;
    private  int tgl;

    public card_buku(String judul, String semester, String imgUrl) {
        this.judul = judul;
        this.semester = semester;
        this.imgUrl = imgUrl;

    }

    public String getJudul() {
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }
    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }
    public  String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
