package com.marrok.myschool.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "class",primaryKeys = {"subject_id","prof_id"}, foreignKeys = {@ForeignKey(entity = Subject.class, parentColumns = "subject_id", childColumns = "subject_id", onDelete = ForeignKey.CASCADE)})
public class Class {
    private int class_id;
    private int subject_id;
    private int prof_id;

    public Class() {
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getProf_id() {
        return prof_id;
    }

    public void setProf_id(int prof_id) {
        this.prof_id = prof_id;
    }
}
