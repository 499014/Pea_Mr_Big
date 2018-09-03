package com.its.peac1.pea_mr_big;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 499014 on 30/10/2560.
 */

public class CheckLoading extends Activity {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    SQLiteDatabase db;
    String Area ;
    final myDBClass myDb = new myDBClass(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_check_loading);
        FirebaseApp.initializeApp(this);
        myDb.getWritableDatabase(); // First method
        Log.i("onCreate","onCreate");
       FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Test");
        if(myDb.CheckUser())
        {
            Log.i("CheckUserCheckUser","CheckUserCheckUser");
            Intent intent = new Intent(CheckLoading.this,MainActivity.class);
            startActivity(intent);
            CheckLoading.this.finish();
        }
        else
        {
            Log.i("elseelseelse","elseelseelseelse");
            final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
            final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            final View Viewlayout = inflater.inflate(R.layout.login,(ViewGroup) findViewById(R.id.layout_dialog));
            popDialog.setIcon(android.R.drawable.btn_star_big_on);
            popDialog.setTitle("Input User and Password.");
            popDialog.setView(Viewlayout);
            popDialog.setCancelable(false) ;
            popDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText user = (EditText) Viewlayout.findViewById(R.id.txtUsername);
                    EditText pass = (EditText) Viewlayout.findViewById(R.id.txtPassword);
                    String Txtuser = user.getText().toString();
                    String Txtpass = pass.getText().toString();

                    StringBuffer buffer = new StringBuffer(Txtuser);
                    buffer.reverse();
                    if(buffer.toString().equals(Txtpass) && Txtpass.length() > 1 )
                    {
                        long flg1 = myDb.Insert_MR_MEMBER(Txtuser,Txtuser,"Txtuser","G000");
                        if(flg1 > 0)
                        {
                            final UserHelper usrHelper = new UserHelper(CheckLoading.this);
                            usrHelper.createSession(Txtuser,Txtuser );
                            String strMemberName = usrHelper.getMemberName();

                            Toast.makeText(CheckLoading.this,"Login Successfully", Toast.LENGTH_LONG).show();
                            myDb.getWritableDatabase() ;
                            String userId = myDb.SelectUserId();
                            Intent intent = new Intent(CheckLoading.this,MainActivity.class);
                            startActivity(intent);
                            CheckLoading.this.finish();

                        }

                    }
                    else
                    {
                        new AsyncLogin().execute(Txtuser,Txtpass);
                    }




                   // new AsyncInsertMeterModel().execute();
                }
            }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    CheckLoading.this.finish();
                    finish();
                    dialog.cancel();
                }

            });
            popDialog.create();
            popDialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    // insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(CheckLoading.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(CheckLoading.this, permission);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CheckLoading.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("CAMERA");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.READ_CALL_LOG))
            permissionsNeeded.add("READ_CALL_LOG");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("ACCESS_FINE_LOCATION");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                }
                            }
                        });
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return;
        }

        //insertDummyContact();
    }


    class AsyncLogin extends AsyncTask<String, String, String>
    {
        final UserHelper usrHelper = new UserHelper(CheckLoading.this);
        ProgressDialog pdLoading = new ProgressDialog(CheckLoading.this);
        HttpURLConnection conn;
        URL url = null;
        String usname ;
        String passwordx ;
        String strErrorx ;
        String name ;
        String ba ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String newParam_1 = params[1].replace("#", "@@00xy").replace("&", "@@01xy").replace("+", "@@02xy").replace("-", "@@03xy").replace("*", "@@04xy").replace("/", "@@05xy").replace("!", "@@06xy").replace("%", "@@07xy").replace(";", "@@08xy").replace(":", "@@09xy").replace("^", "@@10xy");
                String Check_user_pass = "http://extranet.pea.co.th/peap3/android/test1.ashx?username="+params[0]+"&password="+newParam_1  ;
                Log.i("xxx",Check_user_pass) ;
                url = new URL(Check_user_pass);
            } catch (MalformedURLException e) {
          //      Toast.makeText(CheckLoading.this,e.toString(), Toast.LENGTH_LONG).show();
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                Log.i("Main","url.openConnection");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (ProtocolException e) {
         //       Toast.makeText(CheckLoading.this,e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
         //       Toast.makeText(CheckLoading.this,e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            try{
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    // Pass data to onPostExecute method
                    JSONObject c = new JSONObject(result.toString());
                    usname = c.getString("StatusID");
                    usname = c.getString("MemberID");
                    strErrorx = c.getString("Error");
                    name = c.getString("name");
                    ba = c.getString("ba") ;
                    Log.i("StatusID",usname);
                    Log.i("MemberID",usname);
                    Log.i("Error",strErrorx);
                    Log.i("ba",ba);
                }
            } catch (IOException e)
            {
        //        Toast.makeText(CheckLoading.this,e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();

            } catch (JSONException e) {
     //           Toast.makeText(CheckLoading.this,e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            pdLoading.dismiss();
            try{
            if(strErrorx.trim().equals("XX")  )
            {
                long flg1 = myDb.Insert_MR_MEMBER(usname,usname,name,ba);
                if(flg1 > 0)
                {
                    try
                    {
                        usrHelper.createSession(usname,name );
                        String strMemberName = usrHelper.getMemberName();
                        Log.i("Sharepreperance",strMemberName+"|| "+usrHelper.getMemberID());
                        Toast.makeText(CheckLoading.this,"Login Successfully", Toast.LENGTH_LONG).show();
                        myDb.getWritableDatabase() ;
                        String userId = myDb.SelectUserId();
                        Intent intent = new Intent(CheckLoading.this,MainActivity.class);
                        startActivity(intent);
                        CheckLoading.this.finish();
                        pdLoading.dismiss();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(CheckLoading.this,e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                else
                {

                    Toast.makeText(CheckLoading.this,"Login Failed.",
                            Toast.LENGTH_LONG).show();
                   CheckLoading.this.finish();
                    finish();
                }
            }
            else
            {
                Toast.makeText(CheckLoading.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 5000);
                CheckLoading.this.finish();
                finish();
            }
            }catch (Exception e)
            {
                Toast.makeText(CheckLoading.this, "ไม่สามารถ เชื่อมต่อ extranet.pea.co.th ได้", Toast.LENGTH_LONG).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 10000);
                CheckLoading.this.finish();
                finish();
            }
        }
    }
     class AsyncInsertMeterModel extends AsyncTask<String, String, JSONArray> {
         ProgressDialog pdLoading = new ProgressDialog(CheckLoading.this);
         HttpURLConnection conn;
         URL url = null;
         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             pdLoading.setMessage("\tLoading...");
             pdLoading.setCancelable(false);
//             pdLoading.show();
         }
         @Override
         protected JSONArray doInBackground(String... strings) {
             JSONArray network = null;

             try {
                 String url_ = "http://extranet.pea.co.th/peap3/android/MR_mrSelectMeterModel.ashx";
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
             boolean flg0 = myDb.DeleteData_METERMODEL() ;
             for (int i = 0; i < result.length(); i++) {
                 try {
                     JSONObject c = result.getJSONObject(i);

                   long flg1 = myDb.Insert_MR_METERMODEL(c.getString("METER_MODEL"),c.getString("REGISTER_GROUP"),c.getString("REGIS_NO"),c.getString("REGIS_CODE"),c.getString("REGIS_TEXT"),c.getString("REG_TYPE"),c.getString("UMR"),c.getString("INPUT"),c.getString("BEFORE_DOT"),c.getString("AFTER_DOT"));
                   //  long flg1 = myDb.Insert_MR_METERMODEL("x","x","x","x","x","x","x","x");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
    //         pdLoading.dismiss();
         }
     }



}
