package com.marrok.myschool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.marrok.myschool.Entities.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Delete
    void delete(Subject subject);

    @Update
    void update(Subject subject);
    @Query("SELECT * FROM subject")
    List<Subject> getAllSubject();

    @Query("SELECT *FROM subject WHERE subject_id=:id")
    Subject getSubjectById(int id);
}
