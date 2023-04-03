package com.example.sharecollect.models;

import static java.lang.Integer.parseInt;

/**
 * Represents a collection
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class Collection {

    private int id;
    private int owner;
    private String title;
    private String description;

    public Collection(int id, int owner, String title, String description) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getOwner() {
        return owner;
    }

}
