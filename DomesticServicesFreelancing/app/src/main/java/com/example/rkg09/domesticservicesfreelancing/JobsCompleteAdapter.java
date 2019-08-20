package com.example.rkg09.domesticservicesfreelancing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rkg09 on 3/7/2019.
 */
    public class JobsCompleteAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, mArrSchoolData1, mArrSchoolData2, mArrSchoolData3, mArrSchoolData4, mArrSchoolData5, mArrSchoolData6, mArrSchoolData7, mArrSchoolData8, mArrSchoolData9, mArrSchoolData10, mArrSchoolData11, mArrSchoolData12, mArrSchoolData14;
        private JobsActivity jobs;
        String customer_id;
        Button btnAction;

        public JobsCompleteAdapter(Context context, ArrayList arrSchoolData, ArrayList arrSchoolData6, ArrayList arrSchoolData1, ArrayList arrSchoolData7, ArrayList arrSchoolData2, ArrayList arrSchoolData8, ArrayList arrSchoolData3, ArrayList arrSchoolData9, ArrayList arrSchoolData4, ArrayList arrSchoolData10, ArrayList arrSchoolData5, ArrayList arrSchoolData11, ArrayList arrSchoolData12, ArrayList arrSchoolData14) {
            super();
            mContext = context;
            mArrSchoolData = arrSchoolData;
            mArrSchoolData1 = arrSchoolData1;
            mArrSchoolData2 = arrSchoolData2;
            mArrSchoolData3 = arrSchoolData3;
            mArrSchoolData4 = arrSchoolData4;
            mArrSchoolData5 = arrSchoolData5;
            mArrSchoolData6 = arrSchoolData6;
            mArrSchoolData7 = arrSchoolData7;
            mArrSchoolData8 = arrSchoolData8;
            mArrSchoolData9 = arrSchoolData9;
            mArrSchoolData10 = arrSchoolData10;
            mArrSchoolData11 = arrSchoolData11;
            mArrSchoolData12 = arrSchoolData12;
            mArrSchoolData14 = arrSchoolData14;
        }

        public int getCount() {
            // return the number of records
            return mArrSchoolData.size();
        }

        // getView method is called for each item of ListView
        public View getView(final int position, View view, ViewGroup parent) {
            // inflate the layout for each item of listView
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.admin_jobs_complete_list, parent, false);


            // get the reference of textView and button
             TextView txtSchoolTitle = (TextView) view.findViewById(R.id.label);
             TextView txtSchoolTitle6 = (TextView) view.findViewById(R.id.value);
             TextView txtSchoolTitle1 = (TextView) view.findViewById(R.id.label1);
             TextView txtSchoolTitle7 = (TextView) view.findViewById(R.id.value1);
             TextView txtSchoolTitle2 = (TextView) view.findViewById(R.id.label2);
             TextView txtSchoolTitle8 = (TextView) view.findViewById(R.id.value2);
             TextView txtSchoolTitle3 = (TextView) view.findViewById(R.id.label3);
             TextView txtSchoolTitle9 = (TextView) view.findViewById(R.id.value3);
             TextView txtSchoolTitle4 = (TextView) view.findViewById(R.id.label4);
             TextView txtSchoolTitle10 = (TextView) view.findViewById(R.id.value4);
             TextView txtSchoolTitle5 = (TextView) view.findViewById(R.id.label5);
             TextView txtSchoolTitle11 = (TextView) view.findViewById(R.id.value5);
            TextView txtSchoolTitle12 = (TextView) view.findViewById(R.id.label6);
            TextView txtSchoolTitle14 = (TextView) view.findViewById(R.id.value6);

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position));
            txtSchoolTitle6.setText(mArrSchoolData6.get(position));
            txtSchoolTitle1.setText(mArrSchoolData1.get(position));
            txtSchoolTitle7.setText(mArrSchoolData7.get(position));
            txtSchoolTitle2.setText(mArrSchoolData2.get(position));
            txtSchoolTitle8.setText(mArrSchoolData8.get(position));
            txtSchoolTitle3.setText(mArrSchoolData3.get(position));
            txtSchoolTitle9.setText(mArrSchoolData9.get(position));
            txtSchoolTitle4.setText(mArrSchoolData4.get(position));
            txtSchoolTitle10.setText(mArrSchoolData10.get(position));
            txtSchoolTitle5.setText(mArrSchoolData5.get(position));
            txtSchoolTitle11.setText(mArrSchoolData11.get(position));
            txtSchoolTitle12.setText(mArrSchoolData12.get(position));
            txtSchoolTitle14.setText(mArrSchoolData14.get(position));


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

