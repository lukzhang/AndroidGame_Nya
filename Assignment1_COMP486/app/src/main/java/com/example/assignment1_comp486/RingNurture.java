package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases life by 50
 */

public class RingNurture extends Item {

    RingNurture(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(40);

        //Resell value for item
        setResell(12);

        // Choose a Bitmap
        setBitmapName("ringnurture");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases life by 50");

        //Set Title
        setTitle("Ring of Nurture");

    }

    //When bought, increases health by 50
    public void ability(LevelManager lm){

        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax+50;
        lm.player.setMaxHealth(newMax);
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth+50;
        lm.player.setCurrHealth(newHealth);
    }

    //When discarded, decreases health by 50
    public void deactivateAbility(LevelManager lm){

        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax-50;
        lm.player.setMaxHealth(newMax);
        //If player deactivates, do not let player die. Instead reduce health to 1.
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth-50;
        if (newHealth <= 0){
            newHealth = 1;
        }
        lm.player.setCurrHealth(newHealth);
    }


}
