package com.example.rkg09.domesticservicesfreelancing;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class GroceryActivity extends AppCompatActivity {
RadioButton vegetable, fruit, milk_products, snacks, drinks;
    ListView mListview;
    private ArrayList<String> employee_fn, employee_ln, address_list, contact_list, designation_list, employee_fn_val, employee_ln_val, address_list_val, contact_list_val, designation_list_val;
    private GroceryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        vegetable = (RadioButton) findViewById(R.id.vegetables);
        fruit = (RadioButton) findViewById(R.id.fruits);
        milk_products = (RadioButton) findViewById(R.id.milk_products);
        snacks = (RadioButton) findViewById(R.id.snacks);
        drinks = (RadioButton) findViewById(R.id.drinks);

        vegetable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true) {
                 setContentView(R.layout.grocery_list);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mListview = (ListView) findViewById(R.id.listview);
                            //int i = 0;
                            employee_fn = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_fn_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            for(final DataSnapshot ds: dataSnapshot.getChildren()){
                               final String designation = ds.child("Designation").getValue().toString();
                                //Toast.makeText(getApplicationContext(),""+designation,Toast.LENGTH_LONG).show();
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     if(dataSnapshot.exists()){
                                         String area = dataSnapshot.child("Area").getValue().toString().toUpperCase();
                                         if((designation.contentEquals("Vegetable Vendor") && (area.contentEquals(ds.child("Area").getValue().toString().toUpperCase())))){
                                             String fname = ds.child("FirstName").getValue().toString();
                                             String lname = ds.child("LastName").getValue().toString();
                                             String address = ds.child("Address").getValue().toString();
                                             String contact = ds.child("Contact").getValue().toString();
                                             String designation1 = ds.child("Designation").getValue().toString();
                                             employee_fn.add("Name: ");
                                             employee_fn_val.add(fname + " " + lname);
                                             designation_list.add("Designation: ");
                                             designation_list_val.add(designation1);
                                             contact_list.add("Contact: ");
                                             contact_list_val.add(contact);
                                             address_list.add("Address: ");
                                             address_list_val.add(address);
                                             mAdapter = new GroceryAdapter(GroceryActivity.this, employee_fn, employee_fn_val,designation_list, designation_list_val,address_list, address_list_val,contact_list, contact_list_val);
                                             mListview.setAdapter(mAdapter);
                                             mAdapter.notifyDataSetChanged();
                                         }
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
        });

        fruit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true) {
                    setContentView(R.layout.grocery_list);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mListview = (ListView) findViewById(R.id.listview);
                            //int i = 0;
                            employee_fn = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_fn_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            for(final DataSnapshot ds: dataSnapshot.getChildren()){
                                final String designation = ds.child("Designation").getValue().toString();
                                //Toast.makeText(getApplicationContext(),""+designation,Toast.LENGTH_LONG).show();
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            String area = dataSnapshot.child("Area").getValue().toString().toUpperCase();
                                            if((designation.contentEquals("Fruit Vendor") && (area.contentEquals(ds.child("Area").getValue().toString().toUpperCase())))){
                                                String fname = ds.child("FirstName").getValue().toString();
                                                String lname = ds.child("LastName").getValue().toString();
                                                String address = ds.child("Address").getValue().toString();
                                                String contact = ds.child("Contact").getValue().toString();
                                                String designation1 = ds.child("Designation").getValue().toString();
                                                employee_fn.add("Name: ");
                                                employee_fn_val.add(fname + " " + lname);
                                                designation_list.add("Designation: ");
                                                designation_list_val.add(designation1);
                                                address_list.add("Contact: ");
                                                address_list_val.add(contact);
                                                contact_list.add("Address: ");
                                                contact_list_val.add(address);
                                                mAdapter = new GroceryAdapter(GroceryActivity.this, employee_fn, employee_fn_val,designation_list, designation_list_val,address_list, address_list_val,contact_list, address_list_val);
                                                mListview.setAdapter(mAdapter);
                                                mAdapter.notifyDataSetChanged();
                                            }
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
            });

        milk_products.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true) {
                    setContentView(R.layout.grocery_list);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mListview = (ListView) findViewById(R.id.listview);
                            //int i = 0;
                            employee_fn = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_fn_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            for(final DataSnapshot ds: dataSnapshot.getChildren()){
                                final String designation = ds.child("Designation").getValue().toString();
                                //Toast.makeText(getApplicationContext(),""+designation,Toast.LENGTH_LONG).show();
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            String area = dataSnapshot.child("Area").getValue().toString().toUpperCase();
                                            if((designation.contentEquals("Milk Man") && (area.contentEquals(ds.child("Area").getValue().toString().toUpperCase())))){
                                                String fname = ds.child("FirstName").getValue().toString();
                                                String lname = ds.child("LastName").getValue().toString();
                                                String address = ds.child("Address").getValue().toString();
                                                String contact = ds.child("Contact").getValue().toString();
                                                String designation1 = ds.child("Designation").getValue().toString();
                                                employee_fn.add("Name: ");
                                                employee_fn_val.add(fname + " " + lname);
                                                designation_list.add("Designation: ");
                                                designation_list_val.add(designation1);
                                                address_list.add("Contact: ");
                                                address_list_val.add(contact);
                                                contact_list.add("Address: ");
                                                contact_list_val.add(address);
                                                mAdapter = new GroceryAdapter(GroceryActivity.this, employee_fn, employee_fn_val,designation_list, designation_list_val,address_list, address_list_val,contact_list, address_list_val);
                                                mListview.setAdapter(mAdapter);
                                                mAdapter.notifyDataSetChanged();
                                            }
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
        });

        snacks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true) {
                    setContentView(R.layout.grocery_list);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mListview = (ListView) findViewById(R.id.listview);
                            //int i = 0;
                            employee_fn = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_fn_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            for(final DataSnapshot ds: dataSnapshot.getChildren()){
                                final String designation = ds.child("Designation").getValue().toString();
                                //Toast.makeText(getApplicationContext(),""+designation,Toast.LENGTH_LONG).show();
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            String area = dataSnapshot.child("Area").getValue().toString().toUpperCase();
                                            if((designation.contentEquals("Snacks Seller") && (area.contentEquals(ds.child("Area").getValue().toString().toUpperCase())))){
                                                String fname = ds.child("FirstName").getValue().toString();
                                                String lname = ds.child("LastName").getValue().toString();
                                                String address = ds.child("Address").getValue().toString();
                                                String contact = ds.child("Contact").getValue().toString();
                                                String designation1 = ds.child("Designation").getValue().toString();
                                                employee_fn.add("Name: ");
                                                employee_fn_val.add(fname + " " + lname);
                                                designation_list.add("Designation: ");
                                                designation_list_val.add(designation1);
                                                address_list.add("Contact: ");
                                                address_list_val.add(contact);
                                                contact_list.add("Address: ");
                                                contact_list_val.add(address);
                                                mAdapter = new GroceryAdapter(GroceryActivity.this, employee_fn, employee_fn_val,designation_list, designation_list_val,address_list, address_list_val,contact_list, address_list_val);
                                                mListview.setAdapter(mAdapter);
                                                mAdapter.notifyDataSetChanged();
                                            }
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
        });

        drinks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true) {
                    setContentView(R.layout.grocery_list);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mListview = (ListView) findViewById(R.id.listview);
                            //int i = 0;
                            employee_fn = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_fn_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            employee_ln_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            address_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            contact_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            designation_list_val = new ArrayList<String>(Arrays.asList(",".split(",")));
                            for(final DataSnapshot ds: dataSnapshot.getChildren()){
                                final String designation = ds.child("Designation").getValue().toString();
                                //Toast.makeText(getApplicationContext(),""+designation,Toast.LENGTH_LONG).show();
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            String area = dataSnapshot.child("Area").getValue().toString().toUpperCase();
                                            if((designation.contentEquals("Drinks Seller") && (area.contentEquals(ds.child("Area").getValue().toString().toUpperCase())))){
                                                String fname = ds.child("FirstName").getValue().toString();
                                                String lname = ds.child("LastName").getValue().toString();
                                                String address = ds.child("Address").getValue().toString();
                                                String contact = ds.child("Contact").getValue().toString();
                                                String designation1 = ds.child("Designation").getValue().toString();
                                                employee_fn.add("Name: ");
                                                employee_fn_val.add(fname + " " + lname);
                                                designation_list.add("Designation: ");
                                                designation_list_val.add(designation1);
                                                address_list.add("Contact: ");
                                                address_list_val.add(contact);
                                                contact_list.add("Address: ");
                                                contact_list_val.add(address);
                                                mAdapter = new GroceryAdapter(GroceryActivity.this, employee_fn, employee_fn_val,designation_list, designation_list_val,address_list, address_list_val,contact_list, address_list_val);
                                                mListview.setAdapter(mAdapter);
                                                mAdapter.notifyDataSetChanged();
                                            }
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
        });

    }
}
