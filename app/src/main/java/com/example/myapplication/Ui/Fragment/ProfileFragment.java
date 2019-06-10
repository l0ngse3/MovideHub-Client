package com.example.myapplication.Ui.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imgAva = view.findViewById(R.id.imgAva);
        edtFullname = view.findViewById(R.id.txtFullname);
        txtUser = view.findViewById(R.id.txtUser);

        Glide.with(view).load(R.drawable.saitama)
                .apply(RequestOptions.circleCropTransform())
                .into(imgAva);

        viewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        viewModel.getUsername().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtUser.setText(s);
            }
        });


    }
}
