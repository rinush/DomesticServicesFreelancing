package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Client;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Data;
import com.example.rkg09.domesticservicesfreelancing.Notifications.MyResponse;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Sender;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button register;
    EditText email,password,firstname,lastname,contact,address,area;
    RadioButton customer,employee;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener firebaseAuthListener;
    Spinner desg;
    String designation;
    APIService apiService;
    private static final String[] paths = {"Plumber", "Carpenter", "Electrician","Home Cleaner","Gardener","Beautician","HouseMaid","Cook"
    ,"Caretaker","Home Painter","Grill Fitting","Dietician","Home Tutor","Fitness and Yoga Trainer","Pest Control","Appliance Repairer","Vegetable Vendor",
            "Fruit Vendor","Milk Man","Snacks Seller","Drinks Seller","Customer"};

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    //Toast.makeText(getApplicationContext(),"RegisterActivity",Toast.LENGTH_SHORT).show();
                    /*Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;*/
                }
            }
        };

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        register=(Button)findViewById(R.id.register);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        firstname=(EditText)findViewById(R.id.fn);
        lastname=(EditText)findViewById(R.id.ln);
        contact=(EditText)findViewById(R.id.contact);
        desg=(Spinner)findViewById(R.id.spinner);
        address=(EditText)findViewById(R.id.address);
        area=(EditText)findViewById(R.id.area);
        customer=(RadioButton)findViewById(R.id.radioButton);
        employee=(RadioButton)findViewById(R.id.radioButton2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desg.setAdapter(adapter);
        desg.setOnItemSelectedListener(this);

        customer.setChecked(false);
        employee.setChecked(false);

            customer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                    //handle the boolean flag here.
                    if(arg1==true) {
                      employee.setChecked(false);
                    }
                            }
                        });

            employee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                    //handle the boolean flag here.
                    if(arg1==true) {
                        customer.setChecked(false);
                    }
                }
            });

        awesomeValidation.addValidation(RegisterActivity.this, R.id.fn, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.ln, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        //awesomeValidation.addValidation(RegisterActivity.this, R.id.password, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(RegisterActivity.this, R.id.contact, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email1,password1,fn,ln,contact1,desg1,address1,area1;
                email1=email.getText().toString().trim();
                password1=password.getText().toString().trim();
                //password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //Toast.makeText(getApplicationContext(),""+password1,Toast.LENGTH_LONG).show();
                fn=firstname.getText().toString().trim();
                ln=lastname.getText().toString().trim();
                contact1=contact.getText().toString().trim();
                desg1 = designation;
                //desg1=desg.getText().toString().trim();
                address1=address.getText().toString().trim();
                area1 = area.getText().toString().trim();

                if ((fn.isEmpty()) || (email1.isEmpty()) || (password1.isEmpty()) || (ln.isEmpty()) || (contact1.isEmpty()) || (desg1.isEmpty()) || (address1.isEmpty()) || (area1.isEmpty())) {
                    Toast.makeText(getApplicationContext(), "All The Fields Are Required", Toast.LENGTH_SHORT).show();
                } else if ((!(customer.isChecked())) && (!(employee.isChecked()))) {
                    Toast.makeText(getApplicationContext(), "Please Select Customer Or Employee", Toast.LENGTH_SHORT).show();
                } else{
                    //Toast.makeText(getApplicationContext(),""+email1,Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),""+password1,Toast.LENGTH_LONG).show();

                    if(awesomeValidation.validate()) {

                    //awesomeValidation.validate();

                        mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "SignUp Error", Toast.LENGTH_SHORT).show();
                                } else {
                                    String user_id = FirebaseAuth.getInstance().getUid();
                                    DatabaseReference current_user_db;

                                    if (customer.isChecked()) {

                                        //Toast.makeText(getApplicationContext(),"Customer",Toast.LENGTH_LONG).show();

                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("FirstName");
                                        current_user_db.setValue(fn);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("LastName");
                                        current_user_db.setValue(ln);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Email");
                                        current_user_db.setValue(email1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Password");
                                        current_user_db.setValue(password1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Contact");
                                        current_user_db.setValue(contact1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Designation");
                                        current_user_db.setValue(desg1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Address");
                                        current_user_db.setValue(address1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id).child("Area");
                                        current_user_db.setValue(area1);
                                    } else if (employee.isChecked()) {

                                        //Toast.makeText(getApplicationContext(),"Employee",Toast.LENGTH_LONG).show();
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("FirstName");
                                        current_user_db.setValue(fn);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("LastName");
                                        current_user_db.setValue(ln);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("Email");
                                        current_user_db.setValue(email1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("Password");
                                        current_user_db.setValue(password1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("Contact");
                                        current_user_db.setValue(contact1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("Designation");
                                        current_user_db.setValue(desg1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("Address");
                                        current_user_db.setValue(address1);
                                        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(user_id).child("Area");
                                        current_user_db.setValue(area1);
                                    }
                                    updateToken(FirebaseInstanceId.getInstance().getToken(), fn + " " + ln);
                                }
                            }

                        });
                    }
                }
            }

        });
    }

        private void updateToken(String token, String name){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Tokens");
            Token token1 = new Token(token);
            //Toast.makeText(getApplicationContext(),""+FirebaseAuth.getInstance().getUid(),Toast.LENGTH_LONG).show();
            reference.child(FirebaseAuth.getInstance().getUid()).setValue(token1);
            sendNotification(FirebaseAuth.getInstance().getUid(),name,"Your account is created Successfully");
        }

    private void sendNotification(final String receiver, final String name, final String msg){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference().child("Tokens");
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
                                    Toast.makeText(RegisterActivity.this,"Failed!",Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void  onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            designation = parent.getItemAtPosition(position).toString();
            // Showing selected spinner item
            /*Toast.makeText(parent.getContext(), "You selected: " + designation,
                    Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void onStop(){
        super.onStop();
        //mAuth.removeAuthStateListener(firebaseAuthListener);
        finish();
    }

}
