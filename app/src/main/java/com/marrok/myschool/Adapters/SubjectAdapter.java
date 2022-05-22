package com.marrok.myschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marrok.myschool.Activities.ProfFragment;
import com.marrok.myschool.Activities.ProfileFragment;
import com.marrok.myschool.Activities.SubjectFragment;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends  RecyclerView.Adapter<SubjectAdapter.ViewHolder>{
    private static final String TAG = "SubjectAdapter";
    SchoolDB schoolDB;
    private final Context context;
    private ArrayList<Subject> subjects=new ArrayList<>();
    public SubjectAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        SubjectAdapter.ViewHolder holder = new SubjectAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.subject_id.setText(String.valueOf(subjects.get(position).getSubject_id()));
        holder.subjectName.setText(subjects.get(position).getSubject_name());
        Glide.with(context)
                .asBitmap()
                .load(subjects.get(position).getSubject_url())
                .into(holder.subjectImg);


        holder.parent_cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context)
                            .setTitle("Deleting Subjects")
                            .setMessage("Are sure deleting"+subjects.get(position).getSubject_name())
                            .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try{
                                        schoolDB= SchoolDB.getInstance(context);
                                        if(schoolDB!=null){
                                            schoolDB.subjectDao().delete(subjects.get(position));
                                            FragmentTransaction transaction =((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.fragment_container, new SubjectFragment());
                                            transaction.commit();
                                        }
                                        Toast.makeText(context, "deleted subject", Toast.LENGTH_SHORT).show();
                                    }catch (ClassCastException e){
                                        e.printStackTrace();
                                    }
                                }
                            }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create().show();
                    return false;
                }
            });
    }



    @Override
    public int getItemCount() {
        return subjects.size();
    }
    public void clearAdapter(){
        Log.d(TAG, "clearAdapter: started ");
        this.subjects.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subjectName,subject_id;
        private ImageView subjectImg;
        private CardView parent_cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initWidget(itemView);
        }
        private void initWidget(View iteView) {
            subjectName =  iteView.findViewById(R.id.subjectName);
            subjectImg =   iteView.findViewById(R.id.subject_img);
            subject_id =iteView.findViewById(R.id.subject_id);
            parent_cardView = iteView.findViewById(R.id.m_Prof_CardView);
        }
    }
    // add the subjects setter to the class adapter
    public void setSubjects(List<Subject> subjects) {
        this.subjects = (ArrayList<Subject>) subjects;
        notifyDataSetChanged();
    }
}