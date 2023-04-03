package com.example.sharecollect.models;

import static java.lang.Integer.parseInt;

/**
 * Represents a collection
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class Collection {

    private final String owner;
    private String title;
    private String description;

    public Collection(String owner, String title, String description) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }
}
