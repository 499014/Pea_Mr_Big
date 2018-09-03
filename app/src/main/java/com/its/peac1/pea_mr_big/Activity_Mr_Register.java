package com.its.peac1.pea_mr_big;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Activity_Mr_Register extends AppCompatActivity implements  ConnectivityReceiver.ConnectivityReceiverListener  {
    ArrayList<EditText> etCollection = new ArrayList<EditText>();
    Boolean Read_STATE = false;
    EditText etxt_Username;
    EditText etxt_Mru;
    EditText ba_name;
    EditText pea_no;
    String Pea_number ,ReadDate_plan ;
    EditText ZDMR209;
    EditText Txt_Result ;
    TextView textView1 ;
    int idLastChild ;
    RelativeLayout layout ;
    Button button;
    int idButton ;
    int IdRegister ;
    Map<String, EditText> Map_Edit_text ;
    Map<String, String > Map_String ;
    String Pea_meter ,ReadDatePlan ,userid,Pea_Model,State_offline ,METERTYPE,  check_operation_1;
    Map<String,Item_Register> Item_Reagister ;
    Map<String,Item_Result> Map_Result ;
    Button button_send , aa;
    SwitchCompat EX1 ,EX2 ,EX3 ,EX4 , EX88 ,voice_swite ;
    EditText IN1 ,IN2 ,IN4 ,IN5,IN6,TXT ;
    RadioGroup IN3 ;
    Point p;
    private final static int REQUEST_VOICE_RECOGNITION = 10001;

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);

    }
    public void startVoiceRecognitionActivity() {
        try{

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        startActivityForResult(intent, REQUEST_VOICE_RECOGNITION);
        }
          catch (Exception e)
        {}
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult","onActivityResult") ;
        try{
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.i("Resultxx",matches.get(0).toString())  ;
        }
        catch (Exception e)
        {

        }

        if (requestCode == REQUEST_VOICE_RECOGNITION &&
                resultCode == RESULT_OK &&
                data != null) {
           // ArrayList<String> resultList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            try {
                ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                int curent_focus_id = getCurrentFocus().getId();
                EditText now_edite = ((EditText) findViewById(curent_focus_id));
                String now_key = matches.get(0).toString();
                now_edite.setText(now_key.trim());
                for (int i = 0; i < matches.size(); i++) {
                    Log.i("matches", matches.get(i).toString());
                }
                ((EditText) findViewById(curent_focus_id + 2)).requestFocus();

            }
            catch (Exception e)
            {

            }/*
            if(now_key.toLowerCase().contains("ผ่าน"))
            {
                ((EditText) findViewById(curent_focus_id+2)).requestFocus() ;

            }

*/

        }
        // ผลลัพธ์ที่ส่งกลับมา
    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);

    }
    private void MR_MEMBER_HEDER_MASTER(String Pea_Meter )
    {
        try {
            final myDBClass myDb2 = new myDBClass(this);
            myDb2.getWritableDatabase();
            Log.i("Pea_Meterxxx",Pea_Meter);
            Cursor CurData = myDb2.SelectMR_MEMBER_HEDER_MASTER(Pea_Meter) ;;
            int i = 0 ;
            while (!CurData.isAfterLast()) {

                int count = 0;
                etxt_Username.setText(CurData.getString(CurData.getColumnIndex("CUST_NAME")));
                etxt_Mru.setText("สาย : " + CurData.getString(CurData.getColumnIndex("MRO")) + " Ca : " + CurData.getString(CurData.getColumnIndex("CA")));
                ba_name.setText("การไฟฟ้า : " + "");
                pea_no.setText("PEA NO : " + CurData.getString(CurData.getColumnIndex("PEANO")));
                CurData.moveToNext();
                i++ ;

            }
            CurData.close();
            myDb2.close();
        } catch (Exception e) {
            Log.i(" pea_no.setText", e.toString());
        }


    }
    private void select_register(String Pea_Meter )
    {
        String REGIS_TEXT ;
        int check_dot ;
        Map_Edit_text = new HashMap<>();
        Map_String = new HashMap<>();
        int count = 0;
        check_dot =count ;
        final myDBClass myDb2 = new myDBClass(this);
        myDb2.getWritableDatabase();
        Log.i("Pea_Meterxxx",Pea_Meter);
        Cursor CurData = myDb2.SelectRegisterAll(Pea_Meter) ;;
        int i = 0 ;
        while (!CurData.isAfterLast()) {
           // if(!CurData.getString(CurData.getColumnIndex("REGISTER_GROUP")).trim().equals("OTHER"))
            if (CurData.getString(CurData.getColumnIndex("REGISTER_GROUP")).trim().toString().substring(0,2).equals("TO") || CurData.getString(CurData.getColumnIndex("REGISTER_GROUP")).trim().toString().equals("DEMAND")|| CurData.getString(CurData.getColumnIndex("REGISTER_GROUP")).trim().toString().equals("NORMAL") )///////////////////////////////*************************Edit***************

            {
                if (!CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim().toString().equals("00xxx") ||  CurData.getString(CurData.getColumnIndex("REGISTER_GROUP")).trim().toString().equals("DEMAND"))
                {
                    final int total_digit ;
                    final int after_dot ;
                    final int before_dot ;
                    final int dot = i + 2;
                    IdRegister = IdRegister + 1;
                    boolean yy = true ;
                    try {
                        int x  =Integer.parseInt(CurData.getString(CurData.getColumnIndex("BEFORE_DOT")).trim().toString().trim()) ;
                        yy = true ;
                    }
                    catch (Exception e)
                    {
                        yy =false ;
                    }
                    if(yy)
                    {
                        before_dot = Integer.parseInt(CurData.getString(CurData.getColumnIndex("BEFORE_DOT")).toString().trim()) ;
                    }
                    else
                    {
                        before_dot = 24 ;
                    }
                    total_digit = before_dot + 5+1 ;

                    TextView textView_detail = new TextView(Activity_Mr_Register.this);
                    REGIS_TEXT = CurData.getString(CurData.getColumnIndex("REGIS_TEXT")).toString().trim() ;
                    textView_detail.setText(Html.fromHtml(" <font s color='#C71585'><b>"+CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim()+"</b></font> " + CurData.getString(CurData.getColumnIndex("REGIS_TEXT")).toString().trim()+""));
                    textView_detail.setTextSize(18);
                    RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
                    params1.addRule(RelativeLayout.BELOW, idLastChild);
                    textView_detail.setLayoutParams(params1);
                    idLastChild = idLastChild +1 ;
                    // textView_detail.setLayoutParams(params1);
                    textView_detail.setId(idLastChild);
                    layout.addView(textView_detail);



                    final EditText textview = new EditText(Activity_Mr_Register.this);
                    try{
                        String Now_read = CurData.getString(CurData.getColumnIndex("NOW_READ")).toString() ;
                        if( CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("TIME"))
                        {
                            Now_read = Now_read.substring(0,2)+":"+Now_read.substring(2,4);
                        }
                        else if ( CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("DATE"))
                        {
                            Now_read = Now_read.substring(6,8)+"/"+Now_read.substring(4,6)+"/"+Now_read.substring(0,4);
                        }
                        else if( CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("DATETIME"))
                        {
                            Now_read = Now_read.substring(6,8)+"/"+Now_read.substring(4,6)+"/"+Now_read.substring(0,4)+"-"+Now_read.substring(8,10)+":"+Now_read.substring(10,12);
                        }
                        else if( CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("NUMBER"))
                        {
                            String divi =  CurData.getString(CurData.getColumnIndex("AFTER_DOT")).toString().toString().trim() ;
                            double Now_read_D = Double.parseDouble(Now_read);
                            int divial = 1 ;
                            switch (divi) {
                                case "1":
                                    divial = 10;
                                    break;
                                case "2":
                                    divial = 100;
                                    break;
                                case "3":
                                    divial = 1000;
                                    break;
                                case "4":
                                    divial = 10000;
                                    break;
                                case "5":
                                    divial = 100000;
                                    break;
                                case "6":
                                    divial = 1000000;
                                    break;
                                case "7":
                                    divial = 10000000;
                                    break;
                                case "8":
                                    divial = 100000000;
                                    break;
                                case "9":
                                    divial = 1000000000;
                                    break;

                                default:
                                    divial = 1;
                                    break;
                            }
                            Now_read_D = Now_read_D/divial;
                            if(Now_read_D == (long) Now_read_D)
                            {
                                Now_read = String.format("%d",(long)Now_read_D);
                            }

                            else
                            {
                                Now_read = String.format("%s",Now_read_D);
                            }


                            Log.i("2222","CCCCCCCCCCC");

                            if(! CurData.getString(CurData.getColumnIndex("MIN_READ")).toString().equals(""))
                            {
                                if(! CurData.getString(CurData.getColumnIndex("MAX_READ")).toString().equals(""))
                                {
                                    Log.i("333","CCCCCCCCCCC");
                                    double min_read =   Double.parseDouble( CurData.getString(CurData.getColumnIndex("MIN_READ")).toString())/divial ;
                                    String Smin_read = "" ;
                                    String Smax_read = "" ;
                                    double max_read =   Double.parseDouble( CurData.getString(CurData.getColumnIndex("MAX_READ")).toString())/divial ;
                                    if(min_read == (long) min_read)
                                    {
                                        Smin_read = String.format("%d",(long)min_read);
                                    }

                                    else
                                    {
                                        Smin_read = String.format("%s",min_read);
                                    }
                                    if(max_read == (long) max_read)
                                    {
                                        Smax_read = String.format("%d",(long)max_read);
                                    }

                                    else
                                    {
                                        Smax_read = String.format("%s",max_read);
                                    }
                                    textview.setHint("MAX : "+Smax_read +" ; MIN :"+Smin_read);
                                    if(! CurData.getString(CurData.getColumnIndex("MAX_READ")).toString().equals("BEFORE_READ")) {
                                        double before_read =   Double.parseDouble(CurData.getString(CurData.getColumnIndex("BEFORE_READ")).toString())/divial ;
                                        String Sbefore_read = "" ;
                                        if(before_read == (long) before_read)
                                        {
                                            Sbefore_read = String.format("%d",(long)before_read);
                                        }

                                        else
                                        {
                                            Sbefore_read = String.format("%s",before_read);
                                        }
                                        textview.setHint("RANGE: "+Smax_read +"-"+Smin_read+ ", ครั้งก่อน: "+Sbefore_read);
                                    }


                                    Log.i("4444","ค่า MAX : "+max_read +" ; ค่า MIN :"+min_read);
                                }
                            }
                        }
                        else
                        {
                            Now_read = CurData.getString(CurData.getColumnIndex("NOW_READ")).toString() ;
                        }
                        if(!CurData.getString(CurData.getColumnIndex("NOW_READ")).toString().equals("000000000000000"))
                        {
                            textview.setText(Now_read);
                        }

                        Log.i(CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString() ,Now_read) ;


                    }
                    catch (Exception e)
                    {

                    }


                    Map_Edit_text.put(CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim(),textview) ;
                    //   Log.i("Map_Edit_text",c.getString("REGIS_CODE").toString());
                    Map_String.put(CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim(),CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim());
                    final String REGIS_CODE  = CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim();
                    TextWatcher narmal  = new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {
                            String str = textview.getText().toString();
                            //    Log.i("String_onTextChanged",str);
                            String result = s.toString().replaceAll(" ", "");
                            if (!s.toString().equals(result)) {
                                textview.setText(result);
                                textview.setSelection(result.length());
                                // alert the user
                            }
                        }
                        @Override
                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                        }
                        int flag_text=0;
                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {
                        }

                    } ;

                    TextWatcher time_format  = new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {

                            int charCount = 0;
                            char temp;
                            String str = textview.getText().toString();
                            for (int i = 0; i < str.length(); i++) {
                                if (str.charAt(i) == '.') {
                                    charCount = charCount + 1;
                                }
                            }
                            if (str.length() == before_dot ) {//len check for backspace
                                if (!str.contains(".")) {
                                    //           textview.append(".");
                                }


                            }
                        }
                        @Override
                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                            button_send.setEnabled(false);
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {
                            int charCount = 0;
                            char temp;
                            String str = textview.getText().toString();
                            for (int i = 0; i < str.length(); i++) {
                                if (str.charAt(i) == '.') {
                                    charCount = charCount + 1;
                                }
                            }
                            if (charCount > 1) {
                                textview.getText().delete(str.length() - 1, str.length());
                            }

                        }
                    } ;

                    if(!CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("TIME"))
                    {
                        textview.addTextChangedListener(narmal);
                    }
                    if(!CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("DATE"))
                    {
                        textview.addTextChangedListener(narmal);
                    }
                    if(!CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("DATETIME"))
                    {
                        textview.addTextChangedListener(narmal);
                    }

                    if(CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("TIME"))
                    {

                        textview.setShowSoftInputOnFocus(false);
                        //  myTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                        textview.setFocusable(false);
                        textview.setOnClickListener(new View.OnClickListener() {
                            int mHour ;
                            int mMinute ,mYear ,mMonth ,mDay ;

                            @Override
                            public void onClick(View v) {
                                button_send.setEnabled(false);
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_Mr_Register.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                String Sminute = minute+"" ;
                                                String SmHour = hourOfDay+"" ;
                                                if (minute < 10)
                                                {
                                                    Sminute = "0"+minute ;
                                                }
                                                if (hourOfDay < 10)
                                                {
                                                    SmHour = "0"+hourOfDay ;
                                                }

                                                textview.setText(SmHour + ":" + Sminute);
                                            }
                                        }, mHour, mMinute, true);

                                timePickerDialog.show();

                            }
                        });


                    }
                    else if(CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("DATE"))
                    {
                        button_send.setEnabled(false);
                        // textview.setShowSoftInputOnFocus(false);
                        textview.setFocusable(false);
                        //  textview.setEnabled(false);
                        textview.setOnClickListener(new View.OnClickListener() {
                            int mMinute, mYear, mMonth, mDay;
                            @Override
                            public void onClick(View v) {
                                textview.setEnabled(true);
                                final Calendar cc = Calendar.getInstance();
                                mYear =cc.get(Calendar.YEAR);
                                mMonth =cc.get(Calendar.MONTH);
                                mDay =cc.get(Calendar.DAY_OF_MONTH);
                                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {
                                                String sdayOfMonth = dayOfMonth+"" ;
                                                String smonthOfYear = monthOfYear+1+"" ;
                                                if(dayOfMonth < 10)
                                                {
                                                    sdayOfMonth = "0"+sdayOfMonth ;
                                                }
                                                if(monthOfYear < 10)
                                                {
                                                    smonthOfYear = "0"+smonthOfYear ;
                                                }

                                                textview.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }
                        });
                    }
                    else if( CurData.getString(CurData.getColumnIndex("INPUT")).toString().equals("DATETIME"))
                    {
                        button_send.setEnabled(false);
                        textview.setFocusable(false);
                        //  textview.setShowSoftInputOnFocus(false);
                        textview.setOnClickListener(new View.OnClickListener() {
                            int mHour ,mMinute, mYear, mMonth, mDay;
                            String xxdayofmount ;
                            @Override
                            public void onClick(View v) {

                                textview.setEnabled(true);
                                final Calendar cc = Calendar.getInstance();
                                mYear =cc.get(Calendar.YEAR);
                                mMonth =cc.get(Calendar.MONTH);
                                mDay =cc.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {

                                                String sdayOfMonth = dayOfMonth+"" ;
                                                String smonthOfYear = monthOfYear+1+"" ;
                                                if(dayOfMonth < 10)
                                                {
                                                    sdayOfMonth = "0"+sdayOfMonth ;
                                                }
                                                if(monthOfYear < 10)
                                                {
                                                    smonthOfYear = "0"+smonthOfYear ;
                                                }

                                                textview.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                                            }
                                        }, mYear, mMonth, mDay);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_Mr_Register.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                String Sminute = minute+"" ;
                                                String SmHour = hourOfDay+"" ;
                                                if (minute < 10)
                                                {
                                                    Sminute = "0"+minute ;
                                                }
                                                if (hourOfDay < 10)
                                                {
                                                    SmHour = "0"+hourOfDay ;
                                                }
                                                String sdayOfMonth = mDay+"" ;
                                                String smonthOfYear = mMonth+1+"" ;
                                                if(mDay < 10)
                                                {
                                                    sdayOfMonth = "0"+mDay ;
                                                }
                                                if(mMonth < 10)
                                                {
                                                    smonthOfYear = "0"+mMonth ;
                                                }
                                                mMonth =mMonth +1 ;

                                                // xxdayofmount = xxdayofmount + " - "+SmHour + ":" + Sminute ;
                                                textview.append(" - "+SmHour + ":" + Sminute);

                                            }
                                        }, mHour, mMinute, true);


                                timePickerDialog.show();
                                datePickerDialog.show();
                            }
                        });

                    }
                    else
                    {
                        textview.setInputType(3);
                    }

                    textview.setTextSize(16);
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                            android.widget.LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.BELOW, idLastChild);
                    textview.setLayoutParams(params2) ;

                    idLastChild = idLastChild +1 ;
                    textview.setId(idLastChild);
                    layout.addView(textview);
                    etCollection.add(textview);
                    count = count +1 ;
                    Item_Reagister.put(CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim(),new Item_Register(0,CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim(),CurData.getString(CurData.getColumnIndex("CHECK_OPERATION")).toString(),CurData.getString(CurData.getColumnIndex("ERROR_MAX")).toString(),CurData.getString(CurData.getColumnIndex("ERROR_MIN")).toString() ,CurData.getString(CurData.getColumnIndex("CHECK_STATE")).toString(),CurData.getString(CurData.getColumnIndex("MAX_READ")).toString(),CurData.getString(CurData.getColumnIndex("MIN_READ")).toString(),textview ,CurData.getString(CurData.getColumnIndex("BEFORE_READ")).toString().trim(),CurData.getString(CurData.getColumnIndex("AFTER_DOT")).toString().trim(),CurData.getString(CurData.getColumnIndex("BEFORE_DOT")).toString().trim(),CurData.getString(CurData.getColumnIndex("INPUT")).toString().trim()) ) ;

                }

                if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("888") || CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("88"))
                {
                    try
                    {
                        EX88 = (SwitchCompat) findViewById(R.id.switch_compat6);
                        if(CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString().equals("1"))
                        {

                            EX88.setChecked(true);
                        }
                        else
                        {
                            EX88.setChecked(false);
                        }
                    }
                    catch (Exception e)
                    {

                    }

                }


            }



            else {

                TextWatcher Disble_send = new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        button_send.setEnabled(false);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                        button_send.setEnabled(false);

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {

                        button_send.setEnabled(false);
                    }
                };

                Log.i("OTHEROTHER", CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim());

                if (!CurData.getString(CurData.getColumnIndex("NOW_READ")).toString().trim().equals("")) {


                    // SwitchCompat EX1 ,EX2 ,EX3 ,EX4 , EX88 ;
                    if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim().equals("EX1")) {
                        Log.i("EX1", CurData.getString(CurData.getColumnIndex("NOW_READ")).toString());
                        EX1 = (SwitchCompat) findViewById(R.id.switch_compat1);

                        if (CurData.getString(CurData.getColumnIndex("NOW_READ")).toString().trim().equals("1")) {

                            EX1.setChecked(true);
                        } else {
                            EX1.setChecked(false);
                        }
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).toString().trim().equals("EX2")) {
                        EX2 = (SwitchCompat) findViewById(R.id.switch_compat2);
                        if (CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString().equals("1")) {

                            EX2.setChecked(true);
                        } else {
                            EX2.setChecked(false);
                        }
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("EX3")) {
                        Log.i("EX3", CurData.getString(CurData.getColumnIndex("NOW_READ")).toString());
                        EX3 = (SwitchCompat) findViewById(R.id.switch_compat4);
                        if (CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString().equals("1")) {

                            EX3.setChecked(true);
                        } else {
                            EX3.setChecked(false);
                        }
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("EX4")) {
                        EX4 = (SwitchCompat) findViewById(R.id.switch_compat5);
                        if (CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString().equals("1")) {

                            EX4.setChecked(true);
                        } else {
                            EX4.setChecked(false);
                        }
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("888") || CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("88")) {
                        EX88 = (SwitchCompat) findViewById(R.id.switch_compat6);
                        if (CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString().equals("1")) {

                            EX88.setChecked(true);
                        } else {
                            EX88.setChecked(false);
                        }
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("IN1")) {
                        IN1 = (EditText) findViewById(R.id.etxt_Error_code);
                        IN1.setText(CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString());
                        IN1.addTextChangedListener(Disble_send);
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("IN2")) {
                        IN2 = (EditText) findViewById(R.id.etxt_Warning_code);
                        IN2.setText(CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString());
                        IN2.addTextChangedListener(Disble_send);
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("IN3")) {
                        IN3 = (RadioGroup) findViewById(R.id.radio_all);
                        String now_read_value = CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString().trim();
                        RadioButton radio_AB1 = (RadioButton) findViewById(R.id.radio_AB1);
                        RadioButton radio_AB2 = (RadioButton) findViewById(R.id.radio_AB2);
                        RadioButton radio_AB3 = (RadioButton) findViewById(R.id.radio_AB3);
                        if (now_read_value.equals("1")) {
                            radio_AB1.setChecked(true);
                        } else if (now_read_value.equals("2")) {
                            radio_AB2.setChecked(true);
                        } else if (now_read_value.equals("3")) {
                            radio_AB3.setChecked(true);
                        }

                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("IN4")) {
                        IN4 = (EditText) findViewById(R.id.etxt_time_read_pos);
                        IN4.setText(CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString());
                        IN4.addTextChangedListener(Disble_send);
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("IN5")) {
                        IN5 = (EditText) findViewById(R.id.etxt_Reset);
                        IN5.setText(CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString());
                        IN5.addTextChangedListener(Disble_send);
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("IN6")) {
                        IN6 = (EditText) findViewById(R.id.etxt_Reset_2);
                        IN6.setText(CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString());
                        IN6.addTextChangedListener(Disble_send);
                    } else if (CurData.getString(CurData.getColumnIndex("REGIS_CODE")).trim().toString().equals("TXT")) {
                        TXT = (EditText) findViewById(R.id.etxt_See_also);
                        TXT.setText(CurData.getString(CurData.getColumnIndex("NOW_READ")).trim().toString());
                        TXT.addTextChangedListener(Disble_send);

                    }
                }


            }
            //////////////////////////////////********************************/////////////////////////////////////

            CurData.moveToNext();
            i++ ;

        }
        CurData.close();
        myDb2.close();

    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "เชื่อมต่อระบบเครือข่ายได้";
            color = Color.GREEN;
        } else {
            message = "!!! ไม่สามารถเชื่อมต่อระบบเครือข่ายได้";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.button_check), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);

        snackbar.show();
    }
    private void showPopup(final Activity context, Point p,String detail) {
        int popupWidth = 200;
        int popupHeight = 150;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.pop_up_detail, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
      //  popup.setWidth(Matep);
     //   popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        TextView tv_popup_header = (TextView) layout.findViewById(R.id.textViewHeaderMenu);
        tv_popup_header.setText(Html.fromHtml("<font s color='#990000'><b>สูตร<font s color='#C71585'><b>"));
        TextView tv_popup = (TextView) layout.findViewById(R.id.tv_popup);
        tv_popup.setText(Html.fromHtml(detail));
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // Log.i("aaaaaa",""+  BigDecimal.valueOf(57.257).subtract(BigDecimal.valueOf(0.001) ));
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        // or other values
        Pea_Model = "";
        if(b != null) {
            ReadDatePlan = b.getString("ReadDatePlan");
            Pea_meter = b.getString("Pea_meter");
            Pea_Model = b.getString("Pea_Model");
            State_offline = b.getString("State_offline");
            METERTYPE = b.getString("METERTYPE");
            Pea_number = Pea_meter ;
            Log.i("METERTYPE",METERTYPE);
            Log.i("Pea_meter",Pea_meter );

        }



        if(METERTYPE.trim().equals("TOU")  )
        {

            setContentView(R.layout.activity_mr_register_main);
        }
        else
            {
                setContentView(R.layout.activity_mr_register_main_n_d);
            }


        etxt_Username = (EditText) findViewById(R.id.etxt_Username);
        //etxt_Username.setVisibility(View.INVISIBLE);
        etxt_Mru = (EditText) findViewById(R.id.etxt_Mru);
        ba_name = (EditText) findViewById(R.id.ba_name);
        pea_no = (EditText) findViewById(R.id.pea_no);
        ZDMR209 = (EditText) findViewById(R.id.ZDMR209);

        textView1 = (TextView) findViewById(R.id.etxt_Error_code);
        textView1.setVisibility(View.INVISIBLE);
        idLastChild = textView1.getId();

        button_send = (Button) findViewById(R.id.button_send);
        button_send.setEnabled(false);

/*
       ((TextView) findViewById(R.id.txt_steel)).setVisibility(View.INVISIBLE);
      ((LinearLayout) findViewById(R.id.LinearLayout_steel1)).setVisibility(View.INVISIBLE);
      ((LinearLayout) findViewById(R.id.LinearLayout_steel2)).setVisibility(View.INVISIBLE);
       ((TextView) findViewById(R.id.txt_Alternamte_Mode)).setVisibility(View.INVISIBLE);
        ((LinearLayout) findViewById(R.id.LinearLayout_steel3)).setVisibility(View.INVISIBLE);
        ((SwitchCompat) findViewById(R.id.switch_compat6)).setVisibility(View.INVISIBLE);
       ((TextView) findViewById(R.id.txt_steel)).setVisibility(View.INVISIBLE);
        ((LinearLayout) findViewById(R.id.LinearLayout_steel4)).setVisibility(View.INVISIBLE);
        ((LinearLayout) findViewById(R.id.LinearLayout_steel5)).setVisibility(View.INVISIBLE);
        ((LinearLayout) findViewById(R.id.LinearLayout_steel6)).setVisibility(View.INVISIBLE);

*/

        final Button button_check = (Button) findViewById(R.id.button_check);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        FloatingActionButton fab_mic = (FloatingActionButton) findViewById(R.id.fab_mic);
        fab_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                try {
                    startVoiceRecognitionActivity();
                }
                catch (Exception e)
                {

                }
         //       Toast.makeText(getApplication(),"Test Voice", Toast.LENGTH_LONG).show();
            }
        });
        FloatingActionButton fab_next = (FloatingActionButton) findViewById(R.id.fab_next);
        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
               int curent_focus_id = getCurrentFocus().getId() ;
               int next_focus_id = curent_focus_id + 2 ;
                Log.i("Next_focus_id" ,next_focus_id+"");
               try
               {
                   ((EditText) findViewById(next_focus_id)).requestFocus() ;
               }
               catch (Exception e)
               {

               }

              //  Toast.makeText(getApplication(),"Test Toast", Toast.LENGTH_LONG).show();
            }
        });

        checkConnection();
        /////////////////////////////******************************GPS//
        /*
        aa = (Button) findViewById(R.id.button_next);
        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int id = getCurrentFocus().getNextFocusForwardId();
             //   int id = getCurrentFocus().getId();
                Log.i("Next",id+"");
                   // findViewById(id).requestFocus();



                //.requestFocus()
            }
        });

*/
        ////////////////////************///////////  etxt_Error_code
        layout = (RelativeLayout) findViewById(R.id.Myjob2);
        button = (Button) findViewById(R.id.button_send);
        idButton = button.getId();
        idLastChild = idButton + 1000000;



     //   Log.i("ReadDatePlan",ReadDatePlan);
        Log.i("ReadDatePlanXXX_",ReadDatePlan);
        final myDBClass myDb = new myDBClass(this);
        myDb.getWritableDatabase();
        userid = myDb.SelectUserId() ;
        ReadDate_plan  = ReadDatePlan ;
