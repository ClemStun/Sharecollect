package com.example.sharecollect.View.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The type Search view model.
 */
public class SearchViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Instantiates a new Search view model.
     */
    public SearchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Search fragment");
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public LiveData<String> getText() {
        return mText;
    }
}