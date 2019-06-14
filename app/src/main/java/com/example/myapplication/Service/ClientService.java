package com.example.myapplication.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Ui.Activity.HomePageActivity;
import com.example.myapplication.Ui.Activity.MainActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientService {
    RequestQueue queue;


    public ClientService() {
    }

    // login service
    public void postLogin(final String username, String password, final Context context) {

        queue = Volley.newRequestQueue(context.getApplicationContext());
        String url = APIConnectorUltils.HOST_NAME + "AuthorService/authors";

        Log.d("Mine Request", "onRequest" + url);
        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("password", password);

            Log.d("Mine Response", "data : " + object);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (Boolean.parseBoolean(response.getString("result")) && response.getInt("role") == 0) {
                                    Toast.makeText(context, "Login successfully!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(context, HomePageActivity.class);
                                    intent.putExtra("username", username);
                                    context.startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Login failure!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("Mine Response", "onResponse: " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Mine ERROR", "onErrorResponse: " + error.toString());
                            Toast.makeText(context, "Connection to server have problems!", Toast.LENGTH_SHORT).show();
                        }
                    });
            queue.start();
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            Log.d("Mine ERROR", "Exeption: " + e.toString());
        }
    }

    //register service
    public void postRegister(final String username, String password, final Context context)
    {
        queue = Volley.newRequestQueue(context.getApplicationContext());
        String url = APIConnectorUltils.HOST_NAME + "AuthorService/register";
        Log.d("Mine Request", "onRequest" + url);

        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("password", password);

            Log.d("Mine Response", "data : " + object);

            JsonObjectRequest  request = new JsonObjectRequest(Request.Method.POST, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Mine Response", "Response : " + response);
                            try {
                                if (Boolean.parseBoolean(response.getString("result")) )
                                {
                                    Toast.makeText(context, "Register successfully!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(context, HomePageActivity.class);
                                    intent.putExtra("username", username);
                                    context.startActivity(intent);
                                } else {
                                    Toast.makeText(context, response.getString("announce"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Mine ERROR", "onErrorResponse: " + error.toString());
                            Toast.makeText(context, "Connection to server have problems!", Toast.LENGTH_SHORT).show();
                        }
                    });

            queue.start();
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("Mine ERROR", "Exeption: " + e.toString());
        }

    }
}
