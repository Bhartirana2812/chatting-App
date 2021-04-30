package com.example.whatsappclone.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone.Adapters.UserAdapter;
import com.example.whatsappclone.Models.Users;
import com.example.whatsappclone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }
    RecyclerView chatrecyclerview;
    ArrayList<Users> list=new ArrayList<Users>();
    FirebaseDatabase database;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatrecyclerview = view.findViewById(R.id.chatrecyclerview);
        database = FirebaseDatabase.getInstance();


        UserAdapter adapter =new UserAdapter(list,getContext());
        chatrecyclerview.setAdapter(adapter);
        LinearLayoutManager layoutmanager=new LinearLayoutManager(getContext());
        chatrecyclerview.setLayoutManager(layoutmanager);
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users= dataSnapshot.getValue(Users.class);
                  users.setUserId(dataSnapshot.getKey());
                    list.add(users);


                }
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }
}