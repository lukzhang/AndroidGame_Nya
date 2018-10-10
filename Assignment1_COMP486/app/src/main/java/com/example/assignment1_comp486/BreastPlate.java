package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases armour by 2
 */

public class BreastPlate extends Item {

    BreastPlate(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(42);

        //Resell value for item
        setResell(34);

        // Choose a Bitmap
        setBitmapName("breastplate");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases armour by 2");

        //Set Title
        setTitle("Jager's Armour");

    }

    //Increases armour by 2 when bought
    public void ability(LevelManager lm){

        int prevArmour = lm.player.getArmour();
        int newArmour = prevArmour+2;
        lm.player.setArmour(newArmour);
    }

    //Decreases armour by 2 when sold
    public void deactivateAbility(LevelManager lm){

        int prevArmour = lm.player.getArmour();
        int newArmour = prevArmour-2;
        lm.player.setArmour(newArmour);
    }


}
