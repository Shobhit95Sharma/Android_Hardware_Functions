package com.example.shobhit.permissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends runtimePermissions {

    private static final int REQUEST_PERMISSION = 10;
    TextView iemi_num,cid,operator,type;
    Button iemi,network,camera,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermissions(new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE},
                R.string.perm,REQUEST_PERMISSION);

       // tv1=(TextView)findViewById(R.id.textView1);
        iemi_num=(TextView)findViewById(R.id.iemi_num);
        cid=(TextView)findViewById(R.id.cid);
        type=(TextView)findViewById(R.id.network);
        operator=(TextView)findViewById(R.id.mobile_operator);

        iemi=(Button)findViewById(R.id.iemi);
        network=(Button)findViewById(R.id.minfo);
        camera=(Button)findViewById(R.id.button3);
        next=(Button)findViewById(R.id.button4);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        iemi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Accessing IEMI
                TelephonyManager tel=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                GsmCellLocation loc = (GsmCellLocation)tel.getCellLocation();

                int cidq = loc.getCid();
                int lac = loc.getLac();
                //IEMI
                iemi_num.setText("IEMI:" +tel.getDeviceId());
                cid.setText("\nCellId: " + cidq +
                                "\nLocation Code: " + lac);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    String ime1  = tel.getDeviceId(0);

                }

            }
        });

        network.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                //Network Info
                ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                boolean isMobileConn = info.isConnected();

                NetworkInfo Winfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                boolean isWifi = Winfo.isConnected();


                TelephonyManager tel=(TelephonyManager)getApplicationContext()
                        .getSystemService(Context.TELEPHONY_SERVICE);

                int signalStrength = 0;
                String signalType = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {

                    CellInfoGsm cellInfo = (CellInfoGsm)tel.getAllCellInfo().get(0);
                    CellSignalStrengthGsm strengthGsm = cellInfo.getCellSignalStrength();
                    signalStrength = strengthGsm.getAsuLevel();

                    if(signalStrength<7)
                        signalType ="2G or less";
                    else
                        signalType="3G or above";
                 }
                String num = tel.getLine1Number();

                operator.setText(tel.getNetworkOperatorName()+": \t "+ num);
                type.setText("WIFI: " + isWifi + "\nMobile: " + isMobileConn +
                        "\nStrength: " + signalStrength);


            }
        });

        camera.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);


            }
        });

        next.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this, call.class);
                startActivity(i);
            }
        });

    }



    @Override
    public void onPermissionsGranted(int requestCode) {
        //Do anything when permisson granted
        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}