package com.marrok.myschool.Adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.marrok.myschool.Database.SchoolDB;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.R;

import java.util.ArrayList;
import java.util.List;

public class ProfAdapter  extends  RecyclerView.Adapter<ProfAdapter.ViewHolder>{
    private static final String TAG = "ProfAdapter";
    SchoolDB schoolDB;
    private final Context context;
    private ArrayList<Prof> profs=new ArrayList<>();
    public ProfAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prof_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.profName.setText(profs.get(position).getFirstName()+""+profs.get(position).getLastName());
        holder.prof_email.setText(profs.get(position).getEmail());
        holder.prof_phone.setText(profs.get(position).getPhone_number());
        Glide.with(context)
                .asBitmap()
                .load(profs.get(position).getImgUrl())
                .into(holder.profImg);

        holder.parent_cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               //todo :go to profile

                Toast.makeText(context, "go to profile", Toast.LENGTH_SHORT).show();
            }
        });

        holder.parent_cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setTitle("Deleting student")
                        .setMessage("Are sure deleting"+profs.get(position).getFirstName()+""+profs.get(position).getLastName()+"?")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try{
                                 // todo :delete prof
                                    Log.d(TAG, "onClick: "+(profs.get(position).getLastName()));
                                    schoolDB= SchoolDB.getInstance(context);
                                    if(schoolDB!=null){
                                        schoolDB.profDao().delete(profs.get(position));
                                        FragmentTransaction transaction =((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.fragment_container, new ProfFragment());
                                        transaction.commit();
                                    }
                                    Toast.makeText(context, "delete prof", Toast.LENGTH_SHORT).show();
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
        return profs.size();
    }
    public void clearAdapter(){
        Log.d(TAG, "clearAdapter: started ");
        this.profs.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView profName,prof_email,prof_phone;
        private ImageView profImg;
        private CardView parent_cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initWidget(itemView);
        }
        private void initWidget(View iteView) {
            profName =  iteView.findViewById(R.id.profName);
            profImg =   iteView.findViewById(R.id.prof_img);
            prof_email= iteView.findViewById(R.id.prof_email);
            prof_phone =iteView.findViewById(R.id.prof_phone);
            parent_cardView = iteView.findViewById(R.id.m_Prof_CardView);
        }
    }
    // add the profs setter to the class adapter
    public void setProfs(List<Prof> profs) {
        this.profs = (ArrayList<Prof>) profs;
        notifyDataSetChanged();
    }
}
