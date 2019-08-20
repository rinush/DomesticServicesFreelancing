package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

     Button login, register;
     EditText et1, et2;
     String email=null;
     String password=null;

     private FirebaseAuth mAuth;
     private  FirebaseAuth.AuthStateListener firebaseAuthListener;
     //ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //int images[] = {R.drawable.carcleaning,R.drawable.bathroomcleaning,R.drawable.beautician1};
        //viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        //Toast.makeText(getApplicationContext(),""+user.getUid(),Toast.LENGTH_LONG).show();
                if(user!=null){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(mAuth.getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                                                  @Override
                                                  public void onDataChange(DataSnapshot dataSnapshot) {
                                                      if(dataSnapshot.exists()){
                                                          Intent intent = new Intent(LoginActivity.this, Services1Activity.class);
                                                          startActivity(intent);
                                                          finish();
                                                      }
                                                  }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError databaseError) {

                                                  }
                                              }
                    );

                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(mAuth.getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Intent intent = new Intent(LoginActivity.this, JobsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }});

                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Admin").child(mAuth.getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }});

                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                }



        et1=(EditText)findViewById(R.id.em);
        et2=(EditText)findViewById(R.id.pass);
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);

      /*  for(int image:images){
            flipperImages(image);
        } */

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et1.getText().toString().trim();
                password = et2.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Sign In Error", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(mAuth.getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                                              @Override
                                                              public void onDataChange(DataSnapshot dataSnapshot) {
                                                                  if (dataSnapshot.exists()) {
                                                                      Intent intent = new Intent(LoginActivity.this, Services1Activity.class);
                                                                      startActivity(intent);
                                                                      finish();
                                                                  }
                                                              }

                                                              @Override
                                                              public void onCancelled(@NonNull DatabaseError databaseError) {

                                                              }
                                                          }
                                );

                                ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(mAuth.getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent intent = new Intent(LoginActivity.this, JobsActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Admin").child(mAuth.getUid());
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                            finish();
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
        });
                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    }

});
    }
   /* public  void flipperImages(int image){
        ImageView imageView = new ImageView(LoginActivity.this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(LoginActivity.this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(LoginActivity.this, android.R.anim.slide_out_right);
    } */
}
