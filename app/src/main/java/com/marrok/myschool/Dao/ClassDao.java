package com.marrok.myschool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.marrok.myschool.Entities.Class;
import com.marrok.myschool.Entities.Subject;

import java.util.List;

@Dao
public interface ClassDao {

    @Insert
    void insert(Class aClass);

    @Delete
    void delete(Class aClass);

    @Update
    void update(Class aClass);
    @Query("SELECT * FROM class")
    List<Class> getAllProf();

    @Query("SELECT *FROM class WHERE class_id=:id")
    Class getClassById(int id);

}
