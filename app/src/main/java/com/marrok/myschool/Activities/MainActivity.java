package com.marrok.myschool.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.marrok.myschool.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getStart = (Button) findViewById(R.id.get_started);
        getStart.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
    }
}