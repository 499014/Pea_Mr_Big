package com.its.peac1.pea_mr_big;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

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

public class Activity_MrSelectSearchMaster extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT=1000000;
    public static final int READ_TIMEOUT=150000;
    String userid ,read_date_plan ,STATE_OFFLINE,STATE;
    public ListView lview1 ;
    ArrayList<Item_MrSelectSearchMasterOffine> Item_MrSelectSearchMasterOffine ;
    ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster ;
    Adapter_item_MrSelectSearchMaster adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__mr_select_search_master);





        final myDBClass myDb = new myDBClass(this);
        myDb.getWritableDatabase();
        userid = myDb.SelectUserId() ;

        Bundle b = getIntent().getExtras();
        lview1 = (ListView) findViewById(R.id.listview_mr_select_search_master);
        Item_MrSelectSearchMaster = new ArrayList<Item_MrSelectSearchMaster>();
        adapter = new Adapter_item_MrSelectSearchMaster(Activity_MrSelectSearchMaster.this, Item_MrSelectSearchMaster);
        lview1.setAdapter(adapter);
        // or other values
        if(b != null) {
            read_date_plan = b.getString("key");
            STATE_OFFLINE = b.getString("STATE_OFFLINE");
            STATE = b.getString("STATE");
            Log.i("STATE",STATE) ;
        }
        if(STATE_OFFLINE.trim().equals("Offline"))
        {
            Item_MrSelectSearchMaster.clear();
            Cursor CurData = myDb.SelectMR_MEMBER_MRday(userid,read_date_plan) ;
            if (CurData.getCount() == 0) {
                Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE.trim(),"NO DATA","NO DATA","NO DATA"));
            }else {
                try {
                    while (!CurData.isAfterLast()) {
                        Log.i("isAfterLast",CurData.getString(0)) ;
                        Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("CA : "+CurData.getString(4),CurData.getString(2), "ชื่อ : "+CurData.getString(3), "ที่อยู่ : "+CurData.getString(3),read_date_plan,"MRU : "+CurData.getString(5),STATE_OFFLINE.trim(),CurData.getString(7),"NO DATA","NO DATA"));
                        CurData.moveToNext();
                    }
                }
                catch (Exception e) {
                    Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE.trim(),"NO DATA","NO DATA","NO DATA"));
                }

                lview1.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }


        }
        else
        {


            new Activity_MrSelectSearchMaster.AsyncList().execute(userid, read_date_plan, STATE);
            Log.i("arrData_else",STATE_OFFLINE);
            //new Activity_MrSelectSearchMaster.AsyncList().execute(userid, read_date_plan, state);
        }
        myDb.close();
        ImageButton imageButtonx = (ImageButton) findViewById(R.id.imageButton);
        imageButtonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lview1.setAdapter(null);
                new Activity_MrSelectSearchMaster.AsyncList().execute(userid,read_date_plan,STATE);
            }
        });



        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton_log);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent search = new Intent(Activity_MrSelectSearchMaster.this,Activity_SearchDevice.class);
               // Intent search = new Intent(Activity_MrSelectSearchMaster.this,Activity_map.class);
                Bundle b = new Bundle();
                b.putString("userid", userid);
                b.putString("ReadDatePlan",read_date_plan);
                b.putString("STATE_OFFLINE",STATE_OFFLINE);

                search.putExtras(b);
                    startActivity(search);

            }
        });


        ImageButton imageButtonDownload = (ImageButton) findViewById(R.id.DownloadimageButton);
        imageButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new  Activity_MrSelectSearchMaster.AsyncInsertRegister_All().execute(read_date_plan,userid);

            }
        });
        ImageView imageButtonMap = (ImageView) findViewById(R.id.imageButton_map);
        imageButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(Activity_MrSelectSearchMaster.this,Activity_map.class);
                Bundle b = new Bundle();
                b.putString("userid", userid);
                b.putString("ReadDatePlan",read_date_plan);
                b.putString("STATE_OFFLINE",STATE_OFFLINE);

                search.putExtras(b);
                startActivity(search);



            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class AsyncList extends AsyncTask<String, String, JSONArray> {
        URL url = null;
        HttpURLConnection conn;
        ProgressDialog pdLoading = new ProgressDialog(Activity_MrSelectSearchMaster.this);
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
                String url_ = "http://extranet.pea.co.th/peap3/android/MR_mrSelectSearchMaster.ashx?reader="+ params[0]+"&read_date_plan="+ params[1]+"&state="+ params[2];
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

            final myDBClass myDb = new myDBClass(Activity_MrSelectSearchMaster.this);
            if (myDb.DeleteMR_MEMBER_HEDER_TEMP()) {
                long flg1 = myDb.Insert_MR_MEMBER_HEDER_TEMP(result,userid,read_date_plan);
            }

            Item_MrSelectSearchMaster.clear();
            if (result == null) {
                Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE.trim(),"NO DATA","NO DATA","NO DATA"));
            } else {
                try {
                    int count = 0;
                    for (int i = 0; i < result.length(); i++) {

                        JSONObject c = result.getJSONObject(i);
                        Log.i("CCAAACCC",c.getString("CA").trim()+"|"+c.getString("latitude_x").trim());
                        Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("CA : "+c.getString("CA"),c.getString("PEANO"), "ชื่อ : "+c.getString("CUST_NAME"), "ที่อยู่ : "+c.getString("CONOBJ_ADDRESS"),read_date_plan,"MRU : "+c.getString("MRU"),STATE_OFFLINE.trim(),c.getString("METERTYPE").trim(),c.getString("latitude_x").trim(),c.getString("longitude_x").trim()));

                        Log.i("c.getString(METERTYPE)",c.getString("METERTYPE"));

                    }
                } catch (Exception e) {
                    Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE.trim(),"NO DATA","NO DATA","NO DATA"));
                }

            }

            lview1.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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

    class AsyncInsertRegister_All extends AsyncTask<String, String, JSONArray> {
        ProgressDialog pdLoading ;

        HttpURLConnection conn;
        URL url = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading = new ProgressDialog(Activity_MrSelectSearchMaster.this);
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray network = null;

            try {
                String url_ = "http://extranet.pea.co.th/peap3/android/MR_REGISTER_all.ashx?read_date_plan="+params[0]+"&reader="+params[1];
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
            final myDBClass myDb = new myDBClass(Activity_MrSelectSearchMaster.this);
            if (myDb.DeleteMR_MEMBER_HEDER_MASTER()) {
                boolean flg1 = myDb.Insert_MEMBER_HEDER_MASTER();
            }
            if (myDb.DeleteRegisterAllData()) {
                long flg1 = myDb.Insert_MR_REGISTER_ALL(result);
            }
            /*
            if (myDb.DeleteRegisterAllData()) {
                for (int i = 0; i < result.length(); i++) {
                    try {
                        JSONObject c = result.getJSONObject(i);

                        long flg1 = myDb.Insert_MR_REGISTER_ALL(c.getString("READ_DATE_PLAN"), c.getString("READ_PLAN"), c.getString("MRU"), c.getString("PEANO"), c.getString("CA"), c.getString("REGISTER_GROUP"), c.getString("MR_REASON"), c.getString("REGIS_NO"), c.getString("REGIS_CODE"), c.getString("REGIS_TEXT"), c.getString("REG_TYPE"), c.getString("UMR"), c.getString("PD"), c.getString("DP"), c.getString("MRO"), c.getString("NOW_READ"), c.getString("MAX_READ"), c.getString("MIN_READ"), c.getString("READED_TIME"), c.getString("INPUT"), c.getString("BEFORE_DOT"), c.getString("AFTER_DOT"), c.getString("NOTE_READ"), c.getString("CHECK_OPERATION"), c.getString("ERROR_MAX"), c.getString("ERROR_MIN"), c.getString("CHECK_STATE"));
                        Log.i("Insert",c.getString("PEANO"));
                        //  long flg1 = myDb.Insert_MR_METERMODEL("x","x","x","x","x","x","x","x");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Finisth", "Finisth");

            }
            */
            myDb.close();

            pdLoading.dismiss();
        }
    }

}
