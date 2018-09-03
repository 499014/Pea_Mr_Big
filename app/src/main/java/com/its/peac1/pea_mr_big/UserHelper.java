package com.its.peac1.pea_mr_big;

/**
 * Created by 499014 on 10/11/2560.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 499014 on 30/10/2560.
 */


public  class UserHelper {
    Context context;
    SharedPreferences sharedPerfs;
    SharedPreferences.Editor editor;

    // Prefs Keys
    static String perfsName = "UserHelper";
    static int perfsMode = 0;


    public UserHelper(Context context) {
        this.context = context;
        this.sharedPerfs = this.context.getSharedPreferences(perfsName, perfsMode);
        this.editor = sharedPerfs.edit();
    }

    public void createSession(String sMemberID, String sMeberNAME) {

        editor.putBoolean("LoginStatus", true);
        editor.putString("MemberID", sMemberID);
        editor.putString("MemberNAME", sMeberNAME);

        editor.commit();
    }

    public void deleteSession() {
        editor.clear();
        editor.commit();
    }

    public boolean getLoginStatus() {
        return sharedPerfs.getBoolean("LoginStatus", false);
    }

    public String getMemberID() {
        return sharedPerfs.getString("MemberID", null);
    }

    public String getMemberName() {return sharedPerfs.getString("MemberNAME", null);}


}