package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.Models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    TextView username;
    EditText etmsg;
    ImageView image,backarrow,sendbtn;
    RecyclerView chatrecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        username=findViewById(R.id.username);
        etmsg=findViewById(R.id.etmsg);
        sendbtn=findViewById(R.id.sendbtn);
       chatrecycler=findViewById(R.id.chatrecycler);
        image=findViewById(R.id.image);
        backarrow=findViewById(R.id.backarrow);
        getSupportActionBar().hide();

        final  String senderId =auth.getUid();
        String receiveId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("profilePic");
        username.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(image);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ChatDetailActivity.this,MainFrame.class);
                startActivity(intent);

            }
        });
        final ArrayList<MessageModel> messageModels =new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);
        chatrecycler.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        chatrecycler.setLayoutManager(layoutManager);

        final String senderRoom= senderId+ receiveId;
        final String recieverRoom= receiveId+ senderId;


        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    MessageModel model = snapshot1.getValue(MessageModel.class);
                    messageModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message= etmsg.getText().toString();
               final MessageModel model=new MessageModel(senderId,message);
               model.setTimeStamp(new Date().getTime());
               etmsg.setText("");
               database.getReference().child("chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       database.getReference().child("chats").child(recieverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {

                           }
                       });

                   }
               });






            }
        });

    }
}