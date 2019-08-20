package com.example.rkg09.domesticservicesfreelancing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkg09 on 3/7/2019.
 */
    public class MessageAdapter1 extends BaseAdapter {

        private Context mContext;
        private List<String> mArrSchoolData, mArrSchoolData1;
        private JobsActivity jobs;
        String customer_id;
        Button btnAction;

        public MessageAdapter1(Context context, List arrSchoolData, List message_creator_list) {
            super();
            mContext = context;
            mArrSchoolData = arrSchoolData;
            mArrSchoolData1 = message_creator_list;
        }

        public int getCount() {
            // return the number of records
            return mArrSchoolData.size();
        }

        // getView method is called for each item of ListView
        public View getView(final int position, View view, ViewGroup parent) {
            // inflate the layout for each item of listView
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customer_list, parent, false);

            // get the reference of textView and button
             TextView txtSchoolTitle = (TextView) view.findViewById(R.id.label);

            if(mArrSchoolData1.get(position).toString().contentEquals(FirebaseAuth.getInstance().getUid())){
              txtSchoolTitle.setBackgroundResource(R.color.green);
            }
            else{
                txtSchoolTitle.setBackgroundResource(R.color.red);
            }

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position).toString());


            return view;
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }

