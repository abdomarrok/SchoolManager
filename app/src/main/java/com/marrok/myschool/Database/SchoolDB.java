package com.marrok.myschool.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.marrok.myschool.Dao.ClassDao;
import com.marrok.myschool.Dao.EnrollmentDao;
import com.marrok.myschool.Dao.ProfDao;
import com.marrok.myschool.Dao.StudentDao;
import com.marrok.myschool.Dao.SubjectDao;
import com.marrok.myschool.Entities.Enrollment;
import com.marrok.myschool.Entities.Prof;
import com.marrok.myschool.Entities.Student;
import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.Entities.aClass;

@Database(entities = {Prof.class, Subject.class, aClass.class,Enrollment.class,Student.class},version = 1)
public abstract class SchoolDB extends RoomDatabase {
    public abstract ProfDao profDao();
    public abstract SubjectDao subjectDao();
    public abstract ClassDao aClassDao();
    public abstract EnrollmentDao enrollmentDao();
    public abstract StudentDao studentDao();


    private static SchoolDB instance;
    public static synchronized SchoolDB getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                            SchoolDB.class, "SchoolDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }



}
