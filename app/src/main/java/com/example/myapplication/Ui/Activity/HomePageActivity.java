package com.example.myapplication.Ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.example.myapplication.Ui.Fragment.HomeFragment;
import com.example.myapplication.Ui.Fragment.ProfileFragment;
import com.example.myapplication.Ui.Fragment.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;



public class HomePageActivity extends AppCompatActivity {

    private TextView txtTitle;
    private BottomNavigationView navigationView;
    ImageView imgProfileAva, imgSearch;
    ShareViewModel viewModel;

    Profile profile;
    Context context;
    PopupMenu popup;
    HomePageActivity homePageActivity = this;

    MenuItem menuItem;



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
        imgSearch = findViewById(R.id.imgSearch);
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

                                Glide.with(context)
                                        .load(APIConnectorUltils.HOST_STORAGE_IMAGE + new Gson().fromJson(s, Profile.class).getImage())
                                        .placeholder(R.drawable.saitama)
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
                menuItem = item;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
                        txtTitle.setText("Home");
                        imgSearch.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.nav_saved:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedFragment()).addToBackStack(null).commit();
                        txtTitle.setText("Saved");
                        imgSearch.setVisibility(View.GONE);
                        return true;

                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                        txtTitle.setText("Profile");
                        imgSearch.setVisibility(View.GONE);
                        return true;
                }

                return false;
            }
        });

        //show popup menu when click to image avatar
        imgProfileAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup = new PopupMenu(context,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.action_log_out:
                                context.startActivity(new Intent(context, MainActivity.class));
                                homePageActivity.finish();
                                return true;

                            case R.id.action_settings:
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack(null).commit();
                                txtTitle.setText("Profile");
                                navigationView.setSelectedItemId(R.id.nav_profile);
                                return true;
                        }
                        return false;
                    }
                });// to implement on click event on items of menu
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.home, popup.getMenu());
                popup.show();
            }
        });

        //show search activity
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, SearchActivity.class);
                intent.putExtra("username", viewModel.getUsername().getValue());
                startActivity(intent);
            }
        });
    }

    /******************************************************/


}
