package com.example.sharecollect.ui.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sharecollect.R;
import com.example.sharecollect.controllers.CollectionController;

public class CollectionCreateActivity extends AppCompatActivity {

    private CollectionController collectionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_create);

        collectionController = CollectionController.getInstance();
    }

    public void createCollection(View view) {

        String title = ((EditText) findViewById(R.id.collection_title)).getText().toString();
        String description = ((EditText) findViewById(R.id.collection_desc)).getText().toString();
        collectionController.createNewCollection(title, description);

        Intent intent = new Intent(this, ItemCreateActivity.class);
        startActivity(intent);
    }
    public void backToCollections(View view) {
        finish();
    }
}