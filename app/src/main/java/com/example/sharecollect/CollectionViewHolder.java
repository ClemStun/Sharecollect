package com.example.sharecollect;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * View holder for the collections.
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class CollectionViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;

    public CollectionViewHolder(View collectionView) {
        super(collectionView);
        title = collectionView.findViewById(R.id.textViewCollectionTitle);
        description = collectionView.findViewById(R.id.textViewCollectionDescription);
    }

    public void bind(Collection collection) {
        title.setText(collection.getTitle());
        description.setText(collection.getDescription());
    }
}
