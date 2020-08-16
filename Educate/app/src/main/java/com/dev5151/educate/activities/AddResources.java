package com.dev5151.educate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev5151.educate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddResources extends AppCompatActivity {
    String courseId;
    FirebaseFirestore coursesRef = FirebaseFirestore.getInstance();
    EditText meaddmaterial,meaddquiz,meaddvideos;
    Button addmat,addqui,addvid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resources);
        courseId=getIntent().getStringExtra("courseId");
        meaddmaterial=findViewById(R.id.eaddmaterial);
        meaddquiz=findViewById(R.id.eaddquiz);
        meaddvideos=findViewById(R.id.eaddvideos);
        addmat=findViewById(R.id.addmaterial);
        addqui=findViewById(R.id.addquiz);
        addvid=findViewById(R.id.addvideos);

        addmat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMaterial(courseId);
            }
        });

        addqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddQuiz(courseId);
            }
        });

        addvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVideo(courseId);
            }
        });
    }
    private void AddMaterial(final String courseId) {
        final String mat=meaddmaterial.getText().toString();
        coursesRef.collection("Courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String,Object> material=new HashMap<>();
                        material.put("material",mat);
                        coursesRef.collection("Courses").document(courseId).update(material);
                    } else {
                        Toast.makeText(AddResources.this, "Could not add the material", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void AddQuiz(final String courseId) {
        final String qui=meaddquiz.getText().toString();
        coursesRef.collection("Courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String,Object> quiz=new HashMap<>();
                        quiz.put("quiz",qui);
                        coursesRef.collection("Courses").document(courseId).update(quiz);
                    } else {
                        Toast.makeText(AddResources.this, "Could not add the quiz", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void AddVideo(final String courseId) {
        final String vid=meaddvideos.getText().toString();
        coursesRef.collection("Courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String,Object> video=new HashMap<>();
                        video.put("videos",vid);
                        coursesRef.collection("Courses").document(courseId).update(video);
                    } else {
                        Toast.makeText(AddResources.this, "Could not add the video", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}