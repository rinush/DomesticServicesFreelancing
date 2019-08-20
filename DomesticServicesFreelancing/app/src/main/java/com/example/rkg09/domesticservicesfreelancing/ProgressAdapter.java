package com.example.rkg09.domesticservicesfreelancing;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    public class ProgressAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, mArrSchoolData1, mArrSchoolData2, mArrSchoolData3, mArrSchoolData4, mArrSchoolData5, mArrSchoolData6, mArrSchoolData7, mArrSchoolData8, mArrSchoolData9, mArrSchoolData10, mArrSchoolData11;
        private JobsActivity jobs;
        private String mDate, mJob, mJob_Description, mTime, mEmployee_ID;
        APIService apiService;

        public ProgressAdapter(Context context, ArrayList arrSchoolData5,ArrayList arrSchoolData4,ArrayList arrSchoolData, ArrayList arrSchoolData6, String date, String job, String job_description, String time,String employee_id, ArrayList arrSchoolData1,ArrayList arrSchoolData7, ArrayList arrSchoolData2, ArrayList arrSchoolData8, ArrayList arrSchoolData3, ArrayList arrSchoolData9, ArrayList arrSchoolData10, ArrayList arrSchoolData11) {
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
            mDate = date;
            mJob = job;
            mJob_Description = job_description;
            mTime = time;
            mEmployee_ID = employee_id;
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
            view = inflater.inflate(R.layout.progress_list, parent, false);


            // get the reference of textView and button
            TextView txtSchoolTitle = (TextView) view.findViewById(R.id.label);
            TextView txtSchoolTitle6 = (TextView) view.findViewById(R.id.value);
            TextView txtSchoolTitle1 = (TextView) view.findViewById(R.id.label1);
            TextView txtSchoolTitle7 = (TextView) view.findViewById(R.id.value1);
            TextView txtSchoolTitle2 = (TextView) view.findViewById(R.id.label2);
            TextView txtSchoolTitle8 = (TextView) view.findViewById(R.id.value2);
            TextView txtSchoolTitle3 = (TextView) view.findViewById(R.id.label3);
            TextView txtSchoolTitle9 = (TextView) view.findViewById(R.id.value3);
            TextView txtSchoolTitle10 = (TextView) view.findViewById(R.id.label4);
            TextView txtSchoolTitle11 = (TextView) view.findViewById(R.id.value4);
            final Button location = (Button) view.findViewById(R.id.getLocation);
            //final Button chat = (Button) view.findViewById(R.id.chat);
            final Button complete = (Button) view.findViewById(R.id.complete);

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position));
            txtSchoolTitle6.setText(mArrSchoolData6.get(position));
            txtSchoolTitle1.setText(mArrSchoolData1.get(position));
            txtSchoolTitle7.setText(mArrSchoolData7.get(position));
            txtSchoolTitle2.setText(mArrSchoolData2.get(position));
            txtSchoolTitle8.setText(mArrSchoolData8.get(position));
            txtSchoolTitle3.setText(mArrSchoolData3.get(position));
            txtSchoolTitle9.setText(mArrSchoolData9.get(position));
            txtSchoolTitle10.setText(mArrSchoolData10.get(position));
            txtSchoolTitle11.setText(mArrSchoolData11.get(position));
            location.setText("Get Location");
            //chat.setText("Message");
            complete.setText("Job Done");


            // Click listener of button
            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logic goes here
                    int i = (int) getItem(position);
                    get_location(i);
                }
            });

            /*chat.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int i = (int) getItem(position);
                                            get_message(i);
                                        }
                                    });*/

            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) getItem(position);
                    job_complete(i);
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
      public void get_location(final int position){

          DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid());
          ref1.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  int i = 0;
                  for(DataSnapshot ds: dataSnapshot.getChildren()) {
                      //Toast.makeText(mContext,""+position,Toast.LENGTH_LONG).show();
                      if (i == position) {
                          final String emp_id = ds.getKey().toString();
                          Intent intent = new Intent(mContext, CustomerMapsActivity.class);
                          intent.putExtra("Id", emp_id);
                          mContext.startActivity(intent);
                          i = i + 1;
                      }
                      else{
                       i = i + 1;
                      }
                  }
        }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }});
        }

              public void get_message(int position){
        Intent intent = new Intent(mContext,MessageActivity1.class);
        intent.putExtra("Id",mEmployee_ID);
        mContext.startActivity(intent);
    }

    public void job_complete(final int position) {
        //delete_chat(position);
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid());
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
              for(DataSnapshot ds: dataSnapshot.getChildren()) {
                  //Toast.makeText(mContext,""+position,Toast.LENGTH_LONG).show();
                  if (i == position) {
                      final String emp_id = ds.getKey().toString();
                      final String designation1 = ds.child("Job").getValue().toString();
                      final String date1 = ds.child("Date").getValue().toString();
                      final String time1 = ds.child("Time").getValue().toString();
                      final String job_description1 = ds.child("Job Description").getValue().toString();
                      final String payment = ds.child("Payment").getValue().toString();
                      DatabaseReference refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Complete").child(FirebaseAuth.getInstance().getUid()).child(emp_id).child("Date");
                      refdone.setValue(date1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Complete").child(FirebaseAuth.getInstance().getUid()).child(emp_id).child("Job");
                      refdone.setValue(designation1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Complete").child(FirebaseAuth.getInstance().getUid()).child(emp_id).child("Job Description");
                      refdone.setValue(job_description1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Complete").child(FirebaseAuth.getInstance().getUid()).child(emp_id).child("Time");
                      refdone.setValue(time1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Complete").child(FirebaseAuth.getInstance().getUid()).child(emp_id).child("Payment");
                      refdone.setValue(payment);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Completed").child(emp_id).child(FirebaseAuth.getInstance().getUid()).child("Date");
                      refdone.setValue(date1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Completed").child(emp_id).child(FirebaseAuth.getInstance().getUid()).child("Job");
                      refdone.setValue(designation1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Completed").child(emp_id).child(FirebaseAuth.getInstance().getUid()).child("Job Description");
                      refdone.setValue(job_description1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Completed").child(emp_id).child(FirebaseAuth.getInstance().getUid()).child("Time");
                      refdone.setValue(time1);
                      refdone = FirebaseDatabase.getInstance().getReference().child("Jobs Completed").child(emp_id).child(FirebaseAuth.getInstance().getUid()).child("Payment");
                      refdone.setValue(payment);

                      DatabaseReference ref = FirebaseDatabase.getInstance().getReference("employeesWorking").child(emp_id);
                      ref.setValue(null);

                      GeoFire geoFire = new GeoFire(ref);
                      geoFire.removeLocation(emp_id, new GeoFire.CompletionListener() {
                          @Override
                          public void onComplete(String key, DatabaseError error) {

                          }
                      });
                      i = i + 1;
                      DatabaseReference ref100 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                      ref100.addValueEventListener(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              String fn = dataSnapshot.child("FirstName").getValue().toString();
                              String ln = dataSnapshot.child("LastName").getValue().toString();
                              updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln);
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });
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

        //Toast.makeText(mContext,""+mArrSchoolData4.get(position),Toast.LENGTH_LONG).show();
       DatabaseReference refdone10 = FirebaseDatabase.getInstance().getReference().child("Chat");
        refdone10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //int j = 1;
                String empl_id = mArrSchoolData4.get(position);
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    int j = 0;
                    String []id = new String[2];
                    for(DataSnapshot ds1: ds.child("Info").child("Users").getChildren()){
                        id[j] = ds1.getKey().toString();
                        j = j + 1;
                    }
                    //Toast.makeText(mContext,""+emp_id,Toast.LENGTH_LONG).show();
                    if(((id[0].contentEquals(FirebaseAuth.getInstance().getUid()))&&(id[1].contentEquals(empl_id))) || ((id[0].contentEquals(empl_id)&&
                            (id[1].contentEquals(FirebaseAuth.getInstance().getUid())))) ){

                        //Toast.makeText(mContext,"Done " + j,Toast.LENGTH_LONG).show();
                        //j = j + 1;

                        String id1 = ds.child("Info").child("Id").getValue().toString();
                        DatabaseReference refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(id1).child("Info");
                        refdone2.setValue(null);
                        refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(id1).child("messages");
                        refdone2.setValue(null);
                        refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Customers").child(FirebaseAuth.getInstance().getUid()).child("Chat").child(id1);
                        refdone2.setValue(null);
                        refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Employees").child(empl_id).child("Chat").child(id1);
                        refdone2.setValue(null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        String emplo_id = mArrSchoolData4.get(position);
        String designation5 = mArrSchoolData5.get(position);
        DatabaseReference refdone1 = FirebaseDatabase.getInstance().getReference().child("Jobs In Progress").child(FirebaseAuth.getInstance().getUid()).child(designation5);
        refdone1.setValue(null);
        refdone1 = FirebaseDatabase.getInstance().getReference().child("Services").child(designation5).child(FirebaseAuth.getInstance().getUid()).child(emplo_id);
        refdone1.setValue(null);
        refdone1 = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(emplo_id).child(FirebaseAuth.getInstance().getUid());
        refdone1.setValue(null);
        refdone1 = FirebaseDatabase.getInstance().getReference().child("customerRequest").child(FirebaseAuth.getInstance().getUid()).child("g");
        refdone1.setValue(null);
        refdone1 = FirebaseDatabase.getInstance().getReference().child("customerRequest").child(FirebaseAuth.getInstance().getUid()).child("l");
        refdone1.setValue(null);
        refdone1 = FirebaseDatabase.getInstance().getReference().child("employeesWorking").child(emplo_id).child("g");
        refdone1.setValue(null);
        refdone1 = FirebaseDatabase.getInstance().getReference().child("employeesWorking").child(emplo_id).child("l");
        refdone1.setValue(null);
        refdone1 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid()).child(emplo_id);
        refdone1.setValue(null);

        //Toast.makeText(mContext,""+mEmployee_ID,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mContext,Services1Activity.class);
        mContext.startActivity(intent);
    }

    private void delete_chat(final int position) {

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Hires").child(FirebaseAuth.getInstance().getUid());
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    //Toast.makeText(mContext,""+i,Toast.LENGTH_LONG).show();
                    if (i == position) {
                        final String emp_id = ds.getKey().toString();
                        //Toast.makeText(mContext,"Done",Toast.LENGTH_LONG).show();
                        DatabaseReference refdone1 = FirebaseDatabase.getInstance().getReference().child("Chat");
                        refdone1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //int j = 1;
                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                    int j = 0;
                                    String []id = new String[2];
                                    for(DataSnapshot ds1: ds.child("Info").child("Users").getChildren()){
                                        id[j] = ds1.getKey().toString();
                                        j = j + 1;
                                    }
                                    //Toast.makeText(mContext,""+emp_id,Toast.LENGTH_LONG).show();
                                    if(((id[0].contentEquals(FirebaseAuth.getInstance().getUid()))&&(id[1].contentEquals(emp_id))) || ((id[0].contentEquals(emp_id)&&
                                            (id[1].contentEquals(FirebaseAuth.getInstance().getUid())))) ){

                                        //Toast.makeText(mContext,"Done " + j,Toast.LENGTH_LONG).show();
                                        //j = j + 1;

                                        String id1 = ds.child("Info").child("Id").getValue().toString();
                                        DatabaseReference refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(id1).child("Info");
                                        refdone2.setValue(null);
                                        refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(id1).child("messages");
                                        refdone2.setValue(null);
                                        refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Customers").child(FirebaseAuth.getInstance().getUid()).child("Chat").child(id1);
                                        refdone2.setValue(null);
                                        refdone2 = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Employees").child(emp_id).child("Chat").child(id1);
                                        refdone2.setValue(null);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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

    public void updateToken(String token, String name){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        Token token1 = new Token(token);
        //Toast.makeText(getApplicationContext(),""+FirebaseAuth.getInstance().getUid(),Toast.LENGTH_LONG).show();
        reference.child(FirebaseAuth.getInstance().getUid()).setValue(token1);
        sendNotification(FirebaseAuth.getInstance().getUid(),name,"Your Bill is generated ! \n Please check in \"Jobs Complete \" Section");
    }

    private void sendNotification(final String receiver, final String name, final String msg){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference().child("Tokens");
        //Toast.makeText(getApplicationContext(),"Hello 1",Toast.LENGTH_LONG).show();
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
    }

}

