package com.example.sharecollect;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.models.Collection;

/**
 * View holder for the collections.
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class CollectionViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageView owner;

    public CollectionViewHolder(View collectionView) {
        super(collectionView);
        title = collectionView.findViewById(R.id.textViewCollectionTitle);
        description = collectionView.findViewById(R.id.textViewCollectionDescription);
        owner = collectionView.findViewById(R.id.imageViewOwner);
    }

    public void bind(Collection collection) {
        title.setText(collection.getTitle());
        description.setText(collection.getDescription());
        if(UserController.getInstance().getUser().getUsername().equals(collection.getOwner())) {
            owner.setVisibility(View.VISIBLE);
        }
    }
}
