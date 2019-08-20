package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    ImageButton customer, employee, jobs_completed, del_customer, del_employee, jobs_incomplete, report, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        customer = (ImageButton) findViewById(R.id.imageButton1);
        employee = (ImageButton) findViewById(R.id.imageButton);
        jobs_completed = (ImageButton) findViewById(R.id.imageButton2);
        del_customer = (ImageButton) findViewById(R.id.imageButton4);
        del_employee = (ImageButton) findViewById(R.id.imageButton3);
        jobs_incomplete = (ImageButton) findViewById(R.id.imageButton5);
        report = (ImageButton) findViewById(R.id.imageButton6);
        logout = (ImageButton) findViewById(R.id.imageButton7);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });

        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EmployeeActivity.class);
                startActivity(intent);
            }
        });

        jobs_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, JobsCompleteActivity.class);
                intent.putExtra("Id","Admin");
                startActivity(intent);
            }
        });

        del_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DeleteCustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        del_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, DeleteEmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        jobs_incomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, JobsIncompleteActivity.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, GetReportsActivity.class);
                intent.putExtra("Id","Admin");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
