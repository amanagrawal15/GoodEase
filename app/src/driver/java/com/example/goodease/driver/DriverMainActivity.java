package com.example.goodease.driver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.goodease.R;

public class DriverMainActivity extends AppCompatActivity {
    private RadioButton Active;
    private RadioButton Inactive;
    private RadioGroup space;
    private TextView Earnings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_driver_main );
        space = (RadioGroup)findViewById( R.id.rdg_space );
        Active = (RadioButton) findViewById( R.id.rbtn_Active );
        Inactive = (RadioButton)findViewById( R.id.rbtn_InActive ) ;
        Earnings = (TextView) findViewById( R.id.tvEarnings );


        Active.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Active.setChecked( true );
                Inactive.setChecked( false );
                boolean checked = ((RadioButton) v).isChecked();
                if (checked)
                    Toast.makeText( DriverMainActivity.this, "U R ACTIVE", Toast.LENGTH_SHORT ).show();


            }
        } );

        Inactive.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inactive.setChecked( true );
                Active.setChecked( false );
                boolean checked = ((RadioButton) v).isChecked();
                if (checked)
                    Toast.makeText( DriverMainActivity.this, "U R INACTIVE", Toast.LENGTH_SHORT ).show();


            }
        } );



    }
}

