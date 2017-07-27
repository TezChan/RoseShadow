package com.jaqen.roseshadow;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePal;

/**
 * @author chenp
 * @version 2017-02-22 14:22
 */

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        LitePal.initialize(this);
    }
}
