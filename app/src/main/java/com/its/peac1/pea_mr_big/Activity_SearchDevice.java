package com.its.peac1.pea_mr_big;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class Activity_SearchDevice extends AppCompatActivity {
    EditText editsearch;
    public ListView listView ;
    String Username = "" ;
    String read_date_plan ,STATE_OFFLINE ;
    Adapter_item_MrSelectSearchMaster adapter ;
    ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster ;
    ListView lview1 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search_device);

        final myDBClass myDb = new myDBClass(this);
        myDb.getWritableDatabase() ;
        Username = myDb.SelectUserId() ;

        editsearch = (EditText) findViewById(R.id.editText1);
        lview1 = (ListView) findViewById(R.id.listView_search);
        Item_MrSelectSearchMaster = new ArrayList<Item_MrSelectSearchMaster>();
        adapter = new Adapter_item_MrSelectSearchMaster(Activity_SearchDevice.this, Item_MrSelectSearchMaster);
        lview1.setAdapter(adapter);



        Bundle b = getIntent().getExtras();
        // or other values
        if(b != null) {
            read_date_plan = b.getString("ReadDatePlan");
            STATE_OFFLINE = b.getString("STATE_OFFLINE");
            Log.i("STATE_OFFLINE",STATE_OFFLINE) ;
        }
        if(STATE_OFFLINE.trim().equals("Offline"))
        {
            Item_MrSelectSearchMaster.clear();
            Cursor CurData = myDb.SelectMR_MEMBER_MRday(Username,read_date_plan) ;
            if (CurData.getCount() == 0) {
                Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE ,"NO DATA","NO DATA","NO DATA"));
            }else {
                try {
                    while (!CurData.isAfterLast()) {
                        Log.i("isAfterLast",CurData.getString(0)) ;
                        Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("CA : "+CurData.getString(4),CurData.getString(2), "ชื่อ : "+CurData.getString(3), "ที่อยู่ : "+CurData.getString(3),read_date_plan,"MRU : "+CurData.getString(5),STATE_OFFLINE,CurData.getString(7),"NO DATA","NO DATA"));
                        CurData.moveToNext();
                    }
                }
                catch (Exception e) {
                    Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE ,"NO DATA","NO DATA","NO DATA"));
                }

                editsearch.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable arg0) {
                        ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster_2 = new ArrayList<Item_MrSelectSearchMaster>();
                        int textlength = editsearch.getText().length();
                        int ii = 0 ;
                        for (int i = 0; i < Item_MrSelectSearchMaster.size(); i++) {
                            try {

                                if(Item_MrSelectSearchMaster.get(i).getPea_meter().toLowerCase().contains(editsearch.getText().toString().toLowerCase()) ||Item_MrSelectSearchMaster.get(i).getName().toLowerCase().contains(editsearch.getText().toString().toLowerCase())){
                                    Item_MrSelectSearchMaster_2.add(new Item_MrSelectSearchMaster(Item_MrSelectSearchMaster.get(i).getCa(),Item_MrSelectSearchMaster.get(i).getPea_meter(),Item_MrSelectSearchMaster.get(i).getName(), Item_MrSelectSearchMaster.get(i).getAddress(),read_date_plan,Item_MrSelectSearchMaster.get(i).getMru(),STATE_OFFLINE,Item_MrSelectSearchMaster.get(i).getMetertype(),"NO DATA","NO DATA"));
                                }
                            } catch (Exception e) {
                            }
                            Adapter_item_MrSelectSearchMaster  adapter2 = new Adapter_item_MrSelectSearchMaster(Activity_SearchDevice.this, Item_MrSelectSearchMaster_2);
                            lview1.setAdapter(adapter2);
                         /*   lview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                    Intent i = new Intent(Activity_SearchDevice.this, Activity_Mr_Register.class);
                                    Bundle b = new Bundle();
                                    b.putString("Pea_meter", Item_MrSelectSearchMaster.get(ii).getPea_meter().toString());
                                    b.putString("ReadDatePlan",Item_MrSelectSearchMaster.get(ii).getPea_meter().toString() );
                                    i.putExtras(b);

                                }
                            });
                            */

                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start
                            , int count, int after) { }
                    public void onTextChanged(CharSequence s, int start
                            , int before, int count) { }

                });

            }

        }
        else {
            try {
                new Activity_SearchDevice.AsyncLogin().execute(Username, read_date_plan);
            } catch (Exception e) {
                Toast.makeText(this, "network is Unavailable 1", Toast.LENGTH_LONG).show();
            }
        }
        myDb.close();
    }


    private class AsyncLogin extends AsyncTask<String, String, JSONArray>
    {
        URL url = null;
        HttpURLConnection conn;
        ProgressDialog pdLoading = new ProgressDialog(Activity_SearchDevice.this);
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;
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
                String url_ = "http://extranet.pea.co.th/peap3/android/MR_mrSelectSearchMaster.ashx?reader="+ params[0]+"&read_date_plan="+ params[1]+"&state=9";
                Log.i("URL",url_) ;
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
            if (result == null) {
                Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE,"NO DATA","NO DATA","NO DATA"));
            } else {
                try {
                    int count = 0;
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);
                        Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("CA : "+c.getString("CA"),c.getString("PEANO"), "ชื่อ : "+c.getString("CUST_NAME"), "ที่อยู่ : "+c.getString("CONOBJ_ADDRESS"),read_date_plan,"MRU : "+c.getString("MRU"),STATE_OFFLINE,c.getString("METERTYPE"),c.getString("latitude_x"),c.getString("longitude_x")));

                        Log.i("c.getString(CA)",c.getString("CA"));

                    }
                } catch (Exception e) {
                    Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE,"NO DATA","NO DATA","NO DATA"));
                }
                editsearch.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable arg0) {
                        ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster_2 = new ArrayList<Item_MrSelectSearchMaster>();
                        int textlength = editsearch.getText().length();
                        int ii = 0 ;
                        for (int i = 0; i < Item_MrSelectSearchMaster.size(); i++) {
                             ii = i ;
                            try {
                                if(Item_MrSelectSearchMaster.get(i).getPea_meter().toLowerCase().contains(editsearch.getText().toString().toLowerCase()) ||Item_MrSelectSearchMaster.get(i).getName().toLowerCase().contains(editsearch.getText().toString().toLowerCase())){
                                    Item_MrSelectSearchMaster_2.add(new Item_MrSelectSearchMaster(Item_MrSelectSearchMaster.get(i).getCa(),Item_MrSelectSearchMaster.get(i).getPea_meter(),Item_MrSelectSearchMaster.get(i).getName(), Item_MrSelectSearchMaster.get(i).getAddress(),read_date_plan,Item_MrSelectSearchMaster.get(i).getMru(),STATE_OFFLINE,Item_MrSelectSearchMaster.get(i).getMetertype(),Item_MrSelectSearchMaster.get(i).getlatitude(),Item_MrSelectSearchMaster.get(i).getlongitude()));
                                }
                            } catch (Exception e) {
                            }
                             Adapter_item_MrSelectSearchMaster  adapter2 = new Adapter_item_MrSelectSearchMaster(Activity_SearchDevice.this, Item_MrSelectSearchMaster_2);
                            lview1.setAdapter(adapter2);
                         /*   lview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                    Intent i = new Intent(Activity_SearchDevice.this, Activity_Mr_Register.class);
                                    Bundle b = new Bundle();
                                    b.putString("Pea_meter", Item_MrSelectSearchMaster.get(ii).getPea_meter().toString());
                                    b.putString("ReadDatePlan",Item_MrSelectSearchMaster.get(ii).getPea_meter().toString() );
                                    i.putExtras(b);

                                }
                            });
                            */

                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start
                            , int count, int after) { }
                    public void onTextChanged(CharSequence s, int start
                            , int before, int count) { }

                });
                ////////////////////////////////////////////////////////////////////////////////



            }

            pdLoading.dismiss();
        }

        ///////////////////////////////////






    }
}
