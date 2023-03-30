package com.example.sharecollect;

/**
 * Represents an item
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-27
 */
public class Item {
    private String title;

    public Item(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
