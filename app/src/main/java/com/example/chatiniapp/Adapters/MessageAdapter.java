package com.example.chatiniapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatiniapp.Models.Message;
import com.example.chatiniapp.Models.User;
import com.example.chatiniapp.R;
import com.example.chatiniapp.Services.UserService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter  extends  RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context context;
    private List<Message> messageList;


    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = (Message) messageList.get(position);

        if (message.getSender()== PreferenceManager.getDefaultSharedPreferences(context).getInt("id", -1)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message =  messageList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}

 class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;

    SentMessageHolder(View itemView) {
        super(itemView);

        messageText =  itemView.findViewById(R.id.text_gchat_message_me);
        timeText =  itemView.findViewById(R.id.text_gchat_timestamp_me);
    }

    void bind(Message message) {
        messageText.setText(message.getMsg());
        timeText.setText(message.getDate().substring(10,16));
    }
}

 class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    CircleImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_other);
        nameText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
        profileImage =  itemView.findViewById(R.id.image_gchat_profile_other);
    }

    void bind(Message message) {
        messageText.setText(message.getMsg());
        //nameText.setText();
        timeText.setText(message.getDate().substring(10, 16));

        // Insert the profile image  into the ImageView.
        UserService userService = new UserService(itemView.getContext());
        userService.getUserImage(message.getSender(), profileImage);
    }
}
