package com.jaqen.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jaqen.game.core.tetris.TetrisMain;

/**
 * @author chenp
 * @version 2017-07-27 14:41
 */

public class TetrisActivity extends AndroidApplication {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(new TetrisMain());
    }
}
