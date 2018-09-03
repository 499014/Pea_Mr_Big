package com.its.peac1.pea_mr_big;

/**
 * Created by 499014 on 13/11/2560.
 */

public class Item_MrSelectSearchMaster {
    String ca ;
    String pea_meter ;
    String  name ;
    String address ;
    String ReadDatePlan ;
    String mru  ;
    String State_offline ;
    String Metertype ;
    String latitude ;
    String longitude ;




    public Item_MrSelectSearchMaster(String ca, String pea_meter, String name, String address,String ReadDatePlan,String mru ,String State_offline,String Metertype ,String latitude ,String longitude) {
        this.ca = ca ;
        this.pea_meter = pea_meter ;
        this.name = name ;
        this.address =  address;
        this.ReadDatePlan = ReadDatePlan ;
        this.mru = mru ;
        this.State_offline = State_offline ;
        this.Metertype = Metertype ;
        this.latitude = latitude ;
        this.longitude = longitude ;
    }

    public String getlatitude() {
        return latitude;
    }

    public void setlatitude(String latitude) {
        latitude = latitude;
    }

    public String getlongitude() {
        return longitude;
    }

    public void selongitude(String longitude) {
        Metertype = longitude;
    }

    public String getMetertype() {
        return Metertype;
    }

    public void setMetertype(String metertype) {
        Metertype = metertype;
    }

    public String getState_offline() {
        return State_offline;
    }

    public void setState_offline(String state_offline) {
        State_offline = state_offline;
    }

    public String getMru() {
        return mru;
    }

    public void setMru(String mru) {
        this.mru = mru;
    }

    public String getCa() {
        return ca;
    }

    public String getPea_meter() {
        return pea_meter;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public void setPea_meter(String pea_meter) {
        this.pea_meter = pea_meter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setReadDatePlan(String readDatePlan) {
        ReadDatePlan = readDatePlan;
    }



    public String getReadDatePlan() {
        return ReadDatePlan;
    }

}
