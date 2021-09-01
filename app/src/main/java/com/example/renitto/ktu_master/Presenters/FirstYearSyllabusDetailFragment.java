package com.example.renitto.ktu_master.Presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.renitto.ktu_master.R;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Renitto on 8/12/2016.
 */
public class FirstYearSyllabusDetailFragment extends Fragment {

//    @Bind(R.id.tv_subject_name_syllabus)
//    TextView TV_subject_Name;


    String [] Subject_details;
    String [] Module_NO = {"Module I", "Module II", "Module III", "Module IV", "Module V", "Module VI", "Text Book & References"};
    String Subject_name;

    RecyclerView RC_subject_list_detail;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       Subject_name  = getArguments().getString("SUBJECT");

        View view = inflater.inflate(R.layout.syllabus_detail,
                container, false);
//        ButterKnife.bind(getActivity());

        TextView TV_subject_Name = (TextView)view.findViewById(R.id.tv_subject_name_syllabus);

        TV_subject_Name.setText(Subject_name);



        int resourceId= getResources().getIdentifier(Subject_name.replaceAll(" ", "_").toLowerCase() , "array", getActivity().getPackageName());
        if(resourceId != 0){
             Subject_details =  getResources().getStringArray(resourceId);
        }




        RC_subject_list_detail = (RecyclerView)view.findViewById(R.id.rc_syllabus_detail);


        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        RC_subject_list_detail.setLayoutManager(mLayoutManager);




        // setting home recyclerviews


        SubjectListDetailRecyclerViewAdapter subjectListDetailRecyclerViewAdapter = new SubjectListDetailRecyclerViewAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(subjectListDetailRecyclerViewAdapter);
        scaleAdapter.setInterpolator(new AnticipateInterpolator());
        scaleAdapter.setDuration(500);
        RC_subject_list_detail.setAdapter(scaleAdapter);








        return view;
    }


    //adapter for subject detail
    public class SubjectListDetailRecyclerViewAdapter
            extends RecyclerView.Adapter<SubjectListDetailRecyclerViewAdapter.ViewHolder> {




        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;



            public final TextView TV_module_name;
            public final TextView TV_module_detail;



            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_module_name = (TextView) view.findViewById(R.id.tv_module_name);
                TV_module_detail = (TextView) view.findViewById(R.id.tv_module_detail);





            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public SubjectListDetailRecyclerViewAdapter(Context context ) {





        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.syllabus_detail_card, parent, false);



            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {





            holder.TV_module_name.setText(Module_NO[position]);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                holder.TV_module_detail.setText(Html.fromHtml(Subject_details[position],Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.TV_module_detail.setText(Html.fromHtml(Subject_details[position]));
            }






        }

        @Override
        public int getItemCount() {
            return Module_NO.length;
        }






    }


}
