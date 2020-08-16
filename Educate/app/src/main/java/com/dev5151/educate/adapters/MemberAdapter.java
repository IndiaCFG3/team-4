package com.dev5151.educate.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev5151.educate.R;

import java.util.ArrayList;

public class MemberAdapter extends ArrayAdapter<String> {
    ArrayList<String> name;

    public MemberAdapter(@NonNull Context context, ArrayList<String> name) {
        super(context, R.layout.list_item, name);
        this.name = name;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item,null);
        TextView name_text = view.findViewById(R.id.name);
        name_text.setText(name.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on click

            }
        });
        return view;
    }
}
