package com.srmhackathon.haasil;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Ravi on 06-10-2017.
 */

public class elecFrag extends Fragment {
    public elecFrag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_elec, container, false);

        Button button = (Button)v.findViewById(R.id.bu_int_pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),PaymentActivity.class));
            }
        });

        return v;
    }
}
