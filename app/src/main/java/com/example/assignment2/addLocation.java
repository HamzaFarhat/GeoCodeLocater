package com.example.assignment2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class addLocation extends AppCompatActivity {
    EditText lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        lat = findViewById(R.id.latAdd);
        lon = findViewById(R.id.lonAdd);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Adding Address");
            ab.setDisplayHomeAsUpEnabled(true);
        }

        //Declare an alert box to be used in the error handling of null inputs
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //Add backout button onto the alert box that closes the alert box upon "ok" being pressed
        alert.setNegativeButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        Intent backToLocations = new Intent(addLocation.this, ViewAll.class);
        // Cancel
        Button cancel = findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addLocation.this.startActivity(backToLocations);
            }
        });

        // Save
        Button save = findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(lat.getText().toString()))
                {
                    //ALERT MESSAGE
                    alert.setMessage("Latitude is not set.");
                    alert.setCancelable(true);
                    alert.show();
                }
                else if(TextUtils.isEmpty(lon.getText().toString()))
                {
                    //ALERT MESSAGE
                    alert.setMessage("Longitude is not set.");
                    alert.setCancelable(true);
                    alert.show();
                }
                else {
                    Double latitude = Double.parseDouble(lat.getText().toString());
                    Double longitude = Double.parseDouble(lon.getText().toString());
                    if(latitude>90 || latitude <-90)
                    {
                        //ALERT MESSAGE
                        alert.setMessage("Latitude must be between -90 and 90.");
                        alert.setCancelable(true);
                        alert.show();
                    }
                    else if(longitude>180 || longitude <-180)
                    {
                        //ALERT MESSAGE
                        alert.setMessage("Longitude must be between -180 and 180.");
                        alert.setCancelable(true);
                        alert.show();
                    }
                    else
                    {
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder(addLocation.this, Locale.getDefault());
                        database db = new database(addLocation.this);
                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                            db.add(addresses.get(0).getAddressLine(0), latitude, longitude);
                            addLocation.this.startActivity(backToLocations);
                        }
                    }
                }
            }
        });

    }
}
