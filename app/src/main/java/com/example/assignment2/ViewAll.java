package com.example.assignment2;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewAll extends AppCompatActivity {

    private Context context;
    database db;
    Activity activity;
    private ArrayList<String> id, address;
    private ArrayList<Double> lat, lon;
    RecyclerAdapter recycleAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Tap an Entry to Update");
            ab.setDisplayHomeAsUpEnabled(true);
        }

        db = new database(ViewAll.this);
        id = new ArrayList<>();
        address = new ArrayList<>();
        lat= new ArrayList<>();
        lon = new ArrayList<>();

        getData();
        recycleAdapter = new RecyclerAdapter(ViewAll.this,ViewAll.this,id,address,lat,lon);

        recyclerView = findViewById(R.id.recycleLocations);

        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAll.this));
        // Create note:
        Button addLocation = findViewById(R.id.createLocation);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent createNoteActivity = new Intent(ViewAll.this, addLocation.class);
                ViewAll.this.startActivity(createNoteActivity);
            }
        });

    }

    void getData(){
        Cursor cursor = db.readData();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        System.out.println(requestCode);
        if(requestCode ==1){
            System.out.println("Here");
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_locations, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Delete all?");
            alert.setMessage("Are you sure you want to delete all addresses?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(ViewAll.this,"All addresses deleted.",Toast.LENGTH_SHORT).show();
                    db = new database(ViewAll.this);
                    db.deleteAll();
                    Intent intent = new Intent(ViewAll.this,ViewAll.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}