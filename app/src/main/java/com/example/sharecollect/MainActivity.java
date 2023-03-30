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

import com.example.sharecollect.databinding.ActivityMainBinding;
import com.example.sharecollect.ui.collections.CollectionCreateActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

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
                        Log.d("FCM", "Token : " + token);
                    }
                });

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