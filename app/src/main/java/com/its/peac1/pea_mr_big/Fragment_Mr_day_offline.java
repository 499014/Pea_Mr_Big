package com.its.peac1.pea_mr_big;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Mr_day_offline extends Fragment {
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
    Adapter_item_mr_day adapter ;
    ArrayList<Item_mr_day> List_Item_mr_day ;

    public Fragment_Mr_day_offline() {
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
        View rootView = inflater.inflate(R.layout.fragment_fragment__mr_day, container, false);
        lview1 = (ListView) rootView.findViewById(R.id.listview1);
        List_Item_mr_day = new ArrayList<Item_mr_day>();
        adapter = new Adapter_item_mr_day(this.getContext(),this.getActivity(), List_Item_mr_day);
        lview1.setAdapter(adapter);



        ImageButton imageButtonx = (ImageButton) rootView.findViewById(R.id.imageButton_x);
        imageButtonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ist_Item_mr_day = new ArrayList<Item_mr_day>();
            //    lview1.setAdapter(null);
             //   new Fragment_Mr_day_offline.AsyncList1().execute(userid);
            }
        });

/*
        Button button_offine  = (Button) rootView.findViewById(R.id.button_offine);
        button_offine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("userius", count+"");
                //      Log.i("TEEEEEE",count);

                Intent i = new Intent(getActivity(), Activity_MrSelectSearchMaster.class);
                Bundle b = new Bundle();
                b.putString("key", "499014");
                b.putString("ReadDatePlan","20180301");
                b.putString("STATE", "OFFLINE");
                i.putExtras(b);
                getActivity().startActivity(i);


            }
        });

*/
        //   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        setadapter_mrdayAll();



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
