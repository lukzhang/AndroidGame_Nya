package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 6/26/2018.
 */

public class Nugget extends Item {

    Nugget(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(600);

        //Resell value for item
        setResell(500);

        // Choose a Bitmap
        setBitmapName("nugget");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Can sell this for a good price");

        //Set Title
        setTitle("Gold Nugget");

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
        //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }
}
