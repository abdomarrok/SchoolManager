package com.marrok.myschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
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
import com.marrok.myschool.Activities.ClassesFragment;
import com.marrok.myschool.Activities.EnrollmentFragment;
import com.marrok.myschool.Activities.SubjectFragment;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Enrollment;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.Entities.aClass;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentAdapter.ViewHolder>{
    private static final String TAG = "EnrollmentAdapter";
    SchoolDB schoolDB;
    private final Context context;
    private ArrayList<Enrollment> enrollments=new ArrayList<>();
    public EnrollmentAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        schoolDB=SchoolDB.getInstance(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enrollment_item, parent, false);
        EnrollmentAdapter.ViewHolder holder = new EnrollmentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Log.d(TAG, "onBindViewHolder: student id"+enrollments.get(position).getStudent_id());
        holder.enrollment_id.setText(String.valueOf(enrollments.get(position).getEnrollment_id()));
        holder.studentName.setText(getStudentById(enrollments.get(position).getStudent_id()));
        holder.className.setText(getClassByName(enrollments.get(position).getClass_id()));
        holder.enrollment_price.setText(enrollments.get(position).getPrice());
        holder.parent_cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setTitle("Deleting Enrollment")
                        .setMessage("Are sure deleting"+enrollments.get(position).getEnrollment_id())
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try{
                                    schoolDB= SchoolDB.getInstance(context);
                                    if(schoolDB!=null){
                                        //update credit

                                        String oldCredit =schoolDB.studentDao().getOldCredit(enrollments.get(position).getStudent_id());
                                        String subCredit=oldCredit;
                                        String newCredit=enrollments.get(position).getPrice();
                                        try {
                                            subCredit= String.valueOf((Double.valueOf(oldCredit)-Double.valueOf(newCredit)));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        schoolDB.studentDao().updateCredit(subCredit,enrollments.get(position).getStudent_id());

                                        //commit delete
                                        schoolDB.enrollmentDao().delete(enrollments.get(position));
                                        FragmentTransaction transaction =((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.fragment_container, new EnrollmentFragment());
                                        transaction.commit();
                                    }
                                    Toast.makeText(context, "deleted enrollment", Toast.LENGTH_SHORT).show();
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

    private String getClassByName(int class_id) {
        String temp="deleted class";
            if(schoolDB!=null){
                try {
                    if(schoolDB.aClassDao().getClassById(class_id)!=null){
                        temp= schoolDB.aClassDao().getClassById(class_id).getClassName();
                    }

                    return temp;
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return temp;
        }


    private String getStudentById( int student_id) {
        String temp="deleted student";
        if(schoolDB!=null){
            try {
                    if(schoolDB.studentDao().getStudentById(student_id)!=null){
                        temp= schoolDB.studentDao().getStudentById(student_id).getFirstName()+" "+
                                schoolDB.studentDao().getStudentById(student_id).getLastName();
                    }


            }catch (SQLException e){
                e.printStackTrace();
            }
            return temp;
        }return temp;
    }

    @Override
    public int getItemCount() {
        return enrollments.size();
    }

    public void clearAdapter(){
        Log.d(TAG, "clearAdapter: started ");
        this.enrollments.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView studentName,enrollment_id,className,enrollment_price;
        private CardView parent_cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initWidget(itemView);
        }
        private void initWidget(View iteView) {
            studentName=iteView.findViewById(R.id.studentNameE);
            className =  iteView.findViewById(R.id.classNameE);
            enrollment_id =iteView.findViewById(R.id.enrollment_id);
            enrollment_price=iteView.findViewById(R.id.enrollment_price);
            parent_cardView = iteView.findViewById(R.id.m_Enrollment_CardView);
        }
    }
    // add the subjects setter to the class adapter
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = (ArrayList<Enrollment>) enrollments;
        notifyDataSetChanged();
    }
}
