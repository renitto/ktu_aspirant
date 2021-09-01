package com.example.renitto.ktu_master.Presenters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renitto.ktu_master.R;
import com.github.florent37.viewanimator.ViewAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Renitto on 7/28/2016.
 */
public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Bind(R.id.splash_text_main)
    TextView TV_header_splash;

    @Bind(R.id.splash_iv_image)
    ImageView Splash_iv_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        ButterKnife.bind(this);




        // setting shared preference
//        int firsttime = 1;
//        sharedPreferences = getSharedPreferences("FirstTime", Context.MODE_PRIVATE);
//        final int checkvalue = sharedPreferences.getInt("firsttime",0);
//
//
//        SharedPreferences.Editor editor  = sharedPreferences.edit();
//        editor.putInt("firsttime",firsttime);
//        editor.commit();
//


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/PROMETHEUS.ttf");
        TV_header_splash.setTypeface(tf, Typeface.BOLD);


        ViewAnimator
                .animate(Splash_iv_image)
                .translationY(-1000, 0)
                .alpha(0,1)
                .andAnimate(TV_header_splash)
                .dp().translationX(-20, 0)
                .descelerate()
                .duration(2000)

                .thenAnimate(Splash_iv_image)
                .scale(1f,0.5f,1f)
                .accelerate()
                .duration(1000)
                .start();



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 2s = 2000ms

//                if (checkvalue == 2) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
//                }
//                else
//                {
//                    Intent intent = new Intent(SplashActivity.this, AppIntroActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                }

            }
        }, 3000);

    }


}
