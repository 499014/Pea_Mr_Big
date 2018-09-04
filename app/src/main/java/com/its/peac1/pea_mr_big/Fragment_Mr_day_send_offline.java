package com.its.peac1.pea_mr_big;

import android.content.Context;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



public class Fragment_Mr_day_send_offline extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String userid;
    int count ;
    public ListView lview1 ;


    ArrayList<Item_mr_day> List_Item_mr_day ;

    ArrayList<Item_MrSelectSearchMaster_send_offline> Item_MrSelectSearchMaster_send_offline ;
    Adapter_item_MrSelectSearchMaster_send_offline adapter ;

    public Fragment_Mr_day_send_offline() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Fragment_Mr_day.
     */
    // TODO: Rename and change types and number of parameters
    /*
    public static Fragment_Mr_day newInstance(String param1, String param2) {
        Fragment_Mr_day fragment = new Fragment_Mr_day();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        final myDBClass myDb = new myDBClass(this.getActivity());
        myDb.getWritableDatabase();
        userid = myDb.SelectUserId();
        count =myDb.count_data_registerALL();



        Log.i("userius", count+"");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment__mr_day_send_offline, container, false);
        lview1 = (ListView) rootView.findViewById(R.id.listview1);



        // List_Item_mr_day = new ArrayList<Item_mr_day>();
        Item_MrSelectSearchMaster_send_offline = new ArrayList<Item_MrSelectSearchMaster_send_offline>();
        adapter = new Adapter_item_MrSelectSearchMaster_send_offline(this.getActivity(), Item_MrSelectSearchMaster_send_offline);
        lview1.setAdapter(adapter);
        Item_MrSelectSearchMaster_send_offline.clear();
        final myDBClass myDb = new myDBClass(this.getActivity());
        myDb.getWritableDatabase();
        userid = myDb.SelectUserId();
        count =myDb.count_data_registerALL();
        Cursor CurData = myDb.SelectMR_OFFLINE_DATA() ;
        if (CurData.getCount() == 0) {
            Item_MrSelectSearchMaster_send_offline.add(new Item_MrSelectSearchMaster_send_offline("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA","DATA"));
        }
        else
            {
                try {
                    while (!CurData.isAfterLast()) {
                        Log.i("isAfterLast", CurData.getString(0));
                        Item_MrSelectSearchMaster_send_offline.add(new Item_MrSelectSearchMaster_send_offline(CurData.getString(1), CurData.getString(0), "", "", "", "วันที่อ่านหน่วยตามแผน ", CurData.getString(3)));
                        CurData.moveToNext();
                    }
                }
                catch (Exception e)
                {

                }
                lview1.setAdapter(adapter);
                adapter.notifyDataSetChanged() ;
        }



        Button button_send_offline = (Button) rootView.findViewById(R.id.button_send_offline);
        button_send_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final myDBClass myDb = new myDBClass(getContext());
                myDb.getWritableDatabase();
                Cursor CurData = myDb.SelectMR_OFFLINE_DATA() ;
                JsonArray jsArray = new JsonArray() ;
                JsonObject jsObject ;
                while (!CurData.isAfterLast()) {
                    jsObject = new JsonObject() ;
                    jsObject.addProperty("URL",CurData.getString(3)) ;
                    Log.i("aaaaaaaaaaaaaaaaaasss",CurData.getString(0)+"|"+CurData.getString(1)+"|"+CurData.getString(3)) ;
                   jsArray.add(jsObject);
                   // List_Item_mr_day.add(new Item_mr_day(CurData.getString(0), CurData.getString(1),"-", "-","Offline"));
                    CurData.moveToNext();
                }

                try
                {
                    Ion.with(getActivity())
                            .load("http://lai.pea.co.th/499014/")
                            .setJsonArrayBody(jsArray)
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override

                                public void onCompleted(Exception e, String result) {
                                    try{
                                        String xx = "xx";
                                        /*

                                        for (int i = 0; i < result.size(); i++) {
                                            JsonElement elem = result.get(i);
                                            JsonObject obj = elem.getAsJsonObject();
                                            xx = xx + obj.get("Name").getAsString() ;
                                        }
*/
                                        result.toString();
                                        Toast.makeText(getActivity(), result.toString() ,
                                                Toast.LENGTH_LONG).show();
                                    }
                                    catch (Exception x)
                                    {
                                        Toast.makeText(getActivity(), x.toString() ,
                                                Toast.LENGTH_LONG).show();
                                    }



                                }
                            });
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString() ,
                            Toast.LENGTH_LONG).show();
                }

            }
        });



        return rootView ;

    }
    private void setadapter_mrdayAll()
    {
        final myDBClass myDb = new myDBClass(getContext());
        myDb.getWritableDatabase();

        Cursor CurData = myDb.SelectMR_MEMBER_MRday_all() ;
        int i = 0 ;
        if (CurData.getCount() == 0) {
            List_Item_mr_day.add(new Item_mr_day("000000000000", "-", "-", "-","Offline"));
        }else {
            List_Item_mr_day.clear();
            try {
                while (!CurData.isAfterLast()) {
                    Log.i("isAfterLast",CurData.getString(0)) ;
                    List_Item_mr_day.add(new Item_mr_day(CurData.getString(0), CurData.getString(1),"-", "-","Offline"));
                    CurData.moveToNext();
                }
            }
            catch (Exception e) {
                List_Item_mr_day.add(new Item_mr_day("000000000000", "-", "-", "-","Offline"));
            }



        }
        lview1.setAdapter(adapter);
    }


}
