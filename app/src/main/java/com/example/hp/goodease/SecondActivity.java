package com.example.hp.goodease;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity {
private Button Sign_out;
    private TextView welcome;
    private Spinner ddsize;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        welcome =(TextView)findViewById(R.id.tvwelcome);
        ddsize = (Spinner)findViewById(R.id.sp_size);
        Sign_out = (Button)findViewById(R.id.btnsign_out);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        welcome.setText("WELCOME "+user.getEmail());
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.size));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddsize.setAdapter(myAdapter);
        Sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(SecondActivity.this, Login.class));
            }
        });
    }

}
