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

public class JobsPostedActivity extends AppCompatActivity {

    String user_id;
    ListView listview;
    private ArrayList<String> jobs_posted_list, jobs_desc, jobs_date, jobs_time, payment_list, jobs_posted_list_val, jobs_desc_val, jobs_date_val, jobs_time_val, payment_list_val;
    private JobsAdapter mAdapter;
    ImageView home, message, feedback, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_posted);

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);
        user_id = FirebaseAuth.getInstance().getUid();
        //Toast.makeText(getApplicationContext(),"Check 1 "+user_id,Toast.LENGTH_LONG).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(user_id);
        //Toast.makeText(getApplicationContext(),""+ref,Toast.LENGTH_LONG).show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listview = (ListView) findViewById(R.id.listview);
                jobs_posted_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs_desc = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs_date = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs_time = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs_posted_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs_desc_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs_date_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs_time_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                //Toast.makeText(getApplicationContext(),"Check 21",Toast.LENGTH_LONG).show();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(),""+ds.child("FirstName").getValue().toString(),Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),"Check 2",Toast.LENGTH_LONG).show();
                    if (ds.exists()) {
                        //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),""+ds,Toast.LENGTH_LONG).show();
                        String job_name = ds.getKey().toString();
                        String job_description = ds.child("Job Description").getValue().toString();
                        String date = ds.child("Date").getValue().toString();
                        String time = ds.child("Time").getValue().toString();
                        //Toast.makeText(getApplicationContext(),"" + date + time,Toast.LENGTH_LONG).show();
                        jobs_desc.add("Job Description: ");
                        jobs_desc_val.add(job_description);
                        jobs_posted_list.add("Job: ");
                        jobs_posted_list_val.add(job_name);
                        jobs_date.add("Date: ");
                        jobs_date_val.add(date);
                        jobs_time.add("Time: ");
                        jobs_time_val.add(time + "\n");
                        mAdapter = new JobsAdapter(JobsPostedActivity.this, jobs_desc, jobs_desc_val, jobs_posted_list, jobs_posted_list_val, jobs_date, jobs_date_val, jobs_time, jobs_time_val);
                        listview.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }

                    else{
                        Toast.makeText(getApplicationContext(),"You have not posted job required",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsPostedActivity.this,Services1Activity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsPostedActivity.this,ChatActivity1.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsPostedActivity.this,ReportActivity.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(JobsPostedActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
