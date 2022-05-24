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
import com.marrok.myschool.Activities.StudentFragment;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Student;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends  RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private static final String TAG = "StudentAdapter";
    SchoolDB schoolDB;
    private final Context context;
    private ArrayList<Student> students =new ArrayList<>();
    public StudentAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.studentName.setText(students.get(position).getFirstName()+""+ students.get(position).getLastName());

        Glide.with(context)
                .asBitmap()
                .load(students.get(position).getImgUrl())
                .into(holder.studentImg);

        holder.parent_cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //send data
                ProfileFragment fragment =new ProfileFragment();
                Bundle arguments = new Bundle();
                arguments.putInt("student_id", students.get(position).getStudent_id());
                fragment.setArguments(arguments);
                //todo :go to profile
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                Toast.makeText(context, "go to profile", Toast.LENGTH_SHORT).show();
            }
        });

        holder.parent_cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setTitle("Deleting student")
                        .setMessage("Are sure deleting"+ students.get(position).getFirstName()+""+ students.get(position).getLastName()+"?")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try{
                                    Log.d(TAG, "onClick: "+(students.get(position).getLastName()));
                                    schoolDB= SchoolDB.getInstance(context);
                                    if(schoolDB!=null){
                                        schoolDB.studentDao().delete(students.get(position));
                                        FragmentTransaction transaction =((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.fragment_container, new StudentFragment());
                                        transaction.commit();
                                    }
                                    Toast.makeText(context, "delete student", Toast.LENGTH_SHORT).show();
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
        return students.size();
    }
    public void clearAdapter(){
        Log.d(TAG, "clearAdapter: started ");
        this.students.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView studentName;
        private ImageView studentImg;
        private CardView parent_cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initWidget(itemView);
        }
        private void initWidget(View iteView) {
            studentName =  iteView.findViewById(R.id.studentName);
            studentImg =   iteView.findViewById(R.id.student_img);
            parent_cardView = iteView.findViewById(R.id.m_student_cardview);
        }
    }
    // add the profs setter to the class adapter
    public void setStudents(List<Student> students) {
        this.students = (ArrayList<Student>) students;
        notifyDataSetChanged();
    }
}
