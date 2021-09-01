package com.example.renitto.ktu_master.Presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renitto.ktu_master.R;
import com.example.renitto.ktu_master.Utils.GridSpacingItemDecoration;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Renitto on 8/17/2016.
 */
public class SyllabusListFragment extends Fragment {

    RecyclerView RC_department_grid;
    RecyclerView.LayoutManager mLayoutManager;

    String[] Departments = {"Aeronautical Engineering",
            "Automobile Engineering",
            "Biotechnology",
            "Chemical Engineering",
            "Civil Engineering",
            "Computer Science and Enineering",
            "Electrical and Electronics Engineering",
            "Electronics and Communication Engineering",
            "Food Technology",
            "Information Technology",
            "Mechanical Engineering",
            "Production Engineering"
    };

    String[] Departments_shorts = {"AE",
            "AME",
            "BT",
            "ChE",
            "CE",
            "CSE",
            "EEE",
            "ECE",
            "FT",
            "IT",
            "ME",
            "PE"
    };

    String[] Departments_codes = {
            "au",
            "automobile",
            "bio",
            "che",
            "civil",
            "cse",
            "eee",
            "ece",
            "food",
            "it",
            "mech",
            "pro"
    };

    int[] androidColors;

    @Bind(R.id.cv_first_year_sub)
    CardView CV_first_year_sub;

    @Bind(R.id.cv_sub_maths)
    CardView CV_sub_maths;

    @Bind(R.id.cv_sub_common)
    CardView CV_sub_common;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.syllabus_grid,
                container, false);

        ButterKnife.bind(this,view);


        // setting spacing values

        int spanCount = 2;
        int spacing = 10;


        String screenType = getResources().getString(R.string.screentype);
        if (screenType.equals("7tablet")) {
            // do something
            spacing = 25;
        } else if (screenType.equals("phone")) {
            // do something else
            spacing = 15;
        } else if (screenType.equals("loliphone")) {
            spacing = 23;
        }
//        else if(screenType.equals("7tabletland"))
//        {
//            spacing=20;
//            spanCount = 3;
//        }
//        else if(screenType.equals("loliphoneland"))
//        {
//            spacing=20;
//            spanCount = 3;
//        }
//        else if(screenType.equals("10tablet"))
//        {
//            spacing=10;
//        }


        androidColors = getResources().getIntArray(R.array.androidcolors);

        RC_department_grid = (RecyclerView) view.findViewById(R.id.rc_syllabus_grid);

        RC_department_grid.setNestedScrollingEnabled(false); // for total scroll of view


        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);

        boolean includeEdge = true;
        RC_department_grid.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        RC_department_grid.setLayoutManager(mLayoutManager);


        // setting home recyclerviews


        DepartmentGridRecyclerViewAdapter departmentGridRecyclerViewAdapter = new DepartmentGridRecyclerViewAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(departmentGridRecyclerViewAdapter);
        scaleAdapter.setInterpolator(new BounceInterpolator());
        scaleAdapter.setDuration(1000);
        RC_department_grid.setAdapter(scaleAdapter);


        CV_first_year_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity)getActivity()).replaceFragment(new FirstYearSyllabusListBtecFrament());

            }
        });

        CV_sub_maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fr=new OrdinanaceListFragment();
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("TYPE", "maths/subject");
                args.putString("SUBTYPE", "/subject");
                fr.setArguments(args);
                ft.replace(R.id.root_frame, fr);
                ft.addToBackStack("SyllabusListFragment");
                ft.commit();

            }
        });

        CV_sub_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fr=new OrdinanaceListFragment();
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("TYPE", "common/subject");
                args.putString("SUBTYPE", "/subject");
                fr.setArguments(args);
                ft.replace(R.id.root_frame, fr);
                ft.addToBackStack("SyllabusListFragment");
                ft.commit();

            }
        });



        return view;
    }

    // adapter for dept grid

    public class DepartmentGridRecyclerViewAdapter
            extends RecyclerView.Adapter<DepartmentGridRecyclerViewAdapter.ViewHolder> {


        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;


            public final TextView TV_dept_name;
            public final TextView TV_dept_name_short;
            public final RelativeLayout RL_dept_color;


            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_dept_name = (TextView) view.findViewById(R.id.tv_dept_name);
                TV_dept_name_short = (TextView) view.findViewById(R.id.tv_dep_short);
                RL_dept_color = (RelativeLayout) view.findViewById(R.id.rl_dept_color);


            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public DepartmentGridRecyclerViewAdapter(Context context) {


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.syllabus_grid_card, parent, false);


            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            holder.TV_dept_name.setText(Departments[position]);
            holder.TV_dept_name_short.setText(Departments_shorts[position]);
            holder.RL_dept_color.setBackgroundColor(androidColors[new Random().nextInt(androidColors.length)]);


            // clicks for card

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fr=new SemesterListFragment();
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("DEPT", Departments[position] );
                    args.putString("CODE", Departments_codes[position]);
                    fr.setArguments(args);
                    ft.replace(R.id.root_frame, fr);
                    ft.addToBackStack("SyllabusListFragment");
                    ft.commit();

                }
            });


        }

        @Override
        public int getItemCount() {
            return Departments.length;
        }


    }


}
