package com.example.sashank.pokedex;

import android.app.Application;
import android.content.Context;

/**
 * Created by sashank on 30/6/17.
 */

public class PokedexApplication extends Application {

    private static PokedexApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static PokedexApplication getInstance() {
        return instance;
    }

    public static Context getAppContext () {
        return instance.getApplicationContext();
    }
}
