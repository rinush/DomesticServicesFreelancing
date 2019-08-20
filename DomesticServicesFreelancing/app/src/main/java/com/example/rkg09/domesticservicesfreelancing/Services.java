package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Services extends AppCompatActivity {

    String job_no, customer_id, designation, date_str, time_str, contact_str, payment_str, your_proposal_str, firstname ;
    EditText your_proposal, date, time, contact, payment;
    Button submit;
    ImageView home, message, feedback, logout;

    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        job_no = getIntent().getStringExtra("Job");

        your_proposal = (EditText) findViewById(R.id.et_your_proposal);
        date = (EditText) findViewById(R.id.et_date);
        time = (EditText) findViewById(R.id.et_time);
        contact = (EditText) findViewById(R.id.et_cn);
        payment = (EditText) findViewById(R.id.et_payment);
        submit = (Button) findViewById(R.id.submit);

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this,JobsActivity.class);
                startActivity(intent);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this,ChatActivity1.class);
                intent.putExtra("Id","Employee");
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services.this,ReportActivity.class);
                intent.putExtra("Id","Employee");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Services.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(FirebaseAuth.getInstance().getUid()).child("Designation");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        designation = dataSnapshot.getValue().toString();
                        DatabaseReference customer = FirebaseDatabase.getInstance().getReference().child("Jobs").child(designation);
                        customer.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int i = 0;
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if(i == Integer.parseInt(job_no)){
                                        if(ds.exists()) {
                                            customer_id = ds.getKey().toString();
                                            your_proposal_str = your_proposal.getText().toString();
                                            date_str = date.getText().toString();
                                            time_str = time.getText().toString();
                                            contact_str = contact.getText().toString();
                                            payment_str = payment.getText().toString();
                                            if (your_proposal_str.isEmpty() || date_str.isEmpty() || time_str.isEmpty() || contact_str.isEmpty() || payment_str.isEmpty()) {
                                                Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_LONG).show();
                                            }
                                            else if(contact_str.length()!=10){
                                                Toast.makeText(getApplicationContext(),"Contact Should be of 10 characters",Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                DatabaseReference employee = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(FirebaseAuth.getInstance().getUid()).child("FirstName");
                                                employee.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            firstname = dataSnapshot.getValue(String.class);
                                                            DatabaseReference services;
                                                            services = FirebaseDatabase.getInstance().getReference().child("Services").child(designation).child(customer_id).child(FirebaseAuth.getInstance().getUid()).child("FirstName");
                                                            services.setValue(firstname);
                                                            services = FirebaseDatabase.getInstance().getReference().child("Services").child(designation).child(customer_id).child(FirebaseAuth.getInstance().getUid()).child("Proposal");
                                                            services.setValue(your_proposal_str);
                                                            services = FirebaseDatabase.getInstance().getReference().child("Services").child(designation).child(customer_id).child(FirebaseAuth.getInstance().getUid()).child("Date");
                                                            services.setValue(date_str);
                                                            services = FirebaseDatabase.getInstance().getReference().child("Services").child(designation).child(customer_id).child(FirebaseAuth.getInstance().getUid()).child("Time");
                                                            services.setValue(time_str);
                                                            services = FirebaseDatabase.getInstance().getReference().child("Services").child(designation).child(customer_id).child(FirebaseAuth.getInstance().getUid()).child("Contact");
                                                            services.setValue(contact_str);
                                                            services = FirebaseDatabase.getInstance().getReference().child("Services").child(designation).child(customer_id).child(FirebaseAuth.getInstance().getUid()).child("Payment");
                                                            services.setValue(payment_str);
                                                            Toast.makeText(getApplicationContext(),"Your Proposal Submitted",Toast.LENGTH_LONG).show();
                                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(customer_id);
                                                            ref.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    String fn = dataSnapshot.child("FirstName").getValue().toString();
                                                                    String ln = dataSnapshot.child("LastName").getValue().toString();
                                                                    updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln, customer_id);
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
                                        break;
                                    }
                                    else{
                                        i++;
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

            }
        });

    }

    public void updateToken(String token, String name, String customer_id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
        Token token1 = new Token(token);
        //Toast.makeText(getApplicationContext(),""+FirebaseAuth.getInstance().getUid(),Toast.LENGTH_LONG).show();
        reference.child(customer_id).setValue(token1);
        sendNotification(customer_id,name,"Employee has sent proposal ! \n Please check it");
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
        Intent intent = new Intent(Services.this, JobsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
