package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases shield by 1 and adds 30 health
 */

public class Shield extends Item {

    Shield(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(55);

        //Resell value for item
        setResell(2);

        // Choose a Bitmap
        setBitmapName("shield");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases shield by 1 and adds 30 health");

        //Set Title
        setTitle("Shield of the Wolf");

    }

    //Adds 30 health and shield by 1 when bought
    public void ability(LevelManager lm){
        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax+30;
        lm.player.setMaxHealth(newMax);
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth+30;
        lm.player.setCurrHealth(newHealth);

        int prevShield = lm.player.getShield();
        int newShield = prevShield + 1;
        lm.player.setShield(newShield);
    }

    //Decreases 30 health and shield by 1 when discarded
    public void deactivateAbility(LevelManager lm){
        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax-30;
        lm.player.setMaxHealth(newMax);
        //If player deactivates, do not let player die. Instead reduce health to 1.
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth-30;
        if (newHealth <= 0){
            newHealth = 1;
        }
        lm.player.setCurrHealth(newHealth);

        int prevShield = lm.player.getShield();
        int newShield = prevShield - 1;
        lm.player.setShield(newShield);
    }


}
