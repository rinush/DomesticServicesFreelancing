package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class ViewProposalActivity extends AppCompatActivity {

    String user_id;
    ListView listview;
    private ArrayList<String> proposal_list, contact_list, date_list, time_list, payment_list, name_list, proposal_list_val, contact_list_val, date_list_val, time_list_val, payment_list_val, name_list_val;
    private ProposalAdapter mAdapter;
    ImageView home, message, feedback, logout;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_proposal);

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        String position = getIntent().getStringExtra("Proposal");
        final int i = Integer.parseInt(position);
        //Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_LONG).show();

        user_id = FirebaseAuth.getInstance().getUid();

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(user_id);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int j =0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                               //Toast.makeText(getApplicationContext(),""+ds.child("FirstName").getValue().toString(),Toast.LENGTH_LONG).show();
                    if (ds.exists()) {
                        if(i == j){
                            value = ds.getKey().toString();
                            break;
                        }
                        else {
                            j = j + 1;
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(), "" + value, Toast.LENGTH_LONG).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services").child(value).child(user_id);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listview = (ListView) findViewById(R.id.listview);
                        proposal_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        name_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        contact_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        payment_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        date_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        time_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                        proposal_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        name_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        contact_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        payment_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        date_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        time_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Toast.makeText(getApplicationContext(),""+ds.child("FirstName").getValue().toString(),Toast.LENGTH_LONG).show();
                            if (ds.exists()) {
                                //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                                String contact = ds.child("Contact").getValue().toString();
                                String firstname = ds.child("FirstName").getValue().toString();
                                String date = ds.child("Date").getValue().toString();
                                String payment = ds.child("Payment").getValue().toString();
                                String proposal = ds.child("Proposal").getValue().toString();
                                String time = ds.child("Time").getValue().toString();
                                proposal_list.add("Proposal: ");
                                proposal_list_val.add(proposal);
                                contact_list.add("Contact: ");
                                contact_list_val.add(contact);
                                date_list.add("Date: ");
                                date_list_val.add(date);
                                time_list.add("Time: ");
                                time_list_val.add(time);
                                payment_list.add("Payment: ");
                                payment_list_val.add(payment);
                                name_list.add("FirstName: ");
                                name_list_val.add(firstname);
                                mAdapter = new ProposalAdapter(ViewProposalActivity.this, proposal_list, proposal_list_val,value,date,time,proposal, name_list, name_list_val, contact_list, contact_list_val, payment_list, payment_list_val, date_list, date_list_val, time_list, time_list_val);
                                listview.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();

                            }

                            else{
                                Toast.makeText(getApplicationContext(),"No Proposal",Toast.LENGTH_LONG).show();
                            }
                        }
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProposalActivity.this,Services1Activity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProposalActivity.this,ChatActivity1.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProposalActivity.this,ReportActivity.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ViewProposalActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();

        //user_id = FirebaseAuth.getInstance().getUid();


    };

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
