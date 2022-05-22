package com.marrok.myschool.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marrok.myschool.Adapters.SubjectAdapter;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends Fragment {

    SchoolDB schoolDB;
    BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private TextView total;
    private List<Subject> subject;
    private TextView subject_id,subject_name;
    private ImageView subject_url;
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;

    private void initView(View v){
        subject_id=v.findViewById(R.id.subject_id);
        subject_name=v.findViewById(R.id.subjectName);
        subject_url=v.findViewById(R.id.subject_img);
        bottomNavigationView=v.findViewById(R.id.navigation);
        fab=v.findViewById(R.id.fab);
        recyclerView=v.findViewById(R.id.subject_recycler_view);
        total=v.findViewById(R.id.total_value);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_subject_fragment, container, false);
        initView(view);
        subject=new ArrayList<>();
        subjectAdapter=new SubjectAdapter(getActivity());
        SubjectAsyncTask subjectAsyncTask=new SubjectAsyncTask();
        subjectAsyncTask.execute();
        recyclerView.setAdapter(subjectAdapter);//set the Student model adapter to recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // chose tha way to show item



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSubjectDialog addSubjectDialog =new AddSubjectDialog();
                Bundle bundle=new Bundle();
                addSubjectDialog.setArguments(bundle);
                addSubjectDialog.show(getParentFragmentManager(),"add Subject dialog");
            }
        });

        return view;
    }
    public class SubjectAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: started ");
            super.onPreExecute();
            subjectAdapter.clearAdapter();
            schoolDB=SchoolDB.getInstance(getActivity());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: started");
            if(schoolDB!=null){
                subject.clear();
                subject=schoolDB.subjectDao().getAllSubject();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d(TAG, "onPostExecute: s");
            subjectAdapter.setSubjects(subject);// giving the model to the adapter
            subjectAdapter.notifyDataSetChanged();

            ////////////////
            total.setText(((Integer) subjectAdapter.getItemCount()).toString());
        }
    }
}