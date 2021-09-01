package com.example.renitto.ktu_master.Presenters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.example.renitto.ktu_master.DAL.DbManager;
import com.example.renitto.ktu_master.Models.Important_University_Contacts;
import com.example.renitto.ktu_master.Models.University_mails;
import com.example.renitto.ktu_master.R;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Renitto on 7/29/2016.
 */
public class UniversityContactsFragment extends Fragment {

    RecyclerView RC_university_contact_list,RC_University_mails;
    RecyclerView.LayoutManager mLayoutManager,mailLayoutManaer;
    DbManager dbManager= new DbManager();
    ArrayList<Important_University_Contacts> important_university_contactses=new ArrayList<Important_University_Contacts>();
    ArrayList<University_mails> university_mailses =new ArrayList<University_mails>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.university_contacts,
                container, false);
        important_university_contactses=dbManager.getImportantContacts(getActivity()); // all contacts from db
        university_mailses = dbManager.getUniversitymails(getActivity()); // all emails from db

        RC_university_contact_list = (RecyclerView)view.findViewById(R.id.rv_university_contacts);
        RC_University_mails = (RecyclerView)view.findViewById(R.id.rv_university_mails);




        // opeing google map for navigation with lat long of university

                (view.findViewById(R.id.tv_navigate_university)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=8.667060,76.910104"));
                startActivity(intent);
            }
        });

        // call university
        (view.findViewById(R.id.tv_call_university)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).callPhone("04712598122");
            }
        });


        (view.findViewById(R.id.tv_university_result_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showCustomChromTabs(getResources().getString(R.string.university_result_link));
            }
        });

        (view.findViewById(R.id.tv_university_announcement_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showCustomChromTabs(getResources().getString(R.string.university_announcements_link));
            }
        });

        (view.findViewById(R.id.tv_university_syllabus_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showCustomChromTabs(getResources().getString(R.string.university_syllabus_regulations_link));
            }
        });

        (view.findViewById(R.id.tv_university_egovern_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showCustomChromTabs(getResources().getString(R.string.university_egovern_link));
            }
        });


        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mailLayoutManaer = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        RC_university_contact_list.setLayoutManager(mLayoutManager);
        RC_University_mails.setLayoutManager(mailLayoutManaer);

        UniversityContactlistRecyclerViewAdapter universityContactlistRecyclerViewAdapter = new UniversityContactlistRecyclerViewAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(universityContactlistRecyclerViewAdapter);
        scaleAdapter.setInterpolator(new BounceInterpolator());
        scaleAdapter.setDuration(1000);
        RC_university_contact_list.setAdapter(scaleAdapter);

        UniversityMailRecyclerViewAdapter universityMailRecyclerViewAdapter = new UniversityMailRecyclerViewAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter1 = new ScaleInAnimationAdapter(universityMailRecyclerViewAdapter);
        scaleAdapter1.setInterpolator(new BounceInterpolator());
        scaleAdapter1.setDuration(1000);
        RC_University_mails.setAdapter(scaleAdapter1);

        // link clicks


        return view;
    }

    // adapter for university contacts

    public class UniversityContactlistRecyclerViewAdapter
            extends RecyclerView.Adapter<UniversityContactlistRecyclerViewAdapter.ViewHolder> {



        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;


            public final TextView TV_contact_name;
            public final TextView TV_contact_position;
            public final TextView TV_contact_no;
            public final TextView TV_import_cont_phone_call;




















            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_contact_name = (TextView) view.findViewById(R.id.tv_import_cont_name);
                TV_contact_no = (TextView) view.findViewById(R.id.tv_import_cont_phone);
                TV_contact_position = (TextView) view.findViewById(R.id.tv_import_cont_position);
                TV_import_cont_phone_call = (TextView) view.findViewById(R.id.tv_import_cont_phone_call);




            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public UniversityContactlistRecyclerViewAdapter(Context context) {




        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.important_contacts_card, parent, false);



            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {





            holder.TV_contact_name.setText(important_university_contactses.get(position).getName());
            holder.TV_contact_position.setText(important_university_contactses.get(position).getPosition());
            holder.TV_contact_no.setText("0471-"+important_university_contactses.get(position).getPrimary_phone_no());


            holder.TV_import_cont_phone_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((MainActivity)getActivity()).callPhone("0471"+important_university_contactses.get(position).getPrimary_phone_no());
                }
            });



        }

        @Override
        public int getItemCount() {
            return important_university_contactses.size();
        }






    }

    // adapter for university contacts

    public class UniversityMailRecyclerViewAdapter
            extends RecyclerView.Adapter<UniversityMailRecyclerViewAdapter.ViewHolder> {



        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;


            public final TextView TV_department;
            public final TextView TV_email;
            public final TextView TV_university_contact_email_button;


















            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_department = (TextView) view.findViewById(R.id.tv_university_mail_department);
                TV_email = (TextView) view.findViewById(R.id.tv_university_mail_email);
                TV_university_contact_email_button = (TextView) view.findViewById(R.id.tv_university_contact_email_button);






            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public UniversityMailRecyclerViewAdapter(Context context) {




        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.quick_mail_card, parent, false);



            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {





            holder.TV_department.setText(university_mailses.get(position).getDepartment());
            holder.TV_email.setText(university_mailses.get(position).getEmail());


            // for mailing to corresponding departments
            holder.TV_university_contact_email_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).emailIt(university_mailses.get(position).getEmail(),university_mailses.get(position).getDepartment());
                }
            });








        }

        @Override
        public int getItemCount() {
            return university_mailses.size();
        }






    }

}
