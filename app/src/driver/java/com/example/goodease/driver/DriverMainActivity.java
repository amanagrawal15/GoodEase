package com.example.goodease.driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.goodease.R;

public class DriverMainActivity extends AppCompatActivity {
    private RadioButton Active;
    private TextView Earnings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_driver_main );

        Active = (RadioButton) findViewById( R.id.rBtn_Active );
        Earnings = (TextView) findViewById( R.id.tvEarnings );


        Active.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                if (checked)
                    Toast.makeText( DriverMainActivity.this, "U R ACTIVE", Toast.LENGTH_SHORT ).show();
                else
                    Toast.makeText( DriverMainActivity.this, "U R INACTIVE", Toast.LENGTH_SHORT ).show();


            }
        } );
    }
}

