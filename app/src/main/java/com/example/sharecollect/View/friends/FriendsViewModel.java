package com.example.sharecollect.View.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The type Friends view model.
 */
public class FriendsViewModel extends ViewModel {

        private final MutableLiveData<String> mText;

    /**
     * Instantiates a new Friends view model.
     */
    public FriendsViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("This is friends fragment");
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