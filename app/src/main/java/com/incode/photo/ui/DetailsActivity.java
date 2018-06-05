package com.incode.photo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.incode.photo.R;
import com.incode.photo.core.PhotoApp;
import com.incode.photo.model.Post;
import com.incode.photo.presenters.DetailsPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * this activity shows the description of the post taht we clicked in the Home Activity
 */
public class DetailsActivity extends AppCompatActivity {

    @Inject
    DetailsPresenter mDetailsPresenter;

    private CompositeDisposable disposables;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        PhotoApp.getInstance().getAppComponent().inject(this);
        mDetailsPresenter.setPost(getPostFromIntent());
        disposables = new CompositeDisposable();
        image = findViewById(R.id.detail_img);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPostOnUI();
    }

    private void setPostOnUI() {
        Post post = mDetailsPresenter.getPost();
        ((TextView) findViewById(R.id.post_id)).setText("" + post.id);
        ((TextView) findViewById(R.id.title)).setText(post.title);
        ((TextView) findViewById(R.id.comment)).setText(post.comment);
        ((TextView) findViewById(R.id.published)).setText(post.publishedAt);
        Glide.with(this).load(post.photo).asBitmap()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(image);
    }

    private Post getPostFromIntent() {
        Post post = new Post();
        post.id = getIntent().getIntExtra("id", 0);
        post.title = getIntent().getStringExtra("title");
        post.photo = getIntent().getStringExtra("photo");
        post.comment = getIntent().getStringExtra("comment");
        post.publishedAt = getIntent().getStringExtra("publishedAt");
        return post;
    }


    @Override
    protected void onPause() {
        super.onPause();
        disposables.clear();
        Glide.get(this).clearMemory();
    }


    public static void startActivityWithPost(Context context, Post post) {
        Intent i = new Intent(context, DetailsActivity.class);
        i.putExtra("id", post.id);
        i.putExtra("photo", post.photo);
        i.putExtra("title", post.title);
        i.putExtra("comment", post.comment);
        i.putExtra("publishedAt", post.publishedAt);
        context.startActivity(i);
    }
}
