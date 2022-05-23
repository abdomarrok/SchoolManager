package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marrok.myschool.Adapters.ClassAdapter;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.Entities.aClass;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment  extends Fragment {
    private static final String TAG = "ClassesFragment";
    private TextView total;
    private List<Prof> profs;
    private List<Subject> subjects;
    private List<aClass> classes;
    private RecyclerView recyclerView;
    private SchoolDB schoolDB;
    private Spinner spinner_prof,spinner_subject;
    private ClassAdapter classAdapter;
    ArrayList<String> prof;
    ArrayList<String> subject;

    String subject_spinner_str_item="e",prof_spinner_str_item="e";
    private Button CreateClassBtn;
    private ArrayAdapter<String> subjectSpinnerAdapter,profSpinnerAdapter;
    private void initView(View view) {
        total=view.findViewById(R.id.total_value);
        recyclerView=view.findViewById(R.id.classes_recycler_view);
        spinner_prof=view.findViewById(R.id.spinner_prof);
        spinner_subject=view.findViewById(R.id.spinner_subject);
        CreateClassBtn=view.findViewById(R.id.createClass);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view=inflater.inflate(R.layout.activity_classes_fragment, container, false);
        subjects=new ArrayList<>();
        profs=new ArrayList<>();

        classes=new ArrayList<>();
        classAdapter=new ClassAdapter(getActivity());

        prof=new ArrayList<>();
        subject=new ArrayList<>();

        initView(view);
        spinner_prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prof_spinner_str_item = spinner_prof.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject_spinner_str_item = spinner_subject.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Subject_Prof_AsyncTask subject_prof_AsyncTask=new Subject_Prof_AsyncTask();
        ClassAsyncTask classAsyncTask=new ClassAsyncTask();

        subject_prof_AsyncTask.execute();
        classAsyncTask.execute();
        recyclerView.setAdapter(classAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CreateClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked");
                if(schoolDB!=null){
                    int subject_id,prof_id;
                    if(!subject_spinner_str_item.equals("e") && !prof_spinner_str_item.equals("e")) {
                        subject_id = schoolDB.subjectDao().getSubjectIdByName(subject_spinner_str_item);
                        prof_id = schoolDB.profDao().getProfIdByName(prof_spinner_str_item, " ");

                        try {
                            schoolDB= SchoolDB.getInstance(getActivity());
                            if(schoolDB!=null){
                                try {
                                    Toast.makeText(getActivity(), "subject id"+subject_id+"\n prof id"+prof_id, Toast.LENGTH_SHORT).show();
                                    aClass aClass=new aClass(prof_id,subject_id,prof_spinner_str_item+subject_spinner_str_item);
                                    schoolDB.aClassDao().insert(aClass);
                                    FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragment_container, new ClassesFragment());
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



    public class Subject_Prof_AsyncTask extends AsyncTask<Void,Void,Void> {
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
                    subjects=schoolDB.subjectDao().getAllSubject();
                    profs=schoolDB.profDao().getAllProf();
            }return null;

        }

        @Override
        protected void onPostExecute(Void unused) {
            Log.d(TAG, "onPostExecute: calls");
            for (Subject subject1 : subjects) {
                subject.add(subject1.getSubject_name());
            }
            for (Prof prof1 : profs) {
                prof.add(prof1.getFirstName()+" "+prof1.getLastName());
            }

            super.onPostExecute(unused);
            spinner_subject.setAdapter(new ArrayAdapter<String>(getActivity(),
                                                                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                                                                subject
            ));
            spinner_prof.setAdapter(new ArrayAdapter<String>(getActivity(),
                                                             com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                                                             prof
            ));

        }

    }

    public class ClassAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: started ");
            super.onPreExecute();
            classAdapter.clearAdapter();
            schoolDB=SchoolDB.getInstance(getActivity());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: started");
            if(schoolDB!=null){
                classes.clear();
                classes=schoolDB.aClassDao().getAllClasses();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            classAdapter.setClass(classes);// giving the model to the adapter
            classAdapter.notifyDataSetChanged();

            ////////////////
            total.setText(((Integer) classAdapter.getItemCount()).toString());
        }
    }
}