package com.srmhackathon.haasil;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by Ravi on 06-10-2017.
 */

public class foodFrag extends Fragment {

    TextView temp, humidity, heatindex;
    ToggleButton light, pump;

    public foodFrag() {

    }

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    DatabaseReference db1 = db.child("temperature");
    DatabaseReference db2 = db.child("heat_index");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);


        temp = (TextView) v.findViewById(R.id.tx_temp);
        humidity = (TextView) v.findViewById(R.id.tx_hum);
        heatindex = (TextView) v.findViewById(R.id.tx_hi);
        light = (ToggleButton) v.findViewById(R.id.tb_lights);
        pump = (ToggleButton) v.findViewById(R.id.toggleButton2);

        db.child("humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String value = dataSnapshot.getValue().toString();
                    humidity.setText(value+"%");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue().toString();
                    temp.setText(value + " Celcius");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue().toString();
                    heatindex.setText(value + " Celcius");
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value", error.toException());
            }
        });


        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();

                if (isChecked) {
                    db.child("Lights").setValue(0);
                } else {
                    db.child("Lights").setValue(1);
                }
            }
        });

        pump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();

                if (isChecked) {
                    db.child("Pump").setValue(0);
                } else {
                    db.child("Pump").setValue(1);
                }
            }
        });


        return v;
    }
}