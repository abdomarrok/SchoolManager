package com.marrok.myschool.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "enrollment")
public class Enrollment {
    @PrimaryKey(autoGenerate = true)
    private int enrollment_id;
    private int class_id;
    private int student_id;
    private String price;

    public Enrollment(int class_id, int student_id, String price) {
        this.class_id = class_id;
        this.student_id = student_id;
        this.price = price;
    }

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
