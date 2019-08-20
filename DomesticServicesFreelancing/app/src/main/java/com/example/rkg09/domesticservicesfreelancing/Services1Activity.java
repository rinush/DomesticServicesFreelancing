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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Services1Activity extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10, tv15;
    ImageView home, message, feedback, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services1);

        tv1=(TextView) findViewById(R.id.textView2);
        tv2=(TextView) findViewById(R.id.textView8);
        tv3=(TextView) findViewById(R.id.textView16);
        tv4=(TextView) findViewById(R.id.textView24);
        tv5=(TextView) findViewById(R.id.textView27);
        tv6=(TextView) findViewById(R.id.textView28);
        //tv7=(TextView) findViewById(R.id.textView29);
        //tv8=(TextView) findViewById(R.id.textView30);
        tv9=(TextView) findViewById(R.id.textView32);
        tv10=(TextView) findViewById(R.id.textView35);
        tv15 = (TextView) findViewById(R.id.textView36);

        home=(ImageView) findViewById(R.id.home);
        message=(ImageView) findViewById(R.id.message);
        feedback=(ImageView) findViewById(R.id.feedback);
        logout=(ImageView) findViewById(R.id.Log_Out);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,HomeCleanerActivity.class);
                startActivity(intent);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,PEC.class);
                startActivity(intent);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,BeautyActivity.class);
                startActivity(intent);
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,ApplianceRepairActivity.class);
                startActivity(intent);
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,CookActivity.class);
                startActivity(intent);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,HomePainterActivity.class);
                startActivity(intent);
            }
        });
       /* tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,GrillFittingActivity.class);
                startActivity(intent);
            }
        });
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,CatererActivity.class);
                startActivity(intent);
            }
        }); */
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,HomeTutorsActivity.class);
                startActivity(intent);
            }
        });
        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,FitnessActivity.class);
                startActivity(intent);
            }
        });
        tv15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,GroceryActivity.class);
                startActivity(intent);
            }
        });

    home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Services1Activity.this,Services1Activity.class);
            startActivity(intent);
        }
    });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,ChatActivity1.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Services1Activity.this,ReportActivity.class);
                intent.putExtra("Id","Customer");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Services1Activity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;;
        //noinspection SimplifiableIfStatement
        if (id == R.id.jp) {
            intent = new Intent(Services1Activity.this,JobsPostedActivity.class);
            startActivity(intent);
            //finish();
        }
        else if(id == R.id.jobs_in_progress){
            intent = new Intent(Services1Activity.this,JobsInProgressActivity.class);
            startActivity(intent);
            //finish();
        }
        else if(id == R.id.jc){
            intent = new Intent(Services1Activity.this,JobsCompleteActivity.class);
            intent.putExtra("Id","Customer");
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
