package com.dev5151.educate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.dev5151.educate.R;
import com.dev5151.educate.interfaces.OnClickInterface;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.View;

import com.dev5151.educate.fragments.JoinCourseBottomSheetFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth kAuth;
    String userIDDD;
    FirebaseUser userrr;
    MaterialButton btnJoinCourse;
    RecyclerView courseList;
    public static OnClickInterface onClickInterface;
    student_courses_adapter mStudent_courses_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kAuth = FirebaseAuth.getInstance();
        userIDDD = kAuth.getCurrentUser().getUid();
        userrr = kAuth.getCurrentUser();
        courseList = findViewById(R.id.recyclerView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnJoinCourse = findViewById(R.id.join_course);
        mStudent_courses_adapter = new student_courses_adapter(this);
        courseList.setLayoutManager(new LinearLayoutManager(this));

        courseList.setAdapter(mStudent_courses_adapter);


        btnJoinCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JoinCourseBottomSheetFragment joinCourseBottomSheetFragment = new JoinCourseBottomSheetFragment();
                joinCourseBottomSheetFragment.show(getSupportFragmentManager(), joinCourseBottomSheetFragment.getTag());

            }
        });


        onClickInterface = new OnClickInterface() {
            @Override
            public void onClickCourse(String courseId) {
                Intent intent = new Intent(getApplicationContext(), CourseActivity.class);
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signout) {
            FirebaseAuth.getInstance().signOut();
            finish();
        } else if (item.getItemId() == R.id.profile) {
            Toast.makeText(MainActivity.this, "you have selected profile", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (item.getItemId() == R.id.changepass) {
            Toast.makeText(MainActivity.this, "you have selected change password", Toast.LENGTH_SHORT).show();
            final EditText resetpassword = new EditText(MainActivity.this);
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(MainActivity.this);
            passwordResetDialog.setTitle("reset password?");
            passwordResetDialog.setMessage("enter new password");
            passwordResetDialog.setView(resetpassword);

            passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newpassword = resetpassword.getText().toString();
                    userrr.updatePassword(newpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "password reset successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "password reset failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            passwordResetDialog.create().show();
        }

        return true;

    }


}