/*
        Log.i("ReadDatePlan",ReadDatePlan) ;
        Log.i("Pea_meterPea_meter",Pea_meter) ;
        Log.i("useriduserid",userid) ;
*/


        Item_Reagister = new HashMap<>() ;
        Log.i("test0",Pea_Model.toString());
        if (Pea_Model.equals(""))
        {
            if(State_offline.trim().equals("Offline"))
            {
                Log.i("Offline",State_offline) ;
                Log.i("Pea_Model_offline",Pea_meter) ;
                MR_MEMBER_HEDER_MASTER(Pea_meter) ;
                select_register(Pea_meter) ;

            }
            else
                {
                    Log.i("online",State_offline) ;
                    Log.i("Pea_Model_online",Pea_meter) ;
                    new Activity_Mr_Register.AsyncList1().execute(userid,ReadDatePlan,Pea_meter); // Get Header Detail
                    new Activity_Mr_Register.AsyncList2().execute(userid,ReadDatePlan,Pea_meter); // Get Register Detail
            }


        }
        else
        {

          //  ReadDatePlan = "20180401";



            TextView txt_UserDetail =   (TextView) findViewById(R.id.txt_UserDetail);
            txt_UserDetail.setText("กรุณากรอก PEA METER");
         //   etxt_Mru.setEnabled(false);
            etxt_Mru.setHint("วันที่จดหน่วยตามแผน");
           // etxt_Mru.setShowSoftInputOnFocus(false);
            etxt_Mru.setFocusable(false);
            etxt_Mru.setOnClickListener(new View.OnClickListener() {
                int mMinute, mYear, mMonth, mDay;
                @Override
                public void onClick(View v) {
                    etxt_Mru.setEnabled(true);
                    final Calendar cc = Calendar.getInstance();
                    mYear =cc.get(Calendar.YEAR);
                    mMonth =cc.get(Calendar.MONTH);
                    mDay =cc.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    String sdayOfMonth = dayOfMonth+"" ;
                                    String smonthOfYear = monthOfYear+1+"" ;
                                    if(dayOfMonth < 10)
                                    {
                                        sdayOfMonth = "0"+sdayOfMonth ;
                                    }
                                    if(monthOfYear < 10)
                                    {
                                        smonthOfYear = "0"+smonthOfYear ;
                                    }

                                    etxt_Mru.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });


       //     etxt_Mru.setEnabled(false);
            ba_name.setEnabled(false);
            pea_no.setEnabled(false);
            ZDMR209.setEnabled(false);

            Map_Edit_text = new HashMap<>();
            Map_String = new HashMap<>();
            final myDBClass myDb2 = new myDBClass(this);
            myDb2.getWritableDatabase();
            Cursor CurData = myDb2.SelectMeterModel(Pea_Model) ;
//            CurData.moveToFirst();
            int i = 0 ;

            while (!CurData.isAfterLast()) {

                //CurData.getString(CurData.getColumnIndex("CA"));/////////////
               // if(!CurData.getString(1).trim().equals("OTHER"))
                if(true)
                {
///////////////////**************  Create Text View
                    Log.i("Regis Text"+i,CurData.getString(CurData.getColumnIndex("CA"))) ;
                    TextView textView_detail = new TextView(Activity_Mr_Register.this);
                    textView_detail.setText(Html.fromHtml(" <font s color='#C71585'><b>"+CurData.getString(CurData.getColumnIndex("PEANO")).trim()+"</b></font> " + CurData.getString(CurData.getColumnIndex("CA")).trim()+""));
                    textView_detail.setTextSize(18);
                    RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
                    params1.addRule(RelativeLayout.BELOW, idLastChild);
                    textView_detail.setLayoutParams(params1);
                    idLastChild = idLastChild +1 ;
                    // textView_detail.setLayoutParams(params1);
                    textView_detail.setId(idLastChild);
                    layout.addView(textView_detail);
///////////////////**************  Create Edit Text
                    final EditText textview = new EditText(Activity_Mr_Register.this);
                    final String REGIS_CODE  = CurData.getString(CurData.getColumnIndex("PEANO")).trim();
                    final int before_dot = Integer.parseInt(CurData.getString(CurData.getColumnIndex("REGIS_TEXT")).trim().toString().trim());
                    Log.i("textview","BBBB");
                    textview.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Log.i("AAAA","BBBB");
                            startVoiceRecognitionActivity() ;

                        }
                    });


                    TextWatcher narmal  = new TextWatcher() {

                        @Override
                        public void afterTextChanged(Editable s) {
                            String str = textview.getText().toString();
                            String result = s.toString().replaceAll(" ", "");
                            if (!s.toString().equals(result)) {
                                textview.setText(result);
                                textview.setSelection(result.length());
                            }
                        }
                        @Override
                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {

                        }
                        int flag_text=0;
                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {
                        }
                    } ;

                    TextWatcher time_format  = new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {

                            int charCount = 0;
                            char temp;
                            String str = textview.getText().toString();
                            for (int i = 0; i < str.length(); i++) {
                                if (str.charAt(i) == '.') {
                                    charCount = charCount + 1;
                                }
                            }
                            if (str.length() == before_dot ) {//len check for backspace
                                if (!str.contains(".")) {
                                    //           textview.append(".");
                                }


                            }
                        }
                        @Override
                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                            button_send.setEnabled(false);
/*
                                        Toast.makeText(Activity_Mr_Register.this, REGIS_CODE,
                                                Toast.LENGTH_LONG).show();
*/
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                                  int before, int count) {
                            int charCount = 0;
                            char temp;
                            String str = textview.getText().toString();
                            for (int i = 0; i < str.length(); i++) {
                                if (str.charAt(i) == '.') {
                                    charCount = charCount + 1;
                                }
                            }
                            if (charCount > 1) {
                                textview.getText().delete(str.length() - 1, str.length());
                            }

                        }
                    } ;

                    Map_Edit_text.put(CurData.getString(CurData.getColumnIndex("PEANO")).trim(),textview) ;

                    if(!CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim().equals("TIME"))
                    {
                        textview.addTextChangedListener(narmal);
                    }
                    if(!CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim().equals("DATE"))
                    {
                        textview.addTextChangedListener(narmal);
                    }
                    if(!CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim().equals("DATETIME"))
                    {
                        textview.addTextChangedListener(narmal);
                    }

                    if(CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim().equals("TIME"))
                    {
                        textview.setFocusable(false);
                        textview.setShowSoftInputOnFocus(false);
                        textview.setOnClickListener(new View.OnClickListener() {
                            int mHour ;
                            int mMinute ,mYear ,mMonth ,mDay ;

                            @Override
                            public void onClick(View v) {
                                button_send.setEnabled(false);
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_Mr_Register.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                String Sminute = minute+"" ;
                                                String SmHour = hourOfDay+"" ;
                                                if (minute < 10)
                                                {
                                                    Sminute = "0"+minute ;
                                                }
                                                if (hourOfDay < 10)
                                                {
                                                    SmHour = "0"+hourOfDay ;
                                                }

                                                textview.setText(SmHour + ":" + Sminute);
                                            }
                                        }, mHour, mMinute, true);

                                timePickerDialog.show();
                            }
                        });

                    }
                    else if(CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim().equals("DATE"))
                    {
                        textview.setFocusable(false);
//                        button_send.setEnabled(false);
                        textview.setShowSoftInputOnFocus(false);
                        //  textview.setEnabled(false);
                        textview.setOnClickListener(new View.OnClickListener() {
                            int mMinute, mYear, mMonth, mDay;
                            @Override
                            public void onClick(View v) {
                                textview.setEnabled(true);
                                final Calendar cc = Calendar.getInstance();
                                mYear =cc.get(Calendar.YEAR);
                                mMonth =cc.get(Calendar.MONTH);
                                mDay =cc.get(Calendar.DAY_OF_MONTH);
                                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {
                                                String sdayOfMonth = dayOfMonth+"" ;
                                                String smonthOfYear = monthOfYear+1+"" ;
                                                if(dayOfMonth < 10)
                                                {
                                                    sdayOfMonth = "0"+sdayOfMonth ;
                                                }
                                                if(monthOfYear < 10)
                                                {
                                                    smonthOfYear = "0"+smonthOfYear ;
                                                }

                                                textview.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }
                        });
                    }
                    else if( CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim().equals("DATETIME"))
                    {
//                        button_send.setEnabled(false);

                        textview.setFocusable(false);
                        textview.setOnClickListener(new View.OnClickListener() {
                            int mHour ,mMinute, mYear, mMonth, mDay;
                            String xxdayofmount ;
                            @Override
                            public void onClick(View v) {

                                textview.setEnabled(true);
                                final Calendar cc = Calendar.getInstance();
                                mYear =cc.get(Calendar.YEAR);
                                mMonth =cc.get(Calendar.MONTH);
                                mDay =cc.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {

                                                String sdayOfMonth = dayOfMonth+"" ;
                                                String smonthOfYear = monthOfYear+1+"" ;
                                                if(dayOfMonth < 10)
                                                {
                                                    sdayOfMonth = "0"+sdayOfMonth ;
                                                }
                                                if(monthOfYear < 10)
                                                {
                                                    smonthOfYear = "0"+smonthOfYear ;
                                                }

                                                textview.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                                            }
                                        }, mYear, mMonth, mDay);

                                TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_Mr_Register.this,
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                String Sminute = minute+"" ;
                                                String SmHour = hourOfDay+"" ;
                                                if (minute < 10)
                                                {
                                                    Sminute = "0"+minute ;
                                                }
                                                if (hourOfDay < 10)
                                                {
                                                    SmHour = "0"+hourOfDay ;
                                                }
                                                String sdayOfMonth = mDay+"" ;
                                                String smonthOfYear = mMonth+1+"" ;
                                                if(mDay < 10)
                                                {
                                                    sdayOfMonth = "0"+mDay ;
                                                }
                                                if(mMonth < 10)
                                                {
                                                    smonthOfYear = "0"+mMonth ;
                                                }
                                                mMonth =mMonth +1 ;

                                                // xxdayofmount = xxdayofmount + " - "+SmHour + ":" + Sminute ;
                                                textview.append(" - "+SmHour + ":" + Sminute);

                                            }
                                        }, mHour, mMinute, true);


                                timePickerDialog.show();
                                datePickerDialog.show();
                            }
                        });

                    }
                    else
                    {
                        textview.setInputType(3);
                    }

                    /////////////////******************/////////////////


                    textview.setTextSize(16);
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                            android.widget.LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.BELOW, idLastChild);
                    textview.setLayoutParams(params2) ;
                    idLastChild = idLastChild +1 ;
                    textview.setId(idLastChild);
                    layout.addView(textview);

