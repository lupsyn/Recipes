package com.purepoint.receipe;

import android.app.Application;

import com.purepoint.receipe.di.AppComponent;
import com.purepoint.receipe.di.DaggerAppComponent;


public class RecipeApplication extends Application {
    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
