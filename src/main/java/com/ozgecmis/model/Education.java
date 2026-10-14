package com.ozgecmis.model;

public class Education {
    private String schoolName;
    private String department;
    private String graduationYear;
    private String gpa;

    public Education(String schoolName, String department, String graduationYear, String gpa) {
        this.schoolName = schoolName;
        this.department = department;
        this.graduationYear = graduationYear;
        this.gpa = gpa;
    }

    public String getSchoolName() { return schoolName; }
    public String getDepartment() { return department; }
    public String getGraduationYear() { return graduationYear; }
    public String getGpa() { return gpa; }
}