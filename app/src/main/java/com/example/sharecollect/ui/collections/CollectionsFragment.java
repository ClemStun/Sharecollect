package com.example.sharecollect.ui.collections;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.models.Collection;
import com.example.sharecollect.HttpGetRequest;
import com.example.sharecollect.CollectionAdapter;
import com.example.sharecollect.OnCollectionClickListener;
import com.example.sharecollect.R;
import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.databinding.FragmentCollectionsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fragment to display the list of collections.
 * @author Hugo C. and Clement C.
 * @version 1.0
 * @since 2023-03-27
 */
public class CollectionsFragment extends Fragment implements OnCollectionClickListener {

    private FragmentCollectionsBinding binding;
    private CollectionAdapter collectionAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Retrieve the ViewModel
        CollectionsViewModel collectionsViewModel =
                new ViewModelProvider(this).get(CollectionsViewModel.class);

        // Inflate the layout for this fragment and get the root view
        binding = FragmentCollectionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Onclick listeners
        setOnclickListeners(root);

        // Set the RecyclerView
        RecyclerView collectionsRecyclerView = binding.collectionsRecyclerView;
        collectionsRecyclerView.setHasFixedSize(true);

        // Set the layout manager with root context
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        // Initialize the list of collections in the fragment
        initCollectionList(collectionsRecyclerView, layoutManager);

        return root;
    }

    private void setOnclickListeners(View root) {
        binding.createButton.setOnClickListener(v -> {
            Intent intent = new Intent(root.getContext(), CollectionCreateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Allows to initialize the list of collections in the fragment
     * and set drag and drop functionality between collections
     * @param collectionsRecyclerView : RecyclerView of the fragment
     * @param layoutManager : LayoutManager of the fragment
     */
    private void initCollectionList(RecyclerView collectionsRecyclerView, RecyclerView.LayoutManager layoutManager) {
        HashMap<String, Object> response = HttpGetRequest.getCollectionList(Integer.toString(UserController.getInstance().getUser().getId()));

        List<Collection> collectionsList = new ArrayList<>();
        HashMap<String, Object> collections = (HashMap<String, Object>) response.get("collection_id");

        if(collections == null) {
            collections = new HashMap<>();
        }
        else {
            // For each collection, get the information and add it to the list
            for (int i = 0; i < collections.size(); i++) {
                HashMap<String, Object> collection = (HashMap<String, Object>) collections.get(String.valueOf(i));
                assert collection != null;
                HashMap<String, Object> collectionInfo = HttpGetRequest.getCollectionInformation((String) collection.get("collection_id"));
                collectionsList.add(new Collection(Integer.parseInt((String) collection.get("collection_id")), Integer.parseInt((String) collectionInfo.get("owner")), (String) collectionInfo.get("title"), (String) collectionInfo.get("description")));
            }
        }

        // Fill collectionAdapter with the list of collections
        collectionAdapter = new CollectionAdapter(collectionsList, this);

        collectionsRecyclerView.setLayoutManager(layoutManager);
        collectionsRecyclerView.setAdapter(collectionAdapter);

        // Set drag and drop functionality between collections
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                collectionAdapter.moveItem(fromPosition, toPosition);
                return true;
            }



            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    assert viewHolder != null;
                    viewHolder.itemView.setBackgroundResource(R.drawable.collection_area_selected);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundResource(R.drawable.collection_area);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(collectionsRecyclerView);
    }

    @Override
    public void onCollectionClick(Collection collection) {
        Intent intent = new Intent(getContext(), CollectionActivity.class);
        System.out.println(collection.getId());
        intent.putExtra("collection_id", collection.getId());
        startActivity(intent);
    }
}