package com.example.renitto.ktu_master.Presenters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renitto.ktu_master.R;
import com.example.renitto.ktu_master.Utils.GridSpacingItemDecoration;
import com.squareup.picasso.Picasso;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Renitto on 7/28/2016.
 */
public class HomeFragment extends Fragment {

    RecyclerView RC_home_list;
    RecyclerView.LayoutManager mLayoutManager;



    String[] HOme_contents = {"Syllabus",
            "CGPA Finder",
            "Question Bank",
            "Ordinance"
    };



    String[] Home_content_images ={"https://i.ytimg.com/vi/GspEcuv_8vk/maxresdefault.jpg",
            "https://mir-s3-cdn-cf.behance.net/projects/202/18360947.548dd1dc74e34.jpg"
            ,"https://lh5.googleusercontent.com/-2Wsw0tmun20/Vowpj05o2bI/AAAAAAAAAFo/5ika32zGWEI/w818-h518-no/CubMaterialFlatWall.png",
            "https://mir-s3-cdn-cf.behance.net/projects/202/e911d935571037.Y3JvcCwxMTgxLDkyMywyNzEsNzg5.png"
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.home,
                container, false);


        // setting spacing values

        int spanCount = 2;
        int spacing= 10 ;


        String screenType = getResources().getString(R.string.screentype);
        if (screenType.equals("7tablet")) {
            // do something
            spacing=25;
        } else if (screenType.equals("phone")){
            // do something else
            spacing=15;
        }
        else if (screenType.equals("loliphone"))
        {
            spacing=23;
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




        RC_home_list = (RecyclerView)view.findViewById(R.id.recycler_home);


        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);

        boolean includeEdge = true;
        RC_home_list.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        RC_home_list.setLayoutManager(mLayoutManager);




        // setting home recyclerviews


        HomeRecyclerViewAdapter homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(homeRecyclerViewAdapter);
        scaleAdapter.setInterpolator(new BounceInterpolator());
        scaleAdapter.setDuration(1000);
        RC_home_list.setAdapter(scaleAdapter);





        return view;

    }





    // adapter for home contents

    public class HomeRecyclerViewAdapter
            extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {



        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;


            public final TextView TV_artist_name;

            public final ImageView IV_artist_image;

            public final LinearLayout LL_artist_parent;

            public final RelativeLayout RL_name_bg;















            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_artist_name = (TextView) view.findViewById(R.id.tv_artist_name);

                IV_artist_image=(ImageView)view.findViewById(R.id.iv_artist_image);

                LL_artist_parent = (LinearLayout)view.findViewById(R.id.ll_artist_parent);

                RL_name_bg = (RelativeLayout)view.findViewById(R.id.rl_name_bg);


            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public HomeRecyclerViewAdapter(Context context) {




        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_card, parent, false);



            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {





            holder.TV_artist_name.setText(HOme_contents[position]);


            Picasso.with(getActivity())
                    .load(Home_content_images[position])
                    .into(holder.IV_artist_image);



            // clicks for each home category

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //syllabus
                    if (position==0)
                    {

                        ((MainActivity)getActivity()).replaceFragment(new SyllabusListFragment());


                    }else if (position == 1) // cgpa
                    {

                        ((MainActivity)getActivity()).replaceFragment(new CalculatorFrament());


                    }
                    else if (position == 2) // question bank
                    {

                        ((MainActivity)getActivity()).replaceFragment(new QuestionBankFragment());

                    }
                    else if (position == 3) // ordinanace
                    {

                        Fragment fr=new OrdinanaceListFragment();
                        FragmentManager fm=getFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        Bundle args = new Bundle();
                        args.putString("TYPE", "ordinance/subject");
                        args.putString("SUBTYPE", "/subject");
                        fr.setArguments(args);
                        ft.replace(R.id.root_frame, fr);
                        ft.addToBackStack("HomeFragment");
                        ft.commit();

                    }

                }
            });







        }

        @Override
        public int getItemCount() {
            return HOme_contents.length;
        }






    }


}
