package com.example.sharecollect;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.models.Item;

/**
 * View holder for the items.
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-27
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private ImageView image;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textViewItemTitle);
        image = itemView.findViewById(R.id.imageViewItem);
    }

    public void bind(Item item) {
        name.setText(item.getTitle());
        image.setImageURI(item.getImage());
    }
}
