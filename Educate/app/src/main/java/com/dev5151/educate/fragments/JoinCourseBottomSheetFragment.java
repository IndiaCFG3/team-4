package com.dev5151.educate.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev5151.educate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinCourseBottomSheetFragment extends BottomSheetDialogFragment {
    FirebaseFirestore coursesRef = FirebaseFirestore.getInstance();
    private List<String> studentList;
    private String uid;
    FirebaseAuth mAuth;
    Button btnJoin;
    TextInputLayout edtJoinCourse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_join_course, container, false);
        initView(view);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseId=edtJoinCourse.getEditText().getText().toString();
                validateCode(courseId);
            }
        });

        return view;
    }

    private void initView(View view) {
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        edtJoinCourse = view.findViewById(R.id.outlinedTextField);
        btnJoin = view.findViewById(R.id.btn_join_course);
    }

    private void validateCode(final String courseId) {
        coursesRef.collection("Courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        studentList = (List<String>) document.get("students");
                        studentList.add(uid);
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("students", studentList);
                        coursesRef.collection("Courses").document(courseId).update(hashMap);
                        Map<String,Object> map = new HashMap<>();
                        map.put("video",0);
                        map.put("quiz",0);
                        coursesRef.collection("Progress").document(uid+" # "+courseId).set(map);
                    } else {
                        Toast.makeText(getActivity(), "Invalid course Id", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
