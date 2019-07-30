package com.example.myapplication.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Film;
import com.example.myapplication.R;
import com.google.gson.Gson;


public class FilmActivity extends AppCompatActivity {

    TextView txtTitleFilm, txtViewFilm, txtRateFilm, txtDescriptionFilm, txtPlayMode;
    Button btnTrailerFilm, btnWatchFilm;
    ImageView btnLoveFilm, imgThumbnailFilm;
    VideoView videoViewFilm;
    RecyclerView rcyComment;

    LinearLayout layoutVideo;
    Film film;
    Context context;
    int current_progress = 0;
    String username;
    boolean isLoved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        init();
        process();
    }

    private void process() {

        final MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoViewFilm);

        //get current progress of this video
        //if it's not exist response return 0
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = APIConnectorUltils.HOST_STORAGE_FILM + "Watched/GetCurrentProgress?username="+username+"&"+"filmId="+film.getId_film();
        Log.d("Mine Current progress", "process: "+url);
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        current_progress = new Gson().fromJson(response, Integer.class);
                        current_progress*=1000;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Missing data id of film.", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.start();
        queue.add(request);


        layoutVideo.setVisibility(View.VISIBLE);
        videoViewFilm.setVideoPath(APIConnectorUltils.HOST_STORAGE + film.getFilm_url());
        videoViewFilm.setMediaController(mediaController);
        videoViewFilm.start();

        btnTrailerFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPlayMode.setText("Trailer");
                layoutVideo.setVisibility(View.VISIBLE);
                videoViewFilm.setVideoPath(APIConnectorUltils.HOST_STORAGE + film.getFilm_url());
                videoViewFilm.setMediaController(mediaController);
                videoViewFilm.start();
            }
        });

        btnWatchFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FilmPlayActivity.class);
                intent.putExtra("film", new Gson().toJson(film));
                intent.putExtra("username", username);
                intent.putExtra("currentTime", current_progress);
                context.startActivity(intent);
            }
        });

        btnLoveFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLoved)
                {
                    btnLoveFilm.setImageResource(R.drawable.icon_like);
                    isLoved = false;
                }
                else {
                    btnLoveFilm.setImageResource(R.drawable.icon_like_selected);
                    isLoved = true;
                }

                RequestQueue queue = Volley.newRequestQueue(context);
                String url = APIConnectorUltils.HOST_STORAGE_FILM + "Save/Loved?filmId=" + film.getId_film()+"&username="+username;
                Log.d("Mine is saved ", film.getFilm_views());
                StringRequest request = new StringRequest(Request.Method.PUT, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(Boolean.parseBoolean(response))
                                {
                                    btnLoveFilm.setImageResource(R.drawable.icon_like_selected);
                                    isLoved = true;
                                }
                                else {
                                    btnLoveFilm.setImageResource(R.drawable.icon_like);
                                    isLoved = false;
                                }
                            }
                        }, null);
                queue.start();
                queue.add(request);
            }
        });
    }

    private void init() {
        txtTitleFilm = findViewById(R.id.txtTitleFilm);
        txtViewFilm = findViewById(R.id.txtViewFilm);
        txtRateFilm = findViewById(R.id.txtRateFilm);
        txtDescriptionFilm = findViewById(R.id.txtDescriptionFilm);
        txtPlayMode = findViewById(R.id.txtPlayMode);
        btnTrailerFilm = findViewById(R.id.btnTrailerFilm);
        btnWatchFilm = findViewById(R.id.btnWatchFilm);
        btnLoveFilm = findViewById(R.id.btnLoveFilm);
        imgThumbnailFilm = findViewById(R.id.imgThumbnailFilm);
        videoViewFilm = findViewById(R.id.videoViewFilm);
        rcyComment = findViewById(R.id.rcyComment);
        layoutVideo = findViewById(R.id.layoutVideo);
        context = this;
        isLoved = false;

        film = new Gson().fromJson(getIntent().getStringExtra("film"), Film.class);
        username = getIntent().getStringExtra("username");
        txtTitleFilm.setText(film.getTitle_film());
        txtViewFilm.setText(film.getFilm_views() + " views");
        txtRateFilm.setText("Imdb: " + film.getRate_imdb());
        txtDescriptionFilm.setText(film.getFilm_description());

        Glide.with(this).load(APIConnectorUltils.HOST_STORAGE + film.getThumbnail())
                .centerCrop()
                .apply(new RequestOptions().override(128, 72))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imgThumbnailFilm);

        //request to check loved
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = APIConnectorUltils.HOST_STORAGE_FILM + "Save/IsSaved?filmId=" + film.getId_film()+"&username="+username;
        Log.d("Mine is saved ", film.getFilm_views());
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Boolean.parseBoolean(response))
                        {
                            btnLoveFilm.setImageResource(R.drawable.icon_like_selected);
                            isLoved = true;
                        }
                        else {
                            btnLoveFilm.setImageResource(R.drawable.icon_like);
                            isLoved = false;
                        }
                    }
                }, null);
        queue.start();
        queue.add(request);

    }


}
