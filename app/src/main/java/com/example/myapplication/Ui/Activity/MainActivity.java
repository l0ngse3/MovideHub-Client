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
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Service.ClientService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;



public class MainActivity extends AppCompatActivity {


    TextInputEditText txtConfirmPassword;
    TextInputEditText txtUserName;
    TextInputEditText txtPassword;
    TextView txtWelcome, txtRegister;

    TextInputLayout txtConfirmPasswordLayout;

    Button btnLogin;
    Button btnRegisterOpen, btnRegisterSend;
    ImageButton btnBack;
    CheckBox checkBoxRememberMe;

    ShareViewModel viewModel;
    MainActivity context;

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
                    txtPassword.setError("Password required");
                } else {
                    if (checkBoxRememberMe.isChecked()) {
                        saveRemmemberMe(username, password);
                    }
                    else {
                        saveRemmemberMe("", "");
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

                btnBack.setVisibility(View.VISIBLE);
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtWelcome.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);
                checkBoxRememberMe.setVisibility(View.VISIBLE);
                btnRegisterOpen.setVisibility(View.VISIBLE);

                txtRegister.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                txtConfirmPasswordLayout.setVisibility(View.GONE);
                btnRegisterSend.setVisibility(View.GONE);
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
            if(!txtUserName.getText().toString().isEmpty())
            {
                checkBoxRememberMe.setChecked(true);
            }
            else
            {
                checkBoxRememberMe.setChecked(false);
            }
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
        btnBack = findViewById(R.id.btnBack);


        checkRemmemberMe();
        viewModel = ViewModelProviders.of(this).get(ShareViewModel.class);

        context = this;
    }


}

