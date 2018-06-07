package com.incode.photo.presenters;

import com.incode.photo.core.NetworkInterface;
import com.incode.photo.model.Post;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Home presenter is the broker between the ui and the network requests,
 * saves temporaly the information cause t here is no repository yet
 */

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
                    //TODO imprive the way to save this cause there is no repository yet
                    for (Post post : posts) {
                        //TODO unharcode this process
                        URL url = new URL(post.photo);
                        HttpURLConnection ucon = (HttpURLConnection) url.openConnection();
                        ucon.setInstanceFollowRedirects(false);
                        URL url2 = new URL(ucon.getHeaderField("Location"));
                        HttpURLConnection ucon2 = (HttpURLConnection) url2.openConnection();
                        ucon2.setInstanceFollowRedirects(false);
                        post.photo = url2.getProtocol() + "://" + url2.getHost() + ucon2.getHeaderField("Location");
                    }
                    mList = posts;
                    return mList;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
