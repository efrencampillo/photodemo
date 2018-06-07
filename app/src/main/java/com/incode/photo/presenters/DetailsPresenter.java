package com.incode.photo.presenters;


import com.incode.photo.model.Post;

/**
 * details presenter, is not really needed but added to follow the architecture pattern
 */

public class DetailsPresenter {

    private Post mCurrentPost = null;

    public DetailsPresenter() {

    }

    public void setPost(Post post) {
        mCurrentPost = post;
    }

    public Post getPost() {
        return mCurrentPost;
    }

}
