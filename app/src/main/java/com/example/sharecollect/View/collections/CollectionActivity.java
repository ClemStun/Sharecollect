package com.example.sharecollect.View.collections;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.net.Uri;

import com.example.sharecollect.controllers.HttpGetRequest;
import com.example.sharecollect.controllers.ItemAdapter;
import com.example.sharecollect.R;
import com.example.sharecollect.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CollectionActivity extends AppCompatActivity {

    String collectionId;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        // Retrieve infos in intent
        Bundle bundle = getIntent().getExtras();
        collectionId = bundle.getString("collection_id");

        RecyclerView collectionRecyclerView = findViewById(R.id.RecyclerViewCollection);
        collectionRecyclerView.setHasFixedSize(true);

        // Set the layout manager with root context
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        HashMap<String, Object> response = HttpGetRequest.getItems(collectionId);

        itemList = new ArrayList<>();

        HashMap<String, Object> items = (HashMap<String, Object>) response.get("items");

        for (int i = 0; i < Objects.requireNonNull(items).size(); i++) {
            HashMap<String, Object> item = (HashMap<String, Object>) items.get(String.valueOf(i));
            itemList.add(new Item((String) item.get("name"), (Uri) item.get("image")));
        }

        initItemList(collectionRecyclerView, layoutManager);
    }

    public void initItemList(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);
    }
}