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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by rkg09 on 3/7/2019.
 */
    public class JobsAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, mArrSchoolData1, mArrSchoolData2, mArrSchoolData3, mArrSchoolData4, mArrSchoolData5, mArrSchoolData6, mArrSchoolData7;
        private JobsActivity jobs;
        String customer_id;
        Button btnAction;

        public JobsAdapter(Context context, ArrayList arrSchoolData, ArrayList arrSchoolData4, ArrayList arrSchoolData1, ArrayList arrSchoolData5, ArrayList arrSchoolData2, ArrayList arrSchoolData6, ArrayList arrSchoolData3, ArrayList arrSchoolData7) {
            super();
            mContext = context;
            mArrSchoolData = arrSchoolData;
            mArrSchoolData4 = arrSchoolData4;
            mArrSchoolData1 = arrSchoolData1;
            mArrSchoolData5 = arrSchoolData5;
            mArrSchoolData2 = arrSchoolData2;
            mArrSchoolData6 = arrSchoolData6;
            mArrSchoolData3 = arrSchoolData3;
            mArrSchoolData7 = arrSchoolData7;
        }

        public int getCount() {
            // return the number of records
            return mArrSchoolData.size();
        }

        // getView method is called for each item of ListView
        public View getView(final int position, View view, ViewGroup parent) {
            // inflate the layout for each item of listView
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.jobs_posted_list, parent, false);


            // get the reference of textView and button
             TextView txtSchoolTitle = (TextView) view.findViewById(R.id.label);
             TextView txtSchoolTitle4 = (TextView) view.findViewById(R.id.value);
             TextView txtSchoolTitle1 = (TextView) view.findViewById(R.id.label1);
             TextView txtSchoolTitle5 = (TextView) view.findViewById(R.id.value1);
             TextView txtSchoolTitle2 = (TextView) view.findViewById(R.id.label2);
             TextView txtSchoolTitle6 = (TextView) view.findViewById(R.id.value2);
             TextView txtSchoolTitle3 = (TextView) view.findViewById(R.id.label3);
             TextView txtSchoolTitle7 = (TextView) view.findViewById(R.id.value3);
             btnAction = (Button) view.findViewById(R.id.btn);

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position));
            txtSchoolTitle4.setText(mArrSchoolData4.get(position));
            txtSchoolTitle1.setText(mArrSchoolData1.get(position));
            txtSchoolTitle5.setText(mArrSchoolData5.get(position));
            txtSchoolTitle2.setText(mArrSchoolData2.get(position));
            txtSchoolTitle6.setText(mArrSchoolData6.get(position));
            txtSchoolTitle3.setText(mArrSchoolData3.get(position));
            txtSchoolTitle7.setText(mArrSchoolData7.get(position));
            btnAction.setText("View Proposals");

            // Click listener of button
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logic goes here
                    int i = (int) getItem(position);
                    view_proposals(i);
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
      public void view_proposals(final int position){

          Intent intent = new Intent(mContext,ViewProposalActivity.class);
          intent.putExtra("Proposal",""+position);
          mContext.startActivity(intent);
      }
    }

