package com.marrok.schoolmanager.model;


import java.time.LocalDate;

public class Student {
    private int id;
    private String fname;
    private String lname;
    private int code;
    private long fileNumber;
    private LocalDate birthDate;
    private String parentName;
    private String address;
    private String phone;
    private String parentPhone;
    private int level;
    private String remark;
    private String studentSchoolName;
    private int remise;
    private boolean exemption;
    private boolean gender;

    // Default constructor
    public Student() {}

    // Constructor with all fields
    public Student(int id, String fname, String lname, int code, long fileNumber, LocalDate birthDate,
                   String parentName, String address, String phone, String parentPhone, int level, String remark,
                   String studentSchoolName, int remise, boolean exemption, boolean gender) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.code = code;
        this.fileNumber = fileNumber;
        this.birthDate = birthDate;
        this.parentName = parentName;
        this.address = address;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.level = level;
        this.remark = remark;
        this.studentSchoolName = studentSchoolName;
        this.remise = remise;
        this.exemption = exemption;
        this.gender = gender;
    }

    public Student( String fname, String lname, int code, long fileNumber, LocalDate birthDate,
                   String parentName, String address, String phone, String parentPhone, int level, String remark,
                   String studentSchoolName, int remise, boolean exemption, boolean gender) {

        this.fname = fname;
        this.lname = lname;
        this.code = code;
        this.fileNumber = fileNumber;
        this.birthDate = birthDate;
        this.parentName = parentName;
        this.address = address;
        this.phone = phone;
        this.parentPhone = parentPhone;
        this.level = level;
        this.remark = remark;
        this.studentSchoolName = studentSchoolName;
        this.remise = remise;
        this.exemption = exemption;
        this.gender = gender;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStudentSchoolName() {
        return studentSchoolName;
    }

    public void setStudentSchoolName(String studentSchoolName) {
        this.studentSchoolName = studentSchoolName;
    }

    public int getRemise() {
        return remise;
    }

    public void setRemise(int remise) {
        this.remise = remise;
    }

    public boolean isExemption() {
        return exemption;
    }

    public void setExemption(boolean exemption) {
        this.exemption = exemption;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
