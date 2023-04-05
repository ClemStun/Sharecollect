package com.example.sharecollect.View.collections;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.R;
import com.example.sharecollect.controllers.HttpRequest;
import com.example.sharecollect.controllers.ItemAdapter;
import com.example.sharecollect.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * The type Collection activity.
 */
public class CollectionActivity extends AppCompatActivity {

    /**
     * The Collection id.
     */
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

        HashMap<String, Object> response = HttpRequest.getItems(collectionId);

        itemList = new ArrayList<>();

        HashMap<String, Object> items = (HashMap<String, Object>) response.get("items");

        for (int i = 0; i < Objects.requireNonNull(items).size(); i++) {
            HashMap<String, Object> item = (HashMap<String, Object>) items.get(String.valueOf(i));
            itemList.add(new Item((String) item.get("name"), (Uri) item.get("image")));
        }

        initItemList(collectionRecyclerView, layoutManager);
    }

    /**
     * Init item list.
     *
     * @param recyclerView  the recycler view
     * @param layoutManager the layout manager
     */
    public void initItemList(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);
    }
}