package com.dev5151.educate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.dev5151.educate.R;
import com.dev5151.educate.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CourseActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent intent = getIntent();
        courseId = intent.getStringExtra("course");
        initViews();

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), courseId));
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Dashboard");
                                break;
                            case 1:
                                tab.setText("Assignments");
                                break;
                            case 2:
                                tab.setText("Materials");
                                break;
                            case 3:
                                tab.setText("Chats");
                                break;
                            case 4:
                                tab.setText("Members");
                                break;
                        }
                    }
                }).attach();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
    }
}