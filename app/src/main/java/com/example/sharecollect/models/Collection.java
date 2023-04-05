package com.example.sharecollect.models;

/**
 * Represents a collection
 *
 * @author Hugo C.
 * @version 1.0
 * @since 2023 -03-22
 */
public class Collection {

    private int id;
    private int owner;
    private String title;
    private String description;

    /**
     * Instantiates a new Collection.
     *
     * @param id          the id
     * @param owner       the owner
     * @param title       the title
     * @param description the description
     */
    public Collection(int id, int owner, String title, String description) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public int getOwner() {
        return owner;
    }

}
