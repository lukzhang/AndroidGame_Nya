package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Merchant that sells items. Is animated.
 */

public class Merchant extends GameObject {

    Merchant(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 3;
        final int ANIMATION_FRAME_COUNT = 5;
        final String BITMAP_NAME = "dogstand";

        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 2 metre wide

        setType(type);

        // If we kill a chicken, setVisible and setActive to false
        setMoves(false);
        setActive(true);
        setVisible(true);

        // Choose a Bitmap
        setBitmapName(BITMAP_NAME);
        // Set this object up to be animated
        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setBitmapName(BITMAP_NAME);
        setAnimated(context, pixelsPerMetre, true);


        // Choose a Bitmap
        setBitmapName("dogstand");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();

        //Dialogue
        addDialogue("Welcome Traveler. Take a look at my shop");

    }

    public void update(long fps) {
    }
}
