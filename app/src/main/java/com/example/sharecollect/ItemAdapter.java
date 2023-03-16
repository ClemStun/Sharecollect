package com.example.sharecollect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{
    private List<Item> ItemList;

    public ItemAdapter(List<Item> dataList) {
        ItemList = dataList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item data = ItemList.get(position);
        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public void moveItem(int fromPosition, int toPosition) {
        Item item = ItemList.get(fromPosition);
        ItemList.remove(fromPosition);
        ItemList.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }
}
