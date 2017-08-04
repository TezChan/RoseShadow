package com.jaqen.game.core.tetris;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

/**
 * @author chenp
 * @version 2017-08-04 14:53
 */

public class ScreenView extends Group{
    public static final int STANDAR_WIDTH = 480;
    public static final int STANDAR_HEIGHT = 568;

    private Actor[][] units = new Actor[20][10];
    private Image bg;
    private ScoreActor scoreActor;
    private ScoreActor levelActor;
    private ScoreActor linesActor;
    private TimeActor timeActor;

    public ScreenView(Texture bgTexture){
        setSize(STANDAR_WIDTH, STANDAR_HEIGHT);

        scoreActor = new ScoreActor(6, 14);
        linesActor = new ScoreActor(6, 14);
        levelActor = new ScoreActor(2, 14);
        timeActor = new TimeActor(14);
        bg = new Image(bgTexture);

        bg.setPosition(50, 50);
        scoreActor.setPosition(348, 440, Align.right);
        scoreActor.setScore(0);
        linesActor.setPosition(348, 340);
        linesActor.setScore(0);
        levelActor.setPosition(448, 240);
        levelActor.setScore(1);

        addActor(bg);
        addActor(scoreActor);
        addActor(linesActor);
        addActor(levelActor);
        addActor(timeActor);
    }

    public void setLoader(IconNumberLoader loader){
        scoreActor.setLoader(loader);
        linesActor.setLoader(loader);
        levelActor.setLoader(loader);
        timeActor.setLoader(loader);
    }
}
