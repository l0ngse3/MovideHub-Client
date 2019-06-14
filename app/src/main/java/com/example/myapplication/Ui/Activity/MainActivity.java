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
import com.example.myapplication.Service.ClientService;
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
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

//                Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
//                intent.putExtra("username", username);
//                startActivity(intent);


                if (username.isEmpty()) {
                    txtUserName.setError("User name required");
                } else if (password.isEmpty()) {
                    txtPassword.setError("Password required");
                } else {
                    if (checkBoxRememberMe.isChecked()) {
                        saveRemmemberMe(username, password);
                    }
                    ClientService service = new ClientService();
                    service.postLogin(username, password, context);
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
                    ClientService service =  new ClientService();
                    service.postRegister(username, password, context);
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

