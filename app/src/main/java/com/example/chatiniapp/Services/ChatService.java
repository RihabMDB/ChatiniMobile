package com.example.chatiniapp.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

public class ChatService extends Service {
    Context context;
    String url="http://192.168.1.22:8080/api/";
    SharedPreferences sharedPref;
    ArrayList<Conversation> listConv = new ArrayList<>();
    ArrayAdapter<Conversation> adapter ;
    public ChatService(Context context) {
        this.context= context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getAllConversation(ListView ls){
         url = url+"conversation/all/"+sharedPref.getInt("id", 0);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
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
                        Toast.makeText(context, "error : "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    } }
        ){
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
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


    public void getMessages(int idConv, RecyclerView rv){
        url = url+"conversation/message/all/"+idConv;
        ArrayList<Message> listMsgs = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
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
            //            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
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

}