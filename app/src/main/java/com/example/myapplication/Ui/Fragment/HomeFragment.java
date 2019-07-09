package com.example.myapplication.Ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.myapplication.Model.Film;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView rcyGenreHome;
    List<Film>  filmList;
    FilmHomeAdapter filmHomeAdapter;

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

        List<String> categoryList = new ArrayList<>();
        categoryList.add("Science & Fiction");

        // load infor film from server
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = APIConnectorUltils.HOST_STORAGE_FILM + "/FilmHome";
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Mine films", response);
                        Type type = new TypeToken<List<Film>>(){}.getType();
                        filmList = new Gson().fromJson(response, type);

                        for(int i=0; i<30; i++)
                        {
                            Film film = filmList.get(0);
                            filmList.add(film);
                        }
                        filmHomeAdapter = new FilmHomeAdapter(filmList, fragment);
                        rcyGenreHome.setAdapter(filmHomeAdapter);
                        rcyGenreHome.setLayoutManager(new GridLayoutManager(context, 3));
                        filmHomeAdapter.notifyDataSetChanged();

                        Log.d("Mine one film", filmList.get(0).toString());
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
}
