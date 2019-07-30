package com.example.myapplication.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Film;
import com.example.myapplication.Model.FilmWatched;
import com.example.myapplication.R;
import com.example.myapplication.Service.ClientService;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.GET;

public class FilmPlayActivity extends AppCompatActivity {

    VideoView videoViewPlay;
    String username;
    Film film;
    ClientService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_play);

        videoViewPlay = findViewById(R.id.videoViewPlay);
        film = new Gson().fromJson(getIntent().getStringExtra("film"), Film.class);
        int currentProgress = getIntent().getIntExtra("currentTime", 0);
        username = getIntent().getStringExtra("username");

        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoViewPlay);

        videoViewPlay.setVideoPath(APIConnectorUltils.HOST_STORAGE + film.getFilm_url());
        videoViewPlay.setMediaController(mediaController);
        videoViewPlay.seekTo(currentProgress);
        videoViewPlay.start();

        //update film view when film play
        service = new ClientService();
        service.updateFilmViews(film, this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FilmWatched filmWatched = new FilmWatched();
        filmWatched.setIdFilm(film.getId_film());
        filmWatched.setUsername(username);
        filmWatched.setCurProgress( (int) (videoViewPlay.getCurrentPosition()/1000));
        Log.d("Mine back press", "onBackPressed: "+filmWatched.getIdFilm() + " " + filmWatched.getCurProgress());
        service.updateFilmWatched(filmWatched, this);
    }

}
