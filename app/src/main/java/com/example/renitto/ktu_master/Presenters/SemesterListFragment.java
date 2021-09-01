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

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Renitto on 8/17/2016.
 */
public class SemesterListFragment extends Fragment {

    String  Code,Dept_name;
    String [] Semesters = {"Semester - 3","Semester - 4","Semester - 5","Semester - 6","Semester - 7","Semester - 8"};

    RecyclerView RC_semester_list;
    RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.tv_semlist_dept_name)
    TextView TV_semlist_dept_name;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.semester_list,
                container, false);
        ButterKnife.bind(this,view);

        Dept_name = getArguments().getString("DEPT");
        Code = getArguments().getString("CODE");

        TV_semlist_dept_name.setText(Dept_name);

        RC_semester_list = (RecyclerView) view.findViewById(R.id.rv_semester_list);


        mLayoutManager = new LinearLayoutManager(getActivity());

        RC_semester_list.setLayoutManager(mLayoutManager);


        // setting home recyclerviews


        SemesterListRecyclerViewAdapter semesterListRecyclerViewAdapter = new SemesterListRecyclerViewAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(semesterListRecyclerViewAdapter);
        scaleAdapter.setInterpolator(new BounceInterpolator());
        scaleAdapter.setDuration(1000);
        RC_semester_list.setAdapter(scaleAdapter);

        return view;
    }

    // adapter for semester list

    public class SemesterListRecyclerViewAdapter
            extends RecyclerView.Adapter<SemesterListRecyclerViewAdapter.ViewHolder> {


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

        public SemesterListRecyclerViewAdapter(Context context) {


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ordinance_list_card, parent, false);


            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            if (Semesters[position] !=null && !Semesters[position].equals(""))
                holder.TV_ordinance_name.setText(Semesters[position]);


            // click event for cards

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fr=new OrdinanaceListFragment();
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("TYPE", Code+"/semester"+(position+3));
                    args.putString("SUBTYPE", "/subject");
                    fr.setArguments(args);
                    ft.replace(R.id.root_frame, fr);
                    ft.addToBackStack("SemesterListFragment");
                    ft.commit();
                }
            });


        }

        @Override
        public int getItemCount() {
            return Semesters.length;
        }


    }

}
