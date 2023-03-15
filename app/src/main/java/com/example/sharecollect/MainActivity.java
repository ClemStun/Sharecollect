package com.example.sharecollect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.sharecollect.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private String id;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.sharecollect.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ViewPager2 viewPager2 = findViewById(R.id.view_pager2);

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