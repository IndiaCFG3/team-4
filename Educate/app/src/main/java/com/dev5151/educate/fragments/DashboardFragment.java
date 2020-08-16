package com.dev5151.educate.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev5151.educate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardFragment extends Fragment {

    private String courseId;
    private FirebaseFirestore mFirestore;
    private ProgressBar quiz;
    private ProgressBar videos;
    private FirebaseAuth mAuth;
    private Double quizProgress;
    private Double videosProgress;
    TextView course_name;
    TextView description;
    String coursename;
    String coursedes;

    public static DashboardFragment getInstance(String courseId) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString("courseId", courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseId = getArguments().getString("courseId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        course_name = view.findViewById(R.id.course_name);
        description = view.findViewById(R.id.description);
        quiz = view.findViewById(R.id.progressBarQuiz);
        videos = view.findViewById(R.id.progressBar);
        System.out.println(mAuth.getUid() + " # " + courseId);
        mFirestore.collection("Progress").document(mAuth.getUid() + " # " + courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if(doc!=null) {
                        quizProgress = doc.getDouble("quiz");
                        videosProgress = doc.getDouble("video");
                        System.out.println(quizProgress);
                        System.out.println(videosProgress);
                        quiz.setProgress((int)(quizProgress*100));
                        videos.setProgress((int)(videosProgress*100));
                    }
                }
            }
        });
        mFirestore.collection("Courses").document(courseId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {
                                coursename = (String) document.get("name");
                                 coursedes = (String) document.get("description");
                                course_name.setText(coursename);
                                description.setText(coursedes);
                            }
                        }
                    }
                });
        return view;
    }
}