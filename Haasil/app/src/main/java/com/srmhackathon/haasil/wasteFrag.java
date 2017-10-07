package com.srmhackathon.haasil;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ravi on 06-10-2017.
 */

public class wasteFrag extends Fragment{

    public wasteFrag() {

    }
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ValueEventListener levelListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_waste, container, false);
        ListView listView = (ListView) v.findViewById(R.id.listView);

        final List<dustbinPOJO> dustbinPOJOList = new ArrayList<dustbinPOJO>(){
            {
                add(new dustbinPOJO("Dustbin 0","65"));
                add(new dustbinPOJO("Dustbin 1","87"));
                add(new dustbinPOJO("Dustbin 2","35"));
                add(new dustbinPOJO("Dustbin 3","46"));
                add(new dustbinPOJO("Dustbin 4","57"));
            }
        };

        Context c = getActivity();

        final dustbinAdapter myAdapter = new dustbinAdapter(c,dustbinPOJOList);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                myAdapter.registerToggle(i);

            }
        });

        DatabaseReference db = databaseReference.child("Dustbins-level");
        levelListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue().toString();
                dustbinPOJOList.set(1,new dustbinPOJO("Dustbin 1",val));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        db.addValueEventListener(levelListener);

//        final FoldingCell fc = (FoldingCell) v.findViewById(R.id.folding_cell);
//        // attach click listener to folding cell
//        fc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fc.toggle(false);
//            }
//        });
        return v;
    }
}
