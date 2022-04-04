package com.example.lw4.Object;

public class Student {
    int course;
    int age;
    double averageMark;
    String secondName;

    public Student(String secondName, int course,  double averageMark, int age) {
        this.course = course;
        this.age = age;
        this.averageMark = averageMark;
        this.secondName = secondName;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
