package com.example.sharecollect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.models.Collection;

import java.util.List;

/**
 * Adapter for the RecyclerView, contains the collections data.
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionViewHolder>{
    private List<Collection> collectionList;

    public CollectionAdapter(List<Collection> dataList) {
        collectionList = dataList;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_layout, parent, false);
        return new CollectionViewHolder(view);
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

    public void moveItem(int fromPosition, int toPosition) {
        Collection collection = collectionList.get(fromPosition);
        collectionList.remove(fromPosition);
        collectionList.add(toPosition, collection);
        notifyItemMoved(fromPosition, toPosition);
    }
}