//////*******************/

                    Item_Reagister.put(CurData.getString(CurData.getColumnIndex("PEANO")).trim(),new Item_Register(0,CurData.getString(CurData.getColumnIndex("PEANO")).trim(),"",""
                            ,"" ,"","","",textview ,CurData.getString(CurData.getColumnIndex("REGIS_TEXT")).trim(),CurData.getString(CurData.getColumnIndex("REGIS_TEXT")).trim(),CurData.getString(CurData.getColumnIndex("REGIS_TEXT")).trim(),CurData.getString(CurData.getColumnIndex("REGIS_NO")).trim()) ) ;

                }


                //////////////////////////////////////****************////////////////////
                CurData.moveToNext();
                i++ ;
            }
            CurData.close();
            myDb2.close();

        }




        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double latitude = 0 ;
                double longitude = 0 ;
                String[] S_ReadDatePlan = etxt_Mru.getText().toString().trim().split("/");
/*                ReadDatePlan = S_ReadDatePlan[2]+S_ReadDatePlan[1]+S_ReadDatePlan[0] ;
                Pea_meter = etxt_Username.getText().toString().trim();
*/
                Log.i("ReadDatePlan",ReadDatePlan);
                Log.i("Pea_meter",Pea_meter);

                ///////////////////////////////GPS///////////////////////////

                GPSTracker gps = new GPSTracker(Activity_Mr_Register.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();

                    // \n is for new line
                    if(latitude== 0 && longitude == 0 )
                    {
                        Toast.makeText(getApplicationContext(), "ไม่สามารถ รับค่า GPS ได้ ตรวจสอบ การเปิดใช้งาน GPS", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        Log.i(latitude+"",longitude+"");
                    }

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }



                ///////////////////////////////GPS///////////////////////////
                Map_Result = new HashMap<>();

                String CHECK_STATE = "0" ;
                String Value = "" ;
                String REGIS_CODE = "" ;
                String READED_TIME = "" ;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date now = new Date();
                String strDate = sdf.format(now);
                Log.i("strDatestrDate",strDate) ;

                /////////////////////////////////  EX1
                SwitchCompat s  ;
                try
                {
                     s = (SwitchCompat) findViewById(R.id.switch_compat1);
                    if(s.isChecked())
                    {
                        Map_Result.put("EX1",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX1","1",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","TRUE") ;
                    }
                    else
                    {
                        Map_Result.put("EX1",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX1","0",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","FALSE") ;
                    }

                }
                catch (Exception e)
                {

                }

/////////////////////////////////  EX2
                try
                {
                    s = (SwitchCompat) findViewById(R.id.switch_compat2);
                    if(s.isChecked())
                    {
                        Map_Result.put("EX2",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX2","1",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","TRUE") ;
                    }
                    else
                    {
                        Map_Result.put("EX2",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX2","0",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","FALSE") ;
                    }

                }
                catch (Exception e)
                {

                }


                /////////////////////////////////  EX4
                try
                {
                    s = (SwitchCompat) findViewById(R.id.switch_compat4);
                    if(s.isChecked())
                    {
                        Map_Result.put("EX3",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX3","1",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat3","TRUE") ;
                    }
                    else
                    {
                        Map_Result.put("EX3",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX3","0",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat3","FALSE") ;
                    }

                }
                catch (Exception e)
                {

                }

                /////////////////////////////////  EX5
                try
                {
                    s = (SwitchCompat) findViewById(R.id.switch_compat5);
                    if(s.isChecked())
                    {
                        Map_Result.put("EX4",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX4","1",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","TRUE") ;
                    }
                    else
                    {
                        Map_Result.put("EX4",new Item_Result(userid,ReadDatePlan,Pea_meter,"EX4","0",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","FALSE") ;
                    }

                }
                catch (Exception e)
                {

                }


                /////////////////////////////////  888
                try
                {
                    s = (SwitchCompat) findViewById(R.id.switch_compat6);
                    if(s.isChecked())
                    {
                        Map_Result.put("888",new Item_Result(userid,ReadDatePlan,Pea_meter,"888","1",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","TRUE") ;
                    }
                    else
                    {
                        Map_Result.put("888",new Item_Result(userid,ReadDatePlan,Pea_meter,"888","0",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","FALSE") ;
                    }

                }
                catch (Exception e)
                {

                }


                /////////////////////////////////  88
                try
                {
                    s = (SwitchCompat) findViewById(R.id.switch_compat6);
                    if(s.isChecked())
                    {
                        Map_Result.put("88",new Item_Result(userid,ReadDatePlan,Pea_meter,"88","1",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","TRUE") ;
                    }
                    else
                    {
                        Map_Result.put("88",new Item_Result(userid,ReadDatePlan,Pea_meter,"88","0",strDate,"0",latitude,longitude) ) ;
                        Log.i("switch_compat4","FALSE") ;
                    }

                }
                catch (Exception e)
                {

                }



                for (Map.Entry<String, Item_Register> entry : Item_Reagister.entrySet()) {
                    if(entry.getValue().getINPUT().equals("NUMBER"))
                    {
                        String Result =entry.getValue().getEdti_Rigister().getText().toString() ;
                        if(!(entry.getValue().getAFTER_DOT().toString().equals("") || Result.equals("")) )
                        {


                            String Result_dot = Result+".X" ;
                            Log.i("Result_dot",Result_dot) ;
                            String[] Result_dot_array = Result_dot.split("\\.") ;
                            String Result_afterDot = Result_dot_array[1] ;
                            String Result_BeforDot = Result_dot_array[0] ;
                            Log.i("Result_afterDot",Result_afterDot) ;
                            int after_dot = Integer.parseInt(entry.getValue().getAFTER_DOT().toString().trim());
                            if(!Result_afterDot.equals("X"))
                            {
                                int Result_afterDot_length = Result_afterDot.length() ;

                                if(Result_afterDot_length >= after_dot)
                                {
                                    Result_afterDot = Result_afterDot.substring(0,after_dot) ;

                                }
                                else
                                {
                                    for(int i=Result_afterDot_length ; i < after_dot ;i++)
                                    {
                                        Result_afterDot = Result_afterDot+"0";
                                    }
                                }
                                Result_dot = Result_BeforDot+Result_afterDot ;

                            }
                            else
                            {
                                for(int i=0 ; i < after_dot ;i++)
                                {
                                    Result_BeforDot = Result_BeforDot+"0";
                                }
                                Result_dot = Result_BeforDot;
                            }

                            Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),Result_dot,strDate,entry.getValue().getCheck_state(),latitude,longitude) ) ;
                            Log.i("Result_afterDot_Final",entry.getKey().toString()+"|"+Result_dot) ;

                        }
                        else  // ไม่ได้ KEY ข้อมูล
                        {
                            Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),entry.getValue().getEdti_Rigister().getText().toString().trim(),strDate,entry.getValue().getCheck_state(),latitude,longitude) ) ;
                        }
                    }
                    else
                    {
                        if(entry.getValue().getINPUT().toString().trim().equals("DATE"))
                        {
                            try
                            {
                                boolean Cuation = false ;
                                String Now_read_date = entry.getValue().getEdti_Rigister().getText().toString() ;
                                Log.i("getEror_max",entry.getValue().getEror_max().toString()) ;
                                Log.i("getMin_read",entry.getValue().getError_min().toString()) ;
                                Integer Max_time = Integer.parseInt(entry.getValue().getEror_max().toString().trim()) ;
                                Integer Min_time =Integer.parseInt( entry.getValue().getError_min().toString().trim()) ;
                                if(!Now_read_date.equals(""))
                                {
                                    String[] Now_read_date_all = Now_read_date.split("/");
                                    String Now_read_date_all_new = Now_read_date_all[2]+Now_read_date_all[1]+Now_read_date_all[0] ;
                                    Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),Now_read_date_all_new,strDate,entry.getValue().getCheck_state(),latitude,longitude) );

                                }
                                else
                                {
                                    Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),entry.getValue().getEdti_Rigister().getText().toString().trim(),strDate,entry.getValue().getCheck_state(),latitude,longitude) ) ;
                                }

                            }
                            catch (Exception e)
                            {
                                Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),entry.getValue().getEdti_Rigister().getText().toString().trim(),strDate,entry.getValue().getCheck_state(),latitude,longitude) ) ;
                            }
                        }
                        else if(entry.getValue().getINPUT().equals("DATETIME"))
                        {
                            try
                            {

                                String Now_read_date_time = entry.getValue().getEdti_Rigister().getText().toString() ;;
                                if(!Now_read_date_time.equals(""))
                                {
                                    String[] Now_read_date_all = Now_read_date_time.split("-");
                                    String[] Now_read_date = Now_read_date_all[0].split("/");
                                    String[] Now_read_time = Now_read_date_all[1].split(":");
                                    String Now_read_date_all_new =Now_read_date[2].trim()+Now_read_date[1].trim()+Now_read_date[0].trim()+Now_read_time[0].trim()+Now_read_time[1].trim() ;
                                    Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),Now_read_date_all_new,strDate,entry.getValue().getCheck_state(),latitude,longitude) );
                                    Log.i("Now_read_date_all_new",Now_read_date_all_new+"");

                                }
                                else
                                {
                                    Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),"",strDate,entry.getValue().getCheck_state(),latitude,longitude) );
                                }

                            }
                            catch (Exception e)
                            {
                                Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),"",strDate,entry.getValue().getCheck_state(),latitude,longitude) );
                            }

                        }
                        else if(entry.getValue().getINPUT().toString().trim().equals("TIME")) {
                            boolean Cuation = false ;
                            String Now_read_time = entry.getValue().getEdti_Rigister().getText().toString() ;
                            if(!Now_read_time.equals(""))
                            {
                                String[] Now_read_time_all = Now_read_time.split(":");
                                String Now_read_time_all_new =Now_read_time_all[0]+Now_read_time_all[1] ;
                                Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),Now_read_time_all_new,strDate,entry.getValue().getCheck_state(),latitude,longitude) ) ;
                            }
                            else
                            {
                                Map_Result.put(entry.getKey().toString(),new Item_Result(userid,ReadDatePlan,Pea_meter,entry.getKey().toString(),entry.getValue().getEdti_Rigister().getText().toString().trim(),strDate,entry.getValue().getCheck_state(),latitude,longitude) ) ;
                            }


                        }

                    }
                }
                try
                {



                EditText editText = (EditText) findViewById(R.id.etxt_Error_code);
                Map_Result.put("IN1",new Item_Result(userid,ReadDatePlan,Pea_meter,"IN1",editText.getText().toString(),strDate,"0",latitude,longitude) ) ;
                editText = (EditText) findViewById(R.id.etxt_Warning_code);
                Map_Result.put("IN2",new Item_Result(userid,ReadDatePlan,Pea_meter,"IN2",editText.getText().toString(),strDate,"0",latitude,longitude) ) ;
                RadioGroup rg1 = (RadioGroup) findViewById(R.id.radio_all);
                int id= rg1.getCheckedRadioButtonId();
                View radioButton = rg1.findViewById(id);
                int radioId = rg1.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) rg1.getChildAt(radioId);
                String selection = (String) btn.getText();
                Log.i("RadioGroup",selection+"") ;
                String radio_all_select = "" ;
                switch (selection) {
                    case "RATE A":
                        radio_all_select = "1";
                        break;
                    case "RATE B":
                        radio_all_select = "2";
                        break;
                    case "RATE C":
                        radio_all_select = "3";
                        break;
                    default:
                        radio_all_select = "";
                        break;
                }
                Map_Result.put("IN3",new Item_Result(userid,ReadDatePlan,Pea_meter,"IN3",radio_all_select,strDate,"0",latitude,longitude) ) ;
                editText = (EditText) findViewById(R.id.etxt_time_read_pos);
                Map_Result.put("IN4",new Item_Result(userid,ReadDatePlan,Pea_meter,"IN4",editText.getText().toString(),strDate,"0",latitude,longitude) ) ;
                editText = (EditText) findViewById(R.id.etxt_Reset);
                Map_Result.put("IN5",new Item_Result(userid,ReadDatePlan,Pea_meter,"IN5",editText.getText().toString(),strDate,"0",latitude,longitude) ) ;
                editText = (EditText) findViewById(R.id.etxt_Reset_2);
                Map_Result.put("IN6",new Item_Result(userid,ReadDatePlan,Pea_meter,"IN6",editText.getText().toString(),strDate,"0",latitude,longitude) ) ;
                editText = (EditText) findViewById(R.id.etxt_See_also);
                Map_Result.put("TXT",new Item_Result(userid,ReadDatePlan,Pea_meter,"TXT",editText.getText().toString(),strDate,"0",latitude,longitude) ) ;
                }
                catch (Exception e)
                {

                }
                String key = userid+"|"+ReadDatePlan+"|"+Pea_meter.trim()+";" ;




                for (Map.Entry<String, Item_Result> entry2_result : Map_Result.entrySet()) {

                    key = key + entry2_result.getKey() +"|" + entry2_result.getValue().getVALUE() +"|"+entry2_result.getValue().getCHECK_STATE()+"|"+entry2_result.getValue().getLatitude()+"|"+entry2_result.getValue().getLongitude()+";" ;


                }

                //   Log.i("KEY FINAL",key);

                new Activity_Mr_Register.Mr_UpdateRegister().execute(key);

            }
        });


        button_check.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String Cuation_string = "";

                int x = 0;
                for (Map.Entry<String, Item_Register> entry : Item_Reagister.entrySet()) {
                if ((!entry.getValue().Check_Operation.toString().equals("X")) & (!entry.getValue().Check_Operation.toString().equals("MAXMIN"))) {
                //        if ((!entry.getValue().getEror_max().toString().equals("")) & (!entry.getValue().Check_Operation.toString().equals("MAXMIN"))) {
                        try {
                            x = x + 1;
                            Boolean Cuation = false;
                            double sum_check = 0;
                            String[] oper_all = entry.getValue().Check_Operation.toString().split(";");
                            Log.i("oper_all[0]:" + x, oper_all[0].toString());
                            String xxx = oper_all[0].toString();
                            String yyy = oper_all[1].toString();
                            String[] oper_var = xxx.split("\\|");
                            String[] oper_con = yyy.toString().split("\\|");
                            double now_key = 0;

                            if (!entry.getValue().getEdti_Rigister().getText().toString().equals("")) {

                                now_key = Double.parseDouble(entry.getValue().getEdti_Rigister().getText().toString());
                            }
                            //   Log.i("oper_var[i]:",oper_var[0].toString());


                            for (int i = 0; i < oper_var.length; i++) {
                                if (!oper_var[i].toString().equals("")) {
                                    Log.i("oper_var[i]:" + i, oper_var[i].toString());

                                    if (!Item_Reagister.get(oper_var[i].toString()).getEdti_Rigister().getText().toString().equals("")) {
                                        //    Log.i("Item_Reagister",Item_Reagister.get(oper_var[i].toString()).getEdti_Rigister().getText().toString());

                                        //   sum_check = sum_check +Double.parseDouble(Item_Reagister.get(oper_var[i].toString()).getEdti_Rigister().getText().toString());


                                        sum_check = BigDecimal.valueOf(sum_check).add(BigDecimal.valueOf(Double.parseDouble(Item_Reagister.get(oper_var[i].toString()).getEdti_Rigister().getText().toString()))).doubleValue();
                                    }


                                }

                            }


                            for (int i = 0; i < oper_con.length; i++) {
                                if (!oper_con[i].toString().equals("")) {
                                    Log.i("oper_con[i]:" + i, oper_con[i].toString());
                                    Log.i("AAAAAA", Item_Reagister.get(oper_con[i].toString()).Edti_Rigister.toString());
                                    if (!Item_Reagister.get(oper_con[i].toString()).BEFORE_READ.equals("")) {
                                        double sum_con = Double.parseDouble(Item_Reagister.get(oper_con[i].toString()).getBEFORE_READ());
                                        Log.i("sum_con", sum_con + "");
                                        String divi = Item_Reagister.get(oper_con[i].toString()).getAFTER_DOT().toString();
                                        int divial = 1;
                                        switch (divi) {
                                            case "1":
                                                divial = 10;
                                                break;
                                            case "2":
                                                divial = 100;
                                                break;
                                            case "3":
                                                divial = 1000;
                                                break;
                                            case "4":
                                                divial = 10000;
                                                break;
                                            case "5":
                                                divial = 100000;
                                                break;
                                            case "6":
                                                divial = 1000000;
                                                break;
                                            case "7":
                                                divial = 10000000;
                                                break;
                                            case "8":
                                                divial = 100000000;
                                                break;
                                            case "9":
                                                divial = 1000000000;
                                                break;

                                            default:
                                                divial = 1;
                                                break;
                                        }
                                        sum_con = sum_con / divial;
                                        //     sum_check = sum_check + sum_con ;
                                        sum_check = BigDecimal.valueOf(sum_check).add(BigDecimal.valueOf(sum_con)).doubleValue();
                                    }

                                }

                                //sum_check = sum_check + Double.parseDouble(oper_con[i].toString());
                            }


                            Log.i("sum_check", sum_check + "");
                            String S_error_max = entry.getValue().getEror_max().toString();
                            String S_error_min = entry.getValue().getError_min().toString();
                            Double d_error_max = 0.0;
                            Double d_error_min = 0.0;

                            String S_MAX_READ = entry.getValue().getMax_read().toString();
                            String S_MIN_READ = entry.getValue().getMin_read().toString();
                            Double d_MAX_READ = 0.0;
                            Double d_MIN_READ = 0.0;


                            if (!S_error_max.equals("") && !S_error_min.equals("")) {
                                d_error_max = Double.parseDouble(entry.getValue().getEror_max().toString().trim());
                                d_error_min = Double.parseDouble(entry.getValue().getError_min().toString().trim());
                            }
                            ////////////////////

                                                      /*
                                                      if(!S_error_max.equals("") && !S_error_min.equals("") && !entry.getValue().getBEFORE_DOT().toString().trim().equals(""))
                                                      {
                                                          String Min_value = (sum_check - d_error_min) + ".X";
                                                          String Max_value = (sum_check + d_error_min) + ".X";
                                                          String Result_Min_value = Min_value.split("\\.")[0];
                                                          String Result_Max_value = Max_value.split("\\.")[0];
                                                          int difer_max = 0;
                                                          int difer_min = 0;

                                                          if (Result_Min_value.length() > Integer.parseInt(entry.getValue().getBEFORE_DOT().toString().trim())) {
                                                              difer_min = Result_Min_value.length() - Integer.parseInt(entry.getValue().getBEFORE_DOT().toString().trim());

                                                          }

                                                         if (Result_Max_value.length() > Integer.parseInt(entry.getValue().getBEFORE_DOT().toString().trim())) {
                                                            difer_max = Result_Max_value.length() - Integer.parseInt(entry.getValue().getBEFORE_DOT().toString().trim());
                                                          }
                                                          Log.i("ID",entry.getKey().toString());

                                                          String tem_Min_Value1 = (sum_check - d_error_min) + "" ;
                                                          String tem_Min_Value2 = tem_Min_Value1.substring(difer_min,tem_Min_Value1.length()-1) ;
                                                          Double I_Min_Value = Double.parseDouble(tem_Min_Value2);

                                                          String tem_Max_Value1 = (sum_check + d_error_min) + "" ;
                                                          String tem_Max_Value2 = tem_Max_Value1.substring(difer_min,tem_Min_Value1.length()-1) ;
                                                          Double I_Max_Value = Double.parseDouble(tem_Max_Value2);


                                                          Log.i("MIN",I_Min_Value+"");
                                                          Log.i("MAX",I_Max_Value+"");



                                                     //  int I_Min_Value = Integer.parseInt(((sum_check - d_error_min) + "").substring(difer_min, ((sum_check - d_error_min) + "").length()));
                                                    //      int I_Max_Value = Integer.parseInt(((sum_check + d_error_min) + "").substring(difer_min, ((difer_max - d_error_min) + "").length()));
                                                        //
                                                      }

*/
////////////////////////////////////////
                            Log.i("ERegisterTest", entry.getValue().getRegister_Code().toString());
                            Log.i("ENOW READ", now_key + "");
                            Log.i("ENOW sum_check", sum_check + "");
                            Log.i("Ed_error_min", d_error_min + "");
                            BigDecimal sum_check_b = BigDecimal.valueOf(sum_check);
                            BigDecimal d_error_min_b = BigDecimal.valueOf(d_error_min);
                            BigDecimal now_key_b = BigDecimal.valueOf(now_key);

                            if ((now_key * 1000000000) >= ((sum_check * 1000000000) - (d_error_min * 1000000000))) {

// do actionBigDecimal.valueOf(57.257).subtract(BigDecimal.valueOf(0.001) )
                            } else {

                                Log.i("ERegisterSum1", entry.getValue().getRegister_Code().toString());
                                Log.i("ENOW READ", now_key + "");
                                Log.i("ENOW sum_check", sum_check + "");
                                Log.i("ErrorMin", d_error_min + "");

                                Log.i("Ed_error_min", ((sum_check * 1000000000) - (d_error_min * 1000000000)) + "");
                                Log.i("Ed_error_max", ((sum_check * 1000000000) + (d_error_min * 1000000000)) + "" + "");
                                Cuation = true;

                            }
                            if (now_key <= (sum_check + d_error_max)) {

                            } else {
                                Log.i("ERegisterSum2", entry.getValue().getRegister_Code().toString());
                                Log.i("ENOW READ", now_key + "");
                                Log.i("ENOW sum_check", sum_check + "");
                                Log.i("Ed_error_min", d_error_min + "");
                                Cuation = true;
                            }

                            String divi = entry.getValue().getAFTER_DOT().toString();
                            int divial = 1;
                            switch (divi) {
                                case "1":
                                    divial = 10;
                                    break;
                                case "2":
                                    divial = 100;
                                    break;
                                case "3":
                                    divial = 1000;
                                    break;
                                case "4":
                                    divial = 10000;
                                    break;
                                case "5":
                                    divial = 100000;
                                    break;
                                case "6":
                                    divial = 1000000;
                                    break;
                                case "7":
                                    divial = 10000000;
                                    break;
                                case "8":
                                    divial = 100000000;
                                    break;
                                case "9":
                                    divial = 1000000000;
                                    break;

                                default:
                                    divial = 1;
                                    break;
                            }


                            if (!S_MAX_READ.equals("") && !S_MIN_READ.equals("")) {
                                d_MAX_READ = Double.parseDouble(entry.getValue().getMax_read().toString()) / divial;
                                d_MIN_READ = Double.parseDouble(entry.getValue().getMin_read().toString()) / divial;
                                if (!(now_key > d_MIN_READ) && now_key < d_MAX_READ) {

                                    Log.i("RRegisterMaxMin", entry.getValue().getRegister_Code().toString());
                                    Log.i("RNOW READ", now_key + "");
                                    Log.i("RNOW sum_check", sum_check + "");
                                    Log.i("Rd_MIN_READ", d_MIN_READ + "");
                                    Log.i("Rd_MAX_READ", d_MAX_READ + "");

                                    Cuation = true;

// do action
                                }
                            }


                            if (Cuation) {
                                entry.getValue().setCheck_state("1");
                                Cuation_string = Cuation_string + "ค่า Register <font s color='red'>" + entry.getValue().getRegister_Code().toString() + "</font> มีค่าไม่สมเหตุผล<br>";
                            } else {
                                entry.getValue().setCheck_state("0");
                            }
                        } catch (Exception e) {

                        }

                    } else if (entry.getValue().getINPUT().equals("DATE")) {
                        try {
                            boolean Cuation = false;
                            String Now_read_date = entry.getValue().getEdti_Rigister().getText().toString();
                            Log.i("getEror_max", entry.getValue().getEror_max().toString());
                            Log.i("getMin_read", entry.getValue().getError_min().toString());
                            Integer Max_time = Integer.parseInt(entry.getValue().getEror_max().toString().trim());
                            Integer Min_time = Integer.parseInt(entry.getValue().getError_min().toString().trim());
                            if (!Now_read_date.equals("")) {
                                String[] Now_read_date_all = Now_read_date.split("/");
                                Integer Now_read_date_all_new = Integer.parseInt(Now_read_date_all[2] + Now_read_date_all[1] + Now_read_date_all[0]);
                                Log.i("Now_read_date_all_new", Now_read_date_all_new + "");


                                if (!(Now_read_date_all_new > Min_time && Now_read_date_all_new < Max_time)) {
                                    Cuation = true;
                                }

                                if (Cuation) {
                                    entry.getValue().setCheck_state("1");
                                    Cuation_string = Cuation_string + "ค่า Register " + entry.getValue().getRegister_Code().toString() + " มีค่าไม่สมเหตุผล<br>";
                                } else {
                                    entry.getValue().setCheck_state("0");
                                }

                            }

                        } catch (Exception e) {

                        }

                    } else if (entry.getValue().getINPUT().equals("TIME")) {
                        try {
                            boolean Cuation = false;
                            String Now_read_time = entry.getValue().getEdti_Rigister().getText().toString();
                            if (!Now_read_time.equals("")) {
                                String[] Now_read_time_all = Now_read_time.split(":");
                                Integer Now_read_time_all_new = Integer.parseInt(Now_read_time_all[0] + Now_read_time_all[1]);
                                Integer Max_time = Integer.parseInt(entry.getValue().getEror_max().toString().substring(0, 4));
                                Integer Min_time = Integer.parseInt(entry.getValue().getError_min().toString().substring(0, 4));

                                Log.i("Now_read_time_all_new", Now_read_time_all_new + "");


                                if (!(Now_read_time_all_new > Min_time && Now_read_time_all_new < Max_time)) {
                                    Cuation = true;
                                }

                                if (Cuation) {
                                    entry.getValue().setCheck_state("1");
                                    Cuation_string = Cuation_string + "ค่า Register " + entry.getValue().getRegister_Code().toString() + " มีค่าไม่สมเหตุผล<br>";
                                }

                            }

                        } catch (Exception e) {

                        }

                    }

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        Activity_Mr_Register.this);
                builder.setTitle("การแจ้งเตือน");


                builder.setMessage(Html.fromHtml(Cuation_string));
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //  Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
                button_send.setEnabled(true);
                if (ConnectivityReceiver.isConnected()) {
                    button_send.setEnabled(true);
            } else
            {
                showSnack(false);
            }
                }



        });



        /*
        for (int i = 0; i < 2; i++) {
            final int dot = i + 2;
            IdRegister = IdRegister + 1;

            final TextView textView_detail = new TextView(Activity_Mr_Register.this);
            textView_detail.setText("KW สะสม Rate A");
            textView_detail.setTextSize(8);
            textView_detail.setVisibility(View.VISIBLE);
            textView_detail.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT));
            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                params1.addRule(RelativeLayout.BELOW, idLastChild);
            } else {
                params1.addRule(RelativeLayout.BELOW, etCollection.get(i - 1).getId());
            }


            textView_detail.setLayoutParams(params1);
            layout.addView(textView_detail);

            final EditText textview = new EditText(Activity_Mr_Register.this);
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(5);
            textview.setFilters(fArray);
            textview.setHint("สายๆๆๆ");
            textview.setId(IdRegister);
            textview.setInputType(3);
            textview.setTextSize(15);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            textview.setVisibility(View.VISIBLE);
            textview.setLayoutParams(new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT));
            if (i == 0) {
                params.addRule(RelativeLayout.BELOW, idLastChild);
            } else {
                params.addRule(RelativeLayout.BELOW, etCollection.get(i - 1).getId());
            }
            textview.setLayoutParams(params);
            layout.addView(textview);
            etCollection.add(textview);

            textview.addTextChangedListener(new TextWatcher() {
                int len = 0;

                @Override
                public void afterTextChanged(Editable s) {

                    int charCount = 0;
                    char temp;
                    String str = textview.getText().toString();
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == '.') {
                            charCount = charCount + 1;
                        }
                    }
                    // len = str.length() ;
                    if (str.length() == dot && len < str.length()) {//len check for backspace
                        if (!str.contains(".")) {
                            textview.append(".");
                        }


                    }


                }


                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                    String str = textview.getText().toString();
                    len = str.length();
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    int charCount = 0;
                    char temp;
                    String str = textview.getText().toString();
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == '.') {
                            charCount = charCount + 1;
                        }
                    }
                    if (charCount > 1) {
                        textview.getText().delete(str.length() - 1, str.length());
                    }
                    //

                    //  textview.("");
                }
            });


        }
        */


        ////////////////////****************////////

        voice_swite = (SwitchCompat) findViewById(R.id.switch_voice);
        voice_swite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    for (Map.Entry<String, Item_Register> entry : Item_Reagister.entrySet()) {
                        try {
                            entry.getValue().getEdti_Rigister().setInputType(1);
                        }
                        catch (Exception e)
                        {

                        }


                    }
                }
                else
                {

                }
            }
        });

    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

            showSnack(isConnected);
    }


    private class AsyncList1 extends AsyncTask<String, String, JSONArray> {
        URL url = null;
        HttpURLConnection conn;
        ProgressDialog pdLoading = new ProgressDialog(Activity_Mr_Register.this);
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
                String url_ = "http://extranet.pea.co.th/peap3/android/MR_mrSelectCustomer.ashx?reader="+ params[0]+"&read_date_plan="+ params[1]+"&pea_meter="+ params[2] ;
                Log.i("URL",url_);
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

            try {

                int count = 0;
                JSONObject c = result.getJSONObject(0);
                etxt_Username.setText(c.getString("CUST_NAME"));
                etxt_Mru.setText("สาย : " + c.getString("MRU") + " Ca : " + c.getString("CA"));
                ba_name.setText("การไฟฟ้า : " + c.getString("BA"));
                pea_no.setText("PEA NO : " + c.getString("PEANO"));
                Pea_number  = c.getString("PEANO") ;
                if(!c.getString("ZDMR209").toString().equals("N/A"))
                {
                    ZDMR209.setText("มีการสับเปลี่ยน มิเตอร์ จาก PEA NO."+c.getString("PEANO_BEF").toString());
                }
                else
                {
                    ZDMR209.setText("ไม่พบประวัติการสับเปลี่ยนมิเตอร์ภายในเดือน");
                }
                if(c.getString("READ_STATE").toString().equals("1"))
                {
                    Read_STATE = true ;
                    button_send.setText("แก้ไข");
                }
                if(c.getString("METERTYPE").toString().equals("TOU")) {
                  //  setContentView(R.layout.activity_mr_register_main);
                }
                else
                {
                    //setContentView(R.layout.activity_mr_register_main_n_d);
                }

                Log.i(" pea_no.setText", c.getString("PEANO"));


                //             }
            } catch (Exception e) {
                Log.i(" pea_no.setText", e.toString());
            }


            pdLoading.dismiss();
        }

    }

    private class AsyncList2 extends AsyncTask<String, String, JSONArray> {
        URL url = null;
        String REGIS_TEXT ;
        HttpURLConnection conn;
        int check_dot ;
        ProgressDialog pdLoading = new ProgressDialog(Activity_Mr_Register.this);
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
                String url_ = "http://extranet.pea.co.th/peap3/android/MR_REGISTER.ashx?read_date_plan="+params[1]+"&reader="+params[0]+"&PEANO="+params[2];
                Log.i("url_1",url_) ;
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

            try {
                Map_Edit_text = new HashMap<>();
                Map_String = new HashMap<>();

                int count = 0;
                check_dot =count ;
                for (int i = 0; i < result.length(); i++)
                {
                    final JSONObject c = result.getJSONObject(i);
                 //   Log.i("REGISTER_GROUP",c.getString("REGISTER_GROUP").trim().toString().substring(0,5));NORMAL
                    if (c.getString("REGISTER_GROUP").trim().toString().substring(0,2).equals("TO") || c.getString("REGISTER_GROUP").trim().toString().equals("DEMAND")||c.getString("REGISTER_GROUP").trim().toString().equals("NORMAL")  )///////////////////////////////*************************Edit***************
                    {
                       // edit 20180212 if (!c.getString("REGIS_NO").trim().toString().equals("001") ||  c.getString("REGISTER_GROUP").trim().toString().equals("DEMAND"))
                        if (!c.getString("REGIS_CODE").trim().toString().equals("888") && (!c.getString("REGIS_NO").trim().toString().equals("00xxx") ||  c.getString("REGISTER_GROUP").trim().toString().equals("DEMAND")))
                        {

                            //
                            final int total_digit ;
                            final int after_dot ;
                            final int before_dot ;

                            final int dot = i + 2;
                            IdRegister = IdRegister + 1;

                            //   after_dot = Integer.parseInt(c.getString("BEFORE_DOT").toString()) ;
                         /*   if(c.getString("BEFORE_DOT").toString())
                            {

                            }
                            else
                            {
                                before_dot = 10 ;
                            }
                            */
                            boolean yy = true ;
                            try {
                                int x  =Integer.parseInt(c.getString("BEFORE_DOT").trim().toString().trim()) ;
                                yy = true ;
                            }
                            catch (Exception e)
                            {
                                yy =false ;
                            }

                            if(yy)
                            {
                                before_dot = Integer.parseInt(c.getString("BEFORE_DOT").toString().trim()) ;
                              //  Log.i("BEFORE_DOT",before_dot+"" );
                            }
                            else
                            {
                                before_dot = 24 ;
                               // Log.i("BEFORE_DOTXX",before_dot+""  );
                            }



                            total_digit = before_dot + 5+1 ;

                           /* Log.i("REGIS_TEXT", c.getString("REGIS_TEXT").toString()) ;
                            Log.i("before_dot", before_dot+"") ;
                            Log.i("after_dot", after_dot+"") ;
                            */

                            TextView textView_detail = new TextView(Activity_Mr_Register.this);
                            REGIS_TEXT = c.getString("REGIS_TEXT") ;

                            textView_detail.setText(Html.fromHtml(" <font s color='#C71585'><b>"+c.getString("REGIS_CODE").toString().trim()+"</b></font> " + c.getString("REGIS_TEXT")+""));
                            if(!c.getString("CHECK_OPERATION").toString().equals("X") && !c.getString("CHECK_OPERATION").toString().equals("MAXMIN") )
                            {
                                textView_detail.setText(Html.fromHtml(" <font s color='#C71585'><b>"+c.getString("REGIS_CODE").toString().trim()+"</b></font> " + c.getString("REGIS_TEXT")+" <font s color='#CC0000'><b>(ดูสูตร)</b></font>"));
                            }
                            textView_detail.setTextSize(18);
                            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                                    LinearLayout.LayoutParams.FILL_PARENT,
                                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
                            /*
                            if (count == 0) {
                                params1.addRule(RelativeLayout.BELOW, idLastChild);
                            } else {
                                params1.addRule(RelativeLayout.BELOW, etCollection.get(count - 1).getId());
                            }
                            */
                            params1.addRule(RelativeLayout.BELOW, idLastChild);
                            textView_detail.setLayoutParams(params1);
                            idLastChild = idLastChild +1 ;
                           // textView_detail.setLayoutParams(params1);
                            textView_detail.setId(idLastChild);
                            layout.addView(textView_detail);

                            int[] location = new int[2];
                            // Get the x, y location and store it in the location[] array
                            // location[0] = x, location[1] = y.
                            textView_detail.getLocationOnScreen(location);

                            //Initialize the Point with x, and y positions
                            p = new Point();
                            p.x = location[0];
                            p.y = location[1];
                            final String check_operation_11 =c.getString("CHECK_OPERATION").toString();

                            if(!c.getString("CHECK_OPERATION").toString().equals("X") && !c.getString("CHECK_OPERATION").toString().equals("MAXMIN") )
                            {
                                textView_detail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (p != null) {

                                            String[] oper_all = check_operation_11.split(";");
String detail = "ผลการอ่านหน่วย  = " ;
                                            String xxx = oper_all[0].toString();
                                            String yyy = oper_all[1].toString();
                                            String[] oper_var = xxx.split("\\|");
                                            String[] oper_con = yyy.toString().split("\\|");
                                            for (int i = 0; i < oper_var.length; i++) {
                                                if (!oper_var[i].toString().equals("")) {
                                               detail =detail + "Reg No.(" + oper_var[i].toString() + ") + " ;
                                                    }

                                            }
                                            for (int i = 0; i < oper_con.length; i++) {
                                                if (!oper_con[i].toString().equals("")) {
                                                    try
                                                    {
                                                        double sum_con = Double.parseDouble(Item_Reagister.get(oper_con[i].toString()).getBEFORE_READ());
                                                        Log.i("sum_con", sum_con + "");
                                                        String divi = Item_Reagister.get(oper_con[i].toString()).getAFTER_DOT().toString();
                                                        int divial = 1;
                                                        switch (divi) {
                                                            case "1":
                                                                divial = 10;
                                                                break;
                                                            case "2":
                                                                divial = 100;
                                                                break;
                                                            case "3":
                                                                divial = 1000;
                                                                break;
                                                            case "4":
                                                                divial = 10000;
                                                                break;
                                                            case "5":
                                                                divial = 100000;
                                                                break;
                                                            case "6":
                                                                divial = 1000000;
                                                                break;
                                                            case "7":
                                                                divial = 10000000;
                                                                break;
                                                            case "8":
                                                                divial = 100000000;
                                                                break;
                                                            case "9":
                                                                divial = 1000000000;
                                                                break;

                                                            default:
                                                                divial = 1;
                                                                break;
                                                        }
                                                        sum_con = sum_con / divial;
                                                        //     sum_check = sum_check + sum_con ;


                                                        detail =detail + sum_con + "+ " ;
                                                    }
                                                    catch (Exception e)
                                                    {
                                                        detail =detail + "ค ่าครั้งก่อนของ "+oper_con[i].toString() ;
                                                    }

                                                }

                                            }


                                            showPopup(Activity_Mr_Register.this, p, "<font s color='#C71585'><b>"+detail.substring(0,detail.length()-2)+"</b></font>");
                                        }
                                    }
                                });

                            }


                           /* Button Bt_detail = new Button(Activity_Mr_Register.this) ;
                            Bt_detail.setText("X");
                           RelativeLayout.LayoutParams params22 = new RelativeLayout.LayoutParams(100,100);
                            params22.addRule(RelativeLayout.ALIGN_END, idLastChild);
                            Bt_detail.setId(idLastChild+5000);
                            Bt_detail.setLayoutParams(params22);
                            layout.addView(Bt_detail) ;
                            */
                            ///////******///////////
                            final EditText textview = new EditText(Activity_Mr_Register.this);
                            try{
                                String Now_read = c.getString("NOW_READ").toString() ;
                                if(c.getString("INPUT").toString().equals("TIME"))
                                {
                                    Now_read = Now_read.substring(0,2)+":"+Now_read.substring(2,4);
                                }
                                else if (c.getString("INPUT").toString().equals("DATE"))
                                {
                                    Now_read = Now_read.substring(6,8)+"/"+Now_read.substring(4,6)+"/"+Now_read.substring(0,4);
                                }
                                else if(c.getString("INPUT").toString().equals("DATETIME"))
                                {
                                    Now_read = Now_read.substring(6,8)+"/"+Now_read.substring(4,6)+"/"+Now_read.substring(0,4)+"-"+Now_read.substring(8,10)+":"+Now_read.substring(10,12);
                                }
                                else if(c.getString("INPUT").toString().equals("NUMBER"))
                                {
                                    String divi = c.getString("AFTER_DOT").toString().toString().trim() ;
                                    double Now_read_D = Double.parseDouble(Now_read);
                                    int divial = 1 ;
                                    switch (divi) {
                                        case "1":
                                            divial = 10;
                                            break;
                                        case "2":
                                            divial = 100;
                                            break;
                                        case "3":
                                            divial = 1000;
                                            break;
                                        case "4":
                                            divial = 10000;
                                            break;
                                        case "5":
                                            divial = 100000;
                                            break;
                                        case "6":
                                            divial = 1000000;
                                            break;
                                        case "7":
                                            divial = 10000000;
                                            break;
                                        case "8":
                                            divial = 100000000;
                                            break;
                                        case "9":
                                            divial = 1000000000;
                                            break;

                                        default:
                                            divial = 1;
                                            break;
                                    }
                                    Now_read_D = Now_read_D/divial;
                                    if(Now_read_D == (long) Now_read_D)
                                    {
                                        Now_read = String.format("%d",(long)Now_read_D);
                                    }

                                    else
                                    {
                                        Now_read = String.format("%s",Now_read_D);
                                    }


                                    Log.i("2222","CCCCCCCCCCC");

                                    if(!c.getString("MIN_READ").toString().equals(""))
                                    {
                                        if(!c.getString("MAX_READ").toString().equals(""))
                                        {
                                            Log.i("333","CCCCCCCCCCC");
                                            double min_read =   Double.parseDouble(c.getString("MIN_READ").toString())/divial ;
                                            String Smin_read = "" ;
                                            String Smax_read = "" ;
                                            double max_read =   Double.parseDouble(c.getString("MAX_READ").toString())/divial ;
                                            if(min_read == (long) min_read)
                                            {
                                                Smin_read = String.format("%d",(long)min_read);
                                            }

                                            else
                                            {
                                                Smin_read = String.format("%s",min_read);
                                            }
                                            if(max_read == (long) max_read)
                                            {
                                                Smax_read = String.format("%d",(long)max_read);
                                            }

                                            else
                                            {
                                                Smax_read = String.format("%s",max_read);
                                            }
                                            textview.setHint("MAX : "+Smax_read +" ; MIN :"+Smin_read);
                                            if(!c.getString("MAX_READ").toString().equals("BEFORE_READ")) {
                                                double before_read =   Double.parseDouble(c.getString("BEFORE_READ").toString())/divial ;
                                                String Sbefore_read = "" ;
                                                if(before_read == (long) before_read)
                                                {
                                                    Sbefore_read = String.format("%d",(long)before_read);
                                                }

                                                else
                                                {
                                                    Sbefore_read = String.format("%s",before_read);
                                                }
                                                textview.setHint("RANGE: "+Smax_read +"-"+Smin_read+ ", ครั้งก่อน: "+Sbefore_read);
                                            }


                                            Log.i("4444","ค่า MAX : "+max_read +" ; ค่า MIN :"+min_read);
                                        }
                                    }
                                }
                                else
                                {
                                    Now_read = c.getString("NOW_READ").toString() ;
                                }
                                if(!c.getString("NOW_READ").toString().equals("000000000000000"))
                                {
                                    textview.setText(Now_read);
                                }

                            Log.i(c.getString("REGIS_CODE").trim().toString() ,Now_read) ;


                            }
                            catch (Exception e)
                            {

                            }
                            //   int Temp_id_edit = Integer.parseInt("20"+c.getString("REGIS_CODE").toString().trim().substring(0,3)) ;
                      /*      int Id_edit = i +555 ;
                            idLastChild = idLastChild +1 ;
                            textview.setId(idLastChild);
                            textview.setTextSize(20);
                            */

                            Map_Edit_text.put(c.getString("REGIS_CODE").toString().trim(),textview) ;
                            //   Log.i("Map_Edit_text",c.getString("REGIS_CODE").toString());
                            Map_String.put(c.getString("REGIS_CODE").toString().trim(),c.getString("REGIS_CODE").toString().trim());
                            final String REGIS_CODE  = c.getString("REGIS_CODE").toString().trim();
                            TextWatcher narmal  = new TextWatcher() {
                                @Override
                                public void afterTextChanged(Editable s) {
                                    String str = textview.getText().toString();
                                //    Log.i("String_onTextChanged",str);
                                    String result = s.toString().replaceAll(" ", "");
                                    if (!s.toString().equals(result)) {
                                        textview.setText(result);
                                        textview.setSelection(result.length());
                                        // alert the user
                                    }


                                    /*
                                    button_send.setEnabled(false);

                                    int charCount = 0;
                                    char temp;
                                    String str = textview.getText().toString();
                                    for (int i = 0; i < str.length(); i++) {
                                        if (str.charAt(i) == '.') {
                                            charCount = charCount + 1;
                                        }
                                    }
                                    if (str.length() == before_dot ) {//len check for backspace
                                        if (!str.contains(".")) {
                                            //         textview.append(".");
                                        }


                                    }
                                    */
                                }
                                @Override
                                public void beforeTextChanged(CharSequence s, int start,
                                                              int count, int after) {

                                 //   String str = textview.getText().toString();
                                //    Log.i("Stri_beforeTextChanged",str);
                            //        Log.i("beforeTextChanged","beforeTextChanged");
                           //         textview.getText().toString().replace("1", "0") ;
                                  //  textview.getText().toString().replace(" ", "") ;
/*
                                        Toast.makeText(Activity_Mr_Register.this, REGIS_CODE,
                                                Toast.LENGTH_LONG).show();
*/
                                }
                                int flag_text=0;
                                @Override
                                public void onTextChanged(CharSequence s, int start,
                                                          int before, int count) {
                                 //   textview.getText().toString().replace("1", "0") ;
                                 //   Log.i("onTextChanged","onTextChanged");



                                  //  textview.getText().toString().replace(" ", "") ;
                                }
                                    /*
                                    int charCount = 0;
                                    char temp;
                                    String str = textview.getText().toString();
                                    for (int i = 0; i < str.length(); i++) {
                                        if (str.charAt(i) == '.') {
                                            charCount = charCount + 1;
                                        }
                                    }
                                    if (charCount > 1) {
                                        textview.getText().delete(str.length() - 1, str.length());
                                    }

                                }
                                */
                            } ;

                            TextWatcher time_format  = new TextWatcher() {
                                @Override
                                public void afterTextChanged(Editable s) {

                                    int charCount = 0;
                                    char temp;
                                    String str = textview.getText().toString();
                                    for (int i = 0; i < str.length(); i++) {
                                        if (str.charAt(i) == '.') {
                                            charCount = charCount + 1;
                                        }
                                    }
                                    if (str.length() == before_dot ) {//len check for backspace
                                        if (!str.contains(".")) {
                                            //           textview.append(".");
                                        }


                                    }
                                }
                                @Override
                                public void beforeTextChanged(CharSequence s, int start,
                                                              int count, int after) {
                                    button_send.setEnabled(false);
/*
                                        Toast.makeText(Activity_Mr_Register.this, REGIS_CODE,
                                                Toast.LENGTH_LONG).show();
*/
                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start,
                                                          int before, int count) {
                                    int charCount = 0;
                                    char temp;
                                    String str = textview.getText().toString();
                                    for (int i = 0; i < str.length(); i++) {
                                        if (str.charAt(i) == '.') {
                                            charCount = charCount + 1;
                                        }
                                    }
                                    if (charCount > 1) {
                                        textview.getText().delete(str.length() - 1, str.length());
                                    }

                                }
                            } ;

                            if(!c.getString("INPUT").toString().equals("TIME"))
                            {
                                textview.addTextChangedListener(narmal);
                            }
                            if(!c.getString("INPUT").toString().equals("DATE"))
                            {
                                textview.addTextChangedListener(narmal);
                            }
                            if(!c.getString("INPUT").toString().equals("DATETIME"))
                            {
                                textview.addTextChangedListener(narmal);
                            }

                            if(c.getString("INPUT").toString().equals("TIME"))
                            {

                                textview.setShowSoftInputOnFocus(false);
                              //  myTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                                textview.setFocusable(false);
                                textview.setOnClickListener(new View.OnClickListener() {
                                    int mHour ;
                                    int mMinute ,mYear ,mMonth ,mDay ;

                                    @Override
                                    public void onClick(View v) {
                                        button_send.setEnabled(false);
                                        final Calendar c = Calendar.getInstance();
                                        mHour = c.get(Calendar.HOUR);
                                        mMinute = c.get(Calendar.MINUTE);

                                        // Launch Time Picker Dialog
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_Mr_Register.this,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {
                                                        String Sminute = minute+"" ;
                                                        String SmHour = hourOfDay+"" ;
                                                        if (minute < 10)
                                                        {
                                                            Sminute = "0"+minute ;
                                                        }
                                                        if (hourOfDay < 10)
                                                        {
                                                            SmHour = "0"+hourOfDay ;
                                                        }

                                                        textview.setText(SmHour + ":" + Sminute);
                                                    }
                                                }, mHour, mMinute, true);

                                        timePickerDialog.show();

                                    }
                                });


                            }
                            else if(c.getString("INPUT").toString().equals("DATE"))
                            {
                                button_send.setEnabled(false);
                               // textview.setShowSoftInputOnFocus(false);
                                textview.setFocusable(false);
                                //  textview.setEnabled(false);
                                textview.setOnClickListener(new View.OnClickListener() {
                                    int mMinute, mYear, mMonth, mDay;
                                    @Override
                                    public void onClick(View v) {
                                        textview.setEnabled(true);
                                        final Calendar cc = Calendar.getInstance();
                                        mYear =cc.get(Calendar.YEAR);
                                        mMonth =cc.get(Calendar.MONTH);
                                        mDay =cc.get(Calendar.DAY_OF_MONTH);
                                        DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register.this,
                                                new DatePickerDialog.OnDateSetListener() {

                                                    @Override
                                                    public void onDateSet(DatePicker view, int year,
                                                                          int monthOfYear, int dayOfMonth) {
                                                        String sdayOfMonth = dayOfMonth+"" ;
                                                        String smonthOfYear = monthOfYear+1+"" ;
                                                        if(dayOfMonth < 10)
                                                        {
                                                            sdayOfMonth = "0"+sdayOfMonth ;
                                                        }
                                                        if(monthOfYear < 10)
                                                        {
                                                            smonthOfYear = "0"+smonthOfYear ;
                                                        }

                                                        textview.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                                                    }
                                                }, mYear, mMonth, mDay);
                                        datePickerDialog.show();
                                    }
                                });
                            }
                            else if( c.getString("INPUT").toString().equals("DATETIME"))
                            {
                                button_send.setEnabled(false);
                                textview.setFocusable(false);
                              //  textview.setShowSoftInputOnFocus(false);
                                textview.setOnClickListener(new View.OnClickListener() {
                                    int mHour ,mMinute, mYear, mMonth, mDay;
                                    String xxdayofmount ;
                                    @Override
                                    public void onClick(View v) {

                                        textview.setEnabled(true);
                                        final Calendar cc = Calendar.getInstance();
                                        mYear =cc.get(Calendar.YEAR);
                                        mMonth =cc.get(Calendar.MONTH);
                                        mDay =cc.get(Calendar.DAY_OF_MONTH);

                                        DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register.this,
                                                new DatePickerDialog.OnDateSetListener() {

                                                    @Override
                                                    public void onDateSet(DatePicker view, int year,
                                                                          int monthOfYear, int dayOfMonth) {

                                                        String sdayOfMonth = dayOfMonth+"" ;
                                                        String smonthOfYear = monthOfYear+1+"" ;
                                                        if(dayOfMonth < 10)
                                                        {
                                                            sdayOfMonth = "0"+sdayOfMonth ;
                                                        }
                                                        if(monthOfYear < 10)
                                                        {
                                                            smonthOfYear = "0"+smonthOfYear ;
                                                        }

                                                        textview.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                                                    }
                                                }, mYear, mMonth, mDay);

                                        TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_Mr_Register.this,
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                                          int minute) {
                                                        String Sminute = minute+"" ;
                                                        String SmHour = hourOfDay+"" ;
                                                        if (minute < 10)
                                                        {
                                                            Sminute = "0"+minute ;
                                                        }
                                                        if (hourOfDay < 10)
                                                        {
                                                            SmHour = "0"+hourOfDay ;
                                                        }
                                                        String sdayOfMonth = mDay+"" ;
                                                        String smonthOfYear = mMonth+1+"" ;
                                                        if(mDay < 10)
                                                        {
                                                            sdayOfMonth = "0"+mDay ;
                                                        }
                                                        if(mMonth < 10)
                                                        {
                                                            smonthOfYear = "0"+mMonth ;
                                                        }
                                                        mMonth =mMonth +1 ;

                                                       // xxdayofmount = xxdayofmount + " - "+SmHour + ":" + Sminute ;
                                                        textview.append(" - "+SmHour + ":" + Sminute);

                                                    }
                                                }, mHour, mMinute, true);


                                        timePickerDialog.show();
                                        datePickerDialog.show();
                                    }
                                });

                            }
                            else
                            {
                                textview.setInputType(3);
                            }

                            textview.setTextSize(16);
                            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                                    android.widget.LinearLayout.LayoutParams.FILL_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            params2.addRule(RelativeLayout.BELOW, idLastChild);
                            textview.setLayoutParams(params2) ;

                            idLastChild = idLastChild +1 ;
                            textview.setId(idLastChild);
                            layout.addView(textview);
                            etCollection.add(textview);
                            count = count +1 ;



                            Item_Reagister.put(c.getString("REGIS_CODE").toString().trim(),new Item_Register(0,c.getString("REGIS_CODE").toString().trim(),c.getString("CHECK_OPERATION").toString(),c.getString("ERROR_MAX").toString()
                                    ,c.getString("ERROR_MIN").toString() ,c.getString("CHECK_STATE").toString(),c.getString("MAX_READ").toString(),c.getString("MIN_READ").toString(),textview ,c.getString("BEFORE_READ").toString().trim(),c.getString("AFTER_DOT").toString().trim(),c.getString("BEFORE_DOT").toString().trim(),c.getString("INPUT").toString().trim()) ) ;
                        }
                        if (c.getString("REGIS_CODE").trim().toString().equals("888") || c.getString("REGIS_CODE").trim().toString().equals("88"))
                        {
                            EX88 = (SwitchCompat) findViewById(R.id.switch_compat6);
                            if(c.getString("NOW_READ").trim().toString().equals("1"))
                            {

                                EX88.setChecked(true);
                            }
                            else
                            {
                                EX88.setChecked(false);
                            }
                        }


                    }
                    else
                    {

                        TextWatcher Disble_send  = new TextWatcher() {
                            @Override
                            public void afterTextChanged(Editable s) {
                                button_send.setEnabled(false);
                            }
                            @Override
                            public void beforeTextChanged(CharSequence s, int start,
                                                          int count, int after) {
                                button_send.setEnabled(false);

                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start,
                                                      int before, int count) {

                                button_send.setEnabled(false);
                            }
                        } ;

                        Log.i("OTHEROTHER",c.getString("REGIS_CODE").toString().trim());

                        if(!c.getString("NOW_READ").toString().trim().equals(""))
                        {



                            // SwitchCompat EX1 ,EX2 ,EX3 ,EX4 , EX88 ;
                            if(c.getString("REGIS_CODE").toString().trim().equals("EX1"))
                            {
                                Log.i("EX1",c.getString("NOW_READ").toString());
                                EX1 = (SwitchCompat) findViewById(R.id.switch_compat1);

                                if(c.getString("NOW_READ").toString().trim().equals("1"))
                                {

                                    EX1.setChecked(true);
                                }
                                else
                                {
                                    EX1.setChecked(false);
                                }
                            }
                            else if (c.getString("REGIS_CODE").toString().trim().equals("EX2"))
                            {
                                EX2 = (SwitchCompat) findViewById(R.id.switch_compat2);
                                if(c.getString("NOW_READ").trim().toString().equals("1"))
                                {

                                    EX2.setChecked(true);
                                }
                                else
                                {
                                    EX2.setChecked(false);
                                }
                            }
                            else if (c.getString("REGIS_CODE").trim().toString().equals("EX3"))
                            {
                                Log.i("EX3",c.getString("NOW_READ").toString());
                                EX3 = (SwitchCompat) findViewById(R.id.switch_compat4);
                                if(c.getString("NOW_READ").trim().toString().equals("1"))
                                {

                                    EX3.setChecked(true);
                                }
                                else
                                {
                                    EX3.setChecked(false);
                                }
                            }
                            else if (c.getString("REGIS_CODE").trim().toString().equals("EX4"))
                            {
                                EX4 = (SwitchCompat) findViewById(R.id.switch_compat5);
                                if(c.getString("NOW_READ").trim().toString().equals("1"))
                                {

                                    EX4.setChecked(true);
                                }
                                else
                                {
                                    EX4.setChecked(false);
                                }
                            }
                            else if (c.getString("REGIS_CODE").trim().toString().equals("888") || c.getString("REGIS_CODE").trim().toString().equals("88"))
                            {
                                EX88 = (SwitchCompat) findViewById(R.id.switch_compat6);
                                if(c.getString("NOW_READ").trim().toString().equals("1"))
                                {

                                    EX88.setChecked(true);
                                }
                                else
                                {
                                    EX88.setChecked(false);
                                }
                            }
                            else if(c.getString("REGIS_CODE").trim().toString().equals("IN1"))
                            {
                                IN1 = (EditText) findViewById(R.id.etxt_Error_code);
                                IN1.setText(c.getString("NOW_READ").trim().toString());
                                IN1.addTextChangedListener(Disble_send);
                            }
                            else if(c.getString("REGIS_CODE").trim().toString().equals("IN2"))
                            {
                                IN2 = (EditText) findViewById(R.id.etxt_Warning_code);
                                IN2.setText(c.getString("NOW_READ").trim().toString());
                                IN2.addTextChangedListener(Disble_send);
                            }


                            else if(c.getString("REGIS_CODE").trim().toString().equals("IN3"))
                            {
                                IN3 = (RadioGroup) findViewById(R.id.radio_all);
                                String now_read_value = c.getString("NOW_READ").trim().toString().trim();
                                RadioButton   radio_AB1   = (RadioButton) findViewById(R.id.radio_AB1);
                                RadioButton   radio_AB2   = (RadioButton) findViewById(R.id.radio_AB2);
                                RadioButton   radio_AB3   = (RadioButton) findViewById(R.id.radio_AB3);
                                if(now_read_value.equals("1"))
                                {
                                    radio_AB1.setChecked(true);
                                }
                                else if (now_read_value.equals("2"))
                                {
                                    radio_AB2.setChecked(true);
                                }
                                else if (now_read_value.equals("3"))
                                {
                                    radio_AB3.setChecked(true);
                                }

                            }
                            else if(c.getString("REGIS_CODE").trim().toString().equals("IN4"))
                            {
                                IN4 = (EditText) findViewById(R.id.etxt_time_read_pos);
                                IN4.setText(c.getString("NOW_READ").trim().toString());
                                IN4.addTextChangedListener(Disble_send);
                            }

                            else if(c.getString("REGIS_CODE").trim().toString().equals("IN5"))
                            {
                                IN5 = (EditText) findViewById(R.id.etxt_Reset);
                                IN5.setText(c.getString("NOW_READ").trim().toString());
                                IN5.addTextChangedListener(Disble_send);
                            }
                            else if(c.getString("REGIS_CODE").trim().toString().equals("IN6"))
                            {
                                IN6 = (EditText) findViewById(R.id.etxt_Reset_2);
                                IN6.setText(c.getString("NOW_READ").trim().toString());
                                IN6.addTextChangedListener(Disble_send);
                            }
                            else if(c.getString("REGIS_CODE").trim().toString().equals("TXT"))
                            {
                                TXT = (EditText) findViewById(R.id.etxt_See_also);
                                TXT.setText(c.getString("NOW_READ").trim().toString());
                                TXT.addTextChangedListener(Disble_send);

                            }
                        }
                    }


                }




            }
            catch(Exception e){
                Log.i(" pea_no.setText", e.toString());
            }

            pdLoading.dismiss();
        }


    }

    class Mr_UpdateRegister extends AsyncTask<String, String, JSONArray>
    {

        URL url = null;

        HttpURLConnection conn;
        ProgressDialog pdLoadingx = new ProgressDialog(Activity_Mr_Register.this);
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;
        String url_ ;
        private LayoutInflater inflater;
        private ViewGroup container;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoadingx.setMessage("\tLoading...");
            pdLoadingx.setCancelable(false);
            pdLoadingx.show();
        }
        @Override
        protected JSONArray doInBackground(String... params) {

            JSONArray network = null;
            try {
                url_ = "http://extranet.pea.co.th/peap3/android/Mr_UpdateRegister.ashx?key="+params[0];
                url_ = url_.replace(" ","%20");

                Log.i("url_",url_) ;
                url = new URL(url_);
            } catch (MalformedURLException e) {
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
                Log.i("ififif", "if1");
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("ififif", "if2");



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

                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.button_check), "บันทึกข้อมูลเรียบร้อย", Snackbar.LENGTH_LONG);

                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.GREEN);

                    snackbar.show();
                    Log.i("ififif", "if");
                }
                else
                {
                    Log.i("else", "else");
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.button_check), "ไม่สามารถ บันทึกข้อมูลได้01", Snackbar.LENGTH_LONG);

                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbar.show();

                }


            } catch (IOException e) {
                Log.i("catch", "catch");

            //    String Pea_number ,ReadDate_plan ;
                offline(url_,Pea_number,ReadDate_plan);



                network = null;
                e.printStackTrace();


            } finally {
                conn.disconnect();
            }


            return network;


        }
        public void offline(String message,String Peameter ,String Read_date_plan)
        {
           // offline(url_,Pea_number,ReadDate_plan);
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.button_check), "ไม่สามารถ บันทึกข้อมูลได้02 ข้อมูลจะถูบันทึกแบบ Offline", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();

          //  checkConnection();
            final myDBClass myDb3 = new myDBClass(Activity_Mr_Register.this);
            myDb3.getWritableDatabase();



            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> city = new HashMap<>();
            city.put("IsUPdate",true);
            city.put("URL",message);
            DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);
            city.put("time_update","") ;
            city.put("date",reportDate);
            Log.i("Peameter",Peameter);
            boolean chenk_insert_offline_data = myDb3.Insert_MR_OFFLINE_DATA(Peameter,Read_date_plan,true,message,"",reportDate) ;
            if(chenk_insert_offline_data)
            {
                Log.i("chenk_insert_offline","OK") ;
            }
            else
            {
                Log.i("chenk_insert_offline","NOT OK") ;
            }
            db.collection("Test")
                    .add(city)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Sucess", "DocumentSnapshot added with ID: " + documentReference.getId());
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.button_check), "ไม่สามารถ บันทึกข้อมูลได้02 ข้อมูลจะถูบันทึกแบบ Offline", Snackbar.LENGTH_LONG);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.RED);
                            snackbar.show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("onFailure", "Error adding document", e);
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.button_check), "ไม่สามารถ บันทึกข้อมูลได้02 ข้อมูลไม่ถูกบันทึก", Snackbar.LENGTH_LONG);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.RED);
                            snackbar.show();
                        }
                    }) ;
        }
        @Override
        protected void onPostExecute(JSONArray result) {

            pdLoadingx.dismiss();
            //     Toast.makeText(mContext, "XXXXX", Toast.LENGTH_LONG).show();

        }
    }

}
