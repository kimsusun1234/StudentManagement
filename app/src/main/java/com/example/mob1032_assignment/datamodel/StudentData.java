package com.example.mob1032_assignment.datamodel;

public class StudentData
{
    private String id;
    private String name;
    private String dateOfBirth;
    private String classID;//foreign key

    public StudentData()
    {

    }

    public StudentData(String id, String name, String dateOfBirth, String classID) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.classID = classID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
