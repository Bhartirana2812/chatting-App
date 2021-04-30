package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.whatsappclone.Adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainFrame extends AppCompatActivity {
    FirebaseAuth auth;
    TabLayout tablayout;
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
        tablayout=findViewById(R.id.tablayout);
        viewpager=findViewById(R.id.viewpager);
        auth=FirebaseAuth.getInstance();
        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewpager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent2=new Intent(MainFrame.this,SettingsActivity.class);
                startActivity(intent2);


                break;

            case R.id.logout:

                auth.signOut();
                Intent intent=new Intent(MainFrame.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.groupchat:
                Intent intent1 =new Intent(MainFrame.this,GroupChatActivity.class);
                startActivity(intent1);
                break;


        }
        return true;
    }
}