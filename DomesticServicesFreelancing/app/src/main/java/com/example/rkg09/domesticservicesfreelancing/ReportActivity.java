package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportActivity extends AppCompatActivity {
RadioButton customer, employee;
EditText name, email, feedback;
Button submit;
String id;
    ImageView home, message, feedback1, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        name = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText2);
        feedback = (EditText) findViewById(R.id.editText3);
        customer = (RadioButton) findViewById(R.id.radioButton10);
        employee = (RadioButton) findViewById(R.id.radioButton11);
        submit = (Button) findViewById(R.id.button2);

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback1=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        id = getIntent().getStringExtra("Id").toString();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.contentEquals("Customer")) {
                    Intent intent = new Intent(ReportActivity.this, Services1Activity.class);
                    startActivity(intent);
                }
                if(id.contentEquals("Employee")){
                    Intent intent = new Intent(ReportActivity.this, JobsActivity.class);
                    startActivity(intent);
                }
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this,ChatActivity1.class);
                if(id.contentEquals("Employee")) {
                    intent.putExtra("Id", "Employee");
                }
                if(id.contentEquals("Customer")){
                    intent.putExtra("Id","Customer");
                }
                startActivity(intent);
            }
        });

        feedback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this,ReportActivity.class);
                if(id.contentEquals("Employee")) {
                    intent.putExtra("Id", "Employee");
                }
                if(id.contentEquals("Customer")){
                    intent.putExtra("Id","Customer");
                }
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ReportActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mname = name.getText().toString();
                String memail = email.getText().toString();
                String mfeedback = feedback.getText().toString();

                if(mname.isEmpty()||memail.isEmpty()||mfeedback.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else if ((!(customer.isChecked())) && (!(employee.isChecked()))) {
                    Toast.makeText(getApplicationContext(), "Please Select Customer Or Employee", Toast.LENGTH_SHORT).show();
                } else {
                    String user_id = FirebaseAuth.getInstance().getUid();
                    DatabaseReference current_user_db;
                    if(customer.isChecked()){
                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Report").child("Customers").child(user_id).child("Name");
                        current_user_db.setValue(mname);
                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Report").child("Customers").child(user_id).child("Email");
                        current_user_db.setValue(memail);
                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Report").child("Customers").child(user_id).child("FeedBack");
                        current_user_db.setValue(mfeedback);

                    }
                    else if(employee.isChecked()){
                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Report").child("Employees").child(user_id).child("Name");
                        current_user_db.setValue(mname);
                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Report").child("Employees").child(user_id).child("Email");
                        current_user_db.setValue(memail);
                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Report").child("Employees").child(user_id).child("FeedBack");
                        current_user_db.setValue(mfeedback);
                    }

                    Toast.makeText(getApplicationContext(),"FeedBack Submitted",Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
