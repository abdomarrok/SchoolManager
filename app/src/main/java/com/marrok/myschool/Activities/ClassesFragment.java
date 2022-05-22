package com.marrok.myschool.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    private Spinner subject_spinner,prof_spinner;
    private RecyclerView recyclerView;
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
        //todo fill the two spinner
        return view;
    }
}