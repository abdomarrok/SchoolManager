package com.marrok.myschool.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "student",indices = {@Index("first_name")})
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int student_id;
    @ColumnInfo(name="img_url")
    private String imgUrl;
    @ColumnInfo(name="first_name")
    private String firstName;
    @ColumnInfo(name="last_name")
    private String lastName;
    @ColumnInfo(name = "is_activate")
    private Boolean isActivate;
    @ColumnInfo(name="credit")
    private String Credit;
    @ColumnInfo(name="phone_number")
    private String phoneNumber;
    @ColumnInfo(name="parent_phone_number")
    private String parentPhoneNumber;
    @ColumnInfo(name="email")
    private String Email;

    public Student(String imgUrl, String firstName, String lastName, Boolean isActivate, String Credit, String phoneNumber, String parentPhoneNumber, String Email) {
        this.imgUrl = imgUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActivate = isActivate;
        this.Credit = Credit;
        this.phoneNumber = phoneNumber;
        this.parentPhoneNumber = parentPhoneNumber;
        this.Email = Email;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setActivate(Boolean isActivate) {
        this.isActivate = isActivate;
    }

    public void setCredit(String Credit) {
        this.Credit = Credit;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getActivate() {
        return isActivate;
    }

    public String getCredit() {
        return Credit;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public String getEmail() {
        return Email;
    }
}
