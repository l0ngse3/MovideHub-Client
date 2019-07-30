package com.example.myapplication.Model.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Film;
import com.example.myapplication.Model.Genre;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class GenreHomeAdapter extends RecyclerView.Adapter<GenreHomeAdapter.ViewHolder> {

    private List<Genre> list;
    private Fragment fragment;

    FilmHomeAdapter filmHomeAdapter;

    public GenreHomeAdapter(List<Genre> list, Fragment fragment) {
        this.list = list;
        this.fragment = fragment;
    }



    @NonNull
    @Override
    public GenreHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View genreHome = inflater.inflate(R.layout.list_item_genre, parent, false);
        return new ViewHolder(genreHome);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHomeAdapter.ViewHolder holder, int position) {

        Genre genre = list.get(position);
        holder.txtGenre.setText(genre.getNameGenre());

        filmHomeAdapter = new FilmHomeAdapter(genre.getList(), fragment);
        holder.rcyFilmHorizontal.setAdapter(filmHomeAdapter);
        holder.rcyFilmHorizontal.setLayoutManager(new LinearLayoutManager(fragment.getContext(), RecyclerView.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtGenre;
        RecyclerView rcyFilmHorizontal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            rcyFilmHorizontal = itemView.findViewById(R.id.rcyFilmHorizontal);
        }
    }

}
