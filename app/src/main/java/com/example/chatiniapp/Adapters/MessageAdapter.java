package com.example.chatiniapp.Adapters;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatiniapp.Models.Message;
import com.example.chatiniapp.R;

import java.util.List;

public class MessageAdapter  extends  RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context context;
    private List<Message> messageList;


    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList; Toast.makeText(context, "MessageAdapter", Toast.LENGTH_SHORT).show();
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

        //if  if (message.getSender()== PreferenceManager.getDefaultSharedPreferences(context).getInt("id", -1))  {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false); Toast.makeText(context, "onCreateViewHolder", Toast.LENGTH_SHORT).show();
            return new SentMessageHolder(view);
//        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.message_received, parent, false);
//            return new ReceivedMessageHolder(view);
//        }



      //  return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) messageList.get(position);

//        switch (holder.getItemViewType()) {
//            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
        Toast.makeText(context, "onBindViewHolder", Toast.LENGTH_SHORT).show();
//                break;
//            case VIEW_TYPE_MESSAGE_RECEIVED:
//                ((ReceivedMessageHolder) holder).bind(message);
       // }
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

        messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_me);
    }

    void bind(Message message) {
        messageText.setText(message.getMsg());

        // Format the stored timestamp into a readable String using method.
     //   timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
    }
}

 class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
        timeText = (TextView) itemView.findViewById(R.id.text_gchat_timestamp_other);
        nameText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
        profileImage = (ImageView) itemView.findViewById(R.id.image_gchat_profile_other);
    }

    void bind(Message message) {
        messageText.setText(message.getMsg());

        // Format the stored timestamp into a readable String using method.
//        timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
//        nameText.setText(message.getSender().getNickname());

        // Insert the profile image from the URL into the ImageView.
    //    Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
    }
}
