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

public class Login extends AppCompatActivity {

    private EditText Registration_no;
    private EditText Password;
    private Button LOGIN;
    private Button SIGNUP;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Registration_no = (EditText)findViewById(R.id.etReg_no);
        Password = (EditText)findViewById(R.id.etPassword);
        LOGIN = (Button)findViewById(R.id.btnLOGIN);
        SIGNUP = (Button)findViewById(R.id.btnReg);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(Login.this, SecondActivity.class);
            startActivity(intent);
        }

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Registration_no.getText().toString(), Password.getText().toString());


            }
        });

        SIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });


    }
    private void validate(String userNo, String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userNo, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    Toast.makeText(Login.this,"Login Succesful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, SecondActivity.class));
                }
                else{
                    Toast.makeText(Login.this,"Login Unsuccesful", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
