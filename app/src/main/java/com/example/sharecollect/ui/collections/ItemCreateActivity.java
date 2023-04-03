package com.example.sharecollect.ui.collections;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.HttpGetRequest;
import com.example.sharecollect.controllers.CollectionController;
import com.example.sharecollect.models.Item;
import com.example.sharecollect.ItemAdapter;
import com.example.sharecollect.R;
import com.example.sharecollect.controllers.UserController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activity to create a new item list.
 * @author Hugo C. and Clement C.
 * @version 1.0
 * @since 2023-03-27
 */
public class ItemCreateActivity extends AppCompatActivity {

    private UserController userController;
    private CollectionController collectionController;

    // Popup window to add a new item
    private PopupWindow popupWindow;

    private EditText newItemTitle;
    private Uri photoUri = null;

    private List<Item> itemList;

    private ActivityResultLauncher<Uri> cameraLauncher;
    private View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        collectionController = CollectionController.getInstance();
        userController = UserController.getInstance();

        TextView collectionTitle = findViewById(R.id.collection_title_add_item);
        collectionTitle.setText(collectionController.getCollection().getTitle());
        popupView = getLayoutInflater().inflate(R.layout.popup_add_item, null);

        itemList = new ArrayList<>();

        // Set the RecyclerView
        RecyclerView itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setHasFixedSize(true);

        // Set the layout manager with root context
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // Initialize the list of items in the fragment
        initItemList(itemsRecyclerView, layoutManager);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        // La photo a été enregistrée avec succès
                        ImageView imageView = popupView.findViewById(R.id.itemImagePreview);
                        imageView.setImageURI(photoUri);
                    } else {
                        // La photo n'a pas été enregistrée
                        Toast.makeText(this, "Failed to take photo", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Click on the button to add a new item and display the popup window
     * to add the item.
     * @param view The view
     */
    public void addItem(View view){

        newItemTitle = popupView.findViewById(R.id.editTextNewItemTitle);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        //Faut envoyer les images
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
        itemList.add(new Item(item_name, this.photoUri));
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
        HttpGetRequest.createCollection(Integer.toString(userController.getUser().getId()), userController.getUser().getToken(), collectionController.getCollection().getTitle(), collectionController.getCollection().getDescription(), itemList);
        finish();
    }

    public void takePhoto(View view) {

        File imageFile = null;

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }

        try {
            imageFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imageFile != null) {
            this.photoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", imageFile);
            cameraLauncher.launch(photoUri);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

}