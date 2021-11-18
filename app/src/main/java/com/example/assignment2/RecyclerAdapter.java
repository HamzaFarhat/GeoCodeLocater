package com.example.assignment2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<String> id, address;
    private ArrayList<Double> lat, lon;

    RecyclerAdapter(Activity activity, Context context, ArrayList<String> id, ArrayList<String> address, ArrayList<Double> lat, ArrayList<Double> lon){
        this.context = context;
        this.activity = activity;
        this.id = id;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.address.setText(String.valueOf(address.get(position)));
        holder.longitude.setText(String.valueOf(lat.get(position)));
        holder.latitude.setText(String.valueOf(lon.get(position)));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, updateLocation.class);
                intent.putExtra("id",String.valueOf(id.get(position)));
                intent.putExtra("address",String.valueOf(address.get(position)));
                intent.putExtra("lat",lat.get(position).toString());
                intent.putExtra("lon",lon.get(position).toString());
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return address.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address, latitude, longitude;
        ConstraintLayout card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.address);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.Longitude);
            card = itemView.findViewById(R.id.card);
        }
    }
}
