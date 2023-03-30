package com.example.sharecollect.ui.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sharecollect.R;
import com.example.sharecollect.controllers.UserController;

/**
 * Activity to create a new collection.
 * @author Hugo C. and Clement C.
 * @version 1.0
 * @since 2023-03-27
 */
public class CollectionCreateActivity extends AppCompatActivity {

    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_create);

        userController = UserController.getInstance();
    }

    /**
     * Create a new collection and go to the item creation activity.
     * @param view The view
     */
    public void fillCollectionWithItem(View view) {

        String title = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        String description = ((EditText) findViewById(R.id.collection_desc)).getText().toString();

        Intent intent = new Intent(this, ItemCreateActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
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