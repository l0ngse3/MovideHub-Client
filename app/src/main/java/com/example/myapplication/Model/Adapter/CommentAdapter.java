package com.example.myapplication.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.example.myapplication.Ui.Activity.FilmActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> list;
    private FilmActivity context;

    private Profile profile;
    String ownerUser;
    Comment comment;
//    int posDelete=0;

    public CommentAdapter(List<Comment> list, FilmActivity context, String ownerUser) {
        this.list = list;
        this.context = context;
        this.ownerUser = ownerUser;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        comment = list.get(position);
//        posDelete = position;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = APIConnectorUltils.HOST_NAME + "Profile/"+comment.getUsername();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        profile = new Gson().fromJson(response, Profile.class);

                        if(context!=null)
                        {
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
                        }


                        holder.txtFullnameCmt.setText(profile.getFistName()+" "+profile.getLastName());
                    }
                }, null);
        queue.start();
        queue.add(request);

        holder.txtContentCmt.setText(comment.getContent());

        if(comment.getUsername().equals(ownerUser))
        {
            holder.imgMoreAction.setVisibility(View.VISIBLE);
        }

        holder.imgMoreAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.action_edit:
                                context.getTxtCmtText().setText(list.get(position).getContent());
                                context.getTxtCmtText().requestFocus();
                                context.setFlagEdit(1);
                                context.setComment(list.get(position));
                                return true;

                            case R.id.action_delete:
                                context.deleteComment(position);
                                return true;
                        }
                        return false;
                    }
                });// to implement on click event on items of menu
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.more_action_comment, popup.getMenu());
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvaCmt, imgMoreAction;
        TextView txtFullnameCmt, txtContentCmt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvaCmt = itemView.findViewById(R.id.imgAvaCmt);
            txtContentCmt = itemView.findViewById(R.id.txtContentCmt);
            txtFullnameCmt = itemView.findViewById(R.id.txtFullnameCmt);
            imgMoreAction = itemView.findViewById(R.id.imgMoreAction);
        }
    }
}
