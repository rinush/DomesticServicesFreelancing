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
import android.widget.Toast;

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
    public class DeleteCustomerAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, mArrSchoolData1, mArrSchoolData2, mArrSchoolData3, mArrSchoolData4, mArrSchoolData5;
        private JobsActivity jobs;
        String customer_id;
        Button btnAction;
        Boolean flag = false;

        public DeleteCustomerAdapter(Context context, ArrayList arrSchoolData, ArrayList arrSchoolData1, ArrayList arrSchoolData2, ArrayList arrSchoolData3, ArrayList arrSchoolData4, ArrayList arrSchoolData5) {
            super();
            mContext = context;
            mArrSchoolData = arrSchoolData;
            mArrSchoolData1 = arrSchoolData1;
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
            view = inflater.inflate(R.layout.delete_customer_list, parent, false);


            // get the reference of textView and button
             TextView txtSchoolTitle = (TextView) view.findViewById(R.id.label);
             TextView txtSchoolTitle1 = (TextView) view.findViewById(R.id.label1);
             TextView txtSchoolTitle2 = (TextView) view.findViewById(R.id.label2);
             TextView txtSchoolTitle3 = (TextView) view.findViewById(R.id.label3);
             TextView txtSchoolTitle4 = (TextView) view.findViewById(R.id.label4);
             TextView txtSchoolTitle5 = (TextView) view.findViewById(R.id.label5);

             btnAction = (Button) view.findViewById(R.id.btn);

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position));
            txtSchoolTitle1.setText(mArrSchoolData1.get(position));
            txtSchoolTitle2.setText(mArrSchoolData2.get(position));
            txtSchoolTitle3.setText(mArrSchoolData3.get(position));
            txtSchoolTitle4.setText(mArrSchoolData4.get(position));
            txtSchoolTitle5.setText(mArrSchoolData5.get(position));

            btnAction.setText("Delete Customer");

            // Click listener of button
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logic goes here
                    int i = (int) getItem(position);
                    delete_customer(i);
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
      public void delete_customer(final int position){
          DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
          ref.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  int i = 0;
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                      if (i == position) {
                          if (ds.exists()) {
                              customer_id = ds.getKey().toString();
                              //Toast.makeText(mContext,""+customer_id,Toast.LENGTH_LONG).show();
                              DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id).child("Address");
                              ref1.setValue(null);
                              ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id).child("Contact");
                              ref1.setValue(null);
                              ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id).child("Designation");
                              ref1.setValue(null);
                              ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id).child("Email");
                              ref1.setValue(null);
                              ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id).child("FirstName");
                              ref1.setValue(null);
                              ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id).child("LastName");
                              ref1.setValue(null);
                              ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id).child("Password");
                              ref1.setValue(null);
                          }
                          break;
                      }
                      else {
                       i = i + 1;
                      }
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }});

          Intent intent = new Intent(mContext,AdminActivity.class);
          mContext.startActivity(intent);
      }
    }

