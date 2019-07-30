package com.example.myapplication.Ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Adapter.FilmSavedAdapter;
import com.example.myapplication.Model.Adapter.FilmSearchAdapter;
import com.example.myapplication.Model.Adapter.SwipeToDeleteCallback;
import com.example.myapplication.Model.Film;
import com.example.myapplication.R;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SearchView btnSearch;
    TextView txtTitleSearch;
    SearchActivity searchActivity;

    RecyclerView rcySearch;
    FilmSearchAdapter adapter;
    List<Film> filmList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
        process();
    }

    private void process() {
        btnSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitleSearch.setVisibility(View.GONE);
            }
        });

        btnSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                txtTitleSearch.setVisibility(View.VISIBLE);
                return false;
            }
        });


        btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void init() {
        btnSearch = findViewById(R.id.btnSearch);
        rcySearch = findViewById(R.id.rcySearch);
        txtTitleSearch = findViewById(R.id.txtTitleSearch);
        searchActivity = this;
        filmList = new ArrayList<>();

        //search action
        // android.widget.SearchView;
        int searchTextViewId = btnSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchText = btnSearch.findViewById(searchTextViewId);
        searchText.setTextColor(Color.parseColor("#ffffff"));
        searchText.setHintTextColor(Color.parseColor("#ffffff"));
        //set cursor text color
        Field f = null;
        try {
            f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(searchText, R.drawable.color_white_cursor);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = APIConnectorUltils.HOST_STORAGE_FILM + "GetAll";
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Film[] films = new Gson().fromJson(response, Film[].class);

                        if(films.length!=0)
                        {
                            for(Film film : films)
                                filmList.add(film);

                            adapter = new FilmSearchAdapter(filmList, searchActivity);
                            rcySearch.setAdapter(adapter);
                            rcySearch.setLayoutManager(new LinearLayoutManager(searchActivity, RecyclerView.VERTICAL, false));
                            adapter.notifyDataSetChanged();
                        }

                        Log.d("Mine Film saved", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(searchActivity, "Missing data film saved.", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.start();
        queue.add(request);
    }
}
