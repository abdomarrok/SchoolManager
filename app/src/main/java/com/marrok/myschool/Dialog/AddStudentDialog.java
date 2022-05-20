package com.marrok.myschool.Dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marrok.myschool.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddStudentDialog extends DialogFragment {
    private static final String TAG = "addReviewDialog";
    private EditText student_firstTxtName, student_lastTxtName,student_img_Url;
    private TextView txtUserName, txtWarning;
    private Button btnAddStudent;

    private int img_id = 0;

  

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_add_student_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Add Student")
                .setView(view);
        initViews(view);
        /** 1  get  student names */
        Bundle bundle = getArguments();
        
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "add clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
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

    private String getCurrentDate() {
        Log.d(TAG, "getCurrentDate: called");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    private void initViews (View view) {
        Log.d(TAG, "initViews: started");
        student_firstTxtName = (EditText) view.findViewById(R.id.student_firstTxtName);
        student_lastTxtName = (EditText) view.findViewById(R.id.student_lastTxtName);
        student_img_Url=(EditText) view.findViewById(R.id.student_img_URL);
        txtUserName = (TextView) view.findViewById(R.id.userName);
        txtWarning = (TextView) view.findViewById(R.id.txtWarning);
        btnAddStudent = (Button) view.findViewById(R.id.btnAddStudent);
    }
}