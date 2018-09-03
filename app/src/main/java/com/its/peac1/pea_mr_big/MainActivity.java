package com.its.peac1.pea_mr_big;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.* ;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , ConnectivityReceiver.ConnectivityReceiverListener {

    String userid ,usrname;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    GPSTracker gps;
    Button btnShowLocation;

    EditText editsearch;
    public ListView listView ;
    String Username = "" ;
    String read_date_plan ,STATE_OFFLINE ;
    Adapter_item_MrSelectSearchMaster adapter ;
    TextView title_date ;
    ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster ;
    ListView lview1 ;
    boolean doubleBackToExitPressedOnce = false ;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        insertDummyContactWrapper();
        checkConnection();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Test");




        final myDBClass myDb2 = new myDBClass(this);
        myDb2.getWritableDatabase(); // First method

        Username = myDb2.SelectUserId() ;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Crashlytics.setString("key1", Username+"xx") ;

/*
        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
             //   Crashlytics.setUserName(Username+"xxxxx");
                Crashlytics.getInstance().crash(); // Force a crash
            }
        });
        addContentView(crashButton,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

*/

        editsearch = (EditText) findViewById(R.id.editText1);
        lview1 = (ListView) findViewById(R.id.listView);
        Item_MrSelectSearchMaster = new ArrayList<Item_MrSelectSearchMaster>();
        adapter = new Adapter_item_MrSelectSearchMaster(MainActivity.this, Item_MrSelectSearchMaster);
        lview1.setAdapter(adapter);
        try
        {
            read_date_plan = myDb2.SelectMaxdate() ;
            Log.i("read_date_plan",read_date_plan);
        }
        catch (Exception e)
        {
            read_date_plan = "" ;
        }

        STATE_OFFLINE = "Offline" ;
         title_date =  (TextView) findViewById(R.id.textdate);
  //      Log.i("read_date_plan",read_date_plan);
        String Now_read_date_plan = "" ;
        if(!read_date_plan.equals("")) {
             Now_read_date_plan = "(OFFLINE DATA) วันที่จดหน่วยตามแผน " + read_date_plan.substring(6, 8) + "/" + read_date_plan.substring(4, 6) + "/" + read_date_plan.substring(0, 4);
        }
        else
        {
             Now_read_date_plan = "NO OFFLINE DATA ";
        }
            title_date.setText(Now_read_date_plan);
            title_date.setTextSize(20);
            title_date.setTextColor(Color.RED);


        Item_MrSelectSearchMaster.clear();
        int offline_count = 0 ;
        Cursor CurData = myDb2.SelectMR_MEMBER_MRday(Username,read_date_plan) ;
        try
        {

            offline_count = CurData.getCount() ;
        }
        catch (Exception e) {
            offline_count = 0 ;
        }

        if (offline_count == 0) {
            Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE,"NO DATA","NO DATA","NO DATA"));
        }else {
            try {
                while (!CurData.isAfterLast()) {
                    Log.i("isAfterLast",CurData.getString(0)) ;
                    Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("CA : "+CurData.getString(4),CurData.getString(2), "ชื่อ : "+CurData.getString(3), "ที่อยู่ : "+CurData.getString(3),read_date_plan,"MRU : "+CurData.getString(5),STATE_OFFLINE,CurData.getString(7),"NO DATA","NO DATA"));
                    CurData.moveToNext();
                }
            }
            catch (Exception e) {
                Item_MrSelectSearchMaster.add(new Item_MrSelectSearchMaster("NO DATA", "NO DATA", "NO DATA", "NO DATA", "NO DATA","NO DATA",STATE_OFFLINE,"NO DATA","NO DATA","NO DATA"));
            }

            editsearch.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable arg0) {
                    ArrayList<Item_MrSelectSearchMaster> Item_MrSelectSearchMaster_2 = new ArrayList<Item_MrSelectSearchMaster>();
                    int textlength = editsearch.getText().length();
                    int ii = 0 ;
                    for (int i = 0; i < Item_MrSelectSearchMaster.size(); i++) {
                        try {
                            if(Item_MrSelectSearchMaster.get(i).getPea_meter().toLowerCase().contains(editsearch.getText().toString().toLowerCase()) || Item_MrSelectSearchMaster.get(i).getName().toLowerCase().contains(editsearch.getText().toString().toLowerCase()))
                            {
                                Item_MrSelectSearchMaster_2.add(new Item_MrSelectSearchMaster(Item_MrSelectSearchMaster.get(i).getCa(),Item_MrSelectSearchMaster.get(i).getPea_meter(),Item_MrSelectSearchMaster.get(i).getName(), Item_MrSelectSearchMaster.get(i).getAddress(),read_date_plan,Item_MrSelectSearchMaster.get(i).getMru(),STATE_OFFLINE,Item_MrSelectSearchMaster.get(i).getMetertype(),Item_MrSelectSearchMaster.get(i).getlatitude(),Item_MrSelectSearchMaster.get(i).getlongitude()));
                            }
                        } catch (Exception e) {
                        }
                        Adapter_item_MrSelectSearchMaster  adapter2 = new Adapter_item_MrSelectSearchMaster(MainActivity.this, Item_MrSelectSearchMaster_2);
                        lview1.setAdapter(adapter2);
                         /*   lview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                                    Intent i = new Intent(Activity_SearchDevice.this, Activity_Mr_Register.class);
                                    Bundle b = new Bundle();
                                    b.putString("Pea_meter", Item_MrSelectSearchMaster.get(ii).getPea_meter().toString());
                                    b.putString("ReadDatePlan",Item_MrSelectSearchMaster.get(ii).getPea_meter().toString() );
                                    i.putExtras(b);

                                }
                            });
                            */

                    }
                }

                public void beforeTextChanged(CharSequence s, int start
                        , int count, int after) { }
                public void onTextChanged(CharSequence s, int start
                        , int before, int count) { }

            });

        }
        myDb2.close();
       // long flg1 = myDb2.Insert_MR_METERMODEL("x","x","x","x","x","x","x","x");
