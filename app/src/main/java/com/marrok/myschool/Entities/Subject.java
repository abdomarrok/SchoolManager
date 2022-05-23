package com.marrok.myschool.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "subject")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    private int subject_id;
    @ColumnInfo(name = "class_id")
    private int Class_id;
    private String subject_name;
    private String subject_url;

    public Subject(String subject_name, String subject_url) {
        this.subject_name = subject_name;
        this.subject_url = subject_url;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getClass_id() {
        return Class_id;
    }

    public void setClass_id(int aClass_id) {
        this.Class_id = aClass_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSubject_url() {
        return subject_url;
    }

    public void setSubject_url(String subject_url) {
        this.subject_url = subject_url;
    }
}
