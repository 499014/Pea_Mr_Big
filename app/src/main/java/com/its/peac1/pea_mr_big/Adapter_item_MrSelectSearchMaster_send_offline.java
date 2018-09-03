package com.its.peac1.pea_mr_big;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.gson.JsonArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Adapter_item_MrSelectSearchMaster_send_offline extends BaseAdapter {
    public ArrayList<Item_MrSelectSearchMaster_send_offline> Item_MrSelectSearchMaster_send_offline;
    Activity activity;
    String url_ ,pea_meter ,readateplan ;
    public Adapter_item_MrSelectSearchMaster_send_offline(Activity activity, ArrayList<Item_MrSelectSearchMaster_send_offline> Item_MrSelectSearchMaster_send_offline)
    {
        super();
        this.Item_MrSelectSearchMaster_send_offline = Item_MrSelectSearchMaster_send_offline;
        this.activity = activity ;


    }
    @Override
    public int getCount() {
        return Item_MrSelectSearchMaster_send_offline.size();
    }

    @Override
    public Object getItem(int position) {
        return Item_MrSelectSearchMaster_send_offline.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        TextView ca;
        TextView pea;
        TextView name;
        TextView mru ;
        TextView address;
        String ReadDatePlan ;
        RelativeLayout RelativeLayout_xxx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Adapter_item_MrSelectSearchMaster_send_offline.ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_mr_select_serach_master, null);
            holder = new Adapter_item_MrSelectSearchMaster_send_offline.ViewHolder();
            holder.RelativeLayout_xxx = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout_xxx);
            holder.ca = (TextView) convertView.findViewById(R.id.ca);
            holder.pea = (TextView) convertView.findViewById(R.id.pea);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.mru = (TextView) convertView.findViewById(R.id.mru);
            //   holder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        } else {
            holder = (Adapter_item_MrSelectSearchMaster_send_offline.ViewHolder) convertView.getTag();
        }

        final  Item_MrSelectSearchMaster_send_offline item = Item_MrSelectSearchMaster_send_offline.get(position);

        if(!item.getPea_meter().toString().equals("")) {
            convertView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    url_ = item.geturl() ;
                    Log.i("URL" ,item.geturl()) ;
                    Log.i("getReadDatePlan" ,item.getCa()) ;
                    Log.i("getPea_meter" ,item.getPea_meter()) ;
                    pea_meter =item.getPea_meter() ;
                    readateplan = item.getCa() ;

                    try {
                        if(!item.getPea_meter().toString().equals("NO DATA"))
                        {
                           // new Mr_UpdateRegisterxxx().execute(item.geturl());
try
{
                            Ion.with(activity)
                                    .load("http://lai.pea.co.th/499014/")
                                    .asJsonArray()
                                   // .withResponse()
                                   // .asJsonObject()
                                    .setCallback(new FutureCallback<JsonArray>() {
                                        @Override

                                        public void onCompleted(Exception e, JsonArray result) {
                                            try{
                                                String xx = "xx";

                                                for (int i = 0; i < result.size(); i++) {
                                                    JsonElement elem = result.get(i);
                                                    JsonObject obj = elem.getAsJsonObject();
                                                    xx = xx + obj.get("Name").getAsString() ;
                                                }

                                                result.toString();
                                                Toast.makeText(activity, xx ,
                                                        Toast.LENGTH_LONG).show();
                                            }
                                            catch (Exception x)
                                            {
                                                Toast.makeText(activity, x.toString() ,
                                                        Toast.LENGTH_LONG).show();
                                            }



                                        }
                                    });
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(activity, e.toString() ,
                                    Toast.LENGTH_LONG).show();
                        }
                        }

                    }
                    catch (Exception e)
                    {

                        Toast.makeText(activity, "ผิดพลาด 01",
                                Toast.LENGTH_LONG).show();

                    }
                    /*
                    Intent i = new Intent(activity, Activity_Mr_Register.class);
                    Bundle b = new Bundle();
                    b.putString("Pea_meter", item.getPea_meter().toString());
                    //      Log.i("ReadDatePlan",item.getReadDatePlan().toString()) ;
                    b.putString("ReadDatePlan",item.getReadDatePlan().toString() );
                    b.putString("State_offline",item.getState_offline().toString() );
                    b.putString("Pea_Model","" );
                    i.putExtras(b);
                    activity.startActivity(i);
                    */
                }
            });
        }
        if (position % 2 == 0)
        {
            convertView.setBackgroundResource(R.color.color_1) ;
            //    holder.ca.setTextColor(Color.GRAY);
            //       holder.pea.setTextColor(Color.GRAY);
            //     holder.name.setTextColor(Color.GRAY);
            //holder.RelativeLayout_xxx.setBackgroundColor(Color.GREEN);

        }
        else
        {
            //    holder.ca.setTextColor(Color.BLACK);
            //     holder.pea.setTextColor(Color.BLACK);
            //    holder.name.setTextColor(Color.BLACK);
            convertView.setBackgroundResource(R.color.color_2) ;

        }

        try
        {
            holder.ca.setText(item.getCa().substring(6,8)+"/"+item.getCa().substring(4,6)+"/"+item.getCa().substring(0,4));
        }
        catch (Exception e){
            holder.ca.setText(item.getCa() ) ;
        }

        holder.pea.setText("PEANO : "+ item.getPea_meter().toString());
        holder.name.setText(item.getName().toString());
        holder.mru.setText(item.getMru().toString());
        //     holder.address.setText(item.getAddress().toString());

        return convertView;

    }

    class Mr_UpdateRegisterxxx extends AsyncTask<String, String, JSONArray>
    {

        URL url = null;

        HttpURLConnection conn;
        ProgressDialog pdLoadingx = new ProgressDialog(activity );
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;
        String url_ ;
        String message ;
        private LayoutInflater inflater;
        private ViewGroup container;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            message = "ผิดพลาด" ;
            pdLoadingx.setMessage("\tLoading...");
            pdLoadingx.setCancelable(false);
            pdLoadingx.show();
        }
        @Override
        protected JSONArray doInBackground(String... params) {

            JSONArray network = null;
            try {
                url_ = params[0];
                url_ = url_.replace(" ","%20");

                url = new URL(url_);
                Log.i("URLXXX",url_) ;
            } catch (MalformedURLException e) {
               // message = "ผิดพลาด" ;

                e.printStackTrace();
                Log.i("ififif", "if0");
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
               // message = "ผิดพลาด" ;
                e.printStackTrace();
            } catch (IOException e) {
             //   message = "ผิดพลาด" ;



                e.printStackTrace();
            }
            try {
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    final myDBClass myDb = new myDBClass(activity);
                    myDb.getWritableDatabase();
                    myDb.UpdateMR_OFFLINE_DATA(pea_meter,readateplan);
                    message = "เพิ่มข้อมูลเรียบร้อย" ;

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    JSONObject jsonObj;
                    try {
                        jsonObj = new JSONObject(reader.readLine());
                        network = jsonObj.getJSONArray("network");
                    } catch (Exception e) {
                      //  message = "ผิดพลาด" ;
                        jsonObj = null;
                        network = null;
                    }


                }
                else
                {


                }


            } catch (IOException e) {
                Log.i("catch", "catch");






                network = null;
                e.printStackTrace();


            } finally {
                conn.disconnect();
            }


            return network;


        }

        @Override
        protected void onPostExecute(JSONArray result) {


            pdLoadingx.dismiss();
            Toast.makeText(activity, message ,
                    Toast.LENGTH_LONG).show();

        }
    }
}
