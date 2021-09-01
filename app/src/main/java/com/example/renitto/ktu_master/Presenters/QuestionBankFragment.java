package com.example.renitto.ktu_master.Presenters;



import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.renitto.ktu_master.Adapters.CoverFlowAdapter;
import com.example.renitto.ktu_master.Models.GameEntity;
import com.example.renitto.ktu_master.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

/**
 * Created by Renitto on 7/29/2016.
 */
public class QuestionBankFragment extends Fragment {

    public static final String FILE_DESCRIPTOR = "file://";
    public static final String SLASH = "/";
    public static final String MEME_TYPE_PDF = "application/pdf";

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;
    ImageView IM_close;
    String [] Questionbanknames;
    Integer [] Qusetionbankbookimages = {R.drawable.basics_of_civil_engineering ,
            R.drawable.basic_electrical_engineering,
            R.drawable.basic_electronics_engineering,
            R.drawable.intro_mechanical_engineering,
            R.drawable.calculus,
            R.drawable.differential_equations,
            R.drawable.engineering_graphics,
            R.drawable.engineering_physics,
            R.drawable.design_engineering,
            R.drawable.sustainable_enginnering};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        View view = inflater.inflate(R.layout.questionbank_coverflow,
                container, false);

        Questionbanknames = getResources().getStringArray(R.array.question_papper_subjects); // question bank book names array



        for (int i=0;i<Questionbanknames.length;i++)
            mData.add(new GameEntity(Qusetionbankbookimages[i], Questionbanknames[i]));

        IM_close = (ImageView)view.findViewById(R.id.img_bt_close_coverflow);

//        // close button
//        IM_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dismiss();
//            }
//        });

        mTitle = (TextSwitcher)view.findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });

        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        mAdapter = new CoverFlowAdapter(getActivity());
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow)view.findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        mData.get(position).titleResId,
                        Toast.LENGTH_SHORT).show();

                // opening pdf here
                try {
                    readAssets(String.valueOf(position));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Soory,Application needs pdf viewer to open question bank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(mData.get(position).titleResId);
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });

        return view;

    }

    // function to read pdf from assets
    private void readAssets(String qPosition) {
        AssetManager assetManager = getActivity().getAssets();
        File file = new File(getActivity().getFilesDir(), qPosition + ".pdf");
        try {
            InputStream in = assetManager.open("questions/" + qPosition + ".pdf");
            OutputStream out = getActivity().openFileOutput(file.getName(), 1);
            copyFile(in, out);
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse("file://" + getActivity().getFilesDir() + "/" + qPosition + ".pdf"), "application/pdf");
        startActivity(intent);
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int read = in.read(buffer);
            if (read != -1) {
                out.write(buffer, 0, read);
            } else {
                return;
            }
        }
    }





    }
