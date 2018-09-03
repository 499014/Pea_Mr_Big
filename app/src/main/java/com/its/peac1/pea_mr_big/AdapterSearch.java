package com.its.peac1.pea_mr_big;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by 499014 on 30/11/2560.
 */

public class AdapterSearch extends BaseAdapter {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private LayoutInflater mInflater;
    Context mContext;
    List<String> sPea;
    List<String>  sCa;
    List<String>  sName;
    String userId ;

    public AdapterSearch(Context context, List<String> sPea, List<String> sCa, List<String> sName) {
        this.mContext= context;
        this.sCa = sCa;
        this.sName = sName;

        final myDBClass myDb = new myDBClass(context);
        myDb.getWritableDatabase() ;
        userId = myDb.SelectUserId();
        myDb.close();
    }
    @Override
    public int getCount() {
        return sName.size();
    }

    @Override
    public Object getItem(int position) {
        return null ;//mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.adapter_item_mr_day, parent, false);

        return view;
        // return null;
    }



}
