package com.marrok.myschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.marrok.myschool.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button resetBtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth =FirebaseAuth.getInstance();
        initView();
        progressBar.setVisibility(View.GONE);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();

            }
        });
    }

    private void resetPassword() {

        String mail=email.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("pleas provide valid email");
            email.requestFocus();
            return;
        }
        if(mail.isEmpty()){
            email.setError("Full Name is required");
            email.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "email was send check email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "try again something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void initView() {
        progressBar=findViewById(R.id.progress_bar2);
        resetBtn=findViewById(R.id.btnReset);
        email=findViewById(R.id.rectangle4_email_reset);
    }
}