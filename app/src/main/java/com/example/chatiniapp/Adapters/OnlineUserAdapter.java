package com.example.chatiniapp.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

import com.example.chatiniapp.ChatActivity;
import com.example.chatiniapp.Models.User;
import com.example.chatiniapp.R;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnlineUserAdapter extends  RecyclerView.Adapter<OnlineUserAdapter.ViewHolder> {
        private Context context;
        private List<User> users;
        private boolean v;
        public OnlineUserAdapter(Context context, ArrayList<User> objects) {
            this.context=context;
            this.users= objects;
        }

        @Override
        public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.onlineuser, parent, false);
            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(OnlineUserAdapter.ViewHolder holder, int position) {
            User user= users.get(position);

            holder.username.setText(String.valueOf(user.getUsername()));
            if(user.getImg().length()!=0){
                byte[] imageID= android.util.Base64.decode(String.valueOf(user.getImg())   , Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageID, 0, imageID.length);
                holder.img.setImageBitmap(bitmap);}
            else
                holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.logo));

            // Intent to DetailsActivity
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ChatActivity.class );
                   // i.putExtra("formation", user);
                    context.startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView username;
            CircleImageView img;
            public ViewHolder( View itemView) {
                super(itemView);
                username = (TextView) itemView.findViewById(R.id.username);
                img= itemView.findViewById(R.id.img);
            }
        }
    }
