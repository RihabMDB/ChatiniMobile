package com.example.chatiniapp.Services;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chatiniapp.Adapters.OnlineUserAdapter;
import com.example.chatiniapp.Models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

    public class UserService extends Service {
        Context context;
        String url="http://192.168.1.22:8080/api/";
        SharedPreferences sharedPref;
        ArrayList<User> listUsers = new ArrayList<>();
      //  ArrayAdapter<User> adapter ;
        public UserService(Context context) {
            this.context= context;
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        public void getOnlineUsers(RecyclerView rv){
            url = url+"user/store/users/online";
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
                                    User c= new User(
                                            json_data.getInt("idUser"),
                                            json_data.getString("name"),
                                            json_data.getString("img"));
                                    listUsers.add(c);
                                    RecyclerView.Adapter adapter = new OnlineUserAdapter(context, listUsers);
                                    rv.setAdapter(adapter);
                                }
                            } catch (JSONException e) {  }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("error : "+ error.getMessage());
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