package com.example.lw4.Object;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentRecord {
    private final SimpleIntegerProperty course;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty averageMark;
    private final SimpleStringProperty secondName;
    private final SimpleIntegerProperty id;
    public StudentRecord()
    {
        id = new SimpleIntegerProperty(this, "id");
        course = new SimpleIntegerProperty(this, "course");
        age = new SimpleIntegerProperty(this, "age");
        averageMark = new SimpleStringProperty(this, "averageMark");
        secondName = new SimpleStringProperty(this, "secondName");
    }


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
    public int getCourse() {
        return course.get();
    }

    public SimpleIntegerProperty courseProperty() {
        return course;
    }

    public void setCourse(int course) {
        this.course.set(course);
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getAverageMark() {
        return averageMark.get();
    }

    public SimpleStringProperty averageMarkProperty() {
        return averageMark;
    }

    public void setAverageMark(String averageMark) {
        this.averageMark.set(averageMark);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public SimpleStringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }
}
