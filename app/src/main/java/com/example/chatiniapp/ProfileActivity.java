package com.example.chatiniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    EditText email , phone, address, username;
    TextView name;
    ImageView img;
    MaterialButton update;
    SharedPreferences sharedPref;
    String url= "http://192.168.1.19:8080/api/user/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        name= findViewById(R.id.name);
        email= findViewById(R.id.email);
        phone= findViewById(R.id.phone);
        address= findViewById(R.id.address);
        username= findViewById(R.id.username);
        img= findViewById(R.id.img);
        update= findViewById(R.id.update);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest= new JsonObjectRequest(Request.Method.GET, url+"profile",null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                email.setText(response.getString("email"));
                                phone.setText(response.getString("phone"));
                                address.setText(response.getString("place"));
                                username.setText(response.getString("name"));
                                if(response.getString("img").length()!=0){
                                    byte[] imageID= android.util.Base64.decode( response.getString("img")  , Base64.DEFAULT);
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageID, 0, imageID.length);
                                    img.setImageBitmap(bitmap);}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }},
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("error : "+ error.getMessage());
                        } }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params= new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", "Bearer "+ sharedPref.getString("accessToken", ""));
                    return params;
                }
            };
            requestQueue.add(stringRequest);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    void update(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {

            postData.put("phone", phone.getText().toString().trim());
            postData.put("username", ""+username.getText().toString().toLowerCase().trim());
            postData.put("email", email.getText().toString().toLowerCase().trim());
            postData.put("city", ""+address.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url+"profile/update", postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("message").endsWith("successfully!")) {
                        finish();
                        startActivity(getIntent());
                    }
                    else Toast.makeText(ProfileActivity.this, "Error : "+response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Error response code: "+ error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            } }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer "+ sharedPref.getString("accessToken", ""));
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}