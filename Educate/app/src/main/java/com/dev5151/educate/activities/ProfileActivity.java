package com.dev5151.educate.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.dev5151.educate.EditProfile;
import com.dev5151.educate.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    TextView mprofilename,mprofileemail,mprofilenumber;
    Button mchangeprofile;
    ImageView mprofileimage;
    FirebaseAuth fauth;
    FirebaseFirestore mStore;
    String userIDD;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mprofileemail=findViewById(R.id.profileemail);
        mprofilename=findViewById(R.id.profilename);
        mprofilenumber=findViewById(R.id.profilenumber);
        mprofileimage=findViewById(R.id.profileimage);
        mchangeprofile=findViewById(R.id.change_profile);

        fauth=FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference profileRef=storageReference.child("users/"+fauth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mprofileimage);
            }
        });

        userIDD=fauth.getCurrentUser().getUid();

        DocumentReference documentReference=mStore.collection("Users").document(userIDD);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                mprofilenumber.setText(value.getString("phone"));
                mprofilename.setText(value.getString("uname"));
                mprofileemail.setText(value.getString("email"));
            }
        });

        mchangeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(openGalleryIntent,1000);
                Intent i=new Intent(ProfileActivity.this, EditProfile.class);
                i.putExtra("username1",mprofilename.getText().toString());
                i.putExtra("email1",mprofileemail.getText().toString());
                i.putExtra("phone1",mprofilenumber.getText().toString());
                startActivity(i);
            }
        });
    }

}
