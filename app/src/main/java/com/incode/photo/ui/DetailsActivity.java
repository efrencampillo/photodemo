package com.incode.photo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.RequestManager;
import com.incode.photo.R;
import com.incode.photo.core.PhotoApp;
import com.incode.photo.model.Post;
import com.incode.photo.presenters.DetailsPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DetailsActivity extends AppCompatActivity {

    @Inject
    DetailsPresenter mDetailsPresenter;

    @Inject
    RequestManager glide;

    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        PhotoApp.getInstance().getAppComponent().inject(this);
        mDetailsPresenter.setPost(getPostFromIntent());
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPostOnUI();
    }

    private void setPostOnUI() {
        Post post = mDetailsPresenter.getPost();
        //todo set on UI
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
        glide.onStop();
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