/*
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);
                    }
                });


*/
 /*

        btnShowLocation = (Button) findViewById(R.id.button_GPS);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {




                // create class object
                gps = new GPSTracker(MainActivity.this);
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.button_GPS), "TEST", Snackbar.LENGTH_LONG);
                checkConnection();
                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

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

            }
        });





*/


     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ///////////***********//////////
        final UserHelper usrHelper = new UserHelper(this);
        userid = usrHelper.getMemberID() ;
        usrname = usrHelper.getMemberName() ;
        Crashlytics.setString("key2_usrHelper", Username+"xx") ;
        Crashlytics.setString("key3_usrHelper", userid+"xx") ;
        Crashlytics.setString("key4_usrHelper", usrname+"xx") ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);

    }

    private void showSnack(boolean isConnected) {
        try
        {


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
                .make(findViewById(R.id.listView), message, Snackbar.LENGTH_LONG);

       View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.setDuration(30000) ;
        snackbar.show();
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    // insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(MainActivity.this, permission);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("CAMERA");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ_EXTERNAL_STORAGE");
        if (!addPermission(permissionsList, Manifest.permission.READ_CALL_LOG))
            permissionsNeeded.add("READ_CALL_LOG");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("ACCESS_FINE_LOCATION");


        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                                }
                            }
                        });
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return;
        }

        //insertDummyContact();
    }

/*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
*/
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        TextView name = (TextView) findViewById(R.id.textViewName);
        name.setText(usrname);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_personal) {
            fragment = new UserDetail() ;
            // Handle the camera action
        }
        else if (id == R.id.nav_recordmr) {
            fragment = new Fragment_Mr_day();

        }
        else if (id == R.id.nav_ReadMeter_offline) {
            fragment = new Fragment_Mr_day_offline();

        }
        else if (id == R.id.nav_SendReadMeter_offline) {
            fragment = new Fragment_Mr_day_send_offline();

        }
        else if (id == R.id.nav_logout) {

            new AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("คุณต้องการ Log OUT หรือไม่")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.commit();

                            final myDBClass myDb = new myDBClass(MainActivity.this);
                            myDb.getWritableDatabase() ;
                            String userId = myDb.SelectUserId();

                            if(myDb.DeleteData() && myDb.DeleteData_MR_REGISTER_ALL() && myDb.Delete_OFFLINE_DATA() && myDb.DeleteMR_MEMBER_HEDER_MASTER())
                            {

                                try {

                                    File dir = getApplicationContext().getCacheDir();
                                       if( deleteDir(dir))
                                       {
                                           Toast.makeText(getApplication(),"ออกจากระบบ", Toast.LENGTH_LONG).show();
                                           MainActivity.this.finish();
                                       }
else
                                       {
                                           Toast.makeText(getApplication(),"ลบ cache data ไม่ได้ 1", Toast.LENGTH_LONG).show();
                                       }
                                } catch (Exception e) {
                                    Toast.makeText(getApplication(),"ลบ cache data ไม่ได้ 2 "+e.toString(), Toast.LENGTH_LONG).show();

                                }

                            }
                            else
                            {
                                Toast.makeText(getApplication(),"ลบข้อมูลใน Database ไม่ได้ ", Toast.LENGTH_LONG).show();
                            }

                        }})
                    .setNegativeButton(android.R.string.no, null).show();

        }

        if (fragment != null) {
            ((FrameLayout) findViewById(R.id.content_frame)).removeAllViews();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showSnack(isConnected);
    }
}
