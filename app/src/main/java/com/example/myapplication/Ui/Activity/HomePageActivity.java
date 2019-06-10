package com.example.myapplication.Ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Ui.Fragment.HomeFragment;
import com.example.myapplication.Ui.Fragment.ProfileFragment;
import com.example.myapplication.Ui.Fragment.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    private TextView txtTitle;
    private BottomNavigationView navigationView;
    ImageView imgProfileAva;
    ShareViewModel viewModel;


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

        viewModel = ViewModelProviders.of(this).get(ShareViewModel.class);
        viewModel.setUsername(getIntent().getStringExtra("username"));
        Log.d("Bundle", getIntent().getStringExtra("username"));


        txtTitle.setText("Home");
        Glide.with(this).load("https://www.geek.com/wp-content/uploads/2017/06/saitamamain-625x352.png").
                apply(RequestOptions.circleCropTransform()).
                into(imgProfileAva);

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
