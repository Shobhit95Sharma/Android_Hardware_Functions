package com.example.shobhit.permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shobhit on 5/24/2017.
 */

public class call extends runtimePermissions {

    private static final int REQUEST_PERMISSION = 10;
    Button connect,verify,message;
    EditText num,msg,phone_number;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calling);

        requestAppPermissions(new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.SEND_SMS
        }, R.string.perm, REQUEST_PERMISSION);

        num = (EditText) findViewById(R.id.editText);
        msg = (EditText) findViewById(R.id.editText2);
        phone_number = (EditText) findViewById(R.id.editText1);

        connect = (Button) findViewById(R.id.button);
        verify = (Button) findViewById(R.id.button1);
        tv=(TextView)findViewById(R.id.textView);
        message=(Button)findViewById(R.id.button5);

        connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //String number = String.valueOf(num.getText());
                Intent i =new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+num.getText()));
                startActivity(i);

            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer sb = new StringBuffer();
                Uri contacts = CallLog.Calls.CONTENT_URI;

                Cursor managedCursor = getApplicationContext().getContentResolver().query(
                        contacts, null, null, null, null);

                int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
                if(managedCursor.moveToLast() == true){
                    //String phNumber = managedCursor.getString( number );

                    tv.setText(managedCursor.getString(number));
                }

                managedCursor.close();
            }
        });

        message.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String smsBody = msg.getText().toString();
                String phoneNo = phone_number.getText().toString();

                if(smsBody.equals("") || phoneNo.equals("")){
                    Toast.makeText(getApplicationContext(),"Please Fill \n All Fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, smsBody, null, null);
                    Toast.makeText(getApplicationContext(),"SMS Sent",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
        @Override
    public void onPermissionsGranted(int requestCode) {
            Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();

    }
}
