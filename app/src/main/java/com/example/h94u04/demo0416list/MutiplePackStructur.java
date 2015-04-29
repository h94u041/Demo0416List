package com.example.h94u04.demo0416list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MutiplePackStructur
{
    ArrayList<PackStructur> listName = new ArrayList<>();

    MutiplePackStructur(Iterator<PackStructur> struct)
    {
        listName = new ArrayList<>();
         while (struct.hasNext())
         {
             listName.add(struct.next());
         }
    }
    public boolean isGotSet(HashMap<String,DATA> hash)
    {
        boolean bo = true ;
        for(PackStructur PackStructur : listName )
        {
            if(hash.get(PackStructur.getName()).getCount() >= PackStructur.getNum())
            {
               continue ;
            }
            else
            {
                return false ;
            }
        }
        return bo;
    }
    ArrayList<PackStructur> getListName()
    {
        return listName;
    }
}
