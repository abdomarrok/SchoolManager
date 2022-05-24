package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marrok.myschool.Adapters.ProfAdapter;
import com.marrok.myschool.Adapters.StudentAdapter;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Student;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment  extends Fragment implements AddStudentDialog.AddStudent {
    BottomNavigationView bottomNavigationView ;
    private FloatingActionButton fab;
    private TextView total;
    private List<Student> students;
    private RecyclerView recyclerView;
    private SchoolDB schoolDB;
    private StudentAdapter adapter;



    @Override
    public void onAddStudentResult(Student student) {
        Log.d(TAG, "onAddProfResult: prof"+student+" added");
        try {
            schoolDB.studentDao().insert(student);
            StudentAsyncTask studentAsyncTask=new StudentAsyncTask();
            studentAsyncTask.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        total.setText(((Integer) adapter.getItemCount()).toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_student_fragment, container, false);
        bottomNavigationView =view.findViewById(R.id.navigation);
        bottomNavigationView = view.findViewById(R.id.navigation);
        recyclerView = view.findViewById(R.id.student_recycler_view);
        total = view.findViewById(R.id.total_value);
        students = new ArrayList<>();
        fab = view.findViewById(R.id.fab);
        adapter = new StudentAdapter(getActivity());//create adapter instance
        StudentAsyncTask studentAsyncTask=new StudentAsyncTask();
        studentAsyncTask.execute();
        recyclerView.setAdapter(adapter);//set the Student model adapter to recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // chose tha way to show item

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

    public class StudentAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: started ");
            super.onPreExecute();
            adapter.clearAdapter();
            schoolDB=SchoolDB.getInstance(getActivity());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: started");
            if(schoolDB!=null){
                students.clear();
                students=schoolDB.studentDao().getAllStudent();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d(TAG, "onPostExecute: s");
            adapter.setStudents(students);// giving the model to the adapter
            adapter.notifyDataSetChanged();

            ////////////////
            total.setText(((Integer) adapter.getItemCount()).toString());
        }

    }

}