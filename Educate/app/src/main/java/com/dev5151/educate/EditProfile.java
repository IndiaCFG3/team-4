package com.dev5151.educate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dev5151.educate.activities.ProfileActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    EditText editusername,editphone,editmail;
    ImageView editprofileimage;
    FirebaseAuth nAuth;
    Button saveprofile;
    FirebaseFirestore nStore;
    FirebaseUser user;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent data=getIntent();
        String username2=data.getStringExtra("username1");
        String email2=data.getStringExtra("email1");
        String phone2=data.getStringExtra("phone1");

        nAuth=FirebaseAuth.getInstance();
        nStore=FirebaseFirestore.getInstance();
        user=nAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();


        editusername=findViewById(R.id.eprofilename);
        editphone=findViewById(R.id.eprofilenumber);
        editmail=findViewById(R.id.eprofileemail);
        editprofileimage=findViewById(R.id.eprofileimage);
        saveprofile=findViewById(R.id.save_profile);

        editusername.setText(username2);
        editphone.setText(phone2);
        editmail.setText(email2);

        StorageReference profileRef=storageReference.child("users/"+nAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(editprofileimage);
            }
        });

        editprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);

                //Toast.makeText(EditProfile.this,"profile pic clicked",Toast.LENGTH_SHORT).show();
            }
        });

        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editusername.getText().toString().isEmpty() || editphone.getText().toString().isEmpty() || editmail.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this,"one or many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email=editmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef=nStore.collection("users").document(user.getUid());
                        Map<String,Object> edited=new HashMap<>();
                        edited.put("emaill",email);
                        edited.put("uname",editusername.getText().toString());
                        edited.put("phoneee",editphone.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this,"profile updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditProfile.this, ProfileActivity.class));
                                finish();
                            }
                        });
                        //Toast.makeText(EditProfile.this,"email is changed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,@androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri=data.getData();
                //mprofileimage.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri){
        final StorageReference fileref=storageReference.child("users/"+nAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(editprofileimage);
                    }
                });
                //Toast.makeText(ProfileActivity.this,"image uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}