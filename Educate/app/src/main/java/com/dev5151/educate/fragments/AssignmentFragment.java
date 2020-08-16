package com.dev5151.educate.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dev5151.educate.R;
import com.dev5151.educate.adapters.AssignAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AssignmentFragment extends Fragment {
    private String courseId;
    private ListView listView;
    private ArrayList<String> links;
    private FirebaseFirestore mFirestore;

    public static AssignmentFragment getInstance(String courseId) {
        AssignmentFragment fragment = new AssignmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        listView = view.findViewById(R.id.listAss);
        links = new ArrayList<>();
        mFirestore.collection("Courses").document(courseId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    links = (ArrayList<String>) doc.get("quiz");
                    AssignAdapter assignAdapter = new AssignAdapter(getContext(),links,"Assignment");
                    listView.setAdapter(assignAdapter);
                    assignAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }
}
