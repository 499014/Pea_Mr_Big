package com.its.peac1.pea_mr_big;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class Activity_Mr_send_offline extends AppCompatActivity {
String userid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__mr_send_offline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final myDBClass myDb = new myDBClass(this);
        myDb.getWritableDatabase();
        userid = myDb.SelectUserId() ;
        Cursor CurData = myDb.SelectMR_OFFLINE_DATA() ;
        if (CurData.getCount() == 0) {

        }else
            {
            try {
                while (!CurData.isAfterLast()) {
                    Log.i("PEA_NO",CurData.getString(0)) ;
                }
        }
        catch (Exception e)
        {

        }
            }
        }

}
