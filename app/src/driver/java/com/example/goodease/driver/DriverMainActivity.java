package com.example.goodease.driver;

import android.location.Location;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.goodease.BackgroundWorker;
import com.example.hp.goodease.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DriverMainActivity extends AppCompatActivity {
    private RadioButton Active;
    private RadioButton Inactive;
    private RadioGroup space;
    private TextView Earnings;
    private FirebaseAuth firebaseAuth;
    boolean checked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_driver_main );
        space = (RadioGroup)findViewById( R.id.rdg_space );
        Active = (RadioButton) findViewById( R.id.rbtn_Active );
        Inactive = (RadioButton)findViewById( R.id.rbtn_InActive ) ;
        Earnings = (TextView) findViewById( R.id.tvEarnings );
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final String driver_id = firebaseUser.getUid().toString();
        ActivityCompat.requestPermissions(DriverMainActivity.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 123);



       final Thread t=new Thread(){


            @Override
            public void run(){

                while(!isInterrupted()){

                    try {
                        Thread.sleep(30000);  //1000ms = 1 sec

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                LocationTracker locationTracker = new LocationTracker( getApplicationContext() );
                                Location l = locationTracker.getLocation();
                                String lat = " ", lon = " ";
                                if( l == null){
                                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                                }else {
                                     lat = Double.toString(l.getLatitude());
                                     lon = Double.toString(l.getLongitude());
                                    Toast.makeText( getApplicationContext(), "GPS Lat = " + lat + " lon = " + lon, Toast.LENGTH_SHORT ).show();

                                }

                                BackgroundWorker backgroundWorker = new BackgroundWorker(DriverMainActivity.this);
                                backgroundWorker.execute("update",driver_id,lat,lon ,"1");
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        Active.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.start();
                Active.setChecked( true );
                Inactive.setChecked( false );

                checked = ((RadioButton) v).isChecked();
                if (checked)
                    Toast.makeText( DriverMainActivity.this, "U R ACTIVE", Toast.LENGTH_SHORT ).show();



            }
        } );

        Inactive.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.stop();
                Inactive.setChecked( true );
                Active.setChecked( false );
                checked = ((RadioButton) v).isChecked();
                if (checked)
                    Toast.makeText( DriverMainActivity.this, "U R INACTIVE", Toast.LENGTH_SHORT ).show();


            }
        } );



     /*   if(checked)
        {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute("update","")
        }*/



    }
}

