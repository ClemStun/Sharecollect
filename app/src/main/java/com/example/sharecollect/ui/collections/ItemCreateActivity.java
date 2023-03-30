package com.example.sharecollect.ui.collections;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.HttpGetRequest;
import com.example.sharecollect.Item;
import com.example.sharecollect.ItemAdapter;
import com.example.sharecollect.R;
import com.example.sharecollect.controllers.UserController;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to create a new item list.
 * @author Hugo C. and Clement C.
 * @version 1.0
 * @since 2023-03-27
 */
public class ItemCreateActivity extends AppCompatActivity {

    private UserController userController;

    // Popup window to add a new item
    private PopupWindow popupWindow;

    private EditText newItemTitle;

    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        TextView collectionTitle = findViewById(R.id.collection_title_add_item);
        collectionTitle.setText(getIntent().getStringExtra("title"));
        userController = UserController.getInstance();

        itemList = new ArrayList<>();

        // Set the RecyclerView
        RecyclerView itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setHasFixedSize(true);

        // Set the layout manager with root context
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // Initialize the list of items in the fragment
        initItemList(itemsRecyclerView, layoutManager);
    }

    /**
     * Click on the button to add a new item and display the popup window
     * to add the item.
     * @param view The view
     */
    public void addItem(View view){

        final View popupView = getLayoutInflater().inflate(R.layout.popup_add_item, null);

        newItemTitle = popupView.findViewById(R.id.editTextNewItemTitle);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * Add the item to the list of items
     * and close the popup window.
     * Refresh the RecyclerView.
     * @param view The view
     */
    public void addItemPopup(View view){
        String item_name = newItemTitle.getText().toString();
        itemList.add(new Item(item_name));
        popupWindow.dismiss();

        RecyclerView itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * Initialize the adapter with the list of items.
     * @param recyclerView The RecyclerView
     * @param layoutManager The layout manager
     */
    public void initItemList(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);
    }

    public void validCollection(View view) {
        HttpGetRequest.createCollection(Integer.toString(userController.getUser().getId()), userController.getUser().getToken(), getIntent().getStringExtra("title"), getIntent().getStringExtra("description"), itemList);
        finish();
    }
}