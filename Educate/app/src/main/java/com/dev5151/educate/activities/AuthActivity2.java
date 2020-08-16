package com.dev5151.educate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dev5151.educate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    FirebaseFirestore fStore;
    String userID;
    int radioInt;
    EditText mphone,musername;
    EditText email,password,conPassword;
    Button register,link_to_log;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth2);
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(AuthActivity2.this, MainActivity.class));
            finish();
        }
        progressDialog=new ProgressDialog(AuthActivity2.this);
        progressDialog.setMessage("Creating");

        radioGroup = findViewById(R.id.radioGroup);

        email=findViewById(R.id.register_email);
        password=findViewById(R.id.register_password);
        conPassword=findViewById(R.id.register_cpassword);

        mphone=findViewById(R.id.register_phone);
        musername=findViewById(R.id.register_username);

        register=findViewById(R.id.register);
        link_to_log=findViewById(R.id.link_to_login);

        link_to_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthActivity2.this,AuthActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                radioInt = radioGroup.getCheckedRadioButtonId();
                signup();
            }
        });

    }
    private void signup() {

        if(!validateForm()) {
            return;
        }
        final String susername=musername.getText().toString();
        final String sphone=mphone.getText().toString();
        final String em=email.getText().toString();
        final String pw=password.getText().toString();

        mAuth.createUserWithEmailAndPassword(em,pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(AuthActivity2.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            userID=mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("Users").document(userID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("uname",susername);
                            user.put("email",em);
                            user.put("phone",sphone);
                            user.put("uuid",userID);
                            user.put("isStudent",radioInt==2131165335);
                            user.put("courses",new ArrayList<String>());
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Log.d(TAG,"user profile is created for " + userID );
                                }
                            });
                            startActivity(new Intent(AuthActivity2.this,AuthActivity.class));
                            finish();
                        } if(!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(AuthActivity2.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid=true;
        String em=email.getText().toString();
        if (TextUtils.isEmpty(em)){
            email.setError("Required.");
            valid=false;
        } else{
            email.setError(null);
        }

        String pw=password.getText().toString();
        if(TextUtils.isEmpty(pw)){
            password.setError("Required.");
            valid=false;
        } else{
            password.setError(null);
        }

        String cpw=conPassword.getText().toString();
        if(TextUtils.isEmpty(cpw)){
            conPassword.setError("Required.");
            valid=false;
        } else{
            conPassword.setError(null);
        }

        if(!(cpw.equals(pw))) {
            conPassword.setError("Re enter password!");
            valid=false;
        } else {
            conPassword.setError(null);
        }
        return valid;
    }

}


