package com.marrok.myschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.marrok.myschool.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView register,forgot_pass;
    private EditText email,password;
    private Button signInBtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private void initView() {
        forgot_pass=findViewById(R.id.Forgot_pass);
        signInBtn=findViewById(R.id.btnLogin);
        email=findViewById(R.id.rectangle4_email_login);
        password=findViewById(R.id.rectangle4_password_login);
        register=findViewById(R.id.Register_txt);
        progressBar=findViewById(R.id.progress_bar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        initView();
        progressBar.setVisibility(View.GONE);
        setOnClickListner();



    }

    private void setOnClickListner() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogIn();
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }

    private void userLogIn() {
        String mail=email.getText().toString().trim();
        String pass=password.getText().toString().trim();
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

        if(pass.isEmpty()){
            password.setError("password is required");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,DashboardActivity.class));

                }else{
                    Toast.makeText(LoginActivity.this, "failed signIn check your info", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}