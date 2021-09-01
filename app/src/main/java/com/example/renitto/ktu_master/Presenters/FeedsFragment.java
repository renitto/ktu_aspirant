package com.example.renitto.ktu_master.Presenters;




import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.renitto.ktu_master.Application;
import com.example.renitto.ktu_master.DAL.NetworkManager;
import com.example.renitto.ktu_master.Models.Attachments;
import com.example.renitto.ktu_master.Models.FacebookFeed;
import com.example.renitto.ktu_master.R;
import com.example.renitto.ktu_master.Utils.ConnectionDetector;
import com.example.renitto.ktu_master.Utils.TimesAgoHelper;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Renitto on 8/3/2016.
 */
public class FeedsFragment  extends Fragment implements NetworkManager.onServerDataRequestListener {

    private CallbackManager callbackManager;
    private TextView ProfileName,ProfileFirstName,TV_pls_login;
    CircleImageView ProfilePic;
    RelativeLayout RL_fb_data;
    FacebookFeed facebookFeed;
    Button bt_fb_sign_out;

    ArrayList<Attachments> attachmentses = new ArrayList<Attachments>();
    ArrayList<String> dates = new ArrayList<String>();


    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    AccessToken accessToken;


    RecyclerView RC_feeds_list;
    RecyclerView.LayoutManager mLayoutManager;
    LoginButton loginButton;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
             accessToken = loginResult.getAccessToken();
            String token = accessToken.getToken();
            Profile profile = Profile.getCurrentProfile();
            if (accessToken != null)
            Application.getInstance().FbAccessToken = accessToken.getToken();




            displayMessage(profile);





        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public FeedsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();



        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {

                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feeds, container, false);


        bt_fb_sign_out = (Button)view.findViewById(R.id.bt_fb_sign_out);
        TV_pls_login = (TextView)view.findViewById(R.id.tv_pls_login);

        RC_feeds_list = (RecyclerView)view.findViewById(R.id.rv_fb_feeds);



        RC_feeds_list.setNestedScrollingEnabled(false); // for total scroll of view

        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        RC_feeds_list.setLayoutManager(mLayoutManager);





        bt_fb_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loout from facebook
                LoginManager.getInstance().logOut();
                RL_fb_data.setVisibility(View.GONE); // make fb user detail layout gone

                loginButton.setVisibility(View.VISIBLE); // make login button visible

                RC_feeds_list.setVisibility(View.GONE); // recycler visibilty gone

