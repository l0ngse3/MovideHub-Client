package com.example.myapplication.Ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Adapter.FilmHomeAdapter;
import com.example.myapplication.Model.Adapter.GenreHomeAdapter;
import com.example.myapplication.Model.Film;
import com.example.myapplication.Model.Genre;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView rcyGenreHome;
    List<Genre> genreList = new ArrayList<>();
    GenreHomeAdapter genreHomeAdapter;


    Context context = getContext();
    HomeFragment fragment = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    private void init(View view) {
        rcyGenreHome = view.findViewById(R.id.rcyGenreHome);

        List<String> genreListRequest = new ArrayList<>();
        genreListRequest.add("Action");
        genreListRequest.add("Adventure");
        genreListRequest.add("Comedy");
        genreListRequest.add("Cartoon");
        genreListRequest.add("Horror");
        genreListRequest.add("Romance");
        genreListRequest.add("Science & Fiction");

        for (final String genre : genreListRequest) {
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = APIConnectorUltils.HOST_STORAGE_FILM + "/" + genre;
            StringRequest request = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Mine films", response);
//                            Type type = new TypeToken<List<Film>>() {
//                            }.getType();
                            Genre gen = new Genre();
                            try {
                                JSONObject object = new JSONObject(response);
                                gen.setNameGenre(object.getString("genre"));
                                Type type = new TypeToken<List<Film>>() {
                                }.getType();
                                gen.setList((List<Film>) new Gson().fromJson(object.getString("list"), type));


                                if (gen.getList().size() > 0)
                                {
                                    genreList.add(gen);
                                    gen.toString();
                                    for (int i = 0; i < 10; i++)
                                    {
                                        Film film = genreList.get(genreList.size() - 1).getList().get(0);
                                        genreList.get(genreList.size() - 1).getList().add(film);
                                    }

                                    genreHomeAdapter = new GenreHomeAdapter(genreList, fragment);
                                    rcyGenreHome.setAdapter(genreHomeAdapter);
                                    rcyGenreHome.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                                    genreHomeAdapter.notifyDataSetChanged();

                                    Log.d("Mine film genre", response);
                                }
                                else if(genreList.size()>0)
                                {
                                    for(int i=0; i<3; i++)
                                    {
                                        genreList.add(genreList.get(genreList.size()-1));
                                        genreHomeAdapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Missing data film.", Toast.LENGTH_SHORT).show();
                        }
                    });

            queue.start();
            queue.add(request);
        }


        // load infor film from server
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        String url = APIConnectorUltils.HOST_STORAGE_FILM + "/FilmHome";
//        StringRequest request = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Mine films", response);
//                        Type type = new TypeToken<List<Film>>(){}.getType();
//                        filmList = new Gson().fromJson(response, type);
//
//                        for(int i=0; i<30; i++)
//                        {
//                            Film film = filmList.get(0);
//                            filmList.add(film);
//                        }
//                        filmHomeAdapter = new FilmHomeAdapter(filmList, fragment);
//                        rcyGenreHome.setAdapter(filmHomeAdapter);
//                        rcyGenreHome.setLayoutManager(new GridLayoutManager(context, 3));
//                        filmHomeAdapter.notifyDataSetChanged();
//
//                        Log.d("Mine one film", filmList.get(0).toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Missing data film.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        queue.start();
//        queue.add(request);
    }
}
