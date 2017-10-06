package com.srmhackathon.haasil;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return v;
    }
}
