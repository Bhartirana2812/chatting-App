package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsappclone.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    ConstraintLayout container;
    TextView textView1,textview;
    Button btnsignup;
    EditText Email,Password,name;
    boolean isEmailValid, isPasswordValid;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressdialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_sign_up);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        textview=findViewById(R.id.textView);
       Email=findViewById(R.id.Email);
      Password=findViewById(R.id.Password);
      name=findViewById(R.id.name);
      container=findViewById(R.id.container);
        textView1=findViewById(R.id.textView1);
        btnsignup=findViewById(R.id.btnsignup);
        progressdialog=new ProgressDialog(SignUp.this);
        progressdialog.setTitle("Creating Account");
        progressdialog.setMessage("We'r creating your account");

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
                progressdialog.show();

                auth.createUserWithEmailAndPassword(Email.getText().toString(),Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();
                        if(task.isSuccessful()){
                            Users user=new Users(Email.getText().toString(),name.getText().toString(),Password.getText().toString());
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            Toast.makeText(SignUp.this, "User Created Succesfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    }


    public void SetValidation() {
        // Check for a valid email address.
        if (Email.getText().toString().isEmpty()) {
            Email.setError("Fill Email");
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
            Email.setError("Invalid Email");
            isEmailValid = false;
        } else  {
            isEmailValid = true;
        }

        // Check for a valid password.

        if (Password.getText().toString().isEmpty()) {
            Password.setError("fill password");
            isPasswordValid = false;
        } else if (Password.getText().length() < 6) {
            Password.setError("Minimum Length 6");
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }

        if (isEmailValid && isPasswordValid) {
            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onResume(){
        super.onResume();

        Calendar c=Calendar.getInstance();
        int timeofday=c.get(Calendar.HOUR_OF_DAY);
        if(timeofday >=0 && timeofday < 12){

            container.setBackground(getDrawable(R.drawable.good_morning_img));
            textView1.setText("Good Morning");

        }
        else if(timeofday>=12&&timeofday<16 ){
            container.setBackground(getDrawable(R.drawable.good_morning_img));
            textView1.setText("Good Morning");



        }
        else if(timeofday>=16&&timeofday<20){
            container.setBackground(getDrawable(R.drawable.good_morning_img));
            textView1.setText("Good Evening");


        }
        else if(timeofday >=20 && timeofday<24 ){
            container.setBackground(getDrawable(R.drawable.good_night_img));
            textView1.setText("Good Night");

        }


    }
}