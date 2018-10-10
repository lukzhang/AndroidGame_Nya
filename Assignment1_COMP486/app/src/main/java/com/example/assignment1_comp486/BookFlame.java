package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases lifesteal by 30
 */

public class BookFlame extends Item {

    BookFlame(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(305);

        //Resell value for item
        setResell(211);

        // Choose a Bitmap
        setBitmapName("bookflame");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increase lifesteal by 30");

        //Set Title
        setTitle("Book of Eternal Flame");

    }

    //Increases lifesteal by 30 when bought
    public void ability(LevelManager lm){

        int oldLifeSteal = lm.player.getLifeSteal();
        int newLifeSteal = oldLifeSteal+30;
        lm.player.setLifeSteal(newLifeSteal);
    }

    //Decreases lifesteal by 30 when discarded
    public void deactivateAbility(LevelManager lm){

        int oldLifeSteal = lm.player.getLifeSteal();
        int newLifeSteal = oldLifeSteal-30;
        lm.player.setLifeSteal(newLifeSteal);
    }


}
