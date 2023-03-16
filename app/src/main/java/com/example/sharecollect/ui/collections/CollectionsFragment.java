package com.example.sharecollect.ui.collections;

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

import com.example.sharecollect.Item;
import com.example.sharecollect.ItemAdapter;
import com.example.sharecollect.R;
import com.example.sharecollect.databinding.FragmentCollectionsBinding;

import java.util.ArrayList;
import java.util.List;

public class CollectionsFragment extends Fragment {

    private FragmentCollectionsBinding binding;
    private ItemAdapter itemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CollectionsViewModel collectionsViewModel =
                new ViewModelProvider(this).get(CollectionsViewModel.class);

        binding = FragmentCollectionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView collectionsRecyclerView = binding.collectionsRecyclerView;
        collectionsRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        //Example of set of items
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itemList.add(new Item("Item " + i, "Description " + i));
        }

        itemAdapter = new ItemAdapter(itemList);

        collectionsRecyclerView.setLayoutManager(layoutManager);
        collectionsRecyclerView.setAdapter(itemAdapter);

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
                itemAdapter.moveItem(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setBackgroundResource(R.drawable.item_border_selected);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundResource(R.drawable.item_border);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(collectionsRecyclerView);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}