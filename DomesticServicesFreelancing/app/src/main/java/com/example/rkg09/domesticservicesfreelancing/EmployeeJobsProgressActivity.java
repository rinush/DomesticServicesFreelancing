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

public class EmployeeJobsProgressActivity extends AppCompatActivity {
public ListView mListView;
    ImageView home, message, feedback, logout;
String user_id;
    private ArrayList<String> employeeprogresslist, job_list, date_list, time_list, payment_list, employeeprogresslist_val, job_list_val, date_list_val, time_list_val, payment_list_val;
    private EmployeeJobsProgressAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_jobs_progress);

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeJobsProgressActivity.this,JobsActivity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeJobsProgressActivity.this,ChatActivity1.class);
                intent.putExtra("Id","Employee");
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeJobsProgressActivity.this,ReportActivity.class);
                intent.putExtra("Id","Employee");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EmployeeJobsProgressActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        user_id = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Hired Employee").child(user_id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListView = (ListView) findViewById(R.id.listview);
                employeeprogresslist = new ArrayList<String>(Arrays.asList(",".split(",")));
                job_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                date_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                time_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                payment_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                employeeprogresslist_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                job_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                date_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                time_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                payment_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                    if(ds.exists()) {
                        //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                        String date = ds.child("Date").getValue().toString();
                        String job = ds.child("Job").getValue().toString();
                        String jobdescription = ds.child("Job Description").getValue().toString();
                        String time = ds.child("Time").getValue().toString();
                        String payment = ds.child("Payment").getValue().toString();
                        employeeprogresslist.add("Job Description: ");
                        employeeprogresslist_val.add(jobdescription);
                        job_list.add("Job: ");
                        job_list_val.add(job);
                        date_list.add("Date: ");
                        date_list_val.add(date);
                        time_list.add("Time: ");
                        time_list_val.add(time);
                        payment_list.add("Payment: ");
                        payment_list_val.add(payment);
                        mAdapter = new EmployeeJobsProgressAdapter(EmployeeJobsProgressActivity.this, employeeprogresslist, employeeprogresslist_val, job_list, job_list_val, date_list, date_list_val, time_list, time_list_val, payment_list, payment_list_val);
                        mListView.setAdapter(mAdapter);
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
            }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

