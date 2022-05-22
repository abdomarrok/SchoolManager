package com.marrok.myschool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.marrok.myschool.Entities.Prof;

import java.util.List;

@Dao
public interface ProfDao {
    @Insert
    void insert(Prof prof);

    @Delete
    void delete(Prof prof);

    @Query("DELETE FROM prof WHERE prof_id=:id")
    void deleteById(int id);

    @Update
    void update(Prof prof);
    @Query("SELECT * FROM prof")
    List<Prof> getAllProf();

    @Query("SELECT *FROM prof WHERE prof_id=:id")
    Prof getProfById(int id);
}
