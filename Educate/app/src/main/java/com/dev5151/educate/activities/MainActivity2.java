package com.dev5151.educate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dev5151.educate.R;
import com.dev5151.educate.fragments.JoinCourseBottomSheetFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    private FirebaseAuth kAuth;
    String userIDDD;
    FirebaseUser userrr;
    MaterialButton btnJoinCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        kAuth = FirebaseAuth.getInstance();
        userIDDD = kAuth.getCurrentUser().getUid();
        userrr = kAuth.getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnJoinCourse = findViewById(R.id.join_course);

        btnJoinCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,Add_Course_Teacher.class);
                startActivity(intent);
            }
        });
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
            Toast.makeText(MainActivity2.this, "you have selected profile", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity2.this, ProfileActivity.class));
        } else if (item.getItemId() == R.id.changepass) {
            Toast.makeText(MainActivity2.this, "you have selected change password", Toast.LENGTH_SHORT).show();
            final EditText resetpassword = new EditText(MainActivity2.this);
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(MainActivity2.this);
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
                            Toast.makeText(MainActivity2.this, "password reset successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity2.this, "password reset failed", Toast.LENGTH_SHORT).show();
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