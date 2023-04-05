package com.example.sharecollect.controllers;

import com.example.sharecollect.models.Collection;

/**
 * The type Collection controller.
 */
public class CollectionController {

    private static CollectionController instance;
    private Collection collection = null;


    private CollectionController() { }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CollectionController getInstance() {
        if (instance == null) {
            instance = new CollectionController();
        }
        return instance;
    }

    /* GETTER */

    /**
     * Gets collection.
     *
     * @return the collection
     */
    public Collection getCollection() {
        return collection;
    }

    /* SETTER */

    /**
     * Sets collection.
     *
     * @param collection the collection
     */
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

}
