package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.rkg09.domesticservicesfreelancing.Notifications.Client;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Data;
import com.example.rkg09.domesticservicesfreelancing.Notifications.MyResponse;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Sender;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplianceRepairActivity extends AppCompatActivity {
RadioButton acrepair, tvrepair, refrigeratorrepair, microwaverepair, computerrepair;
    Button submit;
    EditText service;
    private FirebaseAuth mAuth;
    String job_required;
    String user_id, firstname,address, date, time ;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_repair);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        acrepair=(RadioButton)findViewById(R.id.acrepair);
        tvrepair=(RadioButton)findViewById(R.id.tvrepair);
        refrigeratorrepair=(RadioButton)findViewById(R.id.refrigeratorrepair);
        microwaverepair=(RadioButton)findViewById(R.id.microwaverepair);
        computerrepair=(RadioButton)findViewById(R.id.computerrepair);

        Date dates = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        date = df.format(dates.getTime()).toString();
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        time = df1.format(dates.getTime()).toString();

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference customer = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("FirstName");
        customer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    firstname = dataSnapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference customer1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Address");
        customer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    address = dataSnapshot.getValue(String.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAuth=FirebaseAuth.getInstance();

        acrepair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                //handle the boolean flag here.
                if(arg1==true){
                    //Toast.makeText(getApplicationContext(),"Plumber Selected",Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.acrepair);
                    submit = (Button)findViewById(R.id.button);
                    service = (EditText)findViewById(R.id.job);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            job_required = service.getText().toString();
                            if(job_required.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Please Describe Your Job",Toast.LENGTH_LONG).show();
                            }
                            else {
                                DatabaseReference current_user_db;
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("AC Repair").child(FirebaseAuth.getInstance().getUid()).child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("AC Repair").child(FirebaseAuth.getInstance().getUid()).child("First Name");
                                current_user_db.setValue(firstname);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("AC Repair").child(FirebaseAuth.getInstance().getUid()).child("Address");
                                current_user_db.setValue(address);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("AC Repair").child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("AC Repair").child("Date");
                                current_user_db.setValue(date);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("AC Repair").child("Time");
                                current_user_db.setValue(time);
                                Toast.makeText(getApplicationContext(), "Job Posted", Toast.LENGTH_SHORT).show();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String fn = dataSnapshot.child("FirstName").getValue().toString();
                                        String ln = dataSnapshot.child("LastName").getValue().toString();
                                        updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln);
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
            //do something else
        });

        tvrepair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                //handle the boolean flag here.
                if(arg1==true){
                    setContentView(R.layout.tvrepair);
                    submit = (Button)findViewById(R.id.button);
                    service = (EditText)findViewById(R.id.job);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            job_required = service.getText().toString();
                            if(job_required.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Please Describe Your Job",Toast.LENGTH_LONG).show();
                            }
                            else {
                                DatabaseReference current_user_db;
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("TV Repair").child(FirebaseAuth.getInstance().getUid()).child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("TV Repair").child(FirebaseAuth.getInstance().getUid()).child("First Name");
                                current_user_db.setValue(firstname);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("TV Repair").child(FirebaseAuth.getInstance().getUid()).child("Address");
                                current_user_db.setValue(address);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("TV Repair").child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("TV Repair").child("Date");
                                current_user_db.setValue(date);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("TV Repair").child("Time");
                                current_user_db.setValue(time);
                                Toast.makeText(getApplicationContext(), "Job Posted", Toast.LENGTH_SHORT).show();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String fn = dataSnapshot.child("FirstName").getValue().toString();
                                        String ln = dataSnapshot.child("LastName").getValue().toString();
                                        updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln);
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
        });

        refrigeratorrepair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                //handle the boolean flag here.
                if(arg1==true){
                    setContentView(R.layout.refrigeratorrepair);
                    submit = (Button)findViewById(R.id.button);
                    service = (EditText)findViewById(R.id.job);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            job_required = service.getText().toString();
                            if(job_required.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Please Describe Your Job",Toast.LENGTH_LONG).show();
                            }
                            else {
                                DatabaseReference current_user_db;
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Refrigerator Repair").child(FirebaseAuth.getInstance().getUid()).child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Refrigerator Repair").child(FirebaseAuth.getInstance().getUid()).child("First Name");
                                current_user_db.setValue(firstname);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Refrigerator Repair").child(FirebaseAuth.getInstance().getUid()).child("Address");
                                current_user_db.setValue(address);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Refrigerator Repair").child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Refrigerator Repair").child("Date");
                                current_user_db.setValue(date);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Refrigerator Repair").child("Time");
                                current_user_db.setValue(time);
                                Toast.makeText(getApplicationContext(), "Job Posted", Toast.LENGTH_SHORT).show();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String fn = dataSnapshot.child("FirstName").getValue().toString();
                                        String ln = dataSnapshot.child("LastName").getValue().toString();
                                        updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln);
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
        });

        microwaverepair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                //handle the boolean flag here.
                if(arg1==true){
                    setContentView(R.layout.microwaverepair);
                    submit = (Button)findViewById(R.id.button);
                    service = (EditText)findViewById(R.id.job);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            job_required = service.getText().toString();
                            if(job_required.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Please Describe Your Job",Toast.LENGTH_LONG).show();
                            }
                            else {
                                DatabaseReference current_user_db;
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Microwave Repair").child(FirebaseAuth.getInstance().getUid()).child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Microwave Repair").child(FirebaseAuth.getInstance().getUid()).child("First Name");
                                current_user_db.setValue(firstname);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Microwave Repair").child(FirebaseAuth.getInstance().getUid()).child("Address");
                                current_user_db.setValue(address);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Microwave Repair").child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Microwave Repair").child("Date");
                                current_user_db.setValue(date);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Microwave Repair").child("Time");
                                current_user_db.setValue(time);
                                Toast.makeText(getApplicationContext(), "Job Posted", Toast.LENGTH_SHORT).show();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String fn = dataSnapshot.child("FirstName").getValue().toString();
                                        String ln = dataSnapshot.child("LastName").getValue().toString();
                                        updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln);
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
        });

        computerrepair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                //handle the boolean flag here.
                if(arg1==true){
                    setContentView(R.layout.computerrepair);
                    submit = (Button)findViewById(R.id.button);
                    service = (EditText)findViewById(R.id.job);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            job_required = service.getText().toString();
                            if(job_required.isEmpty()){
                                Toast.makeText(getApplicationContext(),"Please Describe Your Job",Toast.LENGTH_LONG).show();
                            }
                            else {
                                DatabaseReference current_user_db;
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Computer Repair").child(FirebaseAuth.getInstance().getUid()).child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Computer Repair").child(FirebaseAuth.getInstance().getUid()).child("First Name");
                                current_user_db.setValue(firstname);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs").child("Computer Repair").child(FirebaseAuth.getInstance().getUid()).child("Address");
                                current_user_db.setValue(address);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Computer Repair").child("Job Description");
                                current_user_db.setValue(job_required);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Computer Repair").child("Date");
                                current_user_db.setValue(date);
                                current_user_db = FirebaseDatabase.getInstance().getReference().child("Jobs Posted").child(FirebaseAuth.getInstance().getUid()).child("Computer Repair").child("Time");
                                current_user_db.setValue(time);
                                Toast.makeText(getApplicationContext(), "Job Posted", Toast.LENGTH_SHORT).show();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String fn = dataSnapshot.child("FirstName").getValue().toString();
                                        String ln = dataSnapshot.child("LastName").getValue().toString();
                                        updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln);
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
        });
    }

    public void updateToken(String token, String name){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        Token token1 = new Token(token);
        //Toast.makeText(getApplicationContext(),""+FirebaseAuth.getInstance().getUid(),Toast.LENGTH_LONG).show();
        reference.child(FirebaseAuth.getInstance().getUid()).setValue(token1);
        sendNotification(FirebaseAuth.getInstance().getUid(),name,"Your Job is Posted ! \n We will notify you when employee is available");
    }

    private void sendNotification(final String receiver, final String name, final String msg){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference().child("Tokens");
        //Toast.makeText(getApplicationContext(),"Hello 1",Toast.LENGTH_LONG).show();
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    //Toast.makeText(getApplicationContext(),""+getIntent().getStringExtra("Id"),Toast.LENGTH_LONG).show();
                    Data data = new Data("Y3jtpASaeGYVeL0h03UXu8ZfkGt1",R.drawable.dsf_logo,name+":"+msg,"New Message",receiver);
                    Sender sender = new Sender(data, token.getToken());
                    //Toast.makeText(getApplicationContext(),"Sent",Toast.LENGTH_LONG).show();
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code() == 200){
                                if(response.body().success != 1){
                                    Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent intent = new Intent(ApplianceRepairActivity.this, Services1Activity.class);
        startActivity(intent);
    }

}
