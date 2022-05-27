package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marrok.myschool.R;

public class ProfileUserActivity extends AppCompatActivity {
    private TextView mangerName,email_manager,manager_id,username,phone;
    private ImageView manager_img;
    private FirebaseAuth mAuth;

    private void initView() {
        username=findViewById(R.id.userName);
        phone=findViewById(R.id.phone);
        manager_id=findViewById(R.id.User_id);
        mangerName=findViewById(R.id.User);
        email_manager=findViewById(R.id.email);
        manager_img=findViewById(R.id.avatar);
        username.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        initView();
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            try{
                String name = user.getDisplayName();
                String email = user.getEmail();
                String User_id= user.getUid();
                String img_url=user.getPhotoUrl().toString();
                Log.d(TAG, "onCreate: +"+name);
                mangerName.setText(name);
                manager_id.setText(User_id);
                email_manager.setText(email);
                Glide.with(ProfileUserActivity.this)
                        .asBitmap()
                        .load(img_url)
                        .into(manager_img);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}