package com.example.renitto.ktu_master.Presenters;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.renitto.ktu_master.DAL.DbManager;
import com.example.renitto.ktu_master.Models.CheckList;
import com.example.renitto.ktu_master.R;
import com.example.renitto.ktu_master.Utils.TimesAgoHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Renitto on 7/30/2016.
 */
public class CheckListFragment extends Fragment {

    RecyclerView RV_Checklist;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CheckList> mItems = new ArrayList<CheckList>();
    Button BT_add_item;
    TextInputEditText ET_checklist_header,ET_checklist_desc;
    CheckList checkList;
    DbManager dbManager ;
    ChecklistItemAdapter checklistItemAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.checklist,
                container, false);

        dbManager = new DbManager();


        RV_Checklist = (RecyclerView)view.findViewById(R.id.rv_checklist);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        RV_Checklist.setLayoutManager(mLayoutManager);

        checklistItemAdapter = new ChecklistItemAdapter(dbManager.getMyCheckLists(getActivity()));

        RV_Checklist.setAdapter(checklistItemAdapter);

        BT_add_item = (Button) view.findViewById(R.id.bt_add_checklist);
//        BT_clear_all_shopp_planner_item = (Button)view.findViewById(R.id.bt_shopping_clear_all);

        ET_checklist_header =(TextInputEditText) view.findViewById(R.id.et_checklist_header);
        ET_checklist_desc =(TextInputEditText) view.findViewById(R.id.et_checklist_desc);



        BT_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToChecklist();

            }
        });

//        BT_clear_all_shopp_planner_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                shoppingPlannerItemAdapter.ClearAllData();
//                dbManager.removeAllShoppingPlanner(getActivity());
//            }
//        });


        ET_checklist_header.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addToChecklist();
                    return true;
                }
                return false;
            }
        });

        ET_checklist_desc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addToChecklist();
                    return true;
                }
                return false;
            }
        });


        // swipe to remove

        ItemTouchHelper.Callback callback = new MovieTouchHelper(checklistItemAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(RV_Checklist);
        
        return view;
    }

    public void addToChecklist()
    {
        if (!ET_checklist_header.getText().toString().equals("") && ET_checklist_header.getText().toString().length() != 0)
        {
            checkList = new CheckList(ET_checklist_header.getText().toString(),ET_checklist_desc.getText().toString(),false, String.valueOf(Calendar.getInstance().getTimeInMillis()));
            checklistItemAdapter.addItem(checkList);
            ET_checklist_header.setText("");
            ET_checklist_desc.setText("");
            dbManager.insertToCheckList(getActivity(),checkList);

            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            //to hide it, call the method again
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }
        else {
            ET_checklist_header.setError("Please add a title!");
            ET_checklist_header.requestFocus();
        }
    }


    public class ChecklistItemAdapter
            extends RecyclerView.Adapter<ChecklistItemAdapter.ViewHolder> {

        ArrayList<CheckList> mItems = new ArrayList<CheckList>();




        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;


            public final CheckBox CB_checklist_item;
            public final  TextView TV_checklist_desc;
            public final  TextView TV_checklist_date;




            public ViewHolder(View view) {
                super(view);
                mView = view;


                CB_checklist_item = (CheckBox) view.findViewById(R.id.cb_checklist_item);
                TV_checklist_desc = (TextView) view.findViewById(R.id.tv_checklist_desc);
                TV_checklist_date = (TextView) view.findViewById(R.id.tv_checklist_date);


            }


        }


        public ChecklistItemAdapter(ArrayList mItems) {
            this.mItems = mItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.checklist_card, parent, false);




            return new ViewHolder(view);


        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {





            holder.CB_checklist_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    dbManager.updateCheckList(getActivity(),new CheckList(mItems.get(position).getHeader(),mItems.get(position).getDescription(),isChecked,String.valueOf(Calendar.getInstance().getTimeInMillis())));
                }
            });



            holder.CB_checklist_item.setText(mItems.get(position).getHeader());
            holder.TV_checklist_desc.setText(mItems.get(position).getDescription());



            holder.TV_checklist_date.setText(new TimesAgoHelper(getActivity()).getTimesAgoCheckList(mItems.get(position).getDate()));

            if (mItems.get(position).isCheck_status())
            {
                holder.CB_checklist_item.setTextColor(getResources().getColor(R.color.colorPrimary));
                holder.CB_checklist_item.setChecked(true);
            }









            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });







        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }


        private void addItem(CheckList item) {
            mItems.add(item);
            ChecklistItemAdapter.this.notifyItemInserted(mItems.size()-1);
        }


        private void ClearAllData() {
            int size = this.mItems.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    this.mItems.remove(0);
                }

                this.notifyItemRangeRemoved(0, size);
            }
        }

        public void remove(int position) {

            dbManager.removeCheckList(getActivity(),mItems.get(position));
            mItems.remove(position);
            notifyItemRemoved(position);

        }





    }

    public class MovieTouchHelper extends ItemTouchHelper.SimpleCallback {
        private ChecklistItemAdapter checklistItemAdapter;

        public MovieTouchHelper(ChecklistItemAdapter shoppingPlannerItemAdapter){
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.checklistItemAdapter = shoppingPlannerItemAdapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //TODO: Not implemented here
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //Remove item
            checklistItemAdapter.remove(viewHolder.getAdapterPosition());
        }
    }
}
