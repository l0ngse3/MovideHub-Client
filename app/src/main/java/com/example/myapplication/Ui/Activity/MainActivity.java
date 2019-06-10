package com.example.myapplication.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    TextInputEditText txtConfirmPassword;
    TextInputEditText txtUserName;
    TextInputEditText txtPassword;
    TextView txtWelcome, txtRegister;

    TextInputLayout txtConfirmPasswordLayout;

    Button btnLogin;
    Button btnRegisterOpen, btnRegisterSend;
    CheckBox checkBoxRememberMe;

    RequestQueue queue;
    ShareViewModel viewModel;
    Context context;

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

                final Account account = new Account();
                final String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                account.setUsername(username);
                account.setPassword(password);
//                Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
//                intent.putExtra("username", username);
//                startActivity(intent);


                if (username.isEmpty()) {
                    txtUserName.setError("User name required");
                } else if (password.isEmpty()) {
                    txtPassword.setError("Password required");
                } else {

                    queue = Volley.newRequestQueue(context.getApplicationContext());
                    String url = APIConnectorUltils.HOST_NAME + "AuthorService/authors";

                    Log.d("Mine Request", "onRequest" + url);

                    if (checkBoxRememberMe.isChecked()) {
                        saveRemmemberMe(username, password);
                    }

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
                                            if (Boolean.parseBoolean(response.getString("result"))) {
                                                Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                                                intent.putExtra("username", username);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(MainActivity.this, "Login failure!", Toast.LENGTH_SHORT).show();
                                                txtUserName.setError("Wrong username!");
                                                txtPassword.setError("Wrong password!");
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
                                        Toast.makeText(MainActivity.this, "Connection to server have problems!", Toast.LENGTH_SHORT).show();
                                        Log.d("Mine ERROR", "onErrorResponse: " + error.toString());
                                    }
                                });
                        queue.start();
                        queue.add(jsonObjectRequest);
                    } catch (JSONException e) {
                        Log.d("Mine ERROR", "Exeption: " + e.toString());
                    }

                }


            }
            //end on click
        });


        btnRegisterOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtWelcome.setVisibility(View.GONE);
                btnLogin.setVisibility(View.GONE);
                checkBoxRememberMe.setVisibility(View.GONE);
                btnRegisterOpen.setVisibility(View.GONE);

                txtRegister.setVisibility(View.VISIBLE);
                txtConfirmPasswordLayout.setVisibility(View.VISIBLE);
                btnRegisterSend.setVisibility(View.VISIBLE);
            }
        });

        btnRegisterSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                String passwordConfirm = txtConfirmPassword.getText().toString();

                if (!password.equals(passwordConfirm)) {
                    txtConfirmPassword.setError("Password not matched");
                } else {

                }
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

        txtWelcome = findViewById(R.id.txtWelcome);
        txtRegister = findViewById(R.id.txtRegister);

        txtUserName = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        txtConfirmPasswordLayout = findViewById(R.id.txtConfirmPasswordLayout);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        checkBoxRememberMe = findViewById(R.id.ckcRememberMe);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterOpen = findViewById(R.id.btnRegisterOpen);
        btnRegisterSend = findViewById(R.id.btnRegisterSend);


        checkRemmemberMe();
        viewModel = ViewModelProviders.of(this).get(ShareViewModel.class);

        context = this;
    }


}

