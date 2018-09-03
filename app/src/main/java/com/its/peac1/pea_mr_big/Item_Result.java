package com.its.peac1.pea_mr_big;

/**
 * Created by 499014 on 21/11/2560.
 */

public class Item_Result {
    String READER ;
    String READ_DATE_PLAN ;
    String PEANO  ;
    String REGIS_CODE ;
    String VALUE ;
    String READED_TIME ;
    String CHECK_STATE ;
    double latitude;
    double longitude ;

    public Item_Result(String READER, String READ_DATE_PLAN, String PEANO, String REGIS_CODE, String VALUE, String READED_TIME, String CHECK_STATE,double latitude,double longitude) {
        this.READER = READER;
        this.READ_DATE_PLAN = READ_DATE_PLAN;
        this.PEANO = PEANO;
        this.REGIS_CODE = REGIS_CODE;
        this.VALUE = VALUE;
        this.READED_TIME = READED_TIME;
        this.CHECK_STATE = CHECK_STATE;
        this.latitude = latitude ;
        this.longitude = longitude ;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getREADER() {
        return READER;
    }

    public String getREAD_DATE_PLAN() {
        return READ_DATE_PLAN;
    }

    public String getPEANO() {
        return PEANO;
    }

    public String getREGIS_CODE() {
        return REGIS_CODE;
    }

    public String getVALUE() {
        return VALUE;
    }

    public String getREADED_TIME() {
        return READED_TIME;
    }

    public String getCHECK_STATE() {
        return CHECK_STATE;
    }

    public void setREADER(String READER) {
        this.READER = READER;
    }

    public void setREAD_DATE_PLAN(String READ_DATE_PLAN) {
        this.READ_DATE_PLAN = READ_DATE_PLAN;
    }

    public void setPEANO(String PEANO) {
        this.PEANO = PEANO;
    }

    public void setREGIS_CODE(String REGIS_CODE) {
        this.REGIS_CODE = REGIS_CODE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public void setREADED_TIME(String READED_TIME) {
        this.READED_TIME = READED_TIME;
    }

    public void setCHECK_STATE(String CHECK_STATE) {
        this.CHECK_STATE = CHECK_STATE;
    }
}
