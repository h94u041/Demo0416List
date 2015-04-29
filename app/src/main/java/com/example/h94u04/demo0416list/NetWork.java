package com.example.h94u04.demo0416list;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class NetWork extends AsyncTask<String,Void ,JSONArray>
{

    @Override
    protected JSONArray doInBackground(String... params) {

        JSONArray ja = null;

        HttpGet get = new HttpGet(params[0]);

        HttpClient client = new DefaultHttpClient() ;

        try
        {
            HttpResponse res = client.execute(get);

            String str = EntityUtils.toString(res.getEntity(), "UTF-8");

            ja = new JSONArray(str);
        }
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException e) {e.printStackTrace();}

        return ja ;
    }
}
