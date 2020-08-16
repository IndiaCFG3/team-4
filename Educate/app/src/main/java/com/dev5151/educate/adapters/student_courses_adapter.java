package com.dev5151.educate.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.educate.R;
import com.dev5151.educate.activities.CourseActivity;
import com.dev5151.educate.activities.MainActivity;

import java.util.ArrayList;

public class student_courses_adapter extends ArrayAdapter<Integer> {

    private ArrayList<String> names;
    private ArrayList<String> desc;
    private ArrayList<String> ids;

    public student_courses_adapter(Context context, ArrayList<String> names, ArrayList<String> desc, ArrayList<String> ids) {
        super(context, R.layout.cardview_layout);
        this.names = names;
        this.desc = desc;
        this.ids = ids;
    }

        @Override
        public int getCount() {
        return names.size();
    }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.cardview_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), CourseActivity.class);
                intent.putExtra("course",ids.get(position));
                getContext().startActivity(intent);
            }
        });
        TextView usernameText = view.findViewById(R.id.name);
        usernameText.setText(names.get(position));
        TextView descText = view.findViewById(R.id.description);
        descText.setText(desc.get(position));
        return view;
    }
    }