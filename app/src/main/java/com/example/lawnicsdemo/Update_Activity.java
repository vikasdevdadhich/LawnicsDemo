package com.example.lawnicsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Update_Activity extends AppCompatActivity {
DatabaseReference firebaseDatabase;
TextView textView;
public String id;
EditText name,type,genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_);
        Intent intent= getIntent();
        textView=findViewById(R.id.upload_link);
        String names= intent.getStringExtra("name");
          id=intent.getStringExtra("Id");
        Toast.makeText(this, "Hii"+id, Toast.LENGTH_SHORT).show();
        String types=intent.getStringExtra("type");
        String Genre=intent.getStringExtra("genre");
        name=findViewById(R.id.name);
        type=findViewById(R.id.type);
        genre=findViewById(R.id.genre);
        firebaseDatabase=FirebaseDatabase.getInstance().getReference("artist");
            name.setText(names);
            type.setText(types);
            genre.setText(Genre);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1= new Intent(getApplicationContext(),Upload_data.class);
                    startActivity(intent1);
                }
            });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //and this to handle actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.save) {
            updateData();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void updateData(){
        String new_name,new_type,new_genre;
        new_name=name.getText().toString().trim();
        new_type=type.getText().toString().trim();
        new_genre=genre.getText().toString().trim();
        Data data= new Data(id,new_name,new_genre,new_type);
         firebaseDatabase.child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void aVoid) {
                 Toast.makeText(Update_Activity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                 Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                 startActivity(intent);
             }
         });
    }


}
