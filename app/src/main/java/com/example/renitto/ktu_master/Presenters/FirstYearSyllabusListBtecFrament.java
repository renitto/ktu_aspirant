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
 * Created by Renitto on 7/28/2016.
 */
public class FirstYearSyllabusListBtecFrament extends Fragment {

    RecyclerView RC_subject_list;
    RecyclerView.LayoutManager mLayoutManager;

    String [] Subjects;
    String [] Credits;
    String [] Course_numbers;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.firstyear_syllabus_list_btec,
                container, false);

        Subjects = getResources().getStringArray(R.array.subjects); // subjects array
        Credits = getResources().getStringArray(R.array.credit); // credits array
        Course_numbers = getResources().getStringArray(R.array.course_no); // course number array

        RC_subject_list = (RecyclerView)view.findViewById(R.id.rv_subject_list_btec);


        mLayoutManager = new LinearLayoutManager(getActivity());

        RC_subject_list.setLayoutManager(mLayoutManager);




        // setting home recyclerviews


        SubjectListRecyclerViewAdapter subjectListRecyclerViewAdapter = new SubjectListRecyclerViewAdapter(getActivity(),Subjects,Course_numbers,Credits);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(subjectListRecyclerViewAdapter);
        scaleAdapter.setInterpolator(new BounceInterpolator());
        scaleAdapter.setDuration(1000);
        RC_subject_list.setAdapter(scaleAdapter);

        return view;
    }


    public class SubjectListRecyclerViewAdapter
            extends RecyclerView.Adapter<SubjectListRecyclerViewAdapter.ViewHolder> {

        String [] Subjects;
        String [] Credits;
        String [] Course_numbers;


        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;



            public final TextView TV_subject_name;
            public final TextView TV_credit;
            public final TextView TV_course_no;



            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_subject_name = (TextView) view.findViewById(R.id.tv_subject_name);
                TV_course_no = (TextView) view.findViewById(R.id.tv_course_no);
                TV_credit = (TextView) view.findViewById(R.id.tv_credits);




            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public SubjectListRecyclerViewAdapter(Context context ,String [] Subjects, String [] Credits ,String [] Course_numbers) {

            this.Subjects =Subjects;
            this.Credits = Credits;
            this.Course_numbers = Course_numbers;



        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subject_list_card, parent, false);



            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {





            holder.TV_subject_name.setText(Subjects[position]);
            holder.TV_credit.setText(Credits[position]);
            holder.TV_course_no.setText(Course_numbers[position]);


            // click event for cards

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment fr=new FirstYearSyllabusDetailFragment();
                    FragmentManager fm=getFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("SUBJECT", Subjects[position]);
                    fr.setArguments(args);
                    ft.replace(R.id.root_frame, fr);
                    ft.addToBackStack("FirstYearSyllabusListBtecFrament");
                    ft.commit();
                }
            });





        }

        @Override
        public int getItemCount() {
            return Subjects.length;
        }






    }




}
