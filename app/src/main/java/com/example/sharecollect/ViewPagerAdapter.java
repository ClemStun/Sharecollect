package com.example.sharecollect;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sharecollect.ui.collections.CollectionsFragment;
import com.example.sharecollect.ui.friends.FriendsFragment;
import com.example.sharecollect.ui.home.HomeFragment;
import com.example.sharecollect.ui.profile.ProfileFragment;
import com.example.sharecollect.ui.search.SearchFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

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
