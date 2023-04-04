package com.example.sharecollect.View;

import static java.lang.Integer.parseInt;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.controllers.HttpGetRequest;
import com.example.sharecollect.controllers.OnCollectionClickListener;
import com.example.sharecollect.R;
import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.models.Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private Button followButton;

    private List<Collection> collectionsList;

    public CollectionViewHolder(View collectionView, List<Collection> collectionsList, OnCollectionClickListener onCollectionClickListener) {
        super(collectionView);
        title = collectionView.findViewById(R.id.textViewCollectionTitle);
        description = collectionView.findViewById(R.id.textViewCollectionDescription);
        owner = collectionView.findViewById(R.id.imageViewOwner);
        followButton = collectionView.findViewById(R.id.buttonFollow);

        this.collectionsList = collectionsList;

        collectionView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && onCollectionClickListener != null) {
                onCollectionClickListener.onCollectionClick(collectionsList.get(position));
            }
        });

        followButton.setOnClickListener(v -> {
            HashMap<String, Object> response = HttpGetRequest.addCollectionFollower(collectionsList.get(getAdapterPosition()).getId(), UserController.getInstance().getUser().getId());
            followButton.setVisibility(View.GONE);
        });
    }

    public void bind(Collection collection) {
        title.setText(collection.getTitle());
        description.setText(collection.getDescription());
        if(UserController.getInstance().getUser().getId() == collection.getOwner()) {
            owner.setVisibility(View.VISIBLE);
        }

        // print button only if don't follow collection
        HashMap<String, Object> response = HttpGetRequest.getCollectionFollowers(collection.getId());
        HashMap<String, Object> followers = (HashMap<String, Object>) response.get("users");

        List<Integer> listFollowers = new ArrayList<Integer>();
        for (int i = 0; i < followers.size(); i++) {
            HashMap<String, Object> follower = (HashMap<String, Object>) followers.get(String.valueOf(i));
            System.out.println(follower.get("user_id") + " = " + UserController.getInstance().getUser().getId());
            listFollowers.add(parseInt((String) follower.get("user_id")));
        }
        if(listFollowers.contains(UserController.getInstance().getUser().getId())) {
            followButton.setVisibility(View.GONE);
        } else {
            followButton.setVisibility(View.VISIBLE);
        }
    }
}
