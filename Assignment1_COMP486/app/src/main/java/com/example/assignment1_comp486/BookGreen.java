package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Enables the player to herd sheep. This is part of a simple quest that rewards the player with
 * bones
 */


public class BookGreen extends Item {

    BookGreen(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(6);

        //Resell value for item
        setResell(4);

        // Choose a Bitmap
        setBitmapName("bookgreen");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Add this to your inventory in order to herd sheep");

        //Set Title
        setTitle("Book of Herding");

    }

    //When bought, activates herding ability
    public void ability(LevelManager lm){
        lm.canHerdSheep=true;
    }
    //When discarded, deactivates herding ability
    public void deactivateAbility(LevelManager lm){
        lm.canHerdSheep=false;
    }


}
