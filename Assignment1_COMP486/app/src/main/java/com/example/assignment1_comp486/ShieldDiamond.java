package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases shiled by 2 and adds 2 to armour
 */

public class ShieldDiamond extends Item {

    ShieldDiamond(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(90);

        //Resell value for item
        setResell(40);

        // Choose a Bitmap
        setBitmapName("diamondshield");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases shield by 2 and adds 2 to armour");

        //Set Title
        setTitle("Diamond Shield");

    }

    //When bought increases shield by 2 and 2 to armour
    public void ability(LevelManager lm){

        int prevShield = lm.player.getShield();
        int newShield = prevShield + 1;
        lm.player.setShield(newShield);

        int prevArmour = lm.player.getArmour();
        int newArmour = prevArmour+2;
        lm.player.setArmour(newArmour);
    }

    //When discarded decreases shield by 2 and 2 to armour
    public void deactivateAbility(LevelManager lm){

        int prevShield = lm.player.getShield();
        int newShield = prevShield - 1;
        lm.player.setShield(newShield);

        int prevArmour = lm.player.getArmour();
        int newArmour = prevArmour-2;
        lm.player.setArmour(newArmour);
    }


}
