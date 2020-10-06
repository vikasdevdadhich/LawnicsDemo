package com.example.lawnicsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
ListView listView;
DatabaseReference reference;
List<Data> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         listView=findViewById(R.id.listview);
        reference= FirebaseDatabase.getInstance().getReference("artist");
        list=new ArrayList<>();
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Data data= list.get(position);
        Intent intent= new Intent(getApplicationContext(),Update_Activity.class);
        intent.putExtra("Id",data.getId());
        intent.putExtra("name",data.getName());
        intent.putExtra("type",data.getType());
        intent.putExtra("genre",data.getGenre());
        startActivity(intent);
            }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            for(DataSnapshot artistsnap: dataSnapshot.getChildren())
            {
                Data data= artistsnap.getValue(Data.class);
                list.add(data);
            }
            ListAdapter listAdapter= new ListAdapter(MainActivity.this,list);
            listView.setAdapter(listAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }


}
