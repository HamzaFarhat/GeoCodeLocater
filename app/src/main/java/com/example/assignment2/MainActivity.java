package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static ArrayList lat = new ArrayList<Double>();
    public static ArrayList lon = new ArrayList<Double>();
    EditText addressTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressTxt = findViewById(R.id.addressSearch);
        database db = new database(this);
        if(db.getCount() < 50) {
            InputStream inputStream = getResources().openRawResource(R.raw.lat_long);
            CSVFile csvFile = new CSVFile(inputStream);
            csvFile.read();

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());
            for (int i = 0; i < lat.size(); i++) {
                try
                {
                    addresses = geocoder.getFromLocation((double) lat.get(i), (double) lon.get(i), 1);
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                    db.add(addresses.get(0).getAddressLine(0), (double) lat.get(i), (double) lon.get(i));
                }
            }
        }

        // TO SHOW ALL BUTTON ON CLICKS
        Button viewAll = findViewById(R.id.updateButton);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent createNoteActivity = new Intent(MainActivity.this, ViewAll.class);
                MainActivity.this.startActivity(createNoteActivity);
            }
        });
        // TO SHOW ALL BUTTON ON CLICKS
        Button query = findViewById(R.id.searchButton);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, view_query.class);
                intent.putExtra("address",addressTxt.getText().toString());
                MainActivity.this.startActivity(intent);
            }
        });


    }
}
//DIFFERENT LATITUDE & LONGITUDE INPUTS FROM WEBSITE FROM CSV FILES
class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public void read(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                MainActivity.lat.add(Double.parseDouble(row[0]));
                MainActivity.lon.add(Double.parseDouble(row[1]));
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
    }
}