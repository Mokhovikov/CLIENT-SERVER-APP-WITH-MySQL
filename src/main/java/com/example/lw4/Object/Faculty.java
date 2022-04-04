package com.example.lw4.Object;

public class Faculty {
    String specialization;
    String speciality;
    String secondName;
    int course;



    public Faculty(String specialization, String speciality, String secondName, int course) {
        this.specialization = specialization;
        this.speciality = speciality;
        this.secondName = secondName;

        this.course = course;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }



    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }
}
