package com.example.lawnicsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Upload_data extends AppCompatActivity {
EditText editText,song_name;
Spinner spinner;
Button button;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
         editText=findViewById(R.id.artist_name);
        song_name=findViewById(R.id.song_name);
        databaseReference= FirebaseDatabase.getInstance().getReference("artist");
        spinner=findViewById(R.id.spinner);
        button=findViewById(R.id.upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addArtist();
            }
        });

    }
    private void addArtist()
    {
        String name=editText.getText().toString().trim();
        String song= song_name.getText().toString().trim();
        String type=spinner.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name))
        {
            String id= databaseReference.push().getKey();
            Data data=new Data(id,name,song,type);
            databaseReference.child(id).setValue(data);
            Toast.makeText(this, "Value Added", Toast.LENGTH_SHORT).show();
            }
        else{
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

        }

    }
}
