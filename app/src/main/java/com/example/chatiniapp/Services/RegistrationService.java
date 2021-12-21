package com.example.chatiniapp.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatiniapp.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationService extends Service {

    Context context; boolean bool;
    String url="http://192.168.100.33:8080/api/";
    SharedPreferences sharedPref;

    public RegistrationService(Context context) {
        this.context= context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public  void register(String  username, String email, String phone , String password,   String address) throws JSONException {
        String postUrl = url+"auth/register";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject postData = new JSONObject();
        try {

        postData.put("phone", phone.trim());
        postData.put("username", ""+username.toLowerCase().trim());
        postData.put("email", email.toLowerCase().trim());
        postData.put("password",""+ password.trim());
        postData.put("city", ""+address.trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("message").endsWith("successfully!"))
                      context.startActivity(new Intent(context, MainActivity.class));
                    else Toast.makeText(context, "Error : "+response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Error response code: "+ error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public  void login(String username, String password){
        SharedPreferences.Editor editor = sharedPref.edit();
        String postUrl =url+"auth/login";
        RequestQueue requestLogin = Volley.newRequestQueue(context);
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username.trim().toLowerCase());
            postData.put("password",password.trim());
        } catch (JSONException e) { e.printStackTrace();   }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Get auth details
                    editor.putInt("id", Integer.parseInt(response.getString("id")));
                    editor.putString("accessToken", response.getString("accessToken"));
                    editor.putString("username", response.getString("username"));
                    editor.putString("tokenType", response.getString("tokenType"));
                    editor.apply();
                    context.startActivity(new Intent(context, MainActivity.class));

                } catch (JSONException e) {
                    e.printStackTrace();  }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error: "+error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();  }
        });
        requestLogin.add(jsonObjectRequest);
    }


}