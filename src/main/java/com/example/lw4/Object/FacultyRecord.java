package com.example.lw4.Object;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FacultyRecord{
    private final SimpleStringProperty specialization;
    private final SimpleStringProperty speciality;
    private final SimpleStringProperty secondName;
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty course;

    public FacultyRecord()
    {
        specialization = new SimpleStringProperty(this, "specialization");
        speciality = new SimpleStringProperty(this, "speciality");
        secondName = new SimpleStringProperty(this, "secondName");
        id = new SimpleIntegerProperty(this, "id");
        course = new SimpleIntegerProperty(this, "course");
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
    public String getSpecialization() {
        return specialization.get();
    }

    public SimpleStringProperty specializationProperty() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization.set(specialization);
    }

    public String getSpeciality() {
        return speciality.get();
    }

    public SimpleStringProperty specialityProperty() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality.set(speciality);
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


    public int getCourse() {
        return course.get();
    }

    public SimpleIntegerProperty courseProperty() {
        return course;
    }

    public void setCourse(int course) {
        this.course.set(course);
    }
}
