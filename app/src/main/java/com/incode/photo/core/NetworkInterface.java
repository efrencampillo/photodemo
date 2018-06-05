package com.incode.photo.core;


import com.incode.photo.model.Post;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NetworkInterface {

    @GET("photo")
    Observable<List<Post>> getPhotos();
}
