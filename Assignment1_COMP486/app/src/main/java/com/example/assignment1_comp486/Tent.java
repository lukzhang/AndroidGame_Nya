package com.example.assignment1_comp486;

/**
 * Tent that goes behind merchant or store owners
 */

public class Tent extends GameObject {

    Tent(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 4;
        final float WIDTH = 4;

        setHeight(HEIGHT); // 6 metre tall
        setWidth(WIDTH); // 8 metre wide

        setType(type);


        // Choose a Bitmap
        setBitmapName("mkt");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 1);

        setRectHitbox();

        //House is where the game ends
        setWinning(true);
    }

    public void update(long fps) {
    }
}
