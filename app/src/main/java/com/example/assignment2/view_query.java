package com.example.assignment2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class view_query extends AppCompatActivity {
    database db;
    private ArrayList<String> id, address;
    private ArrayList<Double> lat, lon;
    RecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_query);
        String query = getIntent().getStringExtra("address");

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Searching for: "+query);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        db = new database(view_query.this);
        id = new ArrayList<>();
        address = new ArrayList<>();
        lat= new ArrayList<>();
        lon = new ArrayList<>();

        getData(query);
        recycleAdapter = new RecyclerAdapter(view_query.this,view_query.this,id,address,lat,lon);
        recyclerView = findViewById(R.id.queryRecycler);

        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view_query.this));
    }

    void getData(String query){
        Cursor cursor = db.queryData(query);
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this,"No Locations",Toast.LENGTH_SHORT).show();
        }
        else
        {
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                address.add(cursor.getString(1));
                lat.add(cursor.getDouble(2));
                lon.add(cursor.getDouble(3));
            }
        }
    }
}