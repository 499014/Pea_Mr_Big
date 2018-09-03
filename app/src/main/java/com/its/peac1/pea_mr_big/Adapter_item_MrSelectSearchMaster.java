package com.its.peac1.pea_mr_big;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 499014 on 13/11/2560.
 */

public class Adapter_item_MrSelectSearchMaster extends BaseAdapter {
    String momap ;
    public ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster;
    Activity activity;
    public Adapter_item_MrSelectSearchMaster(Activity activity, ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster)
    {
        super();
        this.Item_MrSelectSearchMaster = Item_MrSelectSearchMaster;
        this.activity = activity ;


    }
    @Override
    public int getCount() {
        return Item_MrSelectSearchMaster.size();
    }

    @Override
    public Object getItem(int position) {
        return Item_MrSelectSearchMaster.get(position);
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
        ImageView map ;
        String ReadDatePlan ;
        RelativeLayout RelativeLayout_xxx;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_mr_select_serach_master, null);
            holder = new Adapter_item_MrSelectSearchMaster.ViewHolder();
            holder.RelativeLayout_xxx = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout_xxx);
            holder.ca = (TextView) convertView.findViewById(R.id.ca);
            holder.pea = (TextView) convertView.findViewById(R.id.pea);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.mru = (TextView) convertView.findViewById(R.id.mru);
            holder.map = (ImageView) convertView.findViewById(R.id.map) ;
         //   holder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);


        } else {
            holder = (Adapter_item_MrSelectSearchMaster.ViewHolder) convertView.getTag();
        }

        final  Item_MrSelectSearchMaster item = Item_MrSelectSearchMaster.get(position);


        Log.i("sssssssd",item.getlatitude().toString());

        if(!item.getPea_meter().toString().equals("")) {

            convertView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Activity_Mr_Register.class);
                    Bundle b = new Bundle();
                    b.putString("METERTYPE",item.getMetertype().toString());
                    b.putString("Pea_meter", item.getPea_meter().toString());
                    Log.i("MetertypeMetertype",item.getMetertype().toString()) ;
                    b.putString("ReadDatePlan",item.getReadDatePlan().toString() );
                    b.putString("State_offline",item.getState_offline().toString() );
                    b.putString("Pea_Model","" );
                    i.putExtras(b);


                    String lat ="7.0070307";  // ละติจูดสมมุติ
                    String lng ="100.5019775";  // ลองจิจูดสมมุติ
                    String strUri = "http://maps.google.com/maps?q=loc:" + lat + "," + lng + " (" + "Label which you want" + ")";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    //startActivity(intent);
                    activity.startActivity(i);
                 //   activity.startActivity(intent);

                }
            });

            convertView.findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    try {


            if(!item.getlatitude().toString().trim().equals("NO DATA") && !item.getlatitude().toString().trim().equals("") )
            {


                String lat =item.getlatitude().trim();  // ละติจูดสมมุติ
                String lng =item.getlongitude().trim();  // ลองจิจูดสมมุติ
                String strUri = "https://www.google.co.th/maps/search/"+lat+","+lng;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                //startActivity(intent);
                // activity.startActivity(i);
                activity.startActivity(intent);
            }
            else
            {


            }
            }
            catch (Exception e)
            { }

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
        holder.ca.setText(item.getCa().toString());
        holder.pea.setText("PEANO  : "+ item.getPea_meter().toString());
        holder.name.setText(item.getName().toString());
        holder.mru.setText(item.getMru().toString());
        if(!item.getlatitude().toString().trim().equals("NO DATA") && !item.getlatitude().toString().trim().equals("") ) {
            holder.map.setImageResource(R.drawable.mapicon);
        }
        else
        {
            holder.map.setImageResource(R.drawable.nomap);
        }





   //     holder.address.setText(item.getAddress().toString());

        return convertView;

    }
}
