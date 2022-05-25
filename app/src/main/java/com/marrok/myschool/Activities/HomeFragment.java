package com.marrok.myschool.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marrok.myschool.R;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private TextView teachers_TXT,subject_TXT,class_TXT,student_TXT,enrollment_TXT;
    private TextView mangerName,email_manager;
    private ImageView manager_img;
    private FirebaseAuth mAuth;

    private void initView(View view) {
        teachers_TXT=view.findViewById(R.id.teacher);
        subject_TXT=view.findViewById(R.id.subject_id);
        class_TXT=view.findViewById(R.id.class_id_txt);
        student_TXT=view.findViewById(R.id.student_txt2);
        enrollment_TXT=view.findViewById(R.id.enrollment_txt);
        mangerName=view.findViewById(R.id.manager_nam);
        email_manager=view.findViewById(R.id.email_manager);
        manager_img=view.findViewById(R.id.user_avatar);
    }
    private void setOnClickListener() {
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        teachers_TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.fragment_container, new ProfFragment());
                transaction.commit();
            }
        });
        subject_TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.fragment_container, new SubjectFragment());
                transaction.commit();
            }
        });
        class_TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.fragment_container, new ClassesFragment());
                transaction.commit();
            }
        });
        student_TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.fragment_container, new StudentFragment());
                transaction.commit();
            }
        });
        enrollment_TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.fragment_container, new EnrollmentFragment());
                transaction.commit();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view=inflater.inflate(R.layout.activity_home_fragment, container, false);
        initView(view);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            String img_url=user.getPhotoUrl().toString();
            mangerName.setText(name);
             mangerName.setText(name);
             email_manager.setText(email);
             Glide.with(getActivity())
             .asBitmap()
             .load(img_url)
             .into(manager_img);
        }

        setOnClickListener();
        return view;
    }




}