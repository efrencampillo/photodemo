package com.incode.photo.core;

import android.app.Application;

public class PhotoApp extends Application {

    private static PhotoApp mThis = null;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mThis = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }


    public static PhotoApp getInstance() {
        return mThis;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
