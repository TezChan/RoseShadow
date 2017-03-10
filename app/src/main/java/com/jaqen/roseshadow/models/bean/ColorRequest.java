package com.jaqen.roseshadow.models.bean;

/**
 * @author chenp
 * @version 2017-01-20 13:40
 */

public class ColorRequest {
    private String color;

    public ColorRequest(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
