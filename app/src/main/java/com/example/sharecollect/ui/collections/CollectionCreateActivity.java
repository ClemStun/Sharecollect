package com.example.sharecollect.ui.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sharecollect.R;
import com.example.sharecollect.controllers.CollectionController;
import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.models.Collection;

/**
 * Activity to create a new collection.
 * @author Hugo C. and Clement C.
 * @version 1.0
 * @since 2023-03-27
 */
public class CollectionCreateActivity extends AppCompatActivity {

    private UserController userController;
    private CollectionController collectionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_create);

        userController = UserController.getInstance();
        collectionController = CollectionController.getInstance();
    }

    /**
     * Create a new collection and go to the item creation activity.
     * @param view The view
     */
    public void fillCollectionWithItem(View view) {

        String title = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        String description = ((EditText) findViewById(R.id.collection_desc)).getText().toString();

        collectionController.setCollection(new Collection(0, userController.getUser().getId(), title, description));

        Intent intent = new Intent(this, ItemCreateActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Go back to the collections activity.
     * @param view The view
     */
    public void backToCollections(View view) {
        finish();
    }
}