package com.jaqen.roseshadow.models.bean;

/**
 * @author chenp
 * @version 2017-04-19 11:24
 */

public class FlingData {
    private float velocityX;
    private float velocityY;

    public FlingData(){

    }

    public FlingData(float velocityX, float velocityY){
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
}
