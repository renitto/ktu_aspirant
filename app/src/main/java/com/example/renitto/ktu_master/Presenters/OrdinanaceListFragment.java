package com.example.renitto.ktu_master.Presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.example.renitto.ktu_master.R;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Renitto on 8/16/2016.
 */
public class OrdinanaceListFragment extends Fragment {

    String[] Ordinances;
    String Datafromfile;

    RecyclerView RC_ordinance_list;
    RecyclerView.LayoutManager mLayoutManager;
    String  sub_path,sub_type;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.ordinance_list,
                container, false);

        sub_path = getArguments().getString("TYPE");
        sub_type = getArguments().getString("SUBTYPE");

        Datafromfile = ((MainActivity) getActivity()).getDataFromAssets("data/"+sub_path+".txt");
        Ordinances = Datafromfile.split("\n");


        RC_ordinance_list = (RecyclerView) view.findViewById(R.id.rc_ordinance_list);


        mLayoutManager = new LinearLayoutManager(getActivity());

        RC_ordinance_list.setLayoutManager(mLayoutManager);


        // setting home recyclerviews


        OrdinanceListRecyclerViewAdapter ordinanceListRecyclerViewAdapter = new OrdinanceListRecyclerViewAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(ordinanceListRecyclerViewAdapter);
        scaleAdapter.setInterpolator(new BounceInterpolator());
        scaleAdapter.setDuration(1000);
        RC_ordinance_list.setAdapter(scaleAdapter);


        return view;
    }

    // adapter for ordinance_list

    public class OrdinanceListRecyclerViewAdapter
            extends RecyclerView.Adapter<OrdinanceListRecyclerViewAdapter.ViewHolder> {


        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;


            public final TextView TV_ordinance_name;


            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_ordinance_name = (TextView) view.findViewById(R.id.tv_ordinance_header);


            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public OrdinanceListRecyclerViewAdapter(Context context) {


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ordinance_list_card, parent, false);


            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            if (Ordinances[position] !=null && !Ordinances[position].equals(""))
            holder.TV_ordinance_name.setText(Ordinances[position]);


            // click event for cards

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fr=new OrdinanceDetailFragment();
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("HEADER", Ordinances[position]);
                    args.putString("FILEPATH", "data/"+sub_path+sub_type+(position+1)+".txt" );
                    fr.setArguments(args);
                    ft.replace(R.id.root_frame, fr);
                    ft.addToBackStack("OrdinanaceListFragment");
                    ft.commit();
                }
            });


        }

        @Override
        public int getItemCount() {
            return Ordinances.length;
        }


    }


}
