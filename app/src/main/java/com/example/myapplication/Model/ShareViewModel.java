package com.example.myapplication.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class ShareViewModel extends AndroidViewModel {
    private MutableLiveData<String> username = new MutableLiveData<>();

    public ShareViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUsername(String username)
    {
        this.username.setValue(username);
    }

    public LiveData<String> getUsername()
    {
        return username;
    }
}
