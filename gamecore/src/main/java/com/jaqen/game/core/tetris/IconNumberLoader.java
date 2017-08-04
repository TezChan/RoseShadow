package com.jaqen.game.core.tetris;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.StringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenp
 * @version 2017-08-02 09:25
 */
//https://github.com/chvin/react-tetris
public class IconNumberLoader {
    public static final String RES_TEXTURE_NAME = "number.png";
    public static final int NUMBER_WIDTH = 14;
    public static final int NUMBER_HEIGHT = 25;

    private Map<String, TextureRegion> iconNumbers;

    public void setTexture(Texture texture){
        iconNumbers = new HashMap<>();

        for (int i = 0; i < 13; i++){
            String key;

            if (i < 10 )
                key = String.valueOf(i);
            else if (i == 10)
                key = "o";
            else if (i == 11)
                key = ":";
            else
                key = "|";

            iconNumbers.put(key, new TextureRegion(
                    texture,
                    NUMBER_WIDTH * i,
                    0,
                    NUMBER_WIDTH,
                    NUMBER_HEIGHT));
        }
    }

    /*private TextureRegion getNumberTexture(int number){
        if (number >= 0 && number < 10){
            return iconNumbers.get(number);
        }

        return null;
    }*/

    /*public TextureRegion getBlockTexture(){
        return iconNumbers.get(10);
    }*/

    /*public TextureRegion getSeparator(boolean visible){
        return iconNumbers.get(visible ? 11 : 12);
    }*/

    public List<TextureRegion> numberFormat(int length, int number){
        if (iconNumbers == null) return null;

        String numStr = String.valueOf(number);
        int numberSize = numStr.length();

        if (length > numberSize){
            int blockLength = length - numberSize;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < blockLength; i ++){
                sb.append('o');
            }

            numStr = sb.append(numStr).toString();
        }else {
            numStr = numStr.substring(numberSize - length);
        }
/*
        numberSize = numStr.length();

        for (int i = 0;i < numberSize; i ++){
            regions.add(iconNumbers.get(String.valueOf(numStr.charAt(i))));
        }*/
        return numberFormat(numStr);
    }

    public List<TextureRegion> numberFormat(String numStr){

        ArrayList<TextureRegion> regions = new ArrayList<>();
        int numberSize = numStr.length();

        for (int i = 0;i < numberSize; i ++){
            regions.add(iconNumbers.get(String.valueOf(numStr.charAt(i))));
        }

        return regions;
    }

    public TextureRegion[] getSep(){
        if (iconNumbers == null) return null;

        return new TextureRegion[]{iconNumbers.get(":"), iconNumbers.get("|")};
    }

    public List<TextureRegion> timeFormat(Date date){

        return null;
    }

    public boolean isLoaded(){
        return iconNumbers != null;
    }
}
