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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatiniapp.Adapters.ConversationsAdapter;
import com.example.chatiniapp.Models.Conversation;
import com.example.chatiniapp.Models.User;
import com.example.chatiniapp.Services.ChatService;
import com.example.chatiniapp.Services.UserService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ChatService chatService;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Find all online users
        userService = new UserService(this);
        RecyclerView r = findViewById(R.id.listonline);
        r.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        userService.getOnlineUsers(r);

        // Set profile img
        CircleImageView profile= findViewById(R.id.profile);
        userService.getUserImage(sharedPref.getInt("id", -1), profile);

        // Go to profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });


        // Go to settings
        ImageView settings= findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });


        // Get All conversations
        chatService = new ChatService(this);
        ListView list = findViewById(R.id.lsv);
        chatService.getAllConversation(list);




    }
}