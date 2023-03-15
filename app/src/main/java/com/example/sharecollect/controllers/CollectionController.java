package com.example.sharecollect.controllers;

import com.example.sharecollect.models.Collection;

public class CollectionController {

    private static CollectionController instance;

    private Collection newCollection;

    private CollectionController() {
    }

    public static CollectionController getInstance() {
        if (instance == null) {
            instance = new CollectionController();
        }
        return instance;
    }

    public void createNewCollection(String title, String description) {
        newCollection = new Collection(title, description);
    }

    public Collection getNewCollection() {
        return newCollection;
    }

}
