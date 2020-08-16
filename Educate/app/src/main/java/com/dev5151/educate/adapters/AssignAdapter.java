package com.dev5151.educate.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev5151.educate.R;

import java.util.ArrayList;
import java.util.List;

public class AssignAdapter extends ArrayAdapter<Integer> {

    private ArrayList<String> links;
    private String title;

    public AssignAdapter(Context context, ArrayList<String> links, String title) {
        super(context, R.layout.admin_list_item);
        this.links = links;
        this.title = title;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.admin_list_item, null);
        TextView usernameText = view.findViewById(R.id.link);
        usernameText.setText(title + Integer.toString(position+1));
        usernameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(links.get(position)); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });
        return view;
    }
}
