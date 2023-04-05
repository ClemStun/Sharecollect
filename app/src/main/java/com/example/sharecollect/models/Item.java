package com.example.sharecollect.models;

import android.net.Uri;

/**
 * Represents an item
 *
 * @author Hugo C.
 * @version 1.0
 * @since 2023 -03-27
 */
public class Item {
    private String title;
    private Uri imageUri;

    /**
     * Instantiates a new Item.
     *
     * @param title    the title
     * @param imageUri the image uri
     */
    public Item(String title, Uri imageUri) {
        this.title = title;
        this.imageUri = imageUri;
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
     * Gets image.
     *
     * @return the image
     */
    public Uri getImage() {
        return imageUri;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets image.
     *
     * @param imageUri the image uri
     */
    public void setImage(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
