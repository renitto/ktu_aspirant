package com.example.renitto.ktu_master.Presenters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.renitto.ktu_master.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Renitto on 8/17/2016.
 */
public class OrdinanceDetailFragment extends Fragment {
    @Bind(R.id.tv_ordinance_detail_header)
    TextView TV_ordinacne_detail_header;
    @Bind(R.id.tv_ordinance_detail_desc)
    TextView TV_ordinacne_detail_desc;
    String Filepath;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ordinance_list_detail,
                container, false);
        ButterKnife.bind(this,view);

        Filepath = getArguments().getString("FILEPATH");

        TV_ordinacne_detail_header.setText(getArguments().getString("HEADER"));
        TV_ordinacne_detail_desc.setText(((MainActivity) getActivity()).getDataFromAssets(Filepath));




        return view;
    }
    }
