package com.its.peac1.pea_mr_big;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.Map;

public class Activity_map extends AppCompatActivity implements OnMapReadyCallback {

    String Username = "" ;
    String read_date_plan ,STATE_OFFLINE ;
    GoogleMap mMap ;
    Map<String, Bundle_marker> mMarkerMap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final myDBClass myDb = new myDBClass(this);
        myDb.getWritableDatabase() ;
        Username = myDb.SelectUserId() ;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(this);
        mMarkerMap = new HashMap<>();

    }

    public class Bundle_marker
    {
        String PEANO ;
        String CA ;
        String METERTYPE ;
        String Name ;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPEANO() {
            return PEANO;
        }

        public String getCA() {
            return CA;
        }

        public String getMETERTYPE() {
            return METERTYPE;
        }

        public void setPEANO(String PEANO) {
            this.PEANO = PEANO;
        }

        public void setCA(String CA) {
            this.CA = CA;
        }

        public void setMETERTYPE(String METERTYPE) {
            this.METERTYPE = METERTYPE;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap ;
        Bundle b = getIntent().getExtras();
        Log.i("Test","test");
        if(b != null) {
            read_date_plan = b.getString("ReadDatePlan");
            STATE_OFFLINE = b.getString("STATE_OFFLINE");
            Log.i("STATE_OFFLINE",STATE_OFFLINE) ;

            try {
                new Activity_map.AsyncLogin().execute(mMap);
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }


        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(14.5896026, 101.0251596);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public class Wrapper
    {
        public GoogleMap getGoogleMap() {
            return googleMap;
        }

        public void setGoogleMap(GoogleMap googleMap) {
            this.googleMap = googleMap;
        }

        public JSONArray getResult() {
            return result;
        }

        public void setResult(JSONArray result) {
            this.result = result;
        }

        public GoogleMap googleMap;
        public JSONArray result;
    }
    private class AsyncLogin extends AsyncTask<GoogleMap, String, Wrapper> {
        URL url = null;
        JSONObject c ;
        HttpURLConnection conn;
        ProgressDialog pdLoading = new ProgressDialog(Activity_map.this);
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
        protected Wrapper doInBackground(GoogleMap... params) {
            JSONArray network = null;

            try {
                String url_ = "http://extranet.pea.co.th/peap3/android/MR_mrSelectSearchMaster.ashx?reader=" + Username + "&read_date_plan=" + read_date_plan + "&state=9";
                Log.i("URL", url_);
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

            Wrapper w = new Wrapper();
            w.googleMap = params[0];
            w.result =network ;
            return w ;

        }

        @Override
        protected void onPostExecute(Wrapper wrapper) {

            JSONArray result = wrapper.getResult();

            if (result == null) {
             //   Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA", STATE_OFFLINE, "NO DATA", "NO DATA", "NO DATA"));
            } else {
                try {
                    int count = 0;
                    for (int i = 0; i < result.length(); i++) {
                         c = result.getJSONObject(i);
                  //      Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("CA : " + c.getString("CA"), c.getString("PEANO"), "ชื่อ : " + c.getString("CUST_NAME"), "ที่อยู่ : " + c.getString("CONOBJ_ADDRESS"), read_date_plan, "MRU : " + c.getString("MRU"), STATE_OFFLINE, c.getString("METERTYPE"), c.getString("latitude_x"), c.getString("longitude_x")));

                      if(c.getString("latitude_x").equals(""))
                      {

                      }
                      else
                      {
                          count = count +1 ;

                          LatLng sydney = new LatLng( Double.parseDouble(c.getString("latitude_x").toString()), Double.parseDouble(c.getString("longitude_x").toString()));

                          Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                          Bitmap bmp = Bitmap.createBitmap(200, 60, conf);
                          Canvas canvas1 = new Canvas(bmp);

// paint defines the text color, stroke width and size
                          Paint color = new Paint();
                          color.setTextSize(26);
                          color.setAlpha(100);
                          color.setHinting(2);
                          color.setColor(Color.BLACK);

// modify canvas
                          canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                                  R.mipmap.marker3), 0,0, color);
                          canvas1.drawText(Integer.toString(count), 30, 50, color);

                          Marker marker = mMap.addMarker
                                  (new MarkerOptions()
                                          .position(sydney)
                                          .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                                        //  .title(c.getString("CA"))
                                          .anchor(0.5f, 1));
                     //     mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                          final Bundle_marker b_marker = new Bundle_marker() ;
                          b_marker.setCA(c.getString("CA").toString());
                          b_marker.setMETERTYPE(c.getString("METERTYPE").toString());
                          b_marker.setPEANO(c.getString("PEANO").toString());
                          b_marker.setName(""+c.getString("CUST_NAME")+ "ที่อยู่ : "+c.getString("CONOBJ_ADDRESS"));
                          //  b_marker.setMETERTYPE(c.getString("METERTYPE"));
                          mMarkerMap.put(marker.getId() ,b_marker ) ;
                          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8));
                          mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                                        @Override
                                                        public View getInfoWindow(Marker marker) {
                                                            return null;
                                                        }

                                                        @Override
                                                        public View getInfoContents(Marker marker) {
                                                            View infoWindow = getLayoutInflater().inflate(R.layout.map_marker, null);



                                                            try {
                                                                Bundle_marker b_markery = mMarkerMap.get(marker.getId()) ;
                                                                TextView textViewPEA = ((TextView) infoWindow.findViewById(R.id.textViewPEA));
                                                                textViewPEA.setText("PEA : "+b_markery.getPEANO());
                                                                TextView textViewCA = ((TextView) infoWindow.findViewById(R.id.textViewCA));
                                                                textViewCA.setText("CA : " +b_markery.getCA());
                                                                TextView textViewName = ((TextView) infoWindow.findViewById(R.id.textViewName));
                                                                textViewName.setText("ชื่อ : "+b_markery.getName());
                                                                TextView textViewTYPE = ((TextView) infoWindow.findViewById(R.id.textViewTYPE));
                                                                textViewTYPE.setText("ประเภทมิเตอร์ :"+ b_markery.getMETERTYPE());
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }



                                                            return infoWindow ;
                                                        }
                                                    });

                          mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                              @Override
                              public void onInfoWindowClick(Marker marker) {
                                  Intent i = new Intent(Activity_map.this, Activity_Mr_Register.class);
                                  Bundle b = new Bundle();
                                  try {
                                      Bundle_marker b_markerx = mMarkerMap.get(marker.getId()) ;
                                      b.putString("METERTYPE",b_markerx.getMETERTYPE());
                                      b.putString("Pea_meter",b_markerx.getPEANO());
                                      Log.i("setOnInfoWinCA",b_markerx.getCA()) ;
                                      Log.i("setOnInfogetPEANO",b_markerx.getPEANO()) ;
                                      Log.i("setOowgetMETERTYPE",b_markerx.getMETERTYPE()) ;
                                      b.putString("ReadDatePlan",read_date_plan );
                                      b.putString("State_offline",STATE_OFFLINE );
                                      b.putString("Pea_Model","" );
                                      i.putExtras(b);
                                      startActivity(i);
                                  } catch (Exception e) {
                                      Toast.makeText(Activity_map.this, e.toString(), Toast.LENGTH_LONG).show();
                                      //   e.printStackTrace();
                                  }

                              }
                          });
                          /*
                                  mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                              @Override
                              public boolean onMarkerClick(Marker marker) {
                                  Intent i = new Intent(Activity_map.this, Activity_Mr_Register.class);
                                  Bundle b = new Bundle();
                                  try {
                                      b.putString("METERTYPE",c.getString("METERTYPE").toString());
                                      b.putString("Pea_meter", c.getString("PEANO"));
                                      Log.i("MetertypeMetertype",c.getString("METERTYPE").toString()) ;
                                      b.putString("ReadDatePlan",read_date_plan );
                                      b.putString("State_offline",STATE_OFFLINE );
                                      b.putString("Pea_Model","" );
                                      i.putExtras(b);
                                      startActivity(i);
                                  } catch (JSONException e) {
                                      Toast.makeText(Activity_map.this, e.toString(), Toast.LENGTH_LONG).show();
                                   //   e.printStackTrace();
                                  }


                                  return false;
                              }
                          });
                          */
                          Log.i("c.getString(CA)", c.getString("CA"));
                      }
                        Log.i("c.getString(latitude_x)", c.getString("latitude_x"));

                    }
                } catch (Exception e) {
                  //  Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA", STATE_OFFLINE, "NO DATA", "NO DATA", "NO DATA"));
                }
                ////////////////////////////////////////////////////////////////////////////////


            }

            pdLoading.dismiss();
        }
    }
}
