package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marrok.myschool.Dialog.AddStudentDialog;
import com.marrok.myschool.R;

public class StudentFragment  extends Fragment {
    BottomNavigationView bottomNavigationView ;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_student_fragment, container, false);
        bottomNavigationView =view.findViewById(R.id.navigation);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddStudentDialog addStudentDialog =new AddStudentDialog();
                Bundle bundle=new Bundle();
                /**send the student info here */
                addStudentDialog.setArguments(bundle);
                addStudentDialog.show(getParentFragmentManager(),"add Student dialog");

            }
        });
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: start");
    }
}