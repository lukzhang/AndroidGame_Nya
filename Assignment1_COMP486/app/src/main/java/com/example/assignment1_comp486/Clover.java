package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that doesn't do anything
 */

public class Clover extends Item {

    Clover(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(1);

        //Resell value for item
        setResell(0);

        // Choose a Bitmap
        setBitmapName("clover");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("A nice green clover");

        //Set Title
        setTitle("3 Leafed Clover");

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
       //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }
}
