package com.example.assignment1_comp486;

/**
 * Note: Not actually an in-game store. Originally I was planning on using this as the store, but
 * opted for NPCs instead.
 */

public class Store extends GameObject {

    Store(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 4;
        final float WIDTH = 4;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 2 metre wide

        setType(type);

        // Choose a Bitmap
        setBitmapName("doghouse");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        setRectHitbox();
    }

    public void update(long fps) {
    }
}
