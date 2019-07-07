package com.example.myapplication.Ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Service.ClientService;
import com.example.myapplication.Ui.Fragment.HomeFragment;
import com.example.myapplication.Ui.Fragment.ProfileFragment;
import com.example.myapplication.Ui.Fragment.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class HomePageActivity extends AppCompatActivity {

    private TextView txtTitle;
    private BottomNavigationView navigationView;
    ImageView imgProfileAva;
    ShareViewModel viewModel;

    Profile profile;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        init();
        process();

    }


    /******************************************************/
    private void init() {
        txtTitle = findViewById(R.id.txtTitle);
        navigationView = findViewById(R.id.bottom_nav);
        imgProfileAva = findViewById(R.id.imgProfileAva);
        context = HomePageActivity.this;

        viewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        viewModel.setUsername(getIntent().getStringExtra("username"));

        txtTitle.setText("Home");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = APIConnectorUltils.HOST_NAME + "Profile/" + viewModel.getUsername().getValue();
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Profile p = new Gson().fromJson(response, Profile.class);
                        profile = p;

                        viewModel.setProfile(response);
                        viewModel.getProfile().observe((LifecycleOwner) context, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {

                                Glide.with(context).load(APIConnectorUltils.HOST_STORAGE_IMAGE + new Gson().fromJson(s, Profile.class).getImage())
                                        .centerCrop()
                                        .apply(RequestOptions.circleCropTransform())
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .into(imgProfileAva);

                                Log.d("Mine update image", "onChanged: Update image imgProfileAva");
                            }
                        });

                        Log.d("Mine Profile 1", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Missing data profile.", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.start();
        queue.add(request);


        navigationView.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
    }

    /******************************************************/
    private void process() {

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(HomePageActivity.this, "Click", Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
                        txtTitle.setText("Home");
                        return true;

                    case R.id.nav_saved:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedFragment()).addToBackStack(null).commit();
                        txtTitle.setText("Saved");
                        return true;

                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                        txtTitle.setText("Profile");
                        return true;
                }

                return false;
            }
        });

    }

}
