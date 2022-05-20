package com.marrok.myschool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.marrok.myschool.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView Register;
    private EditText usernameTXT,passwordTXT;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Register =  findViewById(R.id.Register_txt);
        btnLogin = findViewById(R.id.btnLogin);
        usernameTXT=findViewById(R.id.rectangle4_username);
        passwordTXT=findViewById(R.id.rectangle4_password);
        final Boolean[] firstClickUser = {true};
        final Boolean[] firstClickPass = {true};

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        usernameTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstClickUser[0]){
                    usernameTXT.setText("");
                    firstClickUser[0] =false;
                }
            }
        });
        passwordTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstClickPass[0]){
                    passwordTXT.setText("");
                    firstClickPass[0] =false;
                }
            }
        });


    }


}