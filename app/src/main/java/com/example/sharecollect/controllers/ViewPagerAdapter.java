package com.example.sharecollect.controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sharecollect.View.collections.CollectionsFragment;
import com.example.sharecollect.View.friends.FriendsFragment;
import com.example.sharecollect.View.home.HomeFragment;
import com.example.sharecollect.View.profile.ProfileFragment;
import com.example.sharecollect.View.search.SearchFragment;

/**
 * The type View pager adapter.
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    /**
     * Instantiates a new View pager adapter.
     *
     * @param fragmentActivity the fragment activity
     */
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Retourner le fragment correspondant à la position
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new CollectionsFragment();
            case 3:
                return new FriendsFragment();
            case 4:
                return new ProfileFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        // Retourner le nombre total de fragments
        return 5;
    }
}
