package com.example.sharecollect;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.databinding.ActivityMainBinding;
import com.example.sharecollect.notification.MyFirebaseMessagingService;
import com.example.sharecollect.ui.collections.CollectionActivity;
import com.example.sharecollect.ui.collections.CollectionCreateActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Main activity of the application
 * It contains the navigation bar and the fragments
 * It also contains the id and token of the user
 * @author Hugo C. and Clement C.
 * @version 1.0
 * @since 2023-03-22
 */
public class MainActivity extends AppCompatActivity {

    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userController = UserController.getInstance();

        com.example.sharecollect.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        binding.navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                viewPager2.setCurrentItem(0);
            } else if (itemId == R.id.navigation_search) {
                viewPager2.setCurrentItem(1);
            } else if (itemId == R.id.navigation_collections) {
                viewPager2.setCurrentItem(2);
            } else if (itemId == R.id.navigation_friends) {
                viewPager2.setCurrentItem(3);
            } else if (itemId == R.id.navigation_profile) {
                viewPager2.setCurrentItem(4);
            }
            return true;
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.navView.getMenu().getItem(position).setChecked(true);
            }
        });

        viewPager2.setCurrentItem(0);

        // recover id and token from the login activity
        Bundle extras = getIntent().getExtras();

        // Initialiser Firebase
        FirebaseApp.initializeApp(this);

        // Créer un canal de notification pour Android Oreo et supérieur
        NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id), "SharecollectNotif", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Demander l'autorisation pour recevoir des notifications push
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        sendNotifToken(token);
                    }
                });


    }
    private void sendNotifToken(String token){

        HttpGetRequest.addNotifToken(Integer.toString(userController.getUser().getId()), token);

    }
}