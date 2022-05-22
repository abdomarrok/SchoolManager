package com.marrok.myschool.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marrok.myschool.R;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private TextView teachers_TXT;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view=inflater.inflate(R.layout.activity_home_fragment, container, false);
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        teachers_TXT=view.findViewById(R.id.teacher);
        teachers_TXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.fragment_container, new ProfFragment());
                transaction.commit();
            }
        });
        return view;
    }
}