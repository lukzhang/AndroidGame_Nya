package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Clock item that doesn't do anything
 */

public class Clock extends Item {

    Clock(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(2);

        //Resell value for item
        setResell(1);

        // Choose a Bitmap
        setBitmapName("clock");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Return to Patrick the blue dog for reward");

        //Set Title
        setTitle("Clock");

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
        //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }
}
