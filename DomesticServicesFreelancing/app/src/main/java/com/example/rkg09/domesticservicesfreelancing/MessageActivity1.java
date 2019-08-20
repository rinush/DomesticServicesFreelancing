package com.example.rkg09.domesticservicesfreelancing;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rkg09.domesticservicesfreelancing.Chat.ChatObject;
import com.example.rkg09.domesticservicesfreelancing.Chat.MessageAdapter;
import com.example.rkg09.domesticservicesfreelancing.Chat.MessageObject;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Client;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Data;
import com.example.rkg09.domesticservicesfreelancing.Notifications.MyResponse;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Sender;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Token;
import com.example.rkg09.domesticservicesfreelancing.User.UserObject;
import com.example.rkg09.domesticservicesfreelancing.Utils.SendNotification;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.rkg09.domesticservicesfreelancing.Notifications.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity1 extends AppCompatActivity {

    EditText et;
    Button send;

    private RecyclerView mChat, mMedia;
    private RecyclerView.Adapter mChatAdapter, mMediaAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager, mMediaLayoutManager;

    ArrayList<MessageObject> messageList;

    ChatObject mChatObject;

    DatabaseReference mChatMessagesDb;
    DatabaseReference reference1;
    APIService apiService;
    boolean notify = false;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        id = getIntent().getStringExtra("Id");
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        send = (Button) findViewById(R.id.send);
        reference1 = FirebaseDatabase.getInstance().getReference().child("Chat").child(id).child("Info").child("Users");
        mChatMessagesDb = FirebaseDatabase.getInstance().getReference().child("Chat").child(id).child("messages");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        initializeMessage();
        getChatMessages();

            }

    private void getChatMessages() {
        mChatMessagesDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    String  text = "",
                            creatorID = "";
                    ArrayList<String> mediaUrlList = new ArrayList<>();

                    if(dataSnapshot.child("text").getValue() != null)
                        text = dataSnapshot.child("text").getValue().toString();
                    if(dataSnapshot.child("creator").getValue() != null)
                        creatorID = dataSnapshot.child("creator").getValue().toString();

                    //Toast.makeText(getApplicationContext(),""+text,Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),""+creatorID,Toast.LENGTH_LONG).show();

                 /*   if(dataSnapshot.child("media").getChildrenCount() > 0)
                        for (DataSnapshot mediaSnapshot : dataSnapshot.child("media").getChildren())
                            mediaUrlList.add(mediaSnapshot.getValue().toString()); */

                    MessageObject mMessage = new MessageObject(dataSnapshot.getKey(), creatorID, text, mediaUrlList);
                    messageList.add(mMessage);
                    mChatLayoutManager.scrollToPosition(messageList.size()-1);
                    mChatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    EditText mMessage;
    private void sendMessage(){
        mMessage = findViewById(R.id.messageInput);
        notify = true;
        String messageId = mChatMessagesDb.push().getKey();
        final DatabaseReference newMessageDb = mChatMessagesDb.child(messageId);

        final Map newMessageMap = new HashMap<>();

        newMessageMap.put("creator", FirebaseAuth.getInstance().getUid());

        if(!mMessage.getText().toString().isEmpty())
            newMessageMap.put("text", mMessage.getText().toString());

            if(!mMessage.getText().toString().isEmpty())
                updateDatabaseWithNewMessage(newMessageDb, newMessageMap);

    }



    private void updateDatabaseWithNewMessage(DatabaseReference newMessageDb, Map newMessageMap){
        newMessageDb.updateChildren(newMessageMap);
        mMessage.setText(null);
        //mMediaAdapter.notifyDataSetChanged();

        String message;

        if(newMessageMap.get("text") != null) {
            message = newMessageMap.get("text").toString();
            final String msg = message;
            reference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        if(!(ds.getKey().toString().contentEquals(FirebaseAuth.getInstance().getUid()))){
                           final String receiver = ds.getKey().toString();
                            //Toast.makeText(getApplicationContext(),""+receiver,Toast.LENGTH_LONG).show();
                            if(getIntent().getStringExtra("User").contentEquals("Customers")) {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(FirebaseAuth.getInstance().getUid()).child("FirstName");
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String name = dataSnapshot.getValue().toString();
                                        //Toast.makeText(getApplicationContext(),""+notify,Toast.LENGTH_LONG).show();
                                        if (notify) {
                                            sendNotification(receiver, name, msg);
                                        }
                                        notify = false;
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else if(getIntent().getStringExtra("User").contentEquals("Employees")){
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Employees").child(FirebaseAuth.getInstance().getUid()).child("FirstName");
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String name = dataSnapshot.getValue().toString();
                                        //Toast.makeText(getApplicationContext(),""+notify,Toast.LENGTH_LONG).show();
                                        if (notify) {
                                            sendNotification(receiver, name, msg);
                                        }
                                        notify = false;
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    /*    for(UserObject mUser : mChatObject.getUserObjectArrayList()){
            if(!mUser.getUid().equals(FirebaseAuth.getInstance().getUid())){
                new SendNotification(message, "New Message", mUser.getNotificationKey());
            }
        } */

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
                    Data data = new Data(FirebaseAuth.getInstance().getUid(),R.drawable.dsf_logo,name+":"+msg,"New Message",receiver);
                    Sender sender = new Sender(data, token.getToken());
                    //Toast.makeText(getApplicationContext(),"Sent",Toast.LENGTH_LONG).show();
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code() == 200){
                                if(response.body().success != 1){
                                    Toast.makeText(MessageActivity1.this,"Failed!",Toast.LENGTH_LONG).show();
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
    }

    private void initializeMessage() {
        messageList = new ArrayList<>();
        mChat= findViewById(R.id.messageList);
        mChat.setNestedScrollingEnabled(false);
        mChat.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mChat.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new MessageAdapter(messageList);
        mChat.setAdapter(mChatAdapter);
    }

private List<String> get_message_list(String id){
    final List<String> message_sent_list = new ArrayList<String>(Arrays.asList(",".split(",")));
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chat").child(id).child("messages");
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds : dataSnapshot.getChildren()){
                //Toast.makeText(getApplicationContext(),""+ds.child("text").getValue().toString(),Toast.LENGTH_LONG).show();
                message_sent_list.add(ds.child("text").getValue().toString());
            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    return message_sent_list;
}

    private List<String> get_message_creator_list(String id){
        final List<String> message_creator_list = new ArrayList<String>(Arrays.asList(",".split(",")));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Chat").child(id).child("messages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    //Toast.makeText(getApplicationContext(),""+ds.child("text").getValue().toString(),Toast.LENGTH_LONG).show();
                    message_creator_list.add(ds.child("creator").getValue().toString());
                }
                //messageAdapter1 = new MessageAdapter1(MessageActivity1.this, message_sent_list,message_creator_list);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return message_creator_list;
    }
}
