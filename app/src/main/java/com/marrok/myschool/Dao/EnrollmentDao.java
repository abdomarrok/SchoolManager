package com.marrok.myschool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.marrok.myschool.Entities.Enrollment;

import java.util.List;

@Dao
public interface EnrollmentDao {
    @Insert
    void insert(Enrollment enrollment);

    @Update
    void update(Enrollment enrollment);

    @Delete
    void delete(Enrollment enrollment);

    @Query("SELECT * FROM enrollment")
    List<Enrollment> getAllEnrollment();

    @Query("SELECT * FROM enrollment WHERE student_id=:id")
    List<Enrollment> getEnrollmentByStudentId(int id);

    @Query("SELECT * FROM enrollment WHERE enrollment_id=:id")
    List<Enrollment> getEnrollmentById(int id);

}
