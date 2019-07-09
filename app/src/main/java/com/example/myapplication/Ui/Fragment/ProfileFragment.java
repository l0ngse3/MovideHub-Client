package com.example.myapplication.Ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Service.ClientService;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    ImageView imgAva;
    TextView txtUser;
    TextView txtFullname;
    ShareViewModel viewModel;


    Profile profile;
    Bitmap bitmap;
    int REQUEST_CODE_PHOTO = 1000;
    Context context;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        handleListener();
    }

    private void handleListener() {
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker, REQUEST_CODE_PHOTO);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_PHOTO && resultCode ==  RESULT_OK)
        {
            Uri dataPath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), dataPath);
                Glide.with(this).load(bitmap)
                        .placeholder(R.drawable.saitama)
                        .centerCrop()
                        .apply(RequestOptions.circleCropTransform())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imgAva);

                profile.setImage(bitmapToString(bitmap));
                Log.d("Mine Profile update", profile.toString());
                ClientService service = new ClientService();
                service.postInfoAccount(profile, getActivity());

//                init(view);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init(final View view) {
        imgAva = view.findViewById(R.id.imgAva);
        txtFullname = view.findViewById(R.id.txtFullname);
        txtUser = view.findViewById(R.id.txtUser);
        context = this.getContext();
        this.view = view;

        //get user name from view model
        viewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        viewModel.getUsername().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtUser.setText(s);
            }
        });


        //load infor of account
        loadInforAccount();

        viewModel.getProfile().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Profile profile = new Gson().fromJson(s, Profile.class);
                txtFullname.setText(profile.getFistName() + " " + profile.getLastName());

                Glide.with(context).load(APIConnectorUltils.HOST_STORAGE_IMAGE + profile.getImage())
                        .placeholder(R.drawable.saitama)
                        .centerCrop()
                        .apply(RequestOptions.circleCropTransform())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imgAva);

                Log.d("Mine update image", "onChanged: Update image imgAva");
            }
        });
    }


    private void loadInforAccount()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = APIConnectorUltils.HOST_NAME + "Profile/" + viewModel.getUsername().getValue();
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Profile p = new Gson().fromJson(response, Profile.class);
                        profile = p;
                        viewModel.setProfile(response);

                        Log.d("Mine Profile 2", response);
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

    private String bitmapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, arrayOutputStream);
        byte[] imageByte = arrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }
}
