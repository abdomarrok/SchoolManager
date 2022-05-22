package com.marrok.myschool.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subject")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    private int subject_id;
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
