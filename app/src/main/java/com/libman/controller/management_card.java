package com.libman.controller;

import android.content.Context;

import java.util.ArrayList;

public class management_card {
    private Context context;
    private TinyDB tinyDB;

    public management_card(Context context, TinyDB tinyDB) {
        this.context = context;
        this.tinyDB = tinyDB;
    }
    public void insertFood(card_buku items_card){
        ArrayList<card_buku> items = getListCard();
    }
    public ArrayList<card_buku> getListCard(){
//        return tinyDB.getListObject("list_card", card_buku.class);
        return null;
    }
}
