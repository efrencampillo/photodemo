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
import com.incode.photo.core.PhotoApp;
import com.incode.photo.model.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<Post> posts;

    @Inject
    RequestManager mManager;

    RecyclerAdapter() {
        posts = new ArrayList<>();
        PhotoApp.getInstance().getAppComponent().inject(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostViewHolder moveHolder = (PostViewHolder) holder;
        moveHolder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addItems(List<Post> newItems) {
        int position = posts.size();
        posts.addAll(newItems);
        notifyItemInserted(position);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;

        PostViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }

        void bind(Post post) {
            title.setText(post.title);
            mManager.load(post.photo).asBitmap()
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(img);
            img.setOnClickListener(v -> DetailsActivity.startActivityWithPost(v.getContext(), post));
        }
    }

    public void stopRequests() {
        mManager.onStop();
    }

}

