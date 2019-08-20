package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rkg09.domesticservicesfreelancing.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatActivity1 extends AppCompatActivity {

    private ListView mListview;
    private ArrayList<String> chatlist, key, id, fname, lname;
    private ChatListAdapter1 mAdapter;
    ImageView home, message, feedback, logout;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat1);

        String user = getIntent().getStringExtra("Id");

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        if(user.contentEquals("Customer")) {

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatActivity1.this,Services1Activity.class);
                    startActivity(intent);
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatActivity1.this,ChatActivity1.class);
                    intent.putExtra("Id","Customer");
                    startActivity(intent);
                }
            });

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatActivity1.this,ReportActivity.class);
                    intent.putExtra("Id","Customer");
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(ChatActivity1.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            user_id = FirebaseAuth.getInstance().getUid();
            updateToken(FirebaseInstanceId.getInstance().getToken());


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Customers").child(FirebaseAuth.getInstance().getUid()).child("Chat");
            key = new ArrayList<String>();
            id = new ArrayList<String>();
            fname = new ArrayList<String>();
            lname = new ArrayList<String>();
            mListview = (ListView) findViewById(R.id.listview);
            //int i = 0;
            chatlist = new ArrayList<String>(Arrays.asList(",".split(",")));
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.exists()) {
                            key.add(ds.getKey().toString());
                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Chat").child(key.get(i));
                            //Toast.makeText(getApplicationContext(),""+key.get(0),Toast.LENGTH_LONG).show();
                            final int finalI = i;
                            ref1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        if (key.get(finalI).contentEquals(dataSnapshot.getKey().toString())) {
                                            //Toast.makeText(getApplicationContext(), "" + key.get(finalI), Toast.LENGTH_LONG).show();
                                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(key.get(finalI)).child("Info").child("Users");
                                            ref2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                        id.add(ds.getKey().toString());
                                                    }
                                                    if (!(id.get(0 + (finalI * 2)).contentEquals(FirebaseAuth.getInstance().getUid()))) {
                                                        //Toast.makeText(getApplicationContext(), "" + finalI, Toast.LENGTH_LONG).show();
                                                        String id2 = id.get(0);
                                                        //Toast.makeText(getApplicationContext(), "" + id.get(0 + (finalI * 2)), Toast.LENGTH_LONG).show();
                                                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(id.get(0 + (finalI * 2))).child("FirstName");
                                                        ref3.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    fname.add(dataSnapshot.getValue().toString());
                                                                    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(id.get(0 + (finalI * 2))).child("LastName");

                                                                    ref4.addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()) {
                                                                                lname.add(dataSnapshot.getValue().toString());
                                                                                chatlist.add(fname.get(finalI) + " " + lname.get(finalI));
                                                                                mAdapter = new ChatListAdapter1(ChatActivity1.this, chatlist, key,"Customers");
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
                                                    } else {
                                                        String id2 = id.get(1);
                                                        //Toast.makeText(getApplicationContext(), "" + id.get(1 + (finalI * 2)), Toast.LENGTH_LONG).show();
                                                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(id.get(1 + (finalI * 2))).child("FirstName");
                                                        ref3.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    fname.add(dataSnapshot.getValue().toString());
                                                                    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(id.get(1 + (finalI * 2))).child("LastName");
                                                                    mListview = (ListView) findViewById(R.id.listview);
                                                                    //int i = 0;
                                                                    chatlist = new ArrayList<String>(Arrays.asList(",".split(",")));
                                                                    ref4.addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()) {
                                                                                lname.add(dataSnapshot.getValue().toString());
                                                                                chatlist.add(fname.get(finalI) + " " + lname.get(finalI));
                                                                                mAdapter = new ChatListAdapter1(ChatActivity1.this, chatlist, key,"Customers");
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

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            });
                            i = i + 1;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
        }

        else if(user.contentEquals("Employee")){

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatActivity1.this,JobsActivity.class);
                    startActivity(intent);
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatActivity1.this,ChatActivity1.class);
                    intent.putExtra("Id","Employee");
                    startActivity(intent);
                }
            });

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatActivity1.this,ReportActivity.class);
                    intent.putExtra("Id","Employee");
                    startActivity(intent);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(ChatActivity1.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            user_id = FirebaseAuth.getInstance().getUid();
            updateToken(FirebaseInstanceId.getInstance().getToken());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chat_Users").child("Employees").child(FirebaseAuth.getInstance().getUid()).child("Chat");
            key = new ArrayList<String>();
            id = new ArrayList<String>();
            fname = new ArrayList<String>();
            lname = new ArrayList<String>();
            mListview = (ListView) findViewById(R.id.listview);
            //int i = 0;
            chatlist = new ArrayList<String>(Arrays.asList(",".split(",")));
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.exists()) {
                            key.add(ds.getKey().toString());
                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Chat").child(key.get(i));
                            //Toast.makeText(getApplicationContext(),""+key.get(0),Toast.LENGTH_LONG).show();
                            final int finalI = i;
                            ref1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        if (key.get(finalI).contentEquals(dataSnapshot.getKey().toString())) {
                                            //Toast.makeText(getApplicationContext(), "" + key.get(finalI), Toast.LENGTH_LONG).show();
                                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Chat").child(key.get(finalI)).child("Info").child("Users");
                                            ref2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                        id.add(ds.getKey().toString());
                                                    }
                                                    if (!(id.get(0 + (finalI * 2)).contentEquals(FirebaseAuth.getInstance().getUid()))) {
                                                        //Toast.makeText(getApplicationContext(), "" + finalI, Toast.LENGTH_LONG).show();
                                                        String id2 = id.get(0);
                                                        //Toast.makeText(getApplicationContext(), "" + id.get(0 + (finalI * 2)), Toast.LENGTH_LONG).show();
                                                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(id.get(0 + (finalI * 2))).child("FirstName");
                                                        ref3.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    fname.add(dataSnapshot.getValue().toString());
                                                                    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(id.get(0 + (finalI * 2))).child("LastName");

                                                                    ref4.addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()) {
                                                                                lname.add(dataSnapshot.getValue().toString());
                                                                                chatlist.add(fname.get(finalI) + " " + lname.get(finalI));
                                                                                mAdapter = new ChatListAdapter1(ChatActivity1.this, chatlist, key,"Employees");
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
                                                    } else {
                                                        String id2 = id.get(1);
                                                        //Toast.makeText(getApplicationContext(), "" + id.get(1 + (finalI * 2)), Toast.LENGTH_LONG).show();
                                                        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(id.get(1 + (finalI * 2))).child("FirstName");
                                                        ref3.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    fname.add(dataSnapshot.getValue().toString());
                                                                    DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(id.get(1 + (finalI * 2))).child("LastName");
                                                                    mListview = (ListView) findViewById(R.id.listview);
                                                                    //int i = 0;
                                                                    chatlist = new ArrayList<String>(Arrays.asList(",".split(",")));
                                                                    ref4.addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()) {
                                                                                lname.add(dataSnapshot.getValue().toString());
                                                                                chatlist.add(fname.get(finalI) + " " + lname.get(finalI));
                                                                                mAdapter = new ChatListAdapter1(ChatActivity1.this, chatlist,key,"Employees");
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

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            });

                            i = i + 1;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }

   private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        Token token1 = new Token(token);
        reference.child(FirebaseAuth.getInstance().getUid()).setValue(token1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
