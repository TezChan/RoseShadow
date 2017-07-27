package com.jaqen.game;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.jaqen.game.core.GameMain;

/**
 * @author chenp
 * @version 2017-07-27 14:42
 */

public class DDDGameActivity extends AndroidApplication {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(new GameMain());
    }
}
