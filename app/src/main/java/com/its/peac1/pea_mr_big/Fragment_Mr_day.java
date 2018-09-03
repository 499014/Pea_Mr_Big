package com.its.peac1.pea_mr_big;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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


public class Fragment_Mr_day extends Fragment {
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

    public Fragment_Mr_day() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Mr_day.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Mr_day newInstance(String param1, String param2) {
        Fragment_Mr_day fragment = new Fragment_Mr_day();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

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

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
                lview1.setAdapter(null);
                new AsyncList1().execute(userid);
            }
        });


      /* Button button_offine  = (Button) rootView.findViewById(R.id.button_offine);
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
        new AsyncList1().execute(userid);



        return rootView ;

    }



    private class AsyncList1 extends AsyncTask<String, String, JSONArray> {
        URL url = null;
        HttpURLConnection conn;
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;

        private LayoutInflater inflater;
        private ViewGroup container;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray network = null;

            try {
                String url_ = "http://extranet.pea.co.th/peap3/android/MR_mr_day.ashx?key=" + params[0];
          //      String url_ = "http://extranet.pea.co.th/peap3/android/MR_mr_day.ashx?key=499033" ;
                url = new URL(url_);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", "")
                        .appendQueryParameter("password", "");
                String query = builder.build().getEncodedQuery();
                Log.i("queryquery", query);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                Log.i("writerwriter", writer.toString());
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    JSONObject jsonObj;
                    try {
                        jsonObj = new JSONObject(reader.readLine());
                        network = jsonObj.getJSONArray("network");
                    } catch (Exception e) {
                        jsonObj = null;
                        network = null;
                    }

                }


            } catch (IOException e) {
                network = null;
                e.printStackTrace();

            } finally {
                conn.disconnect();
            }


            return network;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            List_Item_mr_day.clear();
            String test = "";

            final List<String> List_ReadDatePlan = new ArrayList<String>();
            final List<String> List_total = new ArrayList<String>();
            final List<String> List_readed = new ArrayList<String>();
            final List<String> List_notread = new ArrayList<String>();
            if (result == null) {
                List_Item_mr_day.add(new Item_mr_day("000000000000", "-", "-", "-","Online"));
            } else {
                final myDBClass myDb = new myDBClass(getActivity());
                myDb.getWritableDatabase();
                String Username_ = myDb.SelectUserId();
                myDb.close();
                try {
                    int count = 0;
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);
                        List_Item_mr_day.add(new Item_mr_day(c.getString("TXT_READ_DATE_PLAN"), c.getString("TOTAL"), c.getString("READED"), c.getString("NOT_READ"),"Online"));

                    }
                } catch (Exception e) {
                    List_Item_mr_day.add(new Item_mr_day("000000000000", "-", "-", "-","Online"));
                }

            }


            final Activity activity = getActivity();

            lview1.setAdapter(adapter);
         //   adapter.notifyDataSetChanged();
            /*
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Log.i("setOnItemClickListener","setOnItemClickListenersetOnItemClickListener");

                    }
                });

*/


            pdLoading.dismiss();
        }


    }
}
