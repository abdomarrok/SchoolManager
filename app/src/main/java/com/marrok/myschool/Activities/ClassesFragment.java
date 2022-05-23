package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marrok.myschool.Adapters.SubjectAdapter;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends Fragment {
    SchoolDB schoolDB;
    BottomNavigationView bottomNavigationView;
    private Button addClass;
    private TextView total;
    private List<Class> aClass;
    private List<Subject> subject;
    private List<Prof> prof;
    private Spinner subject_spinner,prof_spinner;
    private RecyclerView recyclerView;
    private ArrayAdapter<String> subjectSpinnerAdapter,profSpinnerAdapter;

    //private ClassAdapter classAdapter;

    private void initView(View v){
        addClass=v.findViewById(R.id.addClass);
        total=v.findViewById(R.id.total_value);
        subject_spinner=v.findViewById(R.id.spinner_subject);
        prof_spinner=v.findViewById(R.id.spinner_prof);
        recyclerView=v.findViewById(R.id.classes_recycler_view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_classes_fragment, container, false);
        initView(view);
        aClass=new ArrayList<>();
        Subject_Prof_AsyncTask subject_prof_AsyncTask=new Subject_Prof_AsyncTask();
        subject_prof_AsyncTask.execute();

        return view;
    }

    private ArrayList<String> getAllProfs() {
        ArrayList<String> profs=new ArrayList<>();
        profs.add("profs");
        profs.add("profs");
        profs.add("profs");
        profs.add("profs");
        return profs;
    }

    public class Subject_Prof_AsyncTask extends AsyncTask<Void,Void,Void> {
        ArrayList<String> profs;
        ArrayList<String> subjects;
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: started ");
            super.onPreExecute();
            subjects=new ArrayList<>();
            profs=new ArrayList<>();
            schoolDB=SchoolDB.getInstance(getActivity());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: started");
            if(schoolDB!=null){
                subject=schoolDB.subjectDao().getAllSubject();
                prof=schoolDB.profDao().getAllProf();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

                 for (Subject subject1 : subject) {
                     subjects.add(subject1.getSubject_name());
                 }
            for (Prof prof1 : prof) {
                profs.add(prof1.getFirstName()+" "+prof1.getLastName());
            }

            super.onPostExecute(unused);
            Log.d(TAG, "onPostExecute: s");
            subjectSpinnerAdapter =new ArrayAdapter<String>(
                    getActivity(),
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    subjects
            );

            profSpinnerAdapter =new ArrayAdapter<String>(
                    getActivity(),
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    profs
            );

            subject_spinner.setAdapter(subjectSpinnerAdapter);
            prof_spinner.setAdapter(profSpinnerAdapter);

        }

    }
}