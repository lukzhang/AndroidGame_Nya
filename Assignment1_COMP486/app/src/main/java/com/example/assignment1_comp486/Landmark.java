package com.example.assignment1_comp486;

/**
 * Object (of stone henge like landmark) that player can walk through. Generally, enemies
 * are past this point.
 */

public class Landmark extends GameObject {

    Landmark(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 4;
        final float WIDTH = 5;

        setHeight(HEIGHT); // 4 metre tall
        setWidth(WIDTH); // 5 metre wide

        setType(type);


        // Choose a Bitmap
        setBitmapName("landmark");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 1);

        //Player does not collide with this object
        //setRectHitbox();
    }

    public void update(long fps) {
    }
}
