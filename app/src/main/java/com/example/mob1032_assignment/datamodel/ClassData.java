package com.example.mob1032_assignment.datamodel;

public class ClassData
{
    private String id;
    private String name;

    public ClassData()
    {

    }

    public ClassData(String id, String name) {
        this.id = id;
        this.name = name;
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
}
