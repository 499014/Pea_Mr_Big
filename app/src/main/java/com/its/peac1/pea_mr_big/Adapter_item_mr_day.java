package com.its.peac1.pea_mr_big;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
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

/**
 * Created by 499014 on 10/11/2560.
 */

public class Adapter_item_mr_day extends BaseAdapter {
    public static final int CONNECTION_TIMEOUT=1000000;
    public static final int READ_TIMEOUT=150000;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    SQLiteDatabase db;
    String Area ,userid;
    public ArrayList<Item_mr_day> Item_mr_day;
    Activity activity;
    Context mContext ;
    Fragment fragment ;
    public Adapter_item_mr_day(Context mContext,Activity activity, ArrayList<Item_mr_day> Item_mr_day)
    {
        super();
        this.Item_mr_day = Item_mr_day ;
        this.activity = activity ;
        this.mContext = mContext ;

    }

    @Override
    public int getCount() {
        return Item_mr_day.size();
    }

    @Override
    public Object getItem(int position) {
        return Item_mr_day.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView ReadDatePlan;
        TextView total;
        TextView readed;
        TextView notread;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();



        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_item_mr_day, null);
            holder = new ViewHolder();
            holder.ReadDatePlan = (TextView) convertView.findViewById(R.id.one1);
            holder.total = (TextView) convertView.findViewById(R.id.one2);
            holder.readed = (TextView) convertView.findViewById(R.id.one3);
            holder.notread = (TextView) convertView.findViewById(R.id.one4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final  Item_mr_day item = Item_mr_day.get(position);
        final myDBClass myDb2 = new myDBClass(activity.getApplication());
        userid = myDb2.SelectUserId() ;
        if(!item.getReadDatePlan().toString().equals("")) {
            holder.ReadDatePlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // new Activity_Mr_Register.AsyncList1().execute(userid,ReadDatePlan,Pea_meter);
                 //   new  Adapter_item_mr_day.AsyncInsertRegister_All().execute(item.getReadDatePlan().toString(),userid);


                    Intent i = new Intent(activity, Activity_MrSelectSearchMaster.class);
                    Bundle b = new Bundle();
                    b.putString("key", item.getReadDatePlan().toString());
                    b.putString("STATE", "9");
                    b.putString("STATE_OFFLINE",item.getStatus().toString());
                    i.putExtras(b);
                    activity.startActivity(i);


                }
            });

            holder.notread.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  Log.i("Test","Inseart") ;
                   // new  Adapter_item_mr_day.AsyncInsertRegister_All().execute(item.getReadDatePlan().toString(),userid);
                    //new Adapter_item_mr_day.AsyncInsertRegister_All().execute();

                    Intent i = new Intent(activity, Activity_MrSelectSearchMaster.class);
                    Bundle b = new Bundle();
                    b.putString("key", item.getReadDatePlan().toString());
                    b.putString("ReadDatePlan",item.getReadDatePlan().toString());
                    b.putString("STATE_OFFLINE",item.getStatus().toString());
                    b.putString("STATE", "0");
                    i.putExtras(b);
                    activity.startActivity(i);


                }
            });

            holder.readed.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Log.i("Test","Inseart") ;
                  //  new  Adapter_item_mr_day.AsyncInsertRegister_All().execute(item.getReadDatePlan().toString(),userid);

                    Intent i = new Intent(activity, Activity_MrSelectSearchMaster.class);
                    Bundle b = new Bundle();
                    b.putString("key", item.getReadDatePlan().toString());
                    b.putString("ReadDatePlan",item.getReadDatePlan().toString());
                    b.putString("STATE_OFFLINE",item.getStatus().toString());
                    b.putString("STATE", "1");
                    i.putExtras(b);
                    activity.startActivity(i);

                }
            });
            holder.total.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Log.i("Test","Inseartxx") ;
                  //  new  Adapter_item_mr_day.AsyncInsertRegister_All().execute(item.getReadDatePlan().toString(),userid);

                    Intent i = new Intent(activity, Activity_MrSelectSearchMaster.class);
                    Bundle b = new Bundle();
                    b.putString("key", item.getReadDatePlan().toString());
                    b.putString("ReadDatePlan",item.getReadDatePlan().toString());
                    b.putString("STATE_OFFLINE",item.getStatus().toString());
                    b.putString("STATE", "9");
                    i.putExtras(b);
                    activity.startActivity(i);


                }
            });
            /*
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Activity_MrSelectSearchMaster.class);
                    Bundle b = new Bundle();
                    b.putString("key", item.getReadDatePlan().toString());
                    i.putExtras(b);
                    activity.startActivity(i);

                }
            });
            */
        }

        String ReadPland = item.getReadDatePlan().toString() ;
        Log.i("ReadPland",ReadPland);
        ReadPland = ReadPland.substring(6,8)+"/"+ReadPland.substring(4,6)+"/"+ReadPland.substring(0,4) ;
        holder.ReadDatePlan.setText(ReadPland);
        holder.total.setText(item.getTotal().toString());
        holder.readed.setText(item.getReaded().toString());
        holder.notread.setText(item.getNotread().toString());

        return convertView;
    }

    class AsyncInsertRegister_All extends AsyncTask<String, String, JSONArray> {
        ProgressDialog pdLoading ;

        HttpURLConnection conn;
        URL url = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading = new ProgressDialog(mContext);
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
            final myDBClass myDb = new myDBClass(activity.getApplication());
            if (myDb.DeleteRegisterAllData()) {
                for (int i = 0; i < result.length(); i++) {
                    try {
                        JSONObject c = result.getJSONObject(i);

                        long flg1 = myDb.Insert_MR_REGISTER_ALL(c.getString("READ_DATE_PLAN"), c.getString("READ_PLAN"), c.getString("MRU"), c.getString("PEANO"), c.getString("CA"), c.getString("REGISTER_GROUP"), c.getString("MR_REASON"), c.getString("REGIS_NO"), c.getString("REGIS_CODE"), c.getString("REGIS_TEXT"), c.getString("REG_TYPE"), c.getString("UMR"), c.getString("PD"), c.getString("DP"), c.getString("MRO"), c.getString("NOW_READ"), c.getString("MAX_READ"), c.getString("MIN_READ"), c.getString("READED_TIME"), c.getString("INPUT"), c.getString("BEFORE_DOT"), c.getString("AFTER_DOT"), c.getString("NOTE_READ"), c.getString("CHECK_OPERATION"), c.getString("ERROR_MAX"), c.getString("ERROR_MIN"), c.getString("CHECK_STATE"), c.getString("BEFORE_READ"));
                        Log.i("Insert",c.getString("PEANO"));
                        //  long flg1 = myDb.Insert_MR_METERMODEL("x","x","x","x","x","x","x","x");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Finisth", "Finisth");

            }
            pdLoading.dismiss();
        }
    }
}
