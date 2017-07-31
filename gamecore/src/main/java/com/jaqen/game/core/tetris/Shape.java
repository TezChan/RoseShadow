package com.jaqen.game.core.tetris;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * @author chenp
 * @version 2017-07-28 16:47
 */

public class Shape {
    private static final int[][][] shapeDatas = {
            {
                    {0, 0, 0, 1, 0, 2, 0, 3},
                    {1, 0, 1, 1, 1, 2, 1, 3}
            },
            {
                    {0, 0, 0, 1, 1, 0, 2, 0},
                    {1, 0, 1, 1, 1, 2, 2, 2},
                    {0, 1, 1, 1, 2, 1, 2, 0},
                    {0, 0, 1, 0, 1, 1, 1, 2}
            },
            {
                    {0, 0, 1, 0, 2, 0, 2, 1},
                    {0, 0, 0, 1, 0, 2, 1, 0},
                    {0, 0, 0, 1, 1, 1, 2, 1},
                    {2, 0, 1, 0, 1, 1, 1, 2}
            },
            {
                    {0, 0, 1, 0, 0, 1, 1, 1}
            },
            {
                    {0, 1, 1, 1, 1, 2, 2, 1},
                    {1, 0, 1, 1, 1, 2, 2, 1},
                    {0, 1, 1, 0, 1, 1, 2, 1},
                    {0, 1, 1, 0, 1, 1, 1, 2}
            },
            {
                    {0, 1, 1, 0, 1, 1, 2, 0},
                    {1, 0, 1, 1, 2, 1, 2, 2}
            },
            {
                    {0, 0, 1, 0, 1, 1, 2, 1},
                    {0, 1, 0, 2, 1, 0, 1, 1}
            }
    };

    private int shapeType;
    private int shapeRotate;
    private int x, y;
    private float ptsRatio = 1;
    private Actor[] tetris;

    public Shape(float ptsRatio){
        this.ptsRatio = ptsRatio;
    }

    public Actor[] create(int shape, int rotate, Texture texture, int x, int y){

        if (shape < shapeDatas.length){
            int[] shapeData = shapeDatas[shape][rotate % shapeDatas[shape].length];
            tetris = new Actor[shapeData.length / 2];

            for (int i = shapeData.length - 1; i > 0; i -= 2){
                Image box = new Image(texture);

                box.setSize(ptsRatio, ptsRatio);
                box.setPosition(ptsRatio * (x + shapeData[i - 1]), ptsRatio * (y + shapeData[i]));

                tetris[(i - 1) / 2] = box;
            }

            this.x = x;
            this.y = y;

            return tetris;
        }

        return null;
    }

    public void moveBy(int x, int y){
        int size = tetris.length;

        for (int i = 0; i < size; i ++){
            MoveByAction moveByAction = Actions.moveBy(ptsRatio * x, ptsRatio * y);

            tetris[i].addAction(moveByAction);
        }

        this.x += x;
        this.y += y;
    }

    public void rotateBy(int delta){
        int fixedToRotate = (shapeRotate + delta) % shapeDatas[shapeType].length;
        int[] shapeData = shapeDatas[shapeType][fixedToRotate];
        int size = tetris.length;

        for (int i = 0; i < size; i ++){
            tetris[i].setPosition(ptsRatio * (x + shapeData[i - 1]), ptsRatio * (y + shapeData[i]));
        }
    }

    public Actor[] getTetris() {
        return tetris;
    }
}
