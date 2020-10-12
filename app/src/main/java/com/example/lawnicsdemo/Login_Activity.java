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
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    EditText email,password;
    Button login_btn;
    ProgressBar progressBar;
     TextView link;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.lprogressbar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        email=findViewById(R.id.login_email);
            password=findViewById(R.id.login_pass);
            login_btn=findViewById(R.id.login_btn);
            link=findViewById(R.id.register_link);
             login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                     signIn();
                }
            });


            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getApplicationContext(),Register.class);
                    startActivity(intent);
                    finish();
                }
            });
    }
    public void signIn()

    {
        String emailid= email.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if(!TextUtils.isEmpty(emailid) && !TextUtils.isEmpty(pass))
        {
            firebaseAuth.signInWithEmailAndPassword(emailid,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            { progressBar.setVisibility(View.GONE);
                                Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login_Activity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        { progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Enter Valid Values", Toast.LENGTH_SHORT).show();
        }

    }
}
