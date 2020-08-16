package com.dev5151.educate.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dev5151.educate.fragments.AssignmentFragment;
import com.dev5151.educate.fragments.VideoFragment;
import com.dev5151.educate.fragments.DashboardFragment;
import com.dev5151.educate.fragments.MaterialFragment;
import com.dev5151.educate.fragments.MembersFragment;



public class ViewPagerAdapter extends FragmentStateAdapter {

    private String courseId;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String courseId) {
        super(fragmentManager, lifecycle);
        this.courseId = courseId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return DashboardFragment.getInstance(courseId);

            case 1:
                return AssignmentFragment.getInstance(courseId);

            case 2:
                return MaterialFragment.getInstance(courseId);

            case 3:
                return VideoFragment.getInstance(courseId);

            case 4:
                return MembersFragment.getInstance(courseId);

        }
        return DashboardFragment.getInstance(courseId);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

