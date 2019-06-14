package com.example.myapplication.Ui.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Ui.Activity.HomePageActivity;
import com.google.gson.Gson;


public class ProfileFragment extends Fragment {

    ImageView imgAva;
    TextView txtUser;
    EditText edtFullname;
    ShareViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        imgAva = view.findViewById(R.id.imgAva);
        edtFullname = view.findViewById(R.id.txtFullname);
        txtUser = view.findViewById(R.id.txtUser);



        viewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        viewModel.getUsername().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtUser.setText(s);
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = APIConnectorUltils.HOST_NAME + "Profile/" + viewModel.getUsername().getValue();
        final String image = "http://192.168.73.100:8080/Storage/saitama.png";
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Profile profile = new Gson().fromJson(response, Profile.class);
                        edtFullname.setText(profile.getFistName()+" "+profile.getLastName());

                        Glide.with(view).load(image)
                                .centerCrop()
                                .apply(RequestOptions.circleCropTransform())
                                .into(imgAva);
                        Log.d("Mine Profile", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Missing data profile.", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.start();
        queue.add(request);

    }
}
