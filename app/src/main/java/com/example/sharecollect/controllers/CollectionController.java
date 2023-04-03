package com.example.sharecollect.controllers;

import com.example.sharecollect.models.Collection;

public class CollectionController {

    private static CollectionController instance;
    private Collection collection = null;


    private CollectionController() { }

    public static CollectionController getInstance() {
        if (instance == null) {
            instance = new CollectionController();
        }
        return instance;
    }

    /* GETTER */

    public Collection getCollection() {
        return collection;
    }

    /* SETTER */

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

}
