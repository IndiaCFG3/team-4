package com.dev5151.educate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev5151.educate.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    EditText mlogin_email,mlogin_password;
    Button mlogin,mlink_to_register,mforgotpass;
    private ProgressDialog progressDialog;
    //private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if(doc.getBoolean("isStudent")) {
                            Toast.makeText(AuthActivity.this,"Welcome Student", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AuthActivity.this,MainActivity.class));
                        } else {
                            Toast.makeText(AuthActivity.this,"Welcome Teacher", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AuthActivity.this,MainActivity2.class));
                        }
                        finish();
                    }
                }
            });
        }
        progressDialog=new ProgressDialog(AuthActivity.this);
        progressDialog.setMessage("Signing in");

        mlogin_email=findViewById(R.id.login_email);
        mlogin_password=findViewById(R.id.login_password);

        mlogin=findViewById(R.id.login);
        mlink_to_register=findViewById(R.id.link_to_register);
        mforgotpass=findViewById(R.id.forgotPass);


        mlink_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthActivity.this,AuthActivity2.class));
                finish();
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                signIn();
            }
        });

        mforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail= new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("reset password?");
                passwordResetDialog.setMessage("enter your email to received reset link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail=resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AuthActivity.this,"Reset link sent to your mail",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AuthActivity.this,"Reset link is not sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
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
        });

    }
    private void signIn() {
        if(!validateForm()) {
            return;
        }

        String em=mlogin_email.getText().toString();
        String pw=mlogin_password.getText().toString();

        mAuth.signInWithEmailAndPassword(em,pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(AuthActivity.this,"Sign In Successful",Toast.LENGTH_SHORT).show();
                            mFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot doc = task.getResult();
                                        if(doc.getBoolean("isStudent")) {
                                            Toast.makeText(AuthActivity.this,"Welcome Student", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(AuthActivity.this,MainActivity.class));
                                        } else {
                                            Toast.makeText(AuthActivity.this,"Welcome Teacher", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(AuthActivity.this,MainActivity2.class));
                                        }
                                        finish();
                                    }
                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AuthActivity.this,"Sign In Failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid=true;
        String em=mlogin_email.getText().toString();
        if (TextUtils.isEmpty(em)){
            mlogin_email.setError("Required.");
            valid=false;
        } else{
            mlogin_email.setError(null);
        }

        String pw=mlogin_password.getText().toString();
        if(TextUtils.isEmpty(pw)){
            mlogin_password.setError("Required.");
            valid=false;
        } else{
            mlogin_password.setError(null);
        }

        return valid;
    }

}