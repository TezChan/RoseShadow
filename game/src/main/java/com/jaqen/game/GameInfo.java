package com.jaqen.game;

/**
 * @author chenp
 * @version 2017-07-27 14:13
 */

public class GameInfo {
    private int themeImgId;
    private String name;
    private Class gameClass;

    public GameInfo(int themeImgId, String name, Class gameClass){
        this.themeImgId = themeImgId;
        this.name = name;
        this.gameClass = gameClass;
    }

    public int getThemeImgId() {
        return themeImgId;
    }

    public void setThemeImgId(int themeImgId) {
        this.themeImgId = themeImgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getGameClass() {
        return gameClass;
    }

    public void setGameClass(Class gameClass) {
        this.gameClass = gameClass;
    }
}
