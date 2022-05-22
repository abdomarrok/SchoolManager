package com.marrok.myschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.marrok.myschool.R;

public class DashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private static final String TAG = "DashboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        bottomNavigationView=findViewById(R.id.navigation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        navigate(R.id.home_txt);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.home_txt:
                        transaction.replace(R.id.fragment_container, new HomeFragment());
                        transaction.commit();
                        break;
                    case R.id.student_txt:
                        transaction.replace(R.id.fragment_container, new StudentFragment());
                        transaction.commit();
                        break;
                    case R.id.profile_txt:
                        //todo : send user information
                        Intent intent =new Intent(DashboardActivity.this,ProfileUserActivity.class);
                            startActivity(intent);
                        break;
                    default:
                        break;

                }
                return false;
            }
        });

    }

    protected void navigate(int item_id) {
        Log.d(TAG, "navigate:  "+item_id);
        bottomNavigationView.setSelectedItemId(item_id);
    }
}