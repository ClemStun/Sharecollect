package com.example.sharecollect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sharecollect.databinding.ActivityMainBinding;
import com.example.sharecollect.ui.collections.CollectionsFragment;
import com.example.sharecollect.ui.friends.FriendsFragment;
import com.example.sharecollect.ui.home.HomeFragment;
import com.example.sharecollect.ui.profile.ProfileFragment;
import com.example.sharecollect.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String id;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.sharecollect.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // recover id and token from the login activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            token = extras.getString("token");
        }

        // Create a list of fragments
        /*List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.addAll(getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main).getChildFragmentManager().getFragments());

        // Create an adapter that knows which fragment should be shown on each page
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };

        // Set the adapter onto the view pager
        viewPager2.setAdapter(adapter);

        // Set the bottom navigation view to change the view pager when a menu item is selected
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                navView.getMenu().getItem(position).setChecked(true);
            }
        });*/
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}