package com.example.chatiniapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatiniapp.Adapters.ConversationsAdapter;
import com.example.chatiniapp.Models.Conversation;
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

        // Find all online users
        userService = new UserService(this);
        RecyclerView r = findViewById(R.id.listonline);
        r.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        userService.getOnlineUsers(r);

        // Go to settings
        ImageView settings= findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        // Go to profile
        CircleImageView profile= findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        // Get All conversations
        chatService = new ChatService(this);
        ListView list = findViewById(R.id.lsv);
        chatService.getAllConversation(list);

    }
}