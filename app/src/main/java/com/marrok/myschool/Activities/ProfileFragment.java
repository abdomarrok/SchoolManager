package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Student;
import com.marrok.myschool.R;

public class ProfileFragment extends Fragment {
    SchoolDB schoolDB;
    Prof prof;
    Student student;
    TextView name,email,phone,classes,finance,profile_id;
    ImageView avatar;

    private void initView(View v) {
        profile_id=v.findViewById(R.id.profile_id);
        name=v.findViewById(R.id.name);
        email=v.findViewById(R.id.email);
        phone=v.findViewById(R.id.phone);
        classes=v.findViewById(R.id.classes);
        finance=v.findViewById(R.id.finance);
        avatar=v.findViewById(R.id.avatar);
    }
    private void initprofData() {
        Log.d(TAG, "initData: started");
        profile_id.setText(String.valueOf(prof.getProf_id()));
        name.setText(prof.getFirstName()+" "+prof.getLastName());
        email.setText(prof.getEmail());
        phone.setText(prof.getPhone_number());
        Glide.with(this)
                .asBitmap()
                .load(prof.getImgUrl())
                .into(avatar);
        
    }
    private void initstudentData() {
        Log.d(TAG, "initData: started");
        profile_id.setText(String.valueOf(student.getStudent_id()));
        name.setText( student.getFirstName()+" "+student.getLastName());
        email.setText(student.getEmail());
        phone.setText(student.getPhoneNumber());
        Glide.with(this)
                .asBitmap()
                .load(student.getImgUrl())
                .into(avatar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int prof_id=0,student_id=0;
        View view=inflater.inflate(R.layout.activity_profile_fragment, container, false);
        initView(view);
        Bundle arguments = getArguments();
          prof_id = arguments.getInt("prof_id");
          student_id=arguments.getInt("student_id");
        if(arguments.getInt("prof_id")!=0){
            try {
                schoolDB = SchoolDB.getInstance(getActivity());
                if (schoolDB != null) {
                    try {
                        prof = schoolDB.profDao().getProfById(prof_id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }catch (SQLException e){
            }

            Log.d(TAG, "onCreateView: prof id"+prof.getLastName());
        }
        if(arguments.getInt("student_id")!=0){
            try {
                schoolDB = SchoolDB.getInstance(getActivity());
                if (schoolDB != null) {
                    try {
                        student=schoolDB.studentDao().getStudentById(student_id);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }catch (SQLException e){
            }

            Log.d(TAG, "onCreateView: student id"+student.getFirstName());
        }
        if(prof!=null){
            initprofData();
        }
        if(student!=null){
            initstudentData();
        }

        return view;
    }




}