package com.example.rkg09.domesticservicesfreelancing;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by rkg09 on 3/7/2019.
 */
    public class SchoolAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, mArrSchoolData1, mArrSchoolData2, mArrSchoolData3, mArrSchoolData4, mArrSchoolData5;
        private JobsActivity jobs;

        public SchoolAdapter(Context context, ArrayList arrSchoolData, ArrayList arrSchoolData3, ArrayList arrSchoolData1, ArrayList arrSchoolData4, ArrayList arrSchoolData2, ArrayList arrSchoolData5) {
            super();
            mContext = context;
            mArrSchoolData = arrSchoolData;
            mArrSchoolData1 =arrSchoolData1;
            mArrSchoolData2 = arrSchoolData2;
            mArrSchoolData3 = arrSchoolData3;
            mArrSchoolData4 = arrSchoolData4;
            mArrSchoolData5 = arrSchoolData5;
        }

        public int getCount() {
            // return the number of records
            return mArrSchoolData.size();
        }

        // getView method is called for each item of ListView
        public View getView(final int position, View view, ViewGroup parent) {
            // inflate the layout for each item of listView
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.jobs_list, parent, false);


            // get the reference of textView and button
            TextView txtSchoolTitle = (TextView) view.findViewById(R.id.label);
            TextView txtSchoolTitle3 = (TextView) view.findViewById(R.id.value);
            TextView txtSchoolTitle1 = (TextView) view.findViewById(R.id.label1);
            TextView txtSchoolTitle4 = (TextView) view.findViewById(R.id.value1);
            TextView txtSchoolTitle2 = (TextView) view.findViewById(R.id.label2);
            TextView txtSchoolTitle5 = (TextView) view.findViewById(R.id.value2);
            final Button btnAction = (Button) view.findViewById(R.id.btn);

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position));
            txtSchoolTitle3.setText(mArrSchoolData3.get(position));
            txtSchoolTitle1.setText(mArrSchoolData1.get(position));
            txtSchoolTitle4.setText(mArrSchoolData4.get(position));
            txtSchoolTitle2.setText(mArrSchoolData2.get(position));
            txtSchoolTitle5.setText(mArrSchoolData5.get(position));
            btnAction.setText("Submit Proposal");

            // Click listener of button
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logic goes here
                    int i = (int) getItem(position);
                    submit_proposal(i);
                }
            });

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
      public void submit_proposal(int position){
          Intent intent = new Intent(mContext,Services.class);
          intent.putExtra("Job",""+position);
          mContext.startActivity(intent);
      }
    }

