package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Stationary chicken, with an eating animation provides the player with a little bit of experience
 * when eaten.
 */


public class Chicken extends GameObject {

    Chicken(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 4;
        final String BITMAP_NAME = "chicken";

        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metre wide

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
        setBitmapName("chicken");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();

        //Quest Number 1
        setQuest(1);

        //Gives player health
        setDamage(-1);
        setMaxHealth(1);
        setCurrHealth(getMaxHealth());
        setIsEnemy(true);

        //experience
        setExperienceToGive(1);




    }

    public void update(long fps) {
    }
}
