package com.example.chatiniapp.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatiniapp.Adapters.ConversationsAdapter;
import com.example.chatiniapp.Adapters.MessageAdapter;
import com.example.chatiniapp.Models.Conversation;
import com.example.chatiniapp.Models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatService {
    Context context;
    String url="http://192.168.1.19:8080/api/";
    SharedPreferences sharedPref;
    ArrayList<Conversation> listConv = new ArrayList<>();
    ArrayAdapter<Conversation> adapter ;
    public ChatService(Context context) {
        this.context= context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void getAllConversation(ListView ls){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.GET,  url+"conversation/all/"+sharedPref.getInt("id", -1),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jarr= new JSONArray(response);
                            for (int i=0; i<jarr.length() ; i++){
                                JSONObject json_data = jarr.getJSONObject(i);
                                System.out.println("json_data : "+ json_data);
                                Conversation c= new Conversation(
                                       json_data.getInt("id"),
                                        json_data.getInt("senderId"),
                                        json_data.getString("senderName"),
                                        json_data.getString("senderImg"),
                                        json_data.getString("lastMsg"),
                                        json_data.getString("date"));
                                listConv.add(c);
                                adapter = new ConversationsAdapter(context, 0, listConv);
                                ls.setAdapter(adapter);

                            }
                        } catch (JSONException e) {  }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { //showSnackbar("Failed..");
                       // Toast.makeText(context, "error : "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    } }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params= new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer "+ sharedPref.getString("accessToken", ""));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getMessagesByConv(int idConv, RecyclerView rv){
        ArrayList<Message> listMsgs = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url+"conversation/message/all/"+idConv,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jarr= new JSONArray(response);
                            for (int i=0; i<jarr.length() ; i++){
                                JSONObject json_data = jarr.getJSONObject(i);
                                Message c= new Message(
                                        json_data.getInt("id"),
                                        json_data.getInt("senderID"),
                                        json_data.getInt("receiverID"),
                                        json_data.getString("body"),
                                        json_data.getString("date"));
                                listMsgs.add(c);

                                MessageAdapter messageAdapter = new MessageAdapter(context, listMsgs);
                                rv.setAdapter(messageAdapter);

                            }
                        } catch (JSONException e) {  }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { //showSnackbar("Failed..");
                        Toast.makeText(context, "error : "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    } }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params= new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer "+ sharedPref.getString("accessToken", ""));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getMessagesByUsers(String username, RecyclerView rv){
        ArrayList<Message> listMsgs = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url+"conversation/message/all/byuser/"+username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jarr= new JSONArray(response);
                            for (int i=0; i<jarr.length() ; i++){
                                JSONObject json_data = jarr.getJSONObject(i);
                                Message c= new Message(
                                        json_data.getInt("id"),
                                        json_data.getInt("senderID"),
                                        json_data.getInt("receiverID"),
                                        json_data.getString("body"),
                                        json_data.getString("date"));
                                listMsgs.add(c);

                                MessageAdapter messageAdapter = new MessageAdapter(context, listMsgs);
                                rv.setAdapter(messageAdapter);

                            }
                        } catch (JSONException e) {  }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { //showSnackbar("Failed..");
                        Toast.makeText(context, "error : "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    } }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params= new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer "+ sharedPref.getString("accessToken", ""));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public  void sendMessage(String message , int receiverId){
        RequestQueue requestLogin = Volley.newRequestQueue(context);
        JSONObject postData = new JSONObject();
        try {
            postData.put("body", message);
            postData.put("receiverID", receiverId);
        } catch (JSONException e) { e.printStackTrace();   }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url+"conversation/message/send", postData ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error: "+error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                System.out.println(error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params= new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "Bearer "+ sharedPref.getString("accessToken", ""));
                return params;
            }
        };
        requestLogin.add(jsonObjectRequest);
    }

}