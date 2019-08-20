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

import com.example.rkg09.domesticservicesfreelancing.Notifications.Client;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Data;
import com.example.rkg09.domesticservicesfreelancing.Notifications.MyResponse;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Sender;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Token;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rkg09 on 3/7/2019.
 */
    public class ProposalAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, mArrSchoolData1, mArrSchoolData2, mArrSchoolData3, mArrSchoolData4, mArrSchoolData5, mArrSchoolData6, mArrSchoolData7, mArrSchoolData8, mArrSchoolData9, mArrSchoolData10, mArrSchoolData11;
        private ViewProposalActivity proposals;
        String  mService, mDate, mTime, mJob_description;
        Button btnAction;
        private Boolean flag_available = false;
        private Boolean flag_working = false;
        APIService apiService;

        public ProposalAdapter(Context context, ArrayList arrSchoolData, ArrayList arrSchoolData6, String service, String date, String time, String job_description, ArrayList arrSchoolData1, ArrayList arrSchoolData7, ArrayList arrSchoolData2, ArrayList arrSchoolData8, ArrayList arrSchoolData3, ArrayList arrSchoolData9, ArrayList arrSchoolData4, ArrayList arrSchoolData10, ArrayList arrSchoolData5,  ArrayList arrSchoolData11) {
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
            mService = service;
            mDate = date;
            mTime = time;
            mJob_description = job_description;
            apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        }

        public int getCount() {
            // return the number of records
            return mArrSchoolData.size();
        }

        // getView method is called for each item of ListView
        public View getView(final int position, View view, ViewGroup parent) {
            // inflate the layout for each item of listView
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.proposal_list, parent, false);


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
            btnAction = (Button) view.findViewById(R.id.btn);

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

            btnAction.setText("Hire");

            // Click listener of button
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logic goes here
                    int i = (int) getItem(position);
                    hire_employee(i,mArrSchoolData9.get(position));
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
      public void hire_employee(final int position, final String payment){
          DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services").child(mService).child(FirebaseAuth.getInstance().getUid());
            ref.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          int i = 0;
                          for (DataSnapshot ds : dataSnapshot.getChildren()) {
                              if (i == position) {
                                  if (ds.exists()) {
                                     final String employee_id = ds.getKey().toString();
                                       DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid()).child(employee_id).child("Job");
                                      ref1.setValue(mService);
                                              ref1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid()).child(employee_id).child("Date");
                                              ref1.setValue(mDate);
                                              ref1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid()).child(employee_id).child("Time");
                                              ref1.setValue(mTime);
                                              ref1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid()).child(employee_id).child("Job Description");
                                              ref1.setValue(mJob_description);
                                              ref1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid()).child(employee_id).child("Payment");
                                              ref1.setValue(payment);

                                      DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(employee_id).child(FirebaseAuth.getInstance().getUid()).child("Job");
                                      ref2.setValue(mService);
                                      ref2 = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(employee_id).child(FirebaseAuth.getInstance().getUid()).child("Date");
                                      ref2.setValue(mDate);
                                      ref2 = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(employee_id).child(FirebaseAuth.getInstance().getUid()).child("Time");
                                      ref2.setValue(mTime);
                                      ref2 = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(employee_id).child(FirebaseAuth.getInstance().getUid()).child("Job Description");
                                      ref2.setValue(mJob_description);
                                      ref2 = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(employee_id).child(FirebaseAuth.getInstance().getUid()).child("Payment");
                                      ref2.setValue(payment);

                                      String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();
                                      DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Chat").child(key).child("Info").child("Users").child(FirebaseAuth.getInstance().getUid());
                                      ref3.setValue(true);
                                      ref3 = FirebaseDatabase.getInstance().getReference().child("Chat").child(key).child("Info").child("Users").child(employee_id);
                                      ref3.setValue(true);
                                      ref3 = FirebaseDatabase.getInstance().getReference().child("Chat").child(key).child("Info").child("Id");
                                      ref3.setValue(key);
                                      ref3 = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Customers").child(FirebaseAuth.getInstance().getUid()).child("Chat").child(key);
                                      ref3.setValue(true);
                                      ref3 = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Employees").child(employee_id).child("Chat").child(key);
                                      ref3.setValue(true);
                                  }
                                  break;
                              }
                              else{
                                  i = i+1;
                              }
                          }
                      }
                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
            });

          //Toast.makeText(mContext, "Hired", Toast.LENGTH_SHORT).show();
          btnAction.setText("Hired");

          String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
          DatabaseReference refAvailable = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(userId).child(mService);

          DatabaseReference refWorking = FirebaseDatabase.getInstance().getReference().child("Jobs In Progress").child(userId).child(mService).child("Date");
          refWorking.setValue(mDate);
          refWorking = FirebaseDatabase.getInstance().getReference().child("Jobs In Progress").child(userId).child(mService).child("Time");
          refWorking.setValue(mTime);
          refWorking = FirebaseDatabase.getInstance().getReference().child("Jobs In Progress").child(userId).child(mService).child("Job Description");
          refWorking.setValue(mJob_description);
          refWorking = FirebaseDatabase.getInstance().getReference().child("Jobs In Progress").child(userId).child(mService).child("Payment");
          refWorking.setValue(payment);
          refAvailable.setValue(null);

          DatabaseReference refAvailable1 = FirebaseDatabase.getInstance().getReference().child("Jobs").child(mService).child(userId);
          refAvailable1.setValue(null);


          DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Services").child(mService).child(FirebaseAuth.getInstance().getUid());
          ref3.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  int i = 0;
                  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                      if (i == position) {
                          if (ds.exists()) {
                              final String employee_id = ds.getKey().toString();

          DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
          reference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  String fn = dataSnapshot.child("FirstName").getValue().toString();
                  String ln = dataSnapshot.child("LastName").getValue().toString();
                  updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln, employee_id);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });}
                      i = i + 1;
                      }
                      else {
                          i = i + 1;
                      }
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
      }


          /*Intent intent = new Intent(mContext,PhoneAuthorizationActivity.class);
          intent.putExtra("Hire ",""+position);
          mContext.startActivity(intent);*/

    private void updateToken(String token, String name, String emp_id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        Token token1 = new Token(token);
        //Toast.makeText(getApplicationContext(),""+FirebaseAuth.getInstance().getUid(),Toast.LENGTH_LONG).show();
        reference.child(emp_id).setValue(token1);
        sendNotification(emp_id,name,"You are Hired");
    }

    private void sendNotification(final String receiver, final String name, final String msg){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference().child("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    //Toast.makeText(getApplicationContext(),""+getIntent().getStringExtra("Id"),Toast.LENGTH_LONG).show();
                    Data data = new Data("Y3jtpASaeGYVeL0h03UXu8ZfkGt1",R.drawable.dsf_logo,name+":"+msg,"New Message",receiver);
                    Sender sender = new Sender(data, token.getToken());
                    //Toast.makeText(getApplicationContext(),"Sent",Toast.LENGTH_LONG).show();
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code() == 200){
                                if(response.body().success != 1){
                                    Toast.makeText(mContext,"Failed!",Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent intent = new Intent(mContext, Services1Activity.class);
        mContext.startActivity(intent);
    }

    }

