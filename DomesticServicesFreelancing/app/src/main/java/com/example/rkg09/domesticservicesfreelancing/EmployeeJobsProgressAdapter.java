package com.example.rkg09.domesticservicesfreelancing;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rkg09 on 3/7/2019.
 */
    public class EmployeeJobsProgressAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, mArrSchoolData1, mArrSchoolData2, mArrSchoolData3, mArrSchoolData4, mArrSchoolData5, mArrSchoolData6, mArrSchoolData7, mArrSchoolData8, mArrSchoolData9;
        private JobsActivity jobs;

        public EmployeeJobsProgressAdapter(Context context, ArrayList arrSchoolData, ArrayList arrSchoolData4, ArrayList arrSchoolData1,  ArrayList arrSchoolData5, ArrayList arrSchoolData2, ArrayList arrSchoolData6, ArrayList arrSchoolData3, ArrayList arrSchoolData7, ArrayList arrSchoolData8, ArrayList arrSchoolData9) {
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
        }

        public int getCount() {
            // return the number of records
            return mArrSchoolData.size();
        }

        // getView method is called for each item of ListView
        public View getView(final int position, View view, ViewGroup parent) {
            // inflate the layout for each item of listView
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.employee_jobs_progress_list, parent, false);


            // get the reference of textView and button
            TextView txtSchoolTitle = (TextView) view.findViewById(R.id.label);
            TextView txtSchoolTitle4 = (TextView) view.findViewById(R.id.value);
            TextView txtSchoolTitle1 = (TextView) view.findViewById(R.id.label1);
            TextView txtSchoolTitle5 = (TextView) view.findViewById(R.id.value1);
            TextView txtSchoolTitle2 = (TextView) view.findViewById(R.id.label2);
            TextView txtSchoolTitle6 = (TextView) view.findViewById(R.id.value2);
            TextView txtSchoolTitle3 = (TextView) view.findViewById(R.id.label3);
            TextView txtSchoolTitle7 = (TextView) view.findViewById(R.id.value3);
            TextView txtSchoolTitle8 = (TextView) view.findViewById(R.id.label4);
            TextView txtSchoolTitle9 = (TextView) view.findViewById(R.id.value4);
            final Button btnAction = (Button) view.findViewById(R.id.btn);

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position));
            txtSchoolTitle4.setText(mArrSchoolData4.get(position));
            txtSchoolTitle1.setText(mArrSchoolData1.get(position));
            txtSchoolTitle5.setText(mArrSchoolData5.get(position));
            txtSchoolTitle2.setText(mArrSchoolData2.get(position));
            txtSchoolTitle6.setText(mArrSchoolData6.get(position));
            txtSchoolTitle3.setText(mArrSchoolData3.get(position));
            txtSchoolTitle7.setText(mArrSchoolData7.get(position));
            txtSchoolTitle8.setText(mArrSchoolData8.get(position));
            txtSchoolTitle9.setText(mArrSchoolData9.get(position));
            btnAction.setText("Share Location");

            // Click listener of button
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logic goes here
                    int i = (int) getItem(position);
                    share_location(i);
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
      public void share_location(final int position){
          DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(FirebaseAuth.getInstance().getUid());
          ref.addValueEventListener(new ValueEventListener() {
              int i = 0;
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.exists()) {
                        if (i == position) {
                         String customerId = ds.getKey().toString();
                            Intent intent = new Intent(mContext,EmployeeMapsActivity.class);
                            intent.putExtra("Id",""+customerId);
                            mContext.startActivity(intent);
                        }
                        else {
                            i = i + 1;
                        }
                    }
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }});

      }
    }

