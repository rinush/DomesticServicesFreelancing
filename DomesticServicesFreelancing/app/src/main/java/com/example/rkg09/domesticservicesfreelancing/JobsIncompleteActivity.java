package com.example.rkg09.domesticservicesfreelancing;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class JobsIncompleteActivity extends AppCompatActivity {
    private ListView mListview;
    private ArrayList<String> joblist, date_list, time_list, customer_name, jobs;
    private JobsIncompleteAdapter mAdapter;
    String efname, elname, cfname, clname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_incomplete);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Jobs Posted");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListview = (ListView) findViewById(R.id.listview);
                //int i = 0;
                joblist = new ArrayList<String>(Arrays.asList(",".split(",")));
                date_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                time_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                customer_name = new ArrayList<String>(Arrays.asList(",".split(",")));
                jobs = new ArrayList<String>(Arrays.asList(",".split(",")));
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                    if(ds.exists()) {
                        String customer_id = ds.getKey().toString();
                        for (final DataSnapshot ds1 : ds.getChildren()){
                            if(ds1.exists()) {

                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id);
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            String job = ds1.getKey().toString();
                                            cfname = dataSnapshot.child("FirstName").getValue().toString();
                                            clname = dataSnapshot.child("LastName").getValue().toString();
                                            String customername = cfname+" "+clname;
                                            String date = ds1.child("Date").getValue().toString();
                                            String time = ds1.child("Time").getValue().toString();
                                            String job_description = ds1.child("Job Description").getValue().toString();
                                            joblist.add("\nJob description \n" + job_description);
                                            customer_name.add("\nCustomer Name: \n" + customername);
                                            date_list.add("\nDate: \n" + date);
                                            time_list.add("\nTime \n" + time);
                                            jobs.add("\nJob \n" + job + "\n");
                                            mAdapter = new JobsIncompleteAdapter(JobsIncompleteActivity.this, joblist, jobs, customer_name, date_list, time_list);
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
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No Jobs Completed",Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
