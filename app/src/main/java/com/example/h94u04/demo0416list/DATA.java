package com.example.h94u04.demo0416list;

import android.widget.TextView;

public class DATA {
    TextView tv01, tv02, tv03;
    String id, item, price;
    int count = 0;
    int position;

    DATA(String id, String item, String price) {
        this.id = id;
        this.item = item;
        this.price = price;
    }

    DATA(DATA data) {
        this.id = data.id;
        this.item = data.item;
        this.price = data.price;
    }

    void setData(DATA data) {
        this.tv01 = data.tv01;
        this.tv02 = data.tv02;
        this.tv03 = data.tv03;
        this.id = data.id;
        this.item = data.item;
        this.price = data.price;
    }

    String getId() {
        return id;
    }
    void addCount()
    {
        ++count;
    }
    void setNum()
    {
        price = count+"";
    }
}
