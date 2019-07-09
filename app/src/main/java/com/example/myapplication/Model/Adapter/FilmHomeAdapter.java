package com.example.myapplication.Model.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Film;
import com.example.myapplication.R;

import java.util.List;

public class FilmHomeAdapter extends RecyclerView.Adapter<FilmHomeAdapter.ViewHolder> {

    private List<Film> list;
    private Fragment fragment;

    public FilmHomeAdapter(List<Film> list, Fragment fragment) {
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public FilmHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View homeFilm = inflater.inflate(R.layout.list_sub_item_fiml_of_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(homeFilm);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmHomeAdapter.ViewHolder holder, int position) {
        Film film = list.get(position);

        Glide.with(fragment).load(APIConnectorUltils.HOST_STORAGE + film.getThumbnail())
                .centerCrop()
                .apply(new RequestOptions().override(128, 72))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imgThumbnail);

        holder.txtDescriptionThumbnail.setText(film.getFilm_description());
        holder.txtRateThumbnail.setText(film.getRate_imdb()+"/10");
        holder.txtViewThumbnail.setText(film.getFilm_views()+" views");
        holder.txtTitleThumbnail.setText(film.getTitle_film());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgThumbnail;
        TextView txtRateThumbnail, txtTitleThumbnail, txtDescriptionThumbnail, txtViewThumbnail;
        ProgressBar progressFilmWatched;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            txtRateThumbnail = itemView.findViewById(R.id.txtRateThumbnail);
            txtTitleThumbnail = itemView.findViewById(R.id.txtTitleThumbnail);
            txtDescriptionThumbnail = itemView.findViewById(R.id.txtDescriptionThumbnail);
            txtViewThumbnail = itemView.findViewById(R.id.txtViewThumbnail);
            progressFilmWatched = itemView.findViewById(R.id.progressFilmWatched);
        }
    }
}
