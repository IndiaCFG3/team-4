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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignAdapter extends ArrayAdapter<Integer> {

    private ArrayList<String> links;
    private String title;
    private String courseId;
    private String uid;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public AssignAdapter(Context context, ArrayList<String> links, String title, String courseId, String uid) {
        super(context, R.layout.admin_list_item);
        this.links = links;
        this.title = title;
        this.courseId = courseId;
        this.uid = uid;
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
                if(title.equals("Video")) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("video",((float)(position+1)/links.size()));
                    mFirestore.collection("Progress").document(uid+" # "+courseId).update(map);
                }
                if(title.equals("Assignment")) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("quiz",(((float)(position+1)/links.size())));
                    mFirestore.collection("Progress").document(uid+" # "+courseId).update(map);
                }
                Uri uri = Uri.parse(links.get(position)); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });
        return view;
    }
}
