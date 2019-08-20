package com.example.rkg09.domesticservicesfreelancing;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class JobsActivity extends AppCompatActivity {

    //ArrayList joblist = new ArrayList();
    String user_id, designation;

    private ListView mListview;
    private ArrayList<String> joblist, joblist1, joblist2, joblist3, joblist4, joblist5;
    private SchoolAdapter mAdapter;
    ImageView home, message, feedback, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        user_id = FirebaseAuth.getInstance().getUid();

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsActivity.this,JobsActivity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsActivity.this,ChatActivity1.class);
                intent.putExtra("Id","Employee");
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobsActivity.this,ReportActivity.class);
                intent.putExtra("Id","Employee");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(JobsActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("Designation");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               designation = dataSnapshot.getValue().toString();
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Jobs").child(designation);
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mListview = (ListView) findViewById(R.id.listview);
                        //int i = 0;
                        joblist = new ArrayList<String>(Arrays.asList(",".split(",")));
                        joblist1 = new ArrayList<String>(Arrays.asList(",".split(",")));
                        joblist2 = new ArrayList<String>(Arrays.asList(",".split(",")));
                        joblist3 = new ArrayList<String>(Arrays.asList(",".split(",")));
                        joblist4 = new ArrayList<String>(Arrays.asList(",".split(",")));
                        joblist5 = new ArrayList<String>(Arrays.asList(",".split(",")));
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                        //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                            if(ds.exists()) {
                                //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                                String address = ds.child("Address").getValue().toString();
                                String firstname = ds.child("First Name").getValue().toString();
                                String jobdescription = ds.child("Job Description").getValue().toString();
                                joblist.add("Job Description: ");
                                joblist3.add(jobdescription);
                                joblist1.add("Address: ");
                                joblist4.add(address);
                                joblist2.add("FirstName: ");
                                joblist5.add(firstname);
                                    mAdapter = new SchoolAdapter(JobsActivity.this, joblist, joblist3, joblist1, joblist4, joblist2, joblist5);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_employees,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;;
        //noinspection SimplifiableIfStatement

        if(id == R.id.jobs_in_progress){
            intent = new Intent(JobsActivity.this, EmployeeJobsProgressActivity.class);
            startActivity(intent);
            //finish();
        }

        else if(id == R.id.jc){
            intent = new Intent(JobsActivity.this, JobsCompleteActivity.class);
            intent.putExtra("Id","Employee");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
