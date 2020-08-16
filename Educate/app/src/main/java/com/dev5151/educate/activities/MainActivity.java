package com.dev5151.educate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import com.dev5151.educate.R;
import com.dev5151.educate.fragments.JoinCourseBottomSheetFragment;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    MaterialButton btnJoinCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJoinCourse = findViewById(R.id.join_course);

        btnJoinCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinCourseBottomSheetFragment joinCourseBottomSheetFragment = new JoinCourseBottomSheetFragment();
                joinCourseBottomSheetFragment.show(getSupportFragmentManager(), joinCourseBottomSheetFragment.getTag());
            }
        });
    }
}