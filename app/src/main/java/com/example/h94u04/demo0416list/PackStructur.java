package com.example.h94u04.demo0416list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PackStructur
{
    String foodName ;
    Integer num;

    PackStructur getPackStructur(String foodName , Integer num)
    {
        this.foodName= foodName;
        this.num = num ;
        return this;
    }
    String getName()
    {
        return foodName;
    }
    Integer getNum()
    {
        return num;
    }
}
