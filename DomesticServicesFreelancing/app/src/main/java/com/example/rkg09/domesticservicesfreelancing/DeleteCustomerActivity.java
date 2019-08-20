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

public class DeleteCustomerActivity extends AppCompatActivity {
    private ListView mListview;
    private ArrayList<String> customerlist, address_list, contact_list, email_list, password_list, designation_list;
    private DeleteCustomerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_customer);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListview = (ListView) findViewById(R.id.listview);
                //int i = 0;
                customerlist = new ArrayList<String>(Arrays.asList(",".split(",")));
                address_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                contact_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                email_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                password_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                designation_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Toast.makeText(getApplicationContext(),""+ds.child("Address").getValue().toString(),Toast.LENGTH_LONG).show();
                    if (ds.exists()) {
                        //Toast.makeText(getApplicationContext(), ""+joblist, Toast.LENGTH_SHORT).show();
                        String address = ds.child("Address").getValue().toString();
                        String contact = ds.child("Contact").getValue().toString();
                        String designation = ds.child("Designation").getValue().toString();
                        String email = ds.child("Email").getValue().toString();
                        String firstname = ds.child("FirstName").getValue().toString();
                        String lastname = ds.child("LastName").getValue().toString();
                        String password = ds.child("Password").getValue().toString();
                        customerlist.add("\nName: \n" + firstname + " " +  lastname);
                        address_list.add("\nAddress: \n" + address);
                        contact_list.add("\nContact: \n " + contact);
                        designation_list.add("\nDesignation: \n " + designation);
                        email_list.add("\nEmail: \n" + email);
                        password_list.add("\nPassword: \n" + password + "\n");
                        mAdapter = new DeleteCustomerAdapter(DeleteCustomerActivity.this, customerlist,address_list,contact_list,designation_list,email_list,password_list);
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
