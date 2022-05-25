package com.marrok.myschool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.marrok.myschool.Entities.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

   @Query("UPDATE student SET credit=:newPrice WHERE student_id=:id")
   void updateCredit(String newPrice,int id);

    @Query("SELECT credit FROM student WHERE student_id=:id")
    String getOldCredit(int id);

    @Query("SELECT * FROM student")
    List<Student> getAllStudent();

    @Query("SELECT * FROM student WHERE student_id=:id")
    Student getStudentById(int id);

    @Query("SELECT student_id FROM student WHERE first_name||:space||last_name =:str")
    int getStudentByName(String str,String space);
}
