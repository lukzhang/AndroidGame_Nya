package com.example.assignment1_comp486;

/**
 * Created by Luke on 6/20/2018.
 */

public class SnowMan extends GameObject {

    SnowMan(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 4;
        final float WIDTH = 4;

        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metre wide

        setType(type);


        // Choose a Bitmap
        setBitmapName("snowman");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        //Player walks over this, so no need for hitbox
        //setRectHitbox();
    }

    public void update(long fps) {
    }
}
