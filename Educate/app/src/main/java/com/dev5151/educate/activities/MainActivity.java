package com.dev5151.educate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dev5151.educate.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore coursesRef = FirebaseFirestore.getInstance();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}