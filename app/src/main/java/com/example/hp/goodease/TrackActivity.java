package com.example.hp.goodease;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_track );
        Toast.makeText( TrackActivity.this," Your Order has been placed successfully", Toast.LENGTH_LONG ).show();
    }
}
