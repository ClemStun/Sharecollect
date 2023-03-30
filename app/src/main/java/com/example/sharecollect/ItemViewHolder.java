package com.example.sharecollect;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * View holder for the items.
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-27
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView name;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textViewItemTitle);
    }

    public void bind(Item item) {
        name.setText(item.getTitle());
    }
}
