package com.example.assignment1_comp486;

import android.content.Context;

/**
 *Item that doesn't do anything
 */

public class Paw extends Item {

    Paw(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(3);

        //Resell value for item
        setResell(2);

        // Choose a Bitmap
        setBitmapName("paw");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("A shiny paw pendent");

        //Set Title
        setTitle("Paw pendent");

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
        //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }
}
