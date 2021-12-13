package com.example.chatiniapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatiniapp.ChatActivity;
import com.example.chatiniapp.Models.Conversation;
import com.example.chatiniapp.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationsAdapter extends ArrayAdapter {

        private Context context;
        private List<Conversation> conversations;

        //constructor, call on creation
        public ConversationsAdapter(Context context, int resource, ArrayList<Conversation> objects) {
            super(context, resource, objects);
            this.context = context;
            this.conversations = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            //get the conversation we are displaying
            Conversation conv = conversations.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.conversations, null);

            TextView name =  view.findViewById(R.id.name);
            TextView msg =  view.findViewById(R.id.msg);
            TextView date =  view.findViewById(R.id.date);
            CircleImageView imageV = view.findViewById(R.id.img);

            //set attributes
            name.setText( conv.getName());
            date.setText(conv.getDate());
            msg.setText(conv.getMsg());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ChatActivity.class);
                    i.putExtra("convId", conv.getId());
                    context.startActivity(i);
                }
            });

            if(conv.getImg()!=null){
                byte[] imageID= android.util.Base64.decode(String.valueOf(conv.getImg())   , Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageID, 0, imageID.length);
                imageV.setImageBitmap(bitmap);}
            else
                imageV.setImageDrawable(context.getResources().getDrawable(R.drawable.person));
            return view;
        }
}
