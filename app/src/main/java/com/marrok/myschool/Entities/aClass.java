package com.marrok.myschool.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "class" ,
        indices = {@Index(value = "className",unique = true)})
public class aClass {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "class_id")
    private int Class_id;
    private int prof_id;
    private int subject_id;
    private String className;

    public aClass(int prof_id, int subject_id, String className) {
        this.prof_id = prof_id;
        this.subject_id = subject_id;
        this.className = className;
    }


    public int getClass_id() {
        return Class_id;
    }

    public void setClass_id(int aClass_id) {
        this.Class_id = aClass_id;
    }

    public int getProf_id() {
        return prof_id;
    }

    public void setProf_id(int prof_id) {
        this.prof_id = prof_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
