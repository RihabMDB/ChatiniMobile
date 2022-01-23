package com.example.chatiniapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.chatiniapp.Services.ChatService;
import com.example.chatiniapp.Services.UserService;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    ChatService chatService;
    UserService userService;
    CircleImageView imgProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        userService = new UserService(this);
        imgProfile = findViewById(R.id.profile);

        // Set profile img
        CircleImageView profile= findViewById(R.id.profile);
        userService.getUserImage(sharedPref.getInt("id", -1), profile);

        // Go to profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        // Find all online users
        userService = new UserService(this);
        RecyclerView r = findViewById(R.id.listonline);
        r.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        userService.getOnlineUsers(r);

        // Get All conversations
        chatService = new ChatService(this);
        ListView list = findViewById(R.id.lsv);
        chatService.getAllConversation(list);

        // Go to settings
        ImageView logout= findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

    }
}