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
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddProfDialog extends DialogFragment {
    private SchoolDB schoolDB;
    private List<Prof> prof;
    private EditText prof_firstTxtName, prof_lastTxtName,prof_img_Url,prof_email,prof_phone;
    private TextView txtUserName, txtWarning;
    private Button btnAddProf;

    public interface AddProf {
        void onAddProfResult (Prof prof);
    }

    private AddProf addProf;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_add_prof_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Teacher")
                .setView(view);

        initViews(view);
        /** 1  get  student names */
        Bundle bundle = getArguments();
        btnAddProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProf();
            }
        });
        return builder.create();
    }

    private void initViews(View view) {
        Log.d(TAG, "initViews: started");
        prof_firstTxtName = view.findViewById(R.id.prof_firstTxtName);
        prof_lastTxtName =  view.findViewById(R.id.prof_lastTxtName);
        prof_email=view.findViewById(R.id.prof_email);
        prof_phone=view.findViewById(R.id.prof_phone);
        prof_img_Url= view.findViewById(R.id.prof_img_URL);
        txtUserName =  view.findViewById(R.id.userName);
        txtWarning =  view.findViewById(R.id.txtWarning);
        btnAddProf = view.findViewById(R.id.btnAddProf);
    }

    private void addProf () {
        Log.d(TAG, "addProf:  started");
        if (validateData()) {
            String firstname = prof_firstTxtName.getText().toString();
            String lastname = prof_lastTxtName.getText().toString();
            String email=prof_email.getText().toString();
            String phone_number=prof_phone.getText().toString();
            String img_URL=prof_img_Url.getText().toString();
            String date = getCurrentDate();
            Prof prof1 = new Prof(firstname,lastname,email,phone_number,img_URL);


            try {

                schoolDB= SchoolDB.getInstance(getActivity());
                if(schoolDB!=null){
                    Log.d(TAG, "onAddProfResult: prof"+prof1+" added");
                    try {
                        schoolDB.profDao().insert(prof1);
                        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new ProfFragment());
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
        if (prof_firstTxtName.getText().toString().equals("")) {
            return false;
        }
        if (prof_lastTxtName.getText().toString().equals("")) {
            return false;
        }
        return true;
    }
}