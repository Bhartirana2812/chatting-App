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

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ImageView backarrow,sendbtn;
    RecyclerView chatrecycler;
    TextView username;
    EditText etmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        backarrow=findViewById(R.id.backarrow);
        chatrecycler=findViewById(R.id.chatrecycler);
       username=findViewById(R.id.username);
       sendbtn=findViewById(R.id.sendbtn);
       etmsg=findViewById(R.id.etmsg);
        getSupportActionBar().hide();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(GroupChatActivity.this,MainFrame.class);
                startActivity(intent);

            }
        });

        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messagemodels =new ArrayList<>();
        final String senderId= FirebaseAuth.getInstance().getUid();

        final ChatAdapter adapter =new ChatAdapter(messagemodels,this);
        chatrecycler.setAdapter(adapter);
        username.setText("Friends Group");

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        chatrecycler.setLayoutManager(layoutManager);
        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagemodels.clear();
                for(DataSnapshot datasnapshot:snapshot.getChildren()){
                    MessageModel model = datasnapshot.getValue(MessageModel.class);
                    messagemodels.add(model);
                }
                adapter.notifyDataSetChanged();

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
                database.getReference().child("Group Chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

            }
        });





    }
}