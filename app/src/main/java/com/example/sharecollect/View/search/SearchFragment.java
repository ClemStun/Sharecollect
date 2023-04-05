package com.example.sharecollect.View.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharecollect.controllers.CollectionAdapter;
import com.example.sharecollect.controllers.HttpRequest;
import com.example.sharecollect.controllers.OnCollectionClickListener;
import com.example.sharecollect.databinding.FragmentSearchBinding;
import com.example.sharecollect.models.Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Search fragment.
 */
public class SearchFragment extends Fragment implements OnCollectionClickListener {

    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel SearchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView collectionsRecyclerView = binding.collectionsRecyclerView;
        collectionsRecyclerView.setHasFixedSize(true);

        // Set the layout manager with root context
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        // Initialize the list of collections in the fragment
        initCollectionList(collectionsRecyclerView, layoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initCollectionList(RecyclerView collectionsRecyclerView, RecyclerView.LayoutManager layoutManager) {
        HashMap<String, Object> response = HttpRequest.getAllCollection();

        List<Collection> collectionsList = new ArrayList<>();
        HashMap<String, Object> collections = (HashMap<String, Object>) response.get("collections");

        for (int i = 0; i < collections.size(); i++) {
            HashMap<String, Object> collection = (HashMap<String, Object>) collections.get(String.valueOf(i));
            collectionsList.add(new Collection(Integer.parseInt((String) collection.get("id")), Integer.parseInt((String) collection.get("owner")), (String) collection.get("title"), (String) collection.get("description")));
        }

        // Fill collectionAdapter with the list of collections
        CollectionAdapter collectionAdapter = new CollectionAdapter(collectionsList, this);

        collectionsRecyclerView.setLayoutManager(layoutManager);
        collectionsRecyclerView.setAdapter(collectionAdapter);
    }

    @Override
    public void onCollectionClick(Collection collection) {
    }
}