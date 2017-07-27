package com.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jaqen.game.core.GameMain;

/**
 * @author chenp
 * @version 2017-07-27 09:51
 */

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        new LwjglApplication(new GameMain(), config);
    }
}
