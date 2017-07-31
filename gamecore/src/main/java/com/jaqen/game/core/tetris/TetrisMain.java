package com.jaqen.game.core.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author chenp
 * @version 2017-07-27 11:02
 */

public class TetrisMain extends ApplicationAdapter{

    public static final int COLUMN_SIZE = 20;

    private Actor[] boxs;
    private Shape currentShape;
    private Shape nextShape;
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;

    @Override
    public void create () {
        float ptsRatio = Gdx.graphics.getWidth() / 3 / COLUMN_SIZE;

        /*currentShape = new Shape(ptsRatio);
        nextShape = new Shape(ptsRatio);

        boxs = new Actor[((int) (Gdx.graphics.getHeight() / ptsRatio) + 1) * 20];*/
        spriteBatch = new SpriteBatch();
        assetManager = new AssetManager();

        assetManager.load("icon.png", Texture.class);
    }

    protected void loadRes(){
        Texture texture = assetManager.get("icon.png", Texture.class);


    }

    @Override
    public void render () {

    }

    @Override
    public void dispose () {
    }


}
