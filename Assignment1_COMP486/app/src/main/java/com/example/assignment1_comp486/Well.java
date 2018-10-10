package com.example.assignment1_comp486;

/**
 * Well that replenishes health. The replenishing of health is done in PlatformView.
 */

public class Well extends GameObject {

    Well(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 3;
        final float WIDTH = 3;

        setHeight(HEIGHT); // 3 metre tall
        setWidth(WIDTH); // 3 metre wide

        setType(type);

        // Choose a Bitmap
        setBitmapName("well");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        setRectHitbox();

        //Dialogue
        addDialogue("You drink some water from the well");
        addDialogue("Your health is replenished");
    }

    public void update(long fps) {
    }
}
