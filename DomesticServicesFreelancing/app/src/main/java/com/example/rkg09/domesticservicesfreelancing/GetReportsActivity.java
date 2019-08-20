package com.example.rkg09.domesticservicesfreelancing;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class GetReportsActivity extends AppCompatActivity {

    private ListView mListview;
    private ArrayList<String> customerlist, name_list, email_list;
    private ReportAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_reports);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Report").child("Customers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListview = (ListView) findViewById(R.id.listview);
                //int i = 0;
                customerlist = new ArrayList<String>(Arrays.asList(",".split(",")));
                name_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                email_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                    if (ds.exists()) {
                        //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                        String name = ds.child("Name").getValue().toString();
                        String email = ds.child("Email").getValue().toString();
                        String feedback = ds.child("FeedBack").getValue().toString();
                        customerlist.add("\nCustomers \n" +"\nFeedBack: \n" + feedback);
                        email_list.add("\nEmail: \n" + email);
                        name_list.add("\nName: \n" + name + "\n");
                        mAdapter = new ReportAdapter(GetReportsActivity.this, customerlist, name_list, email_list);
                        mListview.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "No Customer", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Report").child("Employees");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListview = (ListView) findViewById(R.id.listview);
                //int i = 0;
                //customerlist = new ArrayList<String>(Arrays.asList(",".split(",")));
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                    if (ds.exists()) {
                        //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                        String name = ds.child("Name").getValue().toString();
                        String email = ds.child("Email").getValue().toString();
                        String feedback = ds.child("FeedBack").getValue().toString();
                        customerlist.add("\nEmployees \n" +"\nFeedBack: \n" + feedback);
                        email_list.add("\nEmail: \n" + email);
                        name_list.add("\nName: \n" + name + "\n");
                        mAdapter = new ReportAdapter(GetReportsActivity.this, customerlist, name_list, email_list);
                        mListview.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getApplicationContext(), "No Customer", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
