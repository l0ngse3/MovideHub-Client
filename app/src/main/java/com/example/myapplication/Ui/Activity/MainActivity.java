package com.example.myapplication.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {


    TextInputEditText txtUserName;
    TextInputEditText txtPassword;

    Button btnLogin;
    Button btnRegister;
    CheckBox checkBoxRememberMe;

    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        process();

    }

    private void process() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                if (username.isEmpty()) {
                    txtUserName.setError("User name required");
                } else if (password.isEmpty()) {
                    txtPassword.setError("Password Required");
                } else {
                    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
                    Network network = new BasicNetwork(new HurlStack());

                    queue = new RequestQueue(cache, network);
                    queue.start();
                    String url = APIConnectorUltils.HOST_NAME + "AuthorService/authors";
                    url += "?username=" + username;
                    url += "&password=" + password;
                    Log.d("Mine Request", "onRequest" + url);

                    if (checkBoxRememberMe.isChecked()) {
                        saveRemmemberMe(username, password);
                    }


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (Boolean.parseBoolean(response)) {
                                        Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                                    } else {
                                        Toast.makeText(MainActivity.this, "Login failure!", Toast.LENGTH_SHORT).show();
                                        txtUserName.setError("Wrong username!");
                                        txtPassword.setError("Wrong password!");
                                    }

                                    Log.d("Mine Response", "onResponse: " + response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity.this, "Connection to server have problems!", Toast.LENGTH_SHORT).show();
                                    Log.d("Mine ERROR", "onErrorResponse: " + error.toString());
                                }
                            });
                    queue.add(stringRequest);
                }


            }
            //end on click
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void saveRemmemberMe(String u, String p) {
        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", u);
        editor.putString("password", p);
        editor.apply();
    }

    private void checkRemmemberMe() {
        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);

        if (preferences != null) {
            String username = preferences.getString("username", "");
            String password = preferences.getString("password", "");

            txtUserName.setText(username);
            txtPassword.setText(password);
        }

    }


    private void init() {
        txtUserName = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        checkBoxRememberMe = findViewById(R.id.ckcRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        txtUserName.requestFocus();
        checkRemmemberMe();
    }


}

