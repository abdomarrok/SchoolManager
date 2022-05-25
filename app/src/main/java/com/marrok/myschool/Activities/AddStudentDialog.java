package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Enrollment;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Student;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.Entities.aClass;
import com.marrok.myschool.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddStudentDialog extends DialogFragment {
    private SchoolDB schoolDB;
    private EditText student_firstTxtName,student_lastTxtName,student_img_Url,student_email,student_phone,parent_student_phone;
    private TextView txtUserName, txtWarning;
    private Button btnAddStudent;

    public interface AddStudent {
        void onAddStudentResult (Student student);
    }

    private AddStudentDialog.AddStudent addStudent;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_add_student_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Student")
                .setView(view);

        initViews(view);
        Bundle bundle = getArguments();
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
        return builder.create();
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: started");
        student_firstTxtName = view.findViewById(R.id.student_firstTxtName);
        student_lastTxtName =  view.findViewById(R.id.student_lastTxtName);
        student_email=view.findViewById(R.id.student_email);
        student_phone=view.findViewById(R.id.student_phone_number);
        parent_student_phone=view.findViewById(R.id.parent_phone_number);
        student_img_Url= view.findViewById(R.id.student_img_URL);
        txtUserName =  view.findViewById(R.id.userName);
        txtWarning =  view.findViewById(R.id.txtWarning);
        btnAddStudent = view.findViewById(R.id.btnAddStudent);
    }

    private void addStudent () {
        Log.d(TAG, "addStudent:  started");
        if (validateData()) {
            String firstname = student_firstTxtName.getText().toString();
            String lastname = student_lastTxtName.getText().toString();
            String email=student_email.getText().toString();
            String phone_number=student_phone.getText().toString();
            String parent_phone=parent_student_phone.getText().toString();
            String img_URL=student_img_Url.getText().toString();
            String date = getCurrentDate();
            Student student1=new Student(img_URL,firstname,lastname,true,
                    "0",phone_number,parent_phone,email);

            try {

                schoolDB= SchoolDB.getInstance(getActivity());
                if(schoolDB!=null){
                    try {
                        schoolDB.studentDao().insert(student1);
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new StudentFragment());
                        transaction.commit();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                    dismiss();
                }
                dismiss();
            }catch (ClassCastException e) {
                e.printStackTrace();
            }
        }else {
            txtWarning.setVisibility(View.VISIBLE);
        }
    }

    private String getCurrentDate() {
        Log.d(TAG, "getCurrentDate: called");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    private boolean validateData () {
        Log.d(TAG, "validateData: started");
        if (student_firstTxtName.getText().toString().equals("")) {
            return false;
        }
        if (student_lastTxtName.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

}