                TV_pls_login.setVisibility(View.VISIBLE);



            }
        });

        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        ProfileName = (TextView) view.findViewById(R.id.profile_name);
        ProfileFirstName = (TextView) view.findViewById(R.id.tv_fb_firstname);
        ProfilePic = (CircleImageView) view.findViewById(R.id.profilepic);
        RL_fb_data = (RelativeLayout) view.findViewById(R.id.rl_fb_data);



        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void displayMessage(Profile profile){
        if(profile != null){

            TV_pls_login.setVisibility(View.GONE);


            loginButton.setVisibility(View.GONE); // make login button invisible
            RL_fb_data.setVisibility(View.VISIBLE);
            ProfileName.setText(profile.getName());
            ProfileFirstName.setText("Welcome "+profile.getFirstName()+", you can find KTU students facebook feeds below.");

            Picasso.with(getActivity()).load(profile.getProfilePictureUri(200,200)).into(ProfilePic);

            // network call to get fb feeds
            if(new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                NetworkManager.GetDataFromServer(this, NetworkManager.GET_FACEBOOK_FEEDS, getActivity(),null );
            }



        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
//        if (accessToken != null)
//        params[0] = accessToken.getToken();
        displayMessage(profile);
    }

    @Override
    public void showData(Object data, int whatToShow) {

        if (whatToShow == NetworkManager.GET_FACEBOOK_FEEDS) {
            if (data != null) {
                facebookFeed = (FacebookFeed) data;

                //to clear arralists before adding data to them
                attachmentses.clear();
                dates.clear();

                // to skip null attachments

                for (int i=0;i<facebookFeed.getPosts().getData().size();i++)
                {
                    if (facebookFeed.getPosts().getData().get(i).getAttachments() != null)
                    {
                        attachmentses.add(facebookFeed.getPosts().getData().get(i).getAttachments());
                        dates.add(facebookFeed.getPosts().getData().get(i).getCreated_time());
                    }
                }
                // setting feeds here
                RC_feeds_list.setVisibility(View.VISIBLE);
                setFbFeeds(attachmentses);

            }
        }

    }

    @Override
    public void onErrorResponse(String error) {

    }

    public void setFbFeeds(ArrayList<Attachments> attachmentses)
    {

        if (attachmentses != null) {
            FbFeedsListRecyclerViewAdapter fbFeedsListRecyclerViewAdapter = new FbFeedsListRecyclerViewAdapter(getActivity(),attachmentses);
            RC_feeds_list.setAdapter(fbFeedsListRecyclerViewAdapter);
        }

    }


    // adapter for subject list

    public class FbFeedsListRecyclerViewAdapter
            extends RecyclerView.Adapter<FbFeedsListRecyclerViewAdapter.ViewHolder> {


        ArrayList<Attachments> attachmentses = new ArrayList<Attachments>();

        public class ViewHolder extends RecyclerView.ViewHolder {


            public final View mView;


            public final TextView TV_feed_list_item_titile;
            public final TextView TV_feed_list_item_desc;
            public final TextView TV_feed_list_item_date;
            public final ImageView IV_feed_list_item_img;
            public final CardView CV_feeds_card;





            public ViewHolder(View view) {
                super(view);
                mView = view;


                TV_feed_list_item_titile = (TextView) view.findViewById(R.id.tv_feed_item_title);
                TV_feed_list_item_date = (TextView) view.findViewById(R.id.tv_feed_item_date);
                TV_feed_list_item_desc = (TextView) view.findViewById(R.id.tv_feed_item_desc);
                IV_feed_list_item_img = (ImageView) view.findViewById(R.id.iv_feed_item);
                CV_feeds_card = (CardView) view.findViewById(R.id.cv_feeds_card);




            }


        }


        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        public FbFeedsListRecyclerViewAdapter(Context context,ArrayList<Attachments> facebookFeed) {

           this.attachmentses = facebookFeed;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_list_card, parent, false);



            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {



            holder.TV_feed_list_item_date.setText(new TimesAgoHelper(getActivity()).getTimesAgo(dates.get(position)));


            if (attachmentses != null) {

                if(attachmentses.get(position).getData().get(0).getTitle() != null)
                holder.TV_feed_list_item_titile.setText(attachmentses.get(position).getData().get(0).getTitle());
                else
                holder.TV_feed_list_item_titile.setVisibility(View.GONE);

                if (attachmentses.get(position).getData().get(0).getDescription() != null)
                    holder.TV_feed_list_item_desc.setText(attachmentses.get(position).getData().get(0).getDescription());
                else
                    holder.TV_feed_list_item_desc.setVisibility(View.GONE);





                if (attachmentses.get(position).getData().get(0).getMedia() != null) {

                            Picasso.with(getActivity())
                                    .load(attachmentses.get(position).getData().get(0).getMedia().getImage().getSrc())
                                    .placeholder(R.drawable.preview__img)
                                    .error(R.drawable.preview__img)
                                    .into(holder.IV_feed_list_item_img);


                } else
                    holder.IV_feed_list_item_img.setVisibility(View.GONE);


                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) getActivity()).showCustomChromTabs(attachmentses.get(position).getData().get(0).getUrl());
                    }
                });

            }


        }

        @Override
        public int getItemCount() {
            return attachmentses.size();
        }






    }




}
