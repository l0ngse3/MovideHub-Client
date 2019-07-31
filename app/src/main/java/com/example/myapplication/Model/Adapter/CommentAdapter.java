package com.example.myapplication.Model.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.APIConnectorUltils;
import com.example.myapplication.Model.Comment;
import com.example.myapplication.Model.Film;
import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;
import com.google.gson.Gson;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> list;
    private Context context;

    private Profile profile;

    public CommentAdapter(List<Comment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Comment comment = list.get(position);

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = APIConnectorUltils.HOST_NAME + "Profile/"+comment.getUsername();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        profile = new Gson().fromJson(response, Profile.class);

                        if(profile.getImage().equals("null"))
                        {
                            Glide.with(context).load(APIConnectorUltils.HOST_STORAGE_IMAGE+"saitama.png")
                                    .centerCrop()
                                    .apply(new RequestOptions().circleCrop())
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .into(holder.imgAvaCmt);
                        }
                        else {
                            Glide.with(context).load(APIConnectorUltils.HOST_STORAGE_IMAGE + profile.getImage())
                                    .centerCrop()
                                    .apply(new RequestOptions().circleCrop())
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .into(holder.imgAvaCmt);
                        }


                        holder.txtFullnameCmt.setText(profile.getFistName()+" "+profile.getLastName());
                    }
                }, null);
        queue.start();
        queue.add(request);

        holder.txtContentCmt.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvaCmt;
        TextView txtFullnameCmt, txtContentCmt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvaCmt = itemView.findViewById(R.id.imgAvaCmt);
            txtContentCmt = itemView.findViewById(R.id.txtContentCmt);
            txtFullnameCmt = itemView.findViewById(R.id.txtFullnameCmt);
        }
    }
}
