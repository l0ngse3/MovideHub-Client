package com.example.myapplication.Ui.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.myapplication.Model.Adapter.FilmSavedAdapter;
import com.example.myapplication.Model.Adapter.SwipeToDeleteCallback;
import com.example.myapplication.Model.Film;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.Model.ShareViewModel;
import com.example.myapplication.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class SavedFragment extends Fragment {

    RecyclerView rcyFilmSaved;
    List<Film> filmList;
    FilmSavedAdapter adapter;

    ShareViewModel viewModel;
    Fragment fragment;
    SwipeRefreshLayout swipeRefreshSaved;
    View viewSaved;

    ItemTouchHelper itemTouchHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        process();
    }

    private void process() {
        swipeRefreshSaved.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filmList.clear();
                init(viewSaved);
            }
        });
    }

    private void init(View view) {
        viewSaved = view;
        swipeRefreshSaved = view.findViewById(R.id.swipeRefreshSaved);
        rcyFilmSaved = view.findViewById(R.id.rcyFilmSaved);
        fragment = this;
        if(filmList == null )
            filmList = new ArrayList<>();

        viewModel = ViewModelProviders.of(getActivity()).get(ShareViewModel.class);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = APIConnectorUltils.HOST_STORAGE_FILM + "Saved/" + viewModel.getUsername().getValue();
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshSaved.setRefreshing(false);
                        Film[] films = new Gson().fromJson(response, Film[].class);

                        for(Film film : films)
                            filmList.add(film);

                        if(adapter==null)
                        {
                            adapter = new FilmSavedAdapter(filmList, fragment);
                            rcyFilmSaved.setAdapter(adapter);
                            rcyFilmSaved.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                            itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
                            itemTouchHelper.attachToRecyclerView(rcyFilmSaved);
                        }
                        else {
                            adapter.notifyDataSetChanged();
                        }

                        Log.d("Mine Film saved", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Missing data film saved.", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.start();
        queue.add(request);
    }
}
