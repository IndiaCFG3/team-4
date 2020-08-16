package com.dev5151.educate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dev5151.educate.R;
import com.dev5151.educate.models.Courses;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class Add_Course_Teacher extends AppCompatActivity {

    private EditText name;
    private EditText desc;
    private Button reg;
    private String nameStr;
    private String descStr;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__course__teacher);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        reg = findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String teacher = mAuth.getCurrentUser().getUid();
                String teacher = "uuid";
                long time = System.currentTimeMillis();
                String id = Long.toString(time);
                nameStr = name.getText().toString();
                descStr = desc.getText().toString();
                if((!nameStr.isEmpty()) && (!descStr.isEmpty())) {
                    Courses courses = new Courses(id,nameStr,descStr,teacher,"",new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
                    mFirestore.collection("Courses").document(id).set(courses);
                } else {
                    Toast.makeText(Add_Course_Teacher.this,"Please fill in the details!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}