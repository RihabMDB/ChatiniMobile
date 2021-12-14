package com.example.chatiniapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chatiniapp.Adapters.MessageAdapter;
import com.example.chatiniapp.Models.Message;
import com.example.chatiniapp.Services.ChatService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatService = new ChatService(this);

        int idConv = getIntent().getExtras().getInt("convId", 0);

        RecyclerView messageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);

        // Get messages
        chatService.getMessages(idConv , messageRecycler);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL  , false));

        // Send Message
        EditText msgV= findViewById(R.id.edit_gchat_message);
        Button sendButton = findViewById(R.id.button_gchat_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatService.sendMessage(msgV.getText().toString(), getIntent().getExtras().getInt("senderId"));
                refresh();
            }
        });

        // Set Sender image
        CircleImageView senderImg  = findViewById(R.id.senderimg);
        if(getIntent().getExtras().getString("senderImg")!=null){
            byte[] imageID= android.util.Base64.decode(String.valueOf(getIntent().getExtras().getString("senderImg"))   , Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageID, 0, imageID.length);
            senderImg.setImageBitmap(bitmap);}
        // Set sender name
        Toolbar toolbar = findViewById(R.id.toolbar_gchannel);
        toolbar.setTitle(getIntent().getExtras().getString("senderName"));

    }

    public void refresh() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }


}