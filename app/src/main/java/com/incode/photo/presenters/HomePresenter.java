package com.incode.photo.presenters;

import com.incode.photo.core.NetworkInterface;
import com.incode.photo.model.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {

    private NetworkInterface mNetworkInterface;
    private List<Post> mList;

    public HomePresenter(NetworkInterface networkInterface) {
        this.mNetworkInterface = networkInterface;
        mList = new ArrayList<>();
    }


    public Observable<List<Post>> getFeed() {
        return mNetworkInterface.getPhotos()
                .map(posts -> {
                    //TODO validate saved locally
                    mList = posts;
                    return mList;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
