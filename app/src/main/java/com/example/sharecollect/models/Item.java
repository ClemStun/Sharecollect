package com.example.sharecollect.models;

import android.net.Uri;

/**
 * Represents an item
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-27
 */
public class Item {
    private String title;
    private Uri imageUri;

    public Item(String title, Uri imageUri) {
        this.title = title;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }
    public Uri getImage() {
        return imageUri;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
