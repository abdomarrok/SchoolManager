package com.marrok.myschool.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marrok.myschool.Adapters.ClassAdapter;
import com.marrok.myschool.Adapters.EnrollmentAdapter;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Enrollment;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Student;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.Entities.aClass;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentFragment extends Fragment {
    private static final String TAG = "EnrollmentFragment";
    private TextView total;
    private List<aClass> classes;
    private List<Student> students;
    private List<Enrollment> enrollments;
    private RecyclerView recyclerView;
    private SchoolDB schoolDB;
    private Spinner spinner_classes,spinner_student;
    private EditText price_txt;
    private EnrollmentAdapter enrollmentAdapter;
    ArrayList<String> student;
    ArrayList<String> aClass;
    String student_spinner_str_item="e",class_spinner_str_item="e";
    private Button CreateEnrollmentBtn;
    private ArrayAdapter<String> studentSpinnerAdapter,classSpinnerAdapter;
    private void initView(View view) {
        total=view.findViewById(R.id.total_value);
        recyclerView=view.findViewById(R.id.enrollments_recycler_view);
        spinner_classes=view.findViewById(R.id.spinner_class);
        spinner_student=view.findViewById(R.id.spinner_student);
        CreateEnrollmentBtn=view.findViewById(R.id.createEnrollment);
        price_txt=view.findViewById(R.id.price_edt);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view=inflater.inflate(R.layout.activity_enrollment_fragment, container, false);
        classes=new ArrayList<>();
        students=new ArrayList<>();

        enrollments=new ArrayList<>();
        enrollmentAdapter=new EnrollmentAdapter(getActivity());

        student=new ArrayList<>();
        aClass=new ArrayList<>();

        initView(view);
        spinner_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                student_spinner_str_item = spinner_student.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                class_spinner_str_item = spinner_classes.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Student_Class_AsyncTask student_class_asyncTask=new Student_Class_AsyncTask();
        EnrollmentAsyncTask enrollmentAsyncTask=new EnrollmentAsyncTask();

        student_class_asyncTask.execute();
        enrollmentAsyncTask.execute();
        recyclerView.setAdapter(enrollmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CreateEnrollmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked");
                if(schoolDB!=null){
                    int student_id,class_id;
                    if(!student_spinner_str_item.equals("e") && !class_spinner_str_item.equals("e")) {
                        student_id=schoolDB.studentDao().getStudentByName(student_spinner_str_item," ");
                        class_id=schoolDB.aClassDao().getClassIdByName(class_spinner_str_item);
                        Log.d(TAG, "onClick: student id from db "+student_id);
                        Log.d(TAG, "onClick: student_spinner_item"+student_spinner_str_item);

                        try {
                            schoolDB= SchoolDB.getInstance(getActivity());
                            if(schoolDB!=null){
                                try {
                                    Toast.makeText(getActivity(), "student"+student_id+"\n class_id"+class_id, Toast.LENGTH_SHORT).show();
                                    Enrollment enrollment =new Enrollment(class_id,student_id,price_txt.getText().toString());
                                    schoolDB.enrollmentDao().insert(enrollment);
                                    String oldCredit =schoolDB.studentDao().getOldCredit(student_id);
                                    String newCredit=price_txt.getText().toString();
                                    String sumCredit= String.valueOf((Double.valueOf(oldCredit)+Double.valueOf(newCredit)));
                                    schoolDB.studentDao().updateCredit(sumCredit,student_id);
                                    FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragment_container, new EnrollmentFragment());
                                    transaction.commit();
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }
                            }
                        }catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        return view;
    }




    public class EnrollmentAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: started ");
            super.onPreExecute();
            enrollmentAdapter.clearAdapter();
            schoolDB= SchoolDB.getInstance(getActivity());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: started");
            if(schoolDB!=null){
                enrollments.clear();
                enrollments=schoolDB.enrollmentDao().getAllEnrollment();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            enrollmentAdapter.setEnrollments(enrollments);// giving the model to the adapter
            enrollmentAdapter.notifyDataSetChanged();

            ////////////////
            total.setText(((Integer) enrollmentAdapter.getItemCount()).toString());
        }
    }

    public class Student_Class_AsyncTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: started ");
            super.onPreExecute();
            schoolDB=SchoolDB.getInstance(getActivity());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: started");
            if(schoolDB!=null){
                students=schoolDB.studentDao().getAllStudent();
                classes=schoolDB.aClassDao().getAllClasses();
            }return null;

        }

        @Override
        protected void onPostExecute(Void unused) {
            Log.d(TAG, "onPostExecute: calls");
            for (Student student1 : students) {
                student.add(student1.getFirstName()+" "+student1.getLastName());
            }
            for (aClass class1 : classes) {
                aClass.add(class1.getClassName());
            }

            super.onPostExecute(unused);
            spinner_classes.setAdapter(new ArrayAdapter<String>(getActivity(),
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    aClass
            ));
            spinner_student.setAdapter(new ArrayAdapter<String>(getActivity(),
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    student
            ));

        }

    }
}