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

public class JobsInProgressActivity extends AppCompatActivity {

    String user_id, designation;

    private ListView mListview;
    private ArrayList<String> progresslist, jobs, jobs_date, jobs_time, emp_id, jobs_list, payment_list, progresslist_val, jobs_val, jobs_date_val, jobs_time_val, emp_id_val, jobs_list_val, payment_list_val;
    private ProgressAdapter mAdapter;
    ImageView home, message, feedback, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_in_progress);

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        user_id = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Hires").child(user_id);
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mListview = (ListView) findViewById(R.id.listview);
                        //int i = 0;
                        progresslist = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs_date = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs_time = new ArrayList<String>(Arrays.asList(",".split(",")));
                        emp_id = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        payment_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        progresslist_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs_date_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs_time_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        emp_id_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        jobs_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        payment_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                            if(ds.exists()) {
                                //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                                String employee_id = ds.getKey().toString();
                                emp_id.add(employee_id);
                                String date = ds.child("Date").getValue().toString();
                                String job = ds.child("Job").getValue().toString();
                                String jobdescription = ds.child("Job Description").getValue().toString();
                                String time = ds.child("Time").getValue().toString();
                                String payment = ds.child("Payment").getValue().toString();
                                progresslist.add("Job Description: ");
                                progresslist_val.add(jobdescription);
                                jobs.add("Job: ");
                                jobs_val.add(job);
                                jobs_list.add(job);
                                jobs_date.add("Date: ");
                                jobs_date_val.add(date);
                                jobs_time.add("Time: ");
                                jobs_time_val.add(time);
                                payment_list.add("Payment: ");
                                payment_list_val.add(payment);
                                mAdapter = new ProgressAdapter(JobsInProgressActivity.this,jobs_list,emp_id, progresslist, progresslist_val,date,job,jobdescription,time,employee_id, jobs, jobs_val, jobs_date, jobs_date_val, jobs_time, jobs_time_val, payment_list, payment_list_val);
                                mListview.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();

                            }

                            else{
                                Toast.makeText(getApplicationContext(),"No Job Posted",Toast.LENGTH_LONG).show();
                            }
                        }



                        // Set some data to array list

                        // Initialize adapter and set adapter to list view


                        //ArrayAdapter adapter = new ArrayAdapter<String>(JobsActivity.this,
                        //R.layout.jobs_list, joblist);
                        //ListView listView = (ListView) findViewById(R.id.listview);
                        //listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsInProgressActivity.this,Services1Activity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsInProgressActivity.this,ChatActivity1.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsInProgressActivity.this,ReportActivity.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(JobsInProgressActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
