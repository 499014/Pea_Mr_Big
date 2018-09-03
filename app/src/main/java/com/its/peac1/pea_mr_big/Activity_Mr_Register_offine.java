package com.its.peac1.pea_mr_big;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by 499014 on 20/3/2561.
 */

public class Activity_Mr_Register_offine extends AppCompatActivity {
    Cursor C_Meter_model ;
    String S_Meter_model ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        // Log.i("aaaaaa",""+  BigDecimal.valueOf(57.257).subtract(BigDecimal.valueOf(0.001) ));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mr_register_offline);

        Bundle b = getIntent().getExtras();
        // or other values
        if(b != null) {
            S_Meter_model = b.getString("Pea_Model");


        }

        final myDBClass myDb = new myDBClass(this);
        C_Meter_model = myDb.SelectMeterModel(S_Meter_model);
        Log.i("C_Meter_model",C_Meter_model.getCount()+"");
        final EditText etxt_dateplan = (EditText) findViewById(R.id.etxt_dateplan);
        etxt_dateplan.setOnClickListener(new View.OnClickListener() {
            int mMinute, mYear, mMonth, mDay;
            @Override
            public void onClick(View v) {
                final Calendar cc = Calendar.getInstance();
                mYear =cc.get(Calendar.YEAR);
                mMonth =cc.get(Calendar.MONTH);
                mDay =cc.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Mr_Register_offine.this,
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

                                etxt_dateplan.setText(sdayOfMonth + "/" + (smonthOfYear ) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }
}
