package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases damage by 5
 */

public class Bow extends Item {

    Bow(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(15);

        //Resell value for item
        setResell(9);

        // Choose a Bitmap
        setBitmapName("bow");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases damage by 5");

        //Set Title
        setTitle("Basic bow");

    }

    //Increase damage by 5 when bought
    public void ability(LevelManager lm){
        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage+5;
        lm.player.setDamage(newDamage);
    }

    //Decrease damage by 5 when discarded
    public void deactivateAbility(LevelManager lm){
        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage-5;
        lm.player.setDamage(newDamage);
    }


}
