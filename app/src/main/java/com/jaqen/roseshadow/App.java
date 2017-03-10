package com.jaqen.roseshadow;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author chenp
 * @version 2017-02-22 14:22
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}
