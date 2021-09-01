package com.example.renitto.ktu_master.Presenters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.renitto.ktu_master.R;

/**
 * Created by Renitto on 8/10/2016.
 */
public class PlacementFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.coming_soon,
                container, false);

        return view;
    }
}
