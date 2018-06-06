package com.incode.photo.core;

import com.incode.photo.presenters.DetailsPresenter;
import com.incode.photo.presenters.HomePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);

        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //this can be un-harcoded by configurations
                .baseUrl("https://photomaton.herokuapp.com/api/")
                .client(client.build())
                .build();
    }


    @Provides
    @Singleton
    NetworkInterface provideNetworkInterface(Retrofit retrofit) {
        return retrofit.create(NetworkInterface.class);
    }

    @Provides
    @Singleton
    HomePresenter provideHomePresenter(NetworkInterface apiInterface) {
        return new HomePresenter(apiInterface);
    }

    @Provides
    @Singleton
    DetailsPresenter provideMovementsPresenter() {
        return new DetailsPresenter();
    }


}