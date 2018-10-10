package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases shield by 8
 */

public class PotionLuck extends Item {

    PotionLuck(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(142);

        //Resell value for item
        setResell(61);

        // Choose a Bitmap
        setBitmapName("potionluck");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Makes you lucky (increases shield by 8)");

        //Set Title
        setTitle("Leprechaun's Potion of Luck");

    }

    //When bought, gives player 8 shield
    public void ability(LevelManager lm){

        int prevShield = lm.player.getShield();
        int newShield = prevShield + 8;
        lm.player.setShield(newShield);
    }

    //When discarded, removes 8 shield
    public void deactivateAbility(LevelManager lm){

        int prevShield = lm.player.getShield();
        int newShield = prevShield - 8;
        lm.player.setShield(newShield);
    }


}
