package com.example.sharecollect;

import static java.lang.Integer.parseInt;

/**
 * Represents a collection
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class Collection {

    private final String id;
    private final String owner;
    private String title;
    private String description;

    public Collection(String id, String owner, String title, String description) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.id = id;
    }

    public String getId() {
        return id;
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
