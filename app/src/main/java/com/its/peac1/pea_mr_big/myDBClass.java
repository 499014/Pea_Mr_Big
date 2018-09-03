package com.its.peac1.pea_mr_big;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 499014 on 30/10/2560.
 */

public class myDBClass  extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ReadMR_BIG";
    // Table Name
    private static final String MR_MEMBER  = "MR_MEMBER";
    private static final String MR_MASTER = "MR_MASTER";
    private static final String MR_CUSTOMER = "MR_CUSTOMER";
    private static final String MR_METERMODEL = "MR_METERMODEL";
    private static final String MR_REGISTER_ALL = "MR_REGISTER_ALL";
    private static final String MR_MEMBER_HEDER_TEMP  = "MR_MEMBER_HEDER_TEMP";
    private static final String MR_MEMBER_HEDER_MASTER  = "MR_MEMBER_HEDER_MASTER";
    private static final String MR_OFFLINE_DATA  = "MR_OFFLINE_DATA";
    private static final String MR_REGISTER = "V_MR_REGISTER";
    private static final String MR_TRAN = "MR_TRAN";
    public myDBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    public myDBClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MR_MEMBER +
                "(MemberID TEXT(10)," +
                " UsrInfo TEXT(10)," +
                " Name TEXT(100)," +
                " ba TEXT(20));");
        db.execSQL("CREATE TABLE " + MR_MASTER +
                "(MemberID INTEGER ," +
                " Read_date_plan TEXT(10)," +
                " Total INTEGER," +
                " Read INTEGER);");
        db.execSQL("CREATE TABLE " + MR_CUSTOMER +
                "(CA INTEGER ," +
                " Peano TEXT(10)," +
                " Cust_name TEXT(20)," +
                " connobj_address TEXT(30));");

        db.execSQL("CREATE TABLE " + MR_METERMODEL +
                "(METER_MODEL TEXT(30)," +
                " REGISTER_GROUP TEXT(20)," +
                " REGIS_NO TEXT(10)," +
                " REGIS_CODE TEXT(10)," +
                " REGIS_TEXT TEXT(50)," +
                " REG_TYPE TEXT(10)," +
                " UMR TEXT(10)," +
                " INPUT TEXT(20)," +
                " BEFORE_DOT TEXT(10)," +
                " AFTER_DOT TEXT(10));");

        db.execSQL("CREATE TABLE " + MR_OFFLINE_DATA +
                "(PEA_METER TEXT(20)," +
                " READ_DATE_PLAN TEXT(10)," +
                " IsUpdate Boolean," +
                " URL TEXT(300)," +
                " Time_upldate TEXT(15)," +
                " date TEXT(15));");


        db.execSQL("CREATE TABLE " + MR_REGISTER_ALL +
             //   "(READ_DATE_PLAN TEXT(1)," +

                   //     "(READ_PLAN TEXT(10)," +
                  //      "MRU TEXT(1)," +
                        "(PEANO TEXT(20)," +
                  //      "CA TEXT(1)," +
                "REGISTER_GROUP TEXT(30)," +
                " MR_REASON TEXT(5)," +
                " REGIS_NO TEXT(10)," +
                " REGIS_CODE TEXT(10)," +
                " REGIS_TEXT TEXT(40)," +
               // " REG_TYPE TEXT(10)," +
               // " UMR TEXT(10)," +
              //  " PD TEXT(5)," +
              //  " DP TEXT(5)," +
              //  " MRO TEXT(10)," +
                " NOW_READ TEXT(20)," +
                " MAX_READ TEXT(20)," +
                " MIN_READ TEXT(10)," +
             //   " READED_TIME TEXT(1)," +
                " INPUT TEXT(10)," +
                " BEFORE_DOT TEXT(4)," +
                " AFTER_DOT TEXT(4)," +
                " NOTE_READ TEXT(10)," +
                " CHECK_OPERATION TEXT(4)," +
                " ERROR_MAX TEXT(20)," +
                " ERROR_MIN TEXT(20)," +
                " CHECK_STATE TEXT(5)," +
                " BEFORE_READ TEXT(18));");

