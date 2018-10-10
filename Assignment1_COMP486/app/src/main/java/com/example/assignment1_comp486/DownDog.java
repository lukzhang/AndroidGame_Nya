package com.example.assignment1_comp486;

import android.content.Context;

/**
 * This item is used for the animation when the dog has 0 health and is down. The animation shows
 * the dog in a down position. PlatformView uses this animation for 3 seconds before respawning the
 * player. It is triggered when the player's health is 0.
 */

public class DownDog extends Item {

    DownDog(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 2;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("downdog");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }


}
