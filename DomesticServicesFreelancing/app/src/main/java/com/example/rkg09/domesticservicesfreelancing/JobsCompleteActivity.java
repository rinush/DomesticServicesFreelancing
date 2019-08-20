package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class JobsCompleteActivity extends AppCompatActivity {
    private ListView mListview;
    private ArrayList<String> joblist, date_list, time_list, jobs, customer_list, employee_list, payment_list, joblist_val, date_list_val, time_list_val, jobs_val, customer_list_val, employee_list_val, payment_list_val;
    private JobsCompleteAdapter mAdapter;
    private Customer_Employee_JobsCompleteAdapter mAdapter1;
    ImageView home, message, feedback, logout;
    //String efname, elname, cfname, clname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_complete);

        String id = getIntent().getStringExtra("Id");
        //Toast.makeText(getApplicationContext(),""+id,Toast.LENGTH_LONG).show();

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        if(id.contentEquals("Admin")) {

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,AdminActivity.class);
                    //Toast.makeText(getApplicationContext(),"Customer",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,AdminActivity.class);
                    intent.putExtra("Id","Admin");
                    startActivity(intent);
                }
            });

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,AdminActivity.class);
                    intent.putExtra("Id","Admin");
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(JobsCompleteActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Jobs Complete");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mListview = (ListView) findViewById(R.id.listview);
                    //int i = 0;
                    joblist = new ArrayList<String>(Arrays.asList(",".split(",")));
                    date_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    time_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    jobs = new ArrayList<String>(Arrays.asList(",".split(",")));
                    customer_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    employee_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    payment_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    joblist_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    date_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    time_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    jobs_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    customer_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    employee_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    payment_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                        if (ds.exists()) {
                            String customer_id = ds.getKey().toString();
                            for (final DataSnapshot ds1 : ds.getChildren()) {
                                if (ds1.exists()) {

                                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id);
                                    ref1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                String employee_id = ds1.getKey().toString();
                                                //Toast.makeText(getApplicationContext(),""+employee_id,Toast.LENGTH_LONG).show();
                                                 final String cfname = dataSnapshot.child("FirstName").getValue().toString();
                                                 final String clname = dataSnapshot.child("LastName").getValue().toString();
                                                //Toast.makeText(getApplicationContext(),""+cfname+clname,Toast.LENGTH_LONG).show();

                                                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(employee_id);
                                                ref2.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            String efname = dataSnapshot.child("FirstName").getValue().toString();
                                                            String elname = dataSnapshot.child("LastName").getValue().toString();
                                                            String customername = cfname + " " + clname;
                                                            String employeename = efname + " " + elname;
                                                            Toast.makeText(getApplicationContext(),""+employeename,Toast.LENGTH_LONG).show();
                                                            String date = ds1.child("Date").getValue().toString();
                                                            String time = ds1.child("Time").getValue().toString();
                                                            String job = ds1.child("Job").getValue().toString();
                                                            String job_description = ds1.child("Job Description").getValue().toString();
                                                            String payment = ds1.child("Payment").getValue().toString();
                                                            joblist.add("Job description: ");
                                                            joblist_val.add(job_description);
                                                            customer_list.add("Customer Name: ");
                                                            customer_list_val.add(customername);
                                                            employee_list.add("Employee Name: ");
                                                            employee_list_val.add(employeename);
                                                            date_list.add("Date: ");
                                                            date_list_val.add(date);
                                                            time_list.add("Time: ");
                                                            time_list.add(time);
                                                            jobs.add("Job: ");
                                                            jobs_val.add(job);
                                                            payment_list.add("Payment: ");
                                                            payment_list_val.add(payment);
                                                            Toast.makeText(getApplicationContext(),"Notified",Toast.LENGTH_LONG).show();
                                                            mAdapter = new JobsCompleteAdapter(JobsCompleteActivity.this, joblist, joblist_val, jobs, jobs_val, customer_list, customer_list_val,employee_list, employee_list_val, date_list, date_list_val, time_list, time_list_val, payment_list, payment_list_val);
                                                            mListview.setAdapter(mAdapter);
                                                            mAdapter.notifyDataSetChanged();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
                        } else {
                            Toast.makeText(getApplicationContext(), "No Jobs Completed", Toast.LENGTH_LONG).show();
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(id.contentEquals("Customer")){

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,Services1Activity.class);
                    //Toast.makeText(getApplicationContext(),"Customer",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,ChatActivity1.class);
                    intent.putExtra("Id","Customer");
                    startActivity(intent);
                }
            });

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,ReportActivity.class);
                    intent.putExtra("Id","Customer");
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(JobsCompleteActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Jobs Complete").child(FirebaseAuth.getInstance().getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mListview = (ListView) findViewById(R.id.listview);
                    //int i = 0;
                    joblist = new ArrayList<String>(Arrays.asList(",".split(",")));
                    date_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    time_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    jobs = new ArrayList<String>(Arrays.asList(",".split(",")));
                    payment_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    joblist_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    date_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    time_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    jobs_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    payment_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                        if (ds.exists()) {
                            String date = ds.child("Date").getValue().toString();
                            String time = ds.child("Time").getValue().toString();
                            String job = ds.child("Job").getValue().toString();
                            String job_description = ds.child("Job Description").getValue().toString();
                            String payment = ds.child("Payment").getValue().toString();
                            joblist.add("Job description: ");
                            joblist_val.add(job_description);
                            date_list.add("Date: ");
                            date_list_val.add(date);
                            time_list.add("Time: ");
                            time_list_val.add(time);
                            jobs.add("Job: ");
                            jobs_val.add(job);
                            payment_list.add("Payment :");
                            payment_list_val.add(payment);
                            mAdapter1 = new Customer_Employee_JobsCompleteAdapter(JobsCompleteActivity.this, joblist, joblist_val, jobs, jobs_val, date_list, date_list_val, time_list, time_list_val, payment_list, payment_list_val);
                            mListview.setAdapter(mAdapter1);
                            mAdapter1.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }});
        }

        else if(id.contentEquals("Employee")){

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,JobsActivity.class);
                    startActivity(intent);
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,ChatActivity1.class);
                    intent.putExtra("Id","Employee");
                    startActivity(intent);
                }
            });

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JobsCompleteActivity.this,ReportActivity.class);
                    intent.putExtra("Id","Employee");
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(JobsCompleteActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Jobs Completed").child(FirebaseAuth.getInstance().getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mListview = (ListView) findViewById(R.id.listview);
                    //int i = 0;
                    joblist = new ArrayList<String>(Arrays.asList(",".split(",")));
                    date_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    time_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    jobs = new ArrayList<String>(Arrays.asList(",".split(",")));
                    payment_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                    joblist_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    date_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    time_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    jobs_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    payment_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                        if (ds.exists()) {
                            String date = ds.child("Date").getValue().toString();
                            String time = ds.child("Time").getValue().toString();
                            String job = ds.child("Job").getValue().toString();
                            String job_description = ds.child("Job Description").getValue().toString();
                            String payment = ds.child("Payment").getValue().toString();
                            joblist.add("Job description: ");
                            joblist_val.add(job_description);
                            date_list.add("Date: ");
                            date_list_val.add(date);
                            time_list.add("Time: ");
                            time_list_val.add(time);
                            jobs.add("Job: ");
                            jobs_val.add(job);
                            payment_list.add("Payment :");
                            payment_list_val.add(payment);
                            mAdapter1 = new Customer_Employee_JobsCompleteAdapter(JobsCompleteActivity.this, joblist, joblist_val, jobs, jobs_val, date_list, date_list_val, time_list, time_list_val, payment_list, payment_list_val);
                            mListview.setAdapter(mAdapter1);
                            mAdapter1.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }});
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

