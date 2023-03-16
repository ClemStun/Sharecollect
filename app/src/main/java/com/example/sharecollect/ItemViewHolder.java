package com.example.sharecollect;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;

    public ItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textViewItemTitle);
        description = itemView.findViewById(R.id.textViewItemDescription);
    }
}
