package com.jaqen.game.core.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * @author chenp
 * @version 2017-07-27 11:02
 */
public class TetrisMain extends ApplicationAdapter{

    private static final int WIDTH = 540;
    private static final int HEIGHT = 960;

    private Actor[][] units = new Actor[20][10];

    private AssetManager assetManager;
    private Stage stage;
    private IconNumberLoader numberLoader;
    private ScoreActor scoreActor;
    private ScoreActor levelActor;
    private ScoreActor linesActor;
    private TimeActor timeActor;
    private Group pad;
    private Group gameView;
    private ScreenView screenView;
    private boolean isLoading = false;

    private Texture viewBg;

    private int screenRatio = 1;
    private int gameViewRatio;

    @Override
    public void create () {

        //screenRatio = Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        stage = new Stage();
        viewBg = new Texture(Gdx.files.internal("view_bg.png"));
        assetManager = new AssetManager();
        numberLoader = new IconNumberLoader();
        screenView = new ScreenView(viewBg);

        stage.addActor(screenView);
        /*scoreActor = new ScoreActor(6, 20);
        linesActor = new ScoreActor(6, 20);
        levelActor = new ScoreActor(2, 20);
        timeActor = new TimeActor(20);
        pad = new Group();
        gameView = new Group();

        scoreActor.setPosition(100, 100);
        scoreActor.setScore(0);
        linesActor.setScore(0);
        levelActor.setScore(1);
        stage.addActor(scoreActor);
        stage.addActor(timeActor);
        stage.addActor(linesActor);
        stage.addActor(levelActor);*/

        initScreenView();

        assetManager.load(IconNumberLoader.RES_TEXTURE_NAME, Texture.class);
        isLoading = true;
    }

    private void loadRes(){
        Texture texture = assetManager.get(IconNumberLoader.RES_TEXTURE_NAME, Texture.class);

        numberLoader.setTexture(texture);
        screenView.setLoader(numberLoader);
        /*scoreActor.setLoader(numberLoader);
        linesActor.setLoader(numberLoader);
        levelActor.setLoader(numberLoader);
        timeActor.setLoader(numberLoader);*/

        isLoading = false;
    }

    @Override
    public void render () {
        if (isLoading && assetManager.update())
            loadRes();

        Gdx.gl.glClearColor(0.31f, 0.45f, 0.42f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose () {
        if (stage != null)
            stage.dispose();

        if (assetManager != null)
            assetManager.dispose();

        if (viewBg != null)
            viewBg.dispose();
    }

    private void initScreenView(){
        float screenViewRatio = ScreenView.STANDAR_HEIGHT / ScreenView.STANDAR_WIDTH;
        float gameViewRatio = Gdx.graphics.getHeight() * 3 / Gdx.graphics.getWidth() / 2;
        float scale = screenViewRatio > gameViewRatio
                ? Gdx.graphics.getHeight() / ScreenView.STANDAR_HEIGHT
                : Gdx.graphics.getWidth() / ScreenView.STANDAR_WIDTH;

        screenView.setScale(scale);

        float posX = (Gdx.graphics.getWidth() - ScreenView.STANDAR_WIDTH * scale) * 0.5f;
        float posY = 2.0f * Gdx.graphics.getHeight() / 3.0f - ScreenView.STANDAR_HEIGHT * scale * 0.5f;

        screenView.setPosition(posX, posY);
    }
}
