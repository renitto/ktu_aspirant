//package com.example.renitto.ktu_master.Adapters;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.renitto.ktu_master.Presenters.CalculatorFrament;
//import com.example.renitto.ktu_master.Presenters.MainActivity;
//import com.example.renitto.ktu_master.Presenters.FirstYearSyllabusDetailFragment;
//import com.example.renitto.ktu_master.R;
//
///**
// * Created by Renitto on 8/10/2016.
// */
//public class SubjectListDetailRecyclerViewAdapter
//        extends RecyclerView.Adapter<SubjectListDetailRecyclerViewAdapter.ViewHolder> {
//
//    String [] Subjects;
//    String [] Credits;
//    String [] Course_numbers;
//
//    MainActivity mainActivity = new MainActivity();
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//
//        public final View mView;
//
//
//
//        public final TextView TV_module_name;
//        public final TextView TV_credit;
//        public final TextView TV_module_detail;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//
//
//            TV_ordinance_name = (TextView) view.findViewById(R.id.tv_subject_name);
//            TV_module_detail = (TextView) view.findViewById(R.id.tv_course_no);
//            TV_credit = (TextView) view.findViewById(R.id.tv_credits);
//
//
//
//
//        }
//
//
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
//
//    public SubjectListDetailRecyclerViewAdapter(Context context ,String [] Subjects, String [] Credits ,String [] Course_numbers) {
//
//        this.Subjects =Subjects;
//        this.Credits = Credits;
//        this.Course_numbers = Course_numbers;
//
//
//
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.subject_list_card, parent, false);
//
//
//
//        return new ViewHolder(view);
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//
//
//
//
//
//        holder.TV_ordinance_name.setText(Subjects[position]);
//        holder.TV_credit.setText(Credits[position]);
//        holder.TV_module_detail.setText(Course_numbers[position]);
//
//
//        // click event for cards
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                mainActivity.replaceFragment(new SyllabusDetailFragment());
//            }
//        });
//
//
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return Subjects.length;
//    }
//
//
//
//
//
//
//}
//
