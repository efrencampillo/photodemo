package com.incode.photo.presenters;


import com.incode.photo.model.Post;

public class DetailsPresenter {

    Post mCurrentPost = null;

    public DetailsPresenter() {

    }

    public void setPost(Post post) {
        mCurrentPost = post;
    }

    public Post getPost() {
        return mCurrentPost;
    }

}
