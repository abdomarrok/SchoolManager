package com.marrok.myschool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import com.marrok.myschool.Activities.SubjectFragment;
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.Entities.aClass;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>{
    private static final String TAG = "ClassAdapter";
    SchoolDB schoolDB;
    private final Context context;
    private ArrayList<aClass> classes=new ArrayList<>();
    public ClassAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        ClassAdapter.ViewHolder holder = new ClassAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.class_id.setText(String.valueOf(classes.get(position).getClass_id()));
        holder.className.setText(classes.get(position).getClassName());
        holder.parent_cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setTitle("Deleting Class")
                        .setMessage("Are sure deleting"+classes.get(position).getClassName())
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try{
                                    schoolDB= SchoolDB.getInstance(context);
                                    if(schoolDB!=null){
                                        schoolDB.aClassDao().delete(classes.get(position));
                                        FragmentTransaction transaction =((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.fragment_container, new ClassesFragment());
                                        transaction.commit();
                                    }
                                    Toast.makeText(context, "deleted class", Toast.LENGTH_SHORT).show();
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
        return classes.size();
    }

    public void clearAdapter(){
        Log.d(TAG, "clearAdapter: started ");
        this.classes.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView className,class_id;
        private CardView parent_cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initWidget(itemView);
        }
        private void initWidget(View iteView) {
            className =  iteView.findViewById(R.id.className);
            class_id =iteView.findViewById(R.id.class_id);
            parent_cardView = iteView.findViewById(R.id.m_Class_CardView);
        }
    }
    // add the subjects setter to the class adapter
    public void setClass(List<aClass> classes) {
        this.classes = (ArrayList<aClass>) classes;
        notifyDataSetChanged();
    }
}
