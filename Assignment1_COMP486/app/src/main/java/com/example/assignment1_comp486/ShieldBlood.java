package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases shield by 1 and increases lifesteal by 1
 */

public class ShieldBlood extends Item {

    ShieldBlood(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(72);

        //Resell value for item
        setResell(23);

        // Choose a Bitmap
        setBitmapName("bloodshield");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases shield by 1 and adds 1 to lifesteal");

        //Set Title
        setTitle("Vampiric Shield");

    }

    //Increases shiled by 1 and 1 to lifesteal when bought
    public void ability(LevelManager lm){

        int prevShield = lm.player.getShield();
        int newShield = prevShield + 1;
        lm.player.setShield(newShield);

        int oldLifeSteal = lm.player.getLifeSteal();
        int newLifeSteal = oldLifeSteal+1;
        lm.player.setLifeSteal(newLifeSteal);
    }

    //Decreases shiled by 1 and 1 to lifesteal when discarded
    public void deactivateAbility(LevelManager lm){

        int prevShield = lm.player.getShield();
        int newShield = prevShield - 1;
        lm.player.setShield(newShield);

        int oldLifeSteal = lm.player.getLifeSteal();
        int newLifeSteal = oldLifeSteal-1;
        lm.player.setLifeSteal(newLifeSteal);
    }


}
