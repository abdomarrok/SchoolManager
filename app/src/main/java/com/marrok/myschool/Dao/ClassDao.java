package com.marrok.myschool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.marrok.myschool.Entities.Subject;
import com.marrok.myschool.Entities.aClass;

import java.util.List;

@Dao
public interface ClassDao {
    @Insert
    void insert(aClass aClass);

    @Delete
    void delete(aClass aClass);

    @Update
    void update(aClass aClass);
    @Query("SELECT * FROM class")
    List<aClass> getAllClasses();

    @Query("SELECT *FROM class WHERE class_id=:id")
    Subject getClassById(int id);

    @Query("SELECT class_id FROM class WHERE className=:name")
    int getClassIdByName(String name);
}
