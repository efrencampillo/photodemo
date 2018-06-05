package com.incode.photo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.incode.photo.R;
import com.incode.photo.core.PhotoApp;
import com.incode.photo.model.Post;
import com.incode.photo.presenters.HomePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Main Activity that display the lisst of the images retrieved from heroku
 */
public class HomeActivity extends AppCompatActivity {

    @Inject
    HomePresenter mHomePresenter;

    private CompositeDisposable disposables;
    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PhotoApp.getInstance().getAppComponent().inject(this);

        RecyclerView mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAdapter = new RecyclerAdapter(Glide.with(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((View view) ->
                AddPostActivity.startActivityForResult(HomeActivity.this, mRecyclerAdapter.getItemCount() + 1));
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHomePresenter.getFeed().subscribe(mRecyclerAdapter::addItems,
                error -> Toast.makeText(this, "error at try to get feed", Toast.LENGTH_SHORT).show());

    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecyclerAdapter.stopRequests();
        disposables.clear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            Post post = getPostFromIntent(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Post getPostFromIntent(Intent intent) {
        Post post = new Post();
        post.id = intent.getIntExtra("id", 0);
        post.title = intent.getStringExtra("title");
        post.photo = intent.getStringExtra("photo");
        post.comment = intent.getStringExtra("comment");
        post.publishedAt = intent.getStringExtra("publishedAt");
        return post;
    }
}
