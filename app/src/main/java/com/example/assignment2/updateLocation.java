package com.example.assignment2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updateLocation extends AppCompatActivity {

    EditText addressTXT, lonTXT, latTXT;
    String id, address;
    Double lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        addressTXT = findViewById(R.id.addressEditText);
        lonTXT = findViewById(R.id.latUpdate);
        latTXT = findViewById(R.id.lonUpdate);
        database db = new database(updateLocation.this);
        getData();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Updating Address");
            ab.setDisplayHomeAsUpEnabled(true);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //Add backout button onto the alert box that closes the alert box upon "ok" being pressed
        alert.setNegativeButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        Intent backToLocations = new Intent(updateLocation.this, ViewAll.class);

        Button update = findViewById(R.id.update_btn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(addressTXT.getText().toString()))
                {
                    //Tell the user that the mortgage is not set correctly
                    alert.setMessage("Location Address is not set.");
                    alert.setCancelable(true);
                    alert.show();
                }
                else if(TextUtils.isEmpty(latTXT.getText().toString()))
                {
                    //Tell the user that the mortgage is not set correctly
                    alert.setMessage("Latitude is not set.");
                    alert.setCancelable(true);
                    alert.show();
                }
                else if(TextUtils.isEmpty(lonTXT.getText().toString()))
                {
                    //Tell the user that the mortgage is not set correctly
                    alert.setMessage("Longitude is not set.");
                    alert.setCancelable(true);
                    alert.show();
                }
                else {
                    Double latitude = Double.parseDouble(latTXT.getText().toString());
                    Double longitude = Double.parseDouble(lonTXT.getText().toString());
                    if (latitude > 90 || latitude < -90) {
                        //Tell the user that the mortgage is not set correctly
                        alert.setMessage("Latitude must be between -90 and 90.");
                        alert.setCancelable(true);
                        alert.show();
                    } else if (longitude > 180 || longitude < -180) {
                        //Tell the user that the mortgage is not set correctly
                        alert.setMessage("Longitude must be between -180 and 180.");
                        alert.setCancelable(true);
                        alert.show();
                    } else {
                        db.updateData(id, addressTXT.getText().toString(), latitude, longitude);
                        updateLocation.this.startActivity(backToLocations);
                    }
                }
            }
        });

        // Delete
        Button delete = findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go home
                //Tell the user that the mortgage is not set correctly
                alert.setTitle("Delete?");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(id);
                        updateLocation.this.startActivity(backToLocations);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });

    }

    void getData(){
        id = getIntent().getStringExtra("id");
        address = getIntent().getStringExtra("address");
        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        lon = Double.parseDouble(getIntent().getStringExtra("lon"));
        addressTXT.setText(address);
        lonTXT.setText(lon.toString());
        latTXT.setText(lat.toString());
    }
}