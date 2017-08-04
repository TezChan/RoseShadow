package com.jaqen.game.core.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

/**
 * @author chenp
 * @version 2017-08-02 11:55
 */

public class ScoreActor extends Actor {

    private IconNumberLoader loader;
    private int scoreLength = 10;
    private int score = 0;
    private List<TextureRegion> numberTextures;
    private int perNumberWidth = 20;

    public ScoreActor(int scoreLength, int numberWidth){
        this.scoreLength = scoreLength;

        setPerNumberWidth(numberWidth);
    }

    public void setLoader(IconNumberLoader loader) {
        this.loader = loader;

        numberTextures = loader.numberFormat(scoreLength, score);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (numberTextures == null || !isVisible()) return;

        int length = numberTextures.size();

        for (int i = 0; i < length; i ++){
            batch.draw(numberTextures.get(i),
                    getX() + perNumberWidth * i, getY(),
                    perNumberWidth, getHeight());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Gdx.app.debug("delta", delta + "");
    }

    public void setScore(int score){
        if (this.score != score && loader != null){
            numberTextures = loader.numberFormat(scoreLength, score);
        }
    }

    public int getPerNumberWidth() {
        return perNumberWidth;
    }

    public void setPerNumberWidth(int perNumberWidth) {
        this.perNumberWidth = perNumberWidth;

        setSize(perNumberWidth * scoreLength,
                perNumberWidth * IconNumberLoader.NUMBER_HEIGHT / IconNumberLoader.NUMBER_WIDTH);
    }
}
