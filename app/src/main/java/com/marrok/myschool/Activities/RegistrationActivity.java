package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.marrok.myschool.Entities.User;
import com.marrok.myschool.R;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView alreadyHave;
    private EditText userName,password,email,img_url;
    private ProgressBar progressBar;
    private Button BtnRegister;

    private void initView() {
        alreadyHave = findViewById(R.id.already_hav);
        email=findViewById(R.id.rectangle4_email);
        userName=findViewById(R.id.rectangle4_username);
        password=findViewById(R.id.rectangle4_password);
        progressBar=findViewById(R.id.progress_bar1);
        BtnRegister=findViewById(R.id.btnRegister);
        img_url=findViewById(R.id.img_reg_url);
    }
    private void registerUser() {
        String mail=email.getText().toString().trim();
        String username=userName.getText().toString().trim();
        String pass= password.getText().toString().trim();
        String img_ur=img_url.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("pleas provide valid email");
            email.requestFocus();
            return;
        }
        if(pass.length()<6){
            password.setError("min pass length is 6");
            password.requestFocus();
            return;

        }

        if(mail.isEmpty()){
            email.setError("Full Name is required");
            email.requestFocus();
            return;
        }
        if(username.isEmpty()){
            userName.setError("username is required");
            userName.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("password is required");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user =new User(mail,username);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegistrationActivity.this, "user has been registred", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                    }else{
                                        Toast.makeText(RegistrationActivity.this, "failed to register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegistrationActivity.this, "failed to register", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .setPhotoUri(Uri.parse(img_ur))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
    private void setClicklistener() {
        alreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth=FirebaseAuth.getInstance();
        /////////////////
        initView();
        progressBar.setVisibility(View.GONE);
        setClicklistener();
    }




}