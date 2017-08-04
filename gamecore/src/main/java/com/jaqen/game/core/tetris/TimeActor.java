package com.jaqen.game.core.tetris;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author chenp
 * @version 2017-08-02 17:26
 */

public class TimeActor extends Actor {
    private IconNumberLoader loader;
    private TextureRegion[] seps;
    private List<TextureRegion> hourRegion;
    private List<TextureRegion> minRegion;
    private int hour = -1;
    private int min = -1;
    private int sec;
    private int perNumberWidth = 20;

    public TimeActor(int numberWidth){
        setPerNumberWidth(numberWidth);
    }

    public void setLoader(IconNumberLoader loader){
        this.loader = loader;
        seps = loader.getSep();

        updateTime();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (seps == null) return;

        for (int i = 0; i < 5; i ++){
            TextureRegion region;

            if (i < 2){
                region = hourRegion.get(i);
            }else if (i > 2){
                region = minRegion.get(i - 3);
            }else {
                region = seps[sec % 2];
            }

            batch.draw(region,
                    getX() + perNumberWidth * i,
                    getY(),
                    perNumberWidth,
                    getHeight());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (loader != null && loader.isLoaded()) updateTime();

    }

    private void updateTime(){
        Calendar time = Calendar.getInstance();
        int curHour = time.get(Calendar.HOUR_OF_DAY);
        int curMin = time.get(Calendar.MINUTE);
        sec = time.get(Calendar.SECOND);

        if (curHour != hour){
            hourRegion = loader.numberFormat(2, curHour);
            hour = curHour;
        }

        if (curMin != min){
            minRegion = loader.numberFormat(2, curMin);
            min = curMin;
        }
    }

    public int getPerNumberWidth() {
        return perNumberWidth;
    }

    public void setPerNumberWidth(int perNumberWidth) {
        this.perNumberWidth = perNumberWidth;

        setSize(perNumberWidth * 5,
                perNumberWidth * IconNumberLoader.NUMBER_HEIGHT / IconNumberLoader.NUMBER_WIDTH);
    }
}
