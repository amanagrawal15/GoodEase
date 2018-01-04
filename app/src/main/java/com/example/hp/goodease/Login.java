package com.example.hp.goodease;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goodease.driver.DriverMainActivity;
import com.example.goodease.driver.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText Registration_no;
    private EditText Password;
    private Button LOGIN;
    private Button SIGNUP;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        Registration_no = (EditText) findViewById( R.id.etReg_no );
        Password = (EditText) findViewById( R.id.etPassword );
        LOGIN = (Button) findViewById( R.id.btnLOGIN );
        SIGNUP = (Button) findViewById( R.id.btnReg );
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null && Constants.type == Constants.Type.NORMAL) {
            Intent intent = new Intent( Login.this, LocationActivity.class );
            startActivity( intent );

            if (firebaseAuth.getCurrentUser() != null && Constants.type == Constants.Type.DRIVER) {
                startActivity( new Intent( Login.this, DriverMainActivity.class ));

            }

            LOGIN.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebaseAuth.signOut();
                    validate( Registration_no.getText().toString(), Password.getText().toString() );


                }
            } );

            SIGNUP.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.type == Constants.Type.NORMAL) {
                        Intent intent = new Intent( Login.this, RegistrationActivity.class );
                        startActivity( intent );
                    }
                    if (Constants.type == Constants.Type.DRIVER)
                        Toast.makeText( Login.this, " You cannot register", Toast.LENGTH_SHORT ).show();

                }
            } );


        }
    }
    private void validate(String userNo, String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userNo, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    Toast.makeText(Login.this,"Login Succesful", Toast.LENGTH_SHORT).show();
                    if(Constants.type == Constants.Type.NORMAL)
                    startActivity(new Intent(Login.this, LocationActivity.class));
                    else
                        startActivity( new Intent( Login.this, DriverMainActivity.class)  );

                }
                else{
                    Toast.makeText(Login.this,"Login Unsuccesful", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
