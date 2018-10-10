package com.example.assignment1_comp486;

/**
 * House which has not been used yet except being listed by the level manager as a tileset.
 */

public class House extends GameObject {

    House(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 6;
        final float WIDTH = 8;

        setHeight(HEIGHT); // 6 metre tall
        setWidth(WIDTH); // 8 metre wide

        setType(type);


        // Choose a Bitmap
        setBitmapName("house");

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
