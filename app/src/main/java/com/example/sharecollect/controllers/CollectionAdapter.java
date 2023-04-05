package com.example.sharecollect.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.R;
import com.example.sharecollect.View.CollectionViewHolder;
import com.example.sharecollect.models.Collection;

import java.util.List;

/**
 * Adapter for the RecyclerView, contains the collections data.
 *
 * @author Hugo C.
 * @version 1.0
 * @since 2023 -03-22
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionViewHolder>{
    private List<Collection> collectionList;

    private final OnCollectionClickListener onCollectionClickListener;

    /**
     * Instantiates a new Collection adapter.
     *
     * @param dataList                  the data list
     * @param onCollectionClickListener the on collection click listener
     */
    public CollectionAdapter(List<Collection> dataList, OnCollectionClickListener onCollectionClickListener) {
        collectionList = dataList;
        this.onCollectionClickListener = onCollectionClickListener;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_layout, parent, false);
        return new CollectionViewHolder(view, collectionList, onCollectionClickListener);
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        Collection data = collectionList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    /**
     * Move item.
     *
     * @param fromPosition the from position
     * @param toPosition   the to position
     */
    public void moveItem(int fromPosition, int toPosition) {
        Collection collection = collectionList.get(fromPosition);
        collectionList.remove(fromPosition);
        collectionList.add(toPosition, collection);
        notifyItemMoved(fromPosition, toPosition);
    }
}
