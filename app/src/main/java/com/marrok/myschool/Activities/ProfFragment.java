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
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Dialog.AddStudentDialog;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class ProfFragment extends Fragment  implements AddProfDialog.AddProf {
    BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private TextView total;
    private List<Prof> prof;
    private RecyclerView recyclerView;
    private SchoolDB schoolDB;
    private ProfAdapter adapter;


    @Override
    public void onAddProfResult(Prof prof1) {
        Log.d(TAG, "onAddProfResult: prof"+prof1+" added");
        try {
        schoolDB.profDao().insert(prof1);
            ProfAsyncTask profAsyncTask=new ProfAsyncTask();
            profAsyncTask.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        total.setText(((Integer) adapter.getItemCount()).toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_prof_fragment, container, false);
        bottomNavigationView = view.findViewById(R.id.navigation);
        recyclerView = view.findViewById(R.id.prof_recycler_view);
        total = view.findViewById(R.id.total_value);
        prof = new ArrayList<>();
        fab = view.findViewById(R.id.fab);
        adapter = new ProfAdapter(getActivity());//create adapter instance
        ProfAsyncTask profAsyncTask=new ProfAsyncTask();
        profAsyncTask.execute();
        recyclerView.setAdapter(adapter);//set the Student model adapter to recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // chose tha way to show item



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProfDialog addProfDialog =new AddProfDialog();
                Bundle bundle=new Bundle();
                /**send the student info here */
                addProfDialog.setArguments(bundle);
                addProfDialog.show(getParentFragmentManager(),"add Prof dialog");
            }
        });
        return view;

    }
        
    
    

    public class ProfAsyncTask extends AsyncTask<Void,Void,Void> {
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
                prof.clear();
                prof=schoolDB.profDao().getAllProf();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d(TAG, "onPostExecute: s");
            adapter.setProfs(prof);// giving the model to the adapter
            adapter.notifyDataSetChanged();

            ////////////////
            total.setText(((Integer) adapter.getItemCount()).toString());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: start");
    }


}