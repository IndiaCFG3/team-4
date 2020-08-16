package com.dev5151.educate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.dev5151.educate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpash);
        int SPLASH_TIME_OUT = 500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                mFirestore = FirebaseFirestore.getInstance();
                if (mAuth.getCurrentUser() != null) {
                    mFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if(doc.getBoolean("isStudent")) {
                                    Toast.makeText(SplashActivity.this,"Welcome Student", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                                } else {
                                    Toast.makeText(SplashActivity.this,"Welcome Teacher", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SplashActivity.this,MainActivity2.class));
                                }
                                finish();
                            }
                        }
                    });
                } else {
                    Intent i=new Intent(SplashActivity.this,OnBoardingActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}