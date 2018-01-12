package com.example.goodease.driver;

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




        Active.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Active.setChecked( true );
                Inactive.setChecked( false );

                 checked = ((RadioButton) v).isChecked();
                if (checked)
                    Toast.makeText( DriverMainActivity.this, "U R ACTIVE", Toast.LENGTH_SHORT ).show();
                BackgroundWorker backgroundWorker = new BackgroundWorker(DriverMainActivity.this);
                backgroundWorker.execute("update",driver_id,"xyz","1");


            }
        } );

        Inactive.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

