package com.incode.photo.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.incode.photo.R;
import com.incode.photo.model.Post;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * this Adapter handles the post retrieved from internet, and display into the recyclerview
 */
public class RecyclerAdapter extends RecyclerView.Adapter {

    private LinkedHashMap<Integer, Post> posts;

    RequestManager manager;

    RecyclerAdapter(RequestManager manager) {
        posts = new LinkedHashMap<>();
        this.manager = manager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostViewHolder moveHolder = (PostViewHolder) holder;
        //binding by index in linkedHashMap
        List<Post> postList = new ArrayList<>(posts.values());
        moveHolder.bind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addItems(List<Post> newItems) {
        //this way update the ids
        for (Post post : newItems)
            posts.put(post.id, post);
        notifyItemInserted(posts.size());
        notifyDataSetChanged();
    }

    public void addItem(Post newItem) {
        posts.put(newItem.id, newItem);
        notifyItemInserted(posts.size());
        notifyDataSetChanged();
    }

    /**
     * holder that display every row
     */
    class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;


        PostViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }

        void bind(Post post) {
            if (post == null) return;
            title.setText("" + post.title);
            manager.load(post.photo).asBitmap()
                    ///as url are the same for all, we need to identify each one,
                    ///we cant keep all in memory cause it will overheap the memory
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(img);
            img.setOnClickListener(v -> DetailsActivity.startActivityWithPost(v.getContext(), post));
        }
    }

    /**
     * this method stops the request from glide
     */
    public void stopRequests() {
        manager.onStop();
    }

}

