package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 6/25/2018.
 */

public class minotaurtrophy extends Item {

    minotaurtrophy(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(500);

        //Resell value for item
        setResell(400);

        // Choose a Bitmap
        setBitmapName("minotaurtrophy");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Worth quite a bit of money");

        //Set Title
        setTitle("Minotaur Trophy");

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
        //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }
}
