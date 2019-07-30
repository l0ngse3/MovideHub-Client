package com.example.myapplication.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


public class ShareViewModel extends AndroidViewModel {
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> profile = new MutableLiveData<>();
    private MutableLiveData<List<Film>> allFilmList = new MutableLiveData<>();


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

    public MutableLiveData<String> getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile.setValue(profile);
    }

    public MutableLiveData<List<Film>> getAllFilmList() {
        return allFilmList;
    }

    public void setAllFilmList(List<Film> allFilmList) {
        this.allFilmList.setValue(allFilmList);
    }
}
