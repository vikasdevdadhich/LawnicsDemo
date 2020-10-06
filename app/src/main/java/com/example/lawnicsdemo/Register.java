package com.example.lawnicsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
EditText name,pass,email,cnf_pass;
String fname,password,conf_password,emailid;
TextView login_link;
FirebaseAuth firebaseAuth;
Button register_btn;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.rprogressbar);
        name=findViewById(R.id.reg_name);
         pass=findViewById(R.id.register_pass1);
        cnf_pass=findViewById(R.id.register_pass2);
        email=findViewById(R.id.reg_email);
        login_link=findViewById(R.id.login_link);
        register_btn=findViewById(R.id.reg_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                  registerUser();
            }
        });

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void registerUser(){
        fname=name.getText().toString().trim();
        emailid=email.getText().toString().trim();
        password=pass.getText().toString().trim();
        conf_password=cnf_pass.getText().toString().trim();
        if(!TextUtils.isEmpty(fname) &&!TextUtils.isEmpty(emailid) && !TextUtils.isEmpty(password))
        {
            if(password.equals(conf_password))
            {
                firebaseAuth.createUserWithEmailAndPassword(emailid,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {   progressBar.setVisibility(View.GONE);
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                 }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(Register.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(this, "Password is not same", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }

        }
        else
        {
            Toast.makeText(this, "Please Enter Valid Details", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

        }
    }
}
