package com.example.sharecollect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.sharecollect.databinding.ActivityMainBinding;
import com.example.sharecollect.ui.collections.CollectionCreateActivity;

public class MainActivity extends AppCompatActivity {

    private String id;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.sharecollect.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // recover id and token from the login activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            token = extras.getString("token");
        }
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void createCollection(View view) {
        Intent intent = new Intent(this, CollectionCreateActivity.class);
        startActivity(intent);
    }
}