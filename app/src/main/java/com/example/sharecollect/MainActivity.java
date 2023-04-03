package com.example.sharecollect;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

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