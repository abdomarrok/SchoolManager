package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddSubjectDialog extends DialogFragment {
    private SchoolDB schoolDB;
    private List<Subject> subjects;
    private EditText subject_name,subject_img_Url;
    private TextView txtUserName, txtWarning;
    private Button btnAddSubject;

    public interface AddSubject {
        void onAddSubjectResult (Subject subject);
    }

    private AddSubjectDialog.AddSubject addSubject;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_add_subject_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Subject")
                .setView(view);
        initViews(view);
        Bundle bundle = getArguments();
        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubject();
            }
        });
        return builder.create();
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: started");
        subject_name = view.findViewById(R.id.subjectName_dialog);
        subject_img_Url =  view.findViewById(R.id.subject_img_url);
        txtUserName =  view.findViewById(R.id.userName);
        txtWarning =  view.findViewById(R.id.txtWarning);
        btnAddSubject = view.findViewById(R.id.btnAddSubject);
    }

    private void addSubject () {
        Log.d(TAG, "addSubject: started");
        if (validateData()) {
            String subjectname = subject_name.getText().toString();
            String img_URL=subject_img_Url.getText().toString();
            String date = getCurrentDate();
            Subject subject1=new Subject(subjectname,img_URL);


            try {

                schoolDB= SchoolDB.getInstance(getActivity());
                if(schoolDB!=null){
                    Log.d(TAG, "onAddSubjectResult: subject"+subject1+" added");
                   try {
                        schoolDB.subjectDao().insert(subject1);
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new SubjectFragment());
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
        if (subject_name.getText().toString().equals("")) {
            return false;
        }
        if (subject_img_Url.getText().toString().equals("")) {
            return false;
        }
        return true;
    }
}