package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that icnreases damage by 12
 */

public class RingMight extends Item {

    RingMight(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(30);

        //Resell value for item
        setResell(19);

        // Choose a Bitmap
        setBitmapName("ringmight");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases damage by 12");

        //Set Title
        setTitle("Steel Ring of Might");

    }

    //When bought, increases damage by 12
    public void ability(LevelManager lm){

        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage+12;
        lm.player.setDamage(newDamage);
    }

    //When sold, decreases damage by 12
    public void deactivateAbility(LevelManager lm){

        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage-12;
        lm.player.setDamage(newDamage);
    }


}
