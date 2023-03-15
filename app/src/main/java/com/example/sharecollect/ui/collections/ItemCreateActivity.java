package com.example.sharecollect.ui.collections;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sharecollect.R;
import com.example.sharecollect.controllers.CollectionController;

public class ItemCreateActivity extends AppCompatActivity {

    private PopupWindow popupWindow;

    private EditText item_name_edit;

    private CollectionController collectionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        collectionController = CollectionController.getInstance();

        TextView collectionTitle = findViewById(R.id.collection_title_add_item);
        collectionTitle.setText(collectionController.getNewCollection().getName());
    }

    public void addItem(View view){

        final View popupView = getLayoutInflater().inflate(R.layout.popup_add_item, null);

        item_name_edit = (EditText) popupView.findViewById(R.id.item_name);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    public void addItemPopup(View view){

        String item_name = item_name_edit.getText().toString();
        Log.println(Log.DEBUG, "Item name", item_name);
        popupWindow.dismiss();

    }
}