/*
        db.execSQL("CREATE TABLE " + MR_MASTER +
                "(MemberID INTEGER ," +
                " Read_date_plan TEXT(10)," +
                " Total INTEGER," +
                " Read INTEGER);");
*/
        db.execSQL("CREATE TABLE " + MR_MEMBER_HEDER_TEMP +
                "(USERID TEXT(10) ," +
                " READ_DATE_PLAN TEXT(10)," +
                " PEANO TEXT(25)," +
                " CUST_NAME TEXT(50)," +
                " CA TEXT(15)," +
                " MRO TEXT(10)," +
                " CHANG_METER TEXT(2)," +
                " METERTYPE  TEXT(15));");
        db.execSQL("CREATE TABLE " + MR_MEMBER_HEDER_MASTER +
                "(USERID TEXT(10) ," +
                " READ_DATE_PLAN TEXT(10)," +
                " PEANO TEXT(25)," +
                " CUST_NAME TEXT(50)," +
                " CA TEXT(15)," +
                " MRO TEXT(10)," +
                " CHANG_METER TEXT(2)," +
                " METERTYPE  TEXT(15));");

    }
    public boolean Insert_MR_OFFLINE_DATA(String PEA_METER ,String READ_DATE_PLAN ,Boolean IsUpdate ,String URL,String Time_upldate ,String date) {
        SQLiteDatabase db;
        long rows = 0;
            try {
                db = this.getWritableDatabase();
                ContentValues Val = new ContentValues();
                Val.put("PEA_METER", PEA_METER);
                Val.put("READ_DATE_PLAN", READ_DATE_PLAN);
                Val.put("IsUpdate", IsUpdate);
                Val.put("URL", URL);
                Val.put("Time_upldate", Time_upldate);
                Val.put("date", date);
                rows = db.insert(MR_OFFLINE_DATA, null, Val);
                Log.i("Insert_MR_OFFLINE_DATA",rows+"");
                db.close();
                return  true ;
            } catch (Exception e) {
                e.printStackTrace();
                return  false ;
            }
    }
    public Cursor UpdateMR_OFFLINE_DATA(String PEA_METER ,String READ_DATE_PLAN) {
        String arrData[] = null;
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "UPDATE "+MR_OFFLINE_DATA +" SET IsUpdate = 0  where PEA_METER =  '"+PEA_METER+"' and READ_DATE_PLAN = '"+READ_DATE_PLAN+"'" ;
            Log.i("strSQL",strSQL);
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            db.close();
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }
    public boolean Delete_OFFLINE_DATA() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        try {

            db.execSQL("delete from "+ MR_OFFLINE_DATA);
            db.close();
            return  true ;
        }
        catch (Exception e)
        {
            return  false ;
        }
    }
    public Cursor  SelectMR_OFFLINE_DATA() {
        String arrData[] = null;
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  * FROM "+MR_OFFLINE_DATA  +" where IsUpdate =  1" ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            db.close();
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }
    public  boolean DeleteMR_MEMBER_HEDER_MASTER()
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        try {

            db.execSQL("delete from "+ MR_MEMBER_HEDER_MASTER);
            db.close();
            return  true ;
        }
        catch (Exception e)
        {
            return  false ;
        }

    }
    public  boolean DeleteMR_MEMBER_HEDER_TEMP()
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        try {

            db.execSQL("delete from "+ MR_MEMBER_HEDER_TEMP);
            db.close();
            return  true ;
        }
        catch (Exception e)
        {
            return  false ;
        }

    }
    public boolean Insert_MEMBER_HEDER_MASTER() {
        SQLiteDatabase db ;
        db = this.getReadableDatabase();
        String sql = "INSERT INTO "+MR_MEMBER_HEDER_MASTER +"\n" +
                "SELECT * \n" +
                "FROM   "+MR_MEMBER_HEDER_TEMP ;
        try {

            db.execSQL(sql);
            db.close();
            return  true ;
        }
        catch (Exception e)
        {
            return  false ;
        }

    }
    public long Insert_MR_MEMBER_HEDER_TEMP(JSONArray result,String USERID ,String READ_DATE_PLAN) {
        try {
            SQLiteDatabase db;
            long rows = 0;
            for (int i = 0; i < result.length(); i++) {
                try {
                    db = this.getWritableDatabase();
                    JSONObject c = result.getJSONObject(i);
                    ContentValues Val = new ContentValues();
                    Val.put("USERID", USERID);
                    Val.put("READ_DATE_PLAN", READ_DATE_PLAN);
                    Val.put("PEANO", c.getString("PEANO"));
                    Val.put("CUST_NAME", c.getString("CUST_NAME"));
                    Val.put("CA", c.getString("CA"));
                    Val.put("MRO", c.getString("MRU"));
                    Val.put("CHANG_METER", "");
                    Val.put("METERTYPE", c.getString("METERTYPE"));

                    Log.i("Insert_MR_MEMBER_HEDER_TEMP_PEANO ("+i+")",c.getString("METERTYPE")) ;
                    rows = db.insert(MR_MEMBER_HEDER_TEMP, null, Val);
                    db.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return rows; // return rows inserted.
        } catch (Exception e) {
            return -1;
        }
    }

    public long Insert_MR_REGISTER_ALL(String READ_DATE_PLAN,String READ_PLAN,String MRU,String PEANO,String CA,String REGISTER_GROUP, String MR_REASON, String REGIS_NO, String REGIS_CODE,String REGIS_TEXT,String REG_TYPE,String UMR,String PD,String DP,String MRO,String NOW_READ,String MAX_READ
            ,String MIN_READ,String READED_TIME,String INPUT,String BEFORE_DOT,String AFTER_DOT,String NOTE_READ,String CHECK_OPERATION,String ERROR_MAX,String ERROR_MIN,String CHECK_STATE,String BEFORE_READ) {
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data
            ContentValues Val = new ContentValues();
            Val.put("READ_DATE_PLAN", READ_DATE_PLAN);
            Val.put("READ_PLAN", READ_PLAN);
            Val.put("MRU", MRU);
            Val.put("PEANO", PEANO);
            Val.put("CA", CA);
            Val.put("REGISTER_GROUP", REGISTER_GROUP);
            Val.put("MR_REASON", MR_REASON);
            Val.put("REGIS_NO", REGIS_NO);
            Val.put("REGIS_CODE", REGIS_CODE);
            Val.put("REGIS_TEXT", REGIS_TEXT);
            Val.put("REG_TYPE", REG_TYPE);
            Val.put("UMR", UMR);
            Val.put("PD", PD);
            Val.put("DP", DP);
            Val.put("MRO", MRO);
            Val.put("NOW_READ", NOW_READ);
            Val.put("MAX_READ", MAX_READ);
            Val.put("MIN_READ", MIN_READ);
            Val.put("READED_TIME", READED_TIME);
            Val.put("INPUT", INPUT);
            Val.put("BEFORE_DOT", BEFORE_DOT);
            Val.put("AFTER_DOT", AFTER_DOT);
            Val.put("NOTE_READ", NOTE_READ);
            Val.put("CHECK_OPERATION", CHECK_OPERATION);
            Val.put("ERROR_MAX", ERROR_MAX);
            Val.put("ERROR_MIN", ERROR_MIN);
            Val.put("CHECK_STATE", CHECK_STATE);
            Val.put("BEFORE_READ", BEFORE_READ);
            long rows = db.insert(MR_REGISTER_ALL, null, Val);
            db.close();
            return rows; // return rows inserted.
        } catch (Exception e) {
            return -1;
        }
    }
    public long Insert_MR_REGISTER_ALL(JSONArray result) {
        try {
            SQLiteDatabase db;
            long rows = 0;
            db = this.getWritableDatabase();
            ContentValues Val ;
            JSONObject c ;
            String sql = "INSERT INTO MR_REGISTER_ALL ('PEANO','REGISTER_GROUP','MR_REASON','REGIS_NO','REGIS_CODE','REGIS_TEXT','NOW_READ','MAX_READ','MIN_READ'," ;
            sql = sql+ "'INPUT','BEFORE_DOT','AFTER_DOT','NOTE_READ','CHECK_OPERATION','ERROR_MAX','ERROR_MIN','CHECK_STATE','BEFORE_READ') VALUES ";
            for (int i = 0; i < result.length(); i++) {
                try {

                    Val  = new ContentValues() ;

                     c = result.getJSONObject(i);
                     /*
                    sql = sql + " ( '"+c.getString("PEANO")+"', ";
                    sql = sql + "'"+c.getString("REGISTER_GROUP")+"', ";
                    sql = sql + "'"+c.getString("MR_REASON")+"', ";
                    sql = sql + "'"+c.getString("REGIS_NO")+"', ";
                    sql = sql + "'"+c.getString("REGIS_CODE")+"', ";
                    sql = sql + "'"+c.getString("REGIS_TEXT")+"', ";
                    sql = sql + "'"+c.getString("NOW_READ")+"', ";
                    sql = sql + "'"+c.getString("MAX_READ")+"', ";
                    sql = sql + "'"+c.getString("MIN_READ")+"', ";
                    sql = sql + "'"+c.getString("INPUT")+"', ";
                    sql = sql + "'"+c.getString("BEFORE_DOT")+"', ";
                    sql = sql + "'"+c.getString("AFTER_DOT")+"', ";
                    sql = sql + "'"+c.getString("NOTE_READ")+"', ";
                    sql = sql + "'"+c.getString("CHECK_OPERATION")+"', ";
                    sql = sql + "'"+c.getString("ERROR_MAX")+"', ";
                    sql = sql + "'"+c.getString("ERROR_MIN")+"', ";
                    sql = sql + "'"+c.getString("CHECK_STATE")+"', ";
                    sql = sql + "'"+c.getString("BEFORE_READ")+"' ";
                    sql = sql + ") ;" ;

                   // Log.i("sql value",sql);
                    db.execSQL(sql);

*/

               //     Val.put("READ_DATE_PLAN", c.getString("READ_DATE_PLAN"));
                //    Val.put("READ_DATE_PLAN", "");
                 //   Val.put("READ_PLAN", c.getString("READ_PLAN"));
                //    Val.put("MRU", "");
                    Val.put("PEANO", c.getString("PEANO"));
                //    Val.put("CA", "");
                    Val.put("REGISTER_GROUP", c.getString("REGISTER_GROUP"));
                    Val.put("MR_REASON", c.getString("MR_REASON"));
                    Val.put("REGIS_NO",c.getString("REGIS_NO") );
                    Val.put("REGIS_CODE", c.getString("REGIS_CODE"));
                    Val.put("REGIS_TEXT",c.getString("REGIS_TEXT") );
                  //  Val.put("REG_TYPE",c.getString("REG_TYPE") );
                 //   Val.put("UMR", c.getString("UMR"));
                 //   Val.put("PD",c.getString("PD"));
                //    Val.put("DP", c.getString("DP"));
                 //   Val.put("MRO", c.getString("MRO"));
                    Val.put("NOW_READ", c.getString("NOW_READ"));
                    Val.put("MAX_READ", c.getString("MAX_READ"));
                    Val.put("MIN_READ",c.getString("MIN_READ") );
                  //  Val.put("READED_TIME", "");
                    //Val.put("READED_TIME", c.getString("READED_TIME"));
                    Val.put("INPUT", c.getString("INPUT"));
                    Val.put("BEFORE_DOT",c.getString("BEFORE_DOT") );
                    Val.put("AFTER_DOT", c.getString("AFTER_DOT"));
                    Val.put("NOTE_READ",c.getString("NOTE_READ") );
                    Val.put("CHECK_OPERATION", c.getString("CHECK_OPERATION"));
                    Val.put("ERROR_MAX", c.getString("ERROR_MAX"));
                    Val.put("ERROR_MIN",c.getString("ERROR_MIN") );
                    Val.put("CHECK_STATE", c.getString("CHECK_STATE"));
                    Val.put("BEFORE_READ", c.getString("BEFORE_READ"));



                    rows = db.insert(MR_REGISTER_ALL, null, Val);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
           // sql = sql.substring(0, sql.length() - 1);
         //   sql = sql + " ;";
       //     Log.i("sql value",sql);


            db.close();
            return rows; // return rows inserted.
        } catch (Exception e) {
            return -1;
        }
    }
    public long Insert_MR_METERMODEL(String METER_MODEL, String REGISTER_GROUP, String REGIS_NO, String REGIS_CODE,String REGIS_TEXT,String REG_TYPE,String UMR,String INPUT,String BEFORE_DOT,String AFTER_DOT) {
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data
            ContentValues Val = new ContentValues();
            Val.put("METER_MODEL", METER_MODEL);
            Val.put("REGISTER_GROUP", REGISTER_GROUP);
            Val.put("REGIS_NO", REGIS_NO);
            Val.put("REGIS_CODE", REGIS_CODE);
            Val.put("REGIS_TEXT", REGIS_TEXT);
            Val.put("REG_TYPE", REG_TYPE);
            Val.put("UMR", UMR);
            Val.put("INPUT", INPUT);
            Val.put("BEFORE_DOT", BEFORE_DOT);
            Val.put("AFTER_DOT", AFTER_DOT);
            long rows = db.insert(MR_METERMODEL, null, Val);
            db.close();
            return rows; // return rows inserted.
        } catch (Exception e) {
            return -1;
        }
    }
    public  boolean DeleteData_MR_REGISTER_ALL()
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        try {

            db.execSQL("delete from "+ MR_REGISTER_ALL);
            db.close();
            return  true ;
        }
        catch (Exception e)
        {
            return  false ;
        }

    }

    public  boolean DeleteData_METERMODEL()
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        try {

            db.execSQL("delete from "+ MR_METERMODEL);
            db.close();
            return  true ;
        }
        catch (Exception e)
        {
            return  false ;
        }

    }

    public long Insert_MR_MEMBER(String MemberID, String UsrInfo, String Name, String Surname) {
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data
            ContentValues Val = new ContentValues();
            Val.put("MemberID", MemberID);
            Val.put("UsrInfo", UsrInfo);
            Val.put("Name", Name);
            Val.put("ba", Surname);
            long rows = db.insert(MR_MEMBER, null, Val);
            db.close();
            return rows; // return rows inserted.
        } catch (Exception e) {
            return -1;
        }
    }
    public  boolean DeleteData()
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        try {

            db.execSQL("delete from "+ MR_MEMBER);
            db.close();
            return  true ;
        }
        catch (Exception e)
        {
            return  false ;
        }

    }
    public  boolean DeleteRegisterAllData()
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        Log.i("xxxxaa","1") ;
        try {

            db.execSQL("delete from "+ MR_REGISTER_ALL);
            db.close();
            Log.i("xxxxaa","delete from "+ MR_REGISTER_ALL) ;
            return  true ;
        }
        catch (Exception e)
        {
            Log.i("xxxxaa",e.toString()) ;
            return  false ;
        }

    }
    public boolean CheckUser() {
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String count = "SELECT count(*) FROM "+MR_MEMBER;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        db.close();
        cursor.close();
        if(icount > 0) {
            return true;
        }
        else
        {
            return  false ;
        }

    }

    public int  count_data_registerALL() {
        SQLiteDatabase db;
        db = this.getReadableDatabase(); // Read Data
        String count = "SELECT count(*) FROM "+MR_REGISTER_ALL;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        db.close();
        cursor.close();
        return icount;


    }
    public String[]  SelectMeterModel() {
        String arrData[] = null;
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  METER_MODEL FROM "+MR_METERMODEL +" GROUP BY METER_MODEL" ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            String result = "" ;
            arrData = new String[cursor.getCount()];
            int i = 0 ;
            while (!cursor.isAfterLast()) {
                arrData[i] = cursor.getString(0);
                cursor.moveToNext();
                i++ ;
            }
            cursor.close();
            db.close();
            return arrData;
        } catch (Exception e) {
            return null;
        }

    }
    public Cursor  SelectMeterModel(String meter_model) {
        String arrData[] = null;
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  * FROM "+MR_METERMODEL +" where METER_MODEL = '"+meter_model+"' order by REGIS_CODE" ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            db.close();
            return cursor;
        } catch (Exception e) {
            return null;
        }

    }
    public String  SelectMaxdate() {
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  max(READ_DATE_PLAN) FROM "+MR_MEMBER_HEDER_MASTER ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            String result = "" ;
            while (!cursor.isAfterLast()) {
                result = cursor.getString(0);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    public String  SelectUserId() {
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  * FROM "+MR_MEMBER ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            String result = "" ;
            while (!cursor.isAfterLast()) {
                result = cursor.getString(1);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    public String  SelectArea() {
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  * FROM "+MR_MEMBER ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            String result = "" ;
            while (!cursor.isAfterLast()) {
                result = cursor.getString(4);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return result.substring(0,1);
        } catch (Exception e) {
            return null;
        }

    }

    public Cursor  SelectRegisterAll(String Pea_No) {
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  * FROM "+MR_REGISTER_ALL +" where PEANO = '"+Pea_No+"' order by REGIS_NO" ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            db.close();
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor  SelectMR_MEMBER_HEDER_MASTER(String Pea_No) {
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT  * FROM "+MR_MEMBER_HEDER_MASTER +" where PEANO = '"+Pea_No+"' " ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            db.close();
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }


    public Cursor  SelectMR_MEMBER_MRday_all() {
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "select read_date_plan ,count(*) num_low from  mr_member_heder_master" ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            db.close();
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor  SelectMR_MEMBER_MRday(String userid ,String read_date_plan) {
        // TODO Auto-generated method stub
        Cursor cursor ;
        try {
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "select * from mr_member_heder_master where userid = '"+userid+"' and read_date_plan = '"+read_date_plan+"'" ;
            cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            db.close();
            return cursor;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MR_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + MR_METERMODEL);
        db.execSQL("DROP TABLE IF EXISTS " + MR_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + MR_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + MR_REGISTER_ALL);
        db.execSQL("DROP TABLE IF EXISTS " + MR_OFFLINE_DATA);
        onCreate(db);

    }
}
