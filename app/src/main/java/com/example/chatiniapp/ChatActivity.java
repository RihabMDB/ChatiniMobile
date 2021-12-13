package com.example.chatiniapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chatiniapp.Adapters.MessageAdapter;
import com.example.chatiniapp.Models.Message;
import com.example.chatiniapp.Services.ChatService;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ChatService chatService;
    List<Message> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatService = new ChatService(this);

        int idConv = getIntent().getExtras().getInt("convId", 0);
        RecyclerView messageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        chatService.getMessages(idConv , messageRecycler);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL  , false));
//         messageList.add(new Message(11, 2, 1, "tttttt", "gggg"));
//        messageList.add(new Message(11, 2, 1, "tttttt", "gggg"));



    }
}