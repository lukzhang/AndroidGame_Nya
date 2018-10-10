package com.example.assignment1_comp486;

/**
 * Data for background, which stores the paramaters to be used in the Background class
 */

public class BackgroundData {

    String bitmapName;
    boolean isParallax;     //Determines if it is a parralax background
    //layer 0 is the map
    int layer;
    float startY;
    float endY;

    float speed;
    int height;
    int width;
    BackgroundData(String bitmap, boolean isParallax,
                   int layer, float startY, float endY,
                   float speed, int height){
        this.bitmapName = bitmap;
        this.isParallax = isParallax;
        this.layer = layer;
        this.startY = startY;
        this.endY = endY;
        this.speed = speed;
        this.height = height;
    }
}
