package com.example.assignment1_comp486;

/**
 * Created by Luke on 6/26/2018.
 */

public class pickupNugget extends GameObject {

    pickupNugget(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metre wide

        setType(type);

        // Choose a Bitmap
        setBitmapName("nugget");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        //Item is initially not active or visible until dropped by enemy
        setVisible(false);
        setActive(false);

        setRectHitbox();
    }

    public void update(long fps) {
    }
}
