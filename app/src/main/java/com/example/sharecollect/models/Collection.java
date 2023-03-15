package com.example.sharecollect.models;

public class Collection {

    private int id;
    private String name;
    private String description;

    public Collection(String name, String description) {
        this.id = -1;
        this.name = name;
        this.description = description;
    }

    /* GETTERS ANS SETTERS */

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
