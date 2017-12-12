package com.example.hp.goodease;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Contact_No;
    private EditText Password;
    private EditText Cnf_Password;
    private EditText Email_Address;
    private Button Registration;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Name = (EditText)findViewById(R.id.etName);
        Contact_No = (EditText)findViewById(R.id.etContactNo);
        Email_Address = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);
        Cnf_Password = (EditText)findViewById(R.id.etCnfPassword);
        Registration = (Button)findViewById(R.id.btnRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = Email_Address.getText().toString().trim();
                String user_password = Password.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isComplete()){
                       Toast.makeText(RegistrationActivity.this , "Registration Successful" , Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(RegistrationActivity.this, Login.class);
                       startActivity(intent);
                   }else{
                       Toast.makeText(RegistrationActivity.this,"Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                   }
                    }
                });

            }
        });
    }
}
