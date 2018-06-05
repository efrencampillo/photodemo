package com.incode.photo.core;

import javax.inject.Singleton;

import dagger.Component;

import com.incode.photo.ui.HomeActivity;
import com.incode.photo.ui.DetailsActivity;
import com.incode.photo.ui.RecyclerAdapter;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(HomeActivity activity);

    void inject(DetailsActivity activity);
}