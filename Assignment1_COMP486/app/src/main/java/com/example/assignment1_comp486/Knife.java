package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases damage by 1
 */

public class Knife extends Item {

    Knife(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(5);

        //Resell value for item
        setResell(3);

        // Choose a Bitmap
        setBitmapName("knife");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases damage by 1");

        //Set Title
        setTitle("Dagger of Radiance");

    }

    //Increases damage by 1 when bought
    public void ability(LevelManager lm){
        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage+1;
        lm.player.setDamage(newDamage);
    }

    //Decreases damage by 1 when discarded
    public void deactivateAbility(LevelManager lm){
        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage-1;
        lm.player.setDamage(newDamage);
    }


}