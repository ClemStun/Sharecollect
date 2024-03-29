package com.example.sharecollect.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.R;
import com.example.sharecollect.View.ItemViewHolder;
import com.example.sharecollect.models.Item;

import java.util.List;

/**
 * Adapter for the RecyclerView, contains the items data.
 *
 * @author Hugo C.
 * @version 1.0
 * @since 2023 -03-27
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{
    private List<Item> itemList;

    /**
     * Instantiates a new Item adapter.
     *
     * @param itemList the item list
     */
    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item data = itemList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * Move item.
     *
     * @param fromPosition the from position
     * @param toPosition   the to position
     */
    public void moveItem(int fromPosition, int toPosition) {
        Item item = itemList.get(fromPosition);
        itemList.remove(fromPosition);
        itemList.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }
}
