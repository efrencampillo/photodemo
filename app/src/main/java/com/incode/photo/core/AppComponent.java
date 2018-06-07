package com.incode.photo.core;

import com.incode.photo.ui.DetailsActivity;
import com.incode.photo.ui.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(HomeActivity activity);

    void inject(DetailsActivity activity);
}