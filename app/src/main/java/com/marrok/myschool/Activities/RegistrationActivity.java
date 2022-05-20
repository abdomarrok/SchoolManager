package com.marrok.myschool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.marrok.myschool.R;

public class RegistrationActivity extends AppCompatActivity {
    private TextView Sign_in;
    private EditText rectangle4_email,rectangle4_username,rectangle4_password,rectangle4_confirm_password;
    boolean[] firstClickEmail={true},firstClickUsername={true},firstClickPass={true},firstClickConfirmPass={true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        /////////////////
        Sign_in = findViewById(R.id.already_hav);
        rectangle4_email=findViewById(R.id.rectangle4_email);
        rectangle4_username=findViewById(R.id.rectangle4_username);
        rectangle4_password=findViewById(R.id.rectangle4_password);
        rectangle4_confirm_password=findViewById(R.id.rectangle4_confirm_password);
        ////////////////////
        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        rectangle4_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firstClickEmail[0]){
                    rectangle4_email.setText("");
                    firstClickEmail[0] =false;
                }
            }
        });
        rectangle4_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firstClickUsername[0]){
                    rectangle4_username.setText("");
                    firstClickUsername[0] =false;
                }
            }
        });

        rectangle4_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(firstClickPass[0]||firstClickConfirmPass[0]){
                    rectangle4_password.setText("");
                    rectangle4_confirm_password.setText("");
                    firstClickPass[0] =false;
                    firstClickConfirmPass[0]=false;
                }
            }
        });
    }


}