package com.dev5151.educate.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.educate.R;

public class student_courses_adapter extends RecyclerView.Adapter<student_courses_adapter.MyViewHolder> {
    Context context;

    public student_courses_adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_layout,parent,false);
        return new student_courses_adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.course_name.setText("ss");
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course_name;
        TextView description;
        TextView organisation;
        ImageView photo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course_name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            organisation = itemView.findViewById(R.id.organisation);
            photo = itemView.findViewById(R.id.course_logo);
        }
    }
}
