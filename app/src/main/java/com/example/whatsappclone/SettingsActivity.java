package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.whatsappclone.Models.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends AppCompatActivity {
    ImageView backarrow,plus,profileImage;
    Button savebtn;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    EditText etusername, etabout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        backarrow=findViewById(R.id.backarrow);
        savebtn=findViewById(R.id.savebtn);
            plus=findViewById(R.id.plus);
        etusername=findViewById(R.id.etusername);
        etabout=findViewById(R.id.etabout);
        profileImage=findViewById(R.id.proileImage);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,MainFrame.class);
                startActivity(intent);

            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users= snapshot.getValue(Users.class);

                Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(profileImage);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("Image/*");
                startActivityForResult(intent,33);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() !=null){
            Uri file= data.getData();
            profileImage.setImageURI(file);

            final StorageReference reference = storage.getReference().child("profile_pic").child(FirebaseAuth.getInstance().getUid());

            reference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profile_pic").setValue(uri.toString());

                            Toast.makeText(SettingsActivity.this, "Profile pic updated", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            });
        }
        }
    }
