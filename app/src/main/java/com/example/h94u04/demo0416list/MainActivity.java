package com.example.h94u04.demo0416list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ArrayList<DATA> list ,list2 ;
    SwipeRefreshLayout swipeRefreshLayout;
    LinkedHashMap<String,MutiplePackStructur> StructMap =new LinkedHashMap<>();
    LinkedHashMap<String,DATA> HashLink =new LinkedHashMap<>();
    ArrayList<PackStructur> Structlist = new ArrayList<>();
    Button btnC;
    ListView LV;
    ListView LV2;
    TextView textMsg;
    JSONArray MenuJA , PackJA;
    JSONObject JO;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplication();

        findObjet();

        getData();

        getListV();

        btnC.setOnClickListener(this);
        AbsListView.OnScrollListener onListScroll = new scorLive(swipeRefreshLayout);

        SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        list = new ArrayList<>();
                        list.add(new DATA().getDATA("JASON","Sentyr","UniWilSon"));
                        Toast.makeText(getApplicationContext(), "Refresh done!", Toast.LENGTH_SHORT).show();
                }
                }, 3000);
            }
        };
        LV.setOnScrollListener(onListScroll);

        LV.setOnItemClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(onSwipeToRefresh);

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
    }
    private void getListV() {

            HashLink = new LinkedHashMap<>();

            Structlist = new ArrayList<>();
        try
        {   PackJA = null ; MenuJA = null;
            PackJA = new NetWork().execute("http://jasonchi.ddns.net:8080/api/combo/").get();

            MenuJA = new NetWork().execute("http://jasonchi.ddns.net:8080/api/product/").get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            Log.e("h94u04","Internet Not Open Now !!! ");}
        catch (ExecutionException e) {e.printStackTrace();}
        new Runnable(){
            @Override
            public void run() {
                try{
                    int length = MenuJA.length();
                    for (int i = 0; i < length; i++) {
                        JO = MenuJA.getJSONObject(i);

                        DATA Data = new DATA().getDATA(JO.getString("Id"), JO.getString("Name"), JO.getString("Price"));

                        HashLink.put(JO.getString("Id"), Data);

                        list.add(Data);
                    }
                }catch (JSONException e) {e.printStackTrace();}
            }
        }.run();
        new Runnable(){
            @Override
            public void run() {
                try{
                    int lengthP = PackJA.length();
                    for (int i = 0; i < lengthP; i++) {
                        Structlist =new ArrayList<>();

                        JO = PackJA.getJSONObject(i);

                        String str = JO.getString("Id");

                        JSONArray JPack = JO.getJSONArray("Detail");

                        int lengthJP = JPack.length();
                        for (int j = 0; j < lengthJP; j++) {
                            JSONObject JOP = JPack.getJSONObject(j);

                            Structlist.add(new PackStructur().getPackStructur(JOP.getString("Id"), JOP.getInt("Quantity")));
                        }
                        StructMap.put( str , new MutiplePackStructur(Structlist.iterator()));
                    }
                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }.run();

        AdtBase AdtBase = new AdtBase(this , list);

        LV.setAdapter(AdtBase);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        list2= new ArrayList<>();

        HashLink.get(((DATA)parent.getItemAtPosition(position)).getName()).addCount();

        for(String MP :StructMap.keySet())
        {
            MutiplePackStructur MPackstr = StructMap.get(MP);

            if(MPackstr.isGotSet(HashLink))
            {
                HashLink.get(MP).addCount();

                Iterator<PackStructur> It = MPackstr.getListName().iterator();
                while(It.hasNext())
                {
                    PackStructur Pk = It.next();
                    HashLink.get(Pk.getName()).subCount(Pk.getNum());
                }
            }
        }
        for(DATA data : HashLink.values())
        {
            if(!data.getCount().equals(0))
            {
                list2.add( data );
            }
        }
        LV2.setAdapter(new AdtPackBase(this , list2) );

        //openOptionsDialog(NowData) /**裡頭包含著 改變已點項目的方法**/;
    }
    private void openOptionsDialog(final DATA data) {

        if (data.id.substring(0, 1).equals("F")) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle("Do You Want to get the Pack of food ?");

            dialog.setMessage(data.item + "have a packSet !");

            final String str = data.item.substring(2);
            dialog.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (HashLink.get(data.id.substring(2)) != null)
                        data.setData(HashLink.get(data.id.substring(2)));
                        //listChang(data);
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                    //listChang(data);
                }
            });
            dialog.show();
        }
    }

    private void getData() {

    }
    private void findObjet() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip);

        LV = (ListView) findViewById(R.id.ListOut);

        LV2 = (ListView) findViewById(R.id.ListOut2);

        btnC = (Button)findViewById(R.id.btnClear);

        textMsg = (TextView)findViewById(R.id.textMsg);

        MenuJA = new JSONArray();

        PackJA = new JSONArray();

        list = new ArrayList<>();
    }
    @Override
    public void onClick(View v) {
        HashLink = new LinkedHashMap<>();
    }
}
class scorLive implements AbsListView.OnScrollListener {
    SwipeRefreshLayout swipeRefreshLayout;
    scorLive(SwipeRefreshLayout swipeRefreshLayout)
    {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0)
        {
            swipeRefreshLayout.setEnabled(true);
        }
        else
        {
            swipeRefreshLayout.setEnabled(false);
        }
    }
}
//private void getSet() {
//        try
//        {
//            Packlist = new ArrayList<>();
//
//            JSONObject JOPcak;
//
//            int Length = PackJA.length();
//
//            for(int i = 0 ; i< Length ; i++)
//            {
//                JOPcak = PackJA.getJSONObject(i);
//
//                Packlist.add(new PackData().getPackData(JOPcak.getString("Id") , JOPcak.getJSONArray("Detail")));
//            }
//        }
//        catch (JSONException e) {e.printStackTrace();}
//}
//    private PackData checkSet()
//    {
//        boolean bol =false;
//        boolean bol2 = false;
//        for (int i = 0; i < Packlist.size(); i++) {
//            bol = false;
//            bol2 = false;
//            String[] PName = Packlist.get(i).getStructName();
//            Integer[] PNum = Packlist.get(i).getStructNum();
//
//                    for (DATA data : list2) {
//                        if(data.item==PName[0])
//                        {
//                            if(data.count==PNum[0])
//                            {
//                                bol = true;
//                            }
//                        }
//                        if(data.item==PName[1])
//                        {
//                            if(data.count==PNum[1])
//                            {
//                                bol2 = true;
//                            }
//                        }
//                    }
//            if(bol || bol2)
//            {
//                return Packlist.get(i);
//            }
//      }
//        return null;
//    }
//class SetDATA extends DATA
//{
//    LinkedHashMap<String,DATA> Hash;
//    String id,item,price;
//    int position;
//    SetDATA(DATA data , LinkedHashMap Hash ,int position) {
//        super(data.id, data.item, data.price);
//        this.id=data.id;
//        this.item=data.item;
//        this.price=data.price;
//        this.Hash = Hash ;
//        this.position = position;
//    }
//    private void isBinding()
//    {
//        id.substring(3).equals(Hash.get(id.substring(3)).id.substring(2));
//
//    }
//}
//
//    private void getBind() {
//        getEditCustomDialog();
//    }
//    private AlertDialog getEditCustomDialog() {
//        LayoutInflater inflater = getLayoutInflater();
//
//        View view = inflater.inflate(R.layout.dialogxml, null);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setView(view);
//        builder.setTitle("Do You Want to get the Pack of food ?");
//        return builder.create();
//    }