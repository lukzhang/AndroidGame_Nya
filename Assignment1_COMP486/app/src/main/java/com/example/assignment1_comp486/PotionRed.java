package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases life by 100 and lifesteal by 5
 */

public class PotionRed extends Item {

    PotionRed(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(202);

        //Resell value for item
        setResell(64);

        // Choose a Bitmap
        setBitmapName("potionred");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases life by 100 and lifesteal by 5");

        //Set Title
        setTitle("Necromancer's Blood");

    }

    //When bought, increases lifesteal by 5 and health by 100
    public void ability(LevelManager lm){

        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax+100;
        lm.player.setMaxHealth(newMax);
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth+100;
        lm.player.setCurrHealth(newHealth);

        int oldLifeSteal = lm.player.getLifeSteal();
        int newLifeSteal = oldLifeSteal+5;
        lm.player.setLifeSteal(newLifeSteal);
    }

    //When discarded, decreases lifesteal by 5 and health by 100
    public void deactivateAbility(LevelManager lm){

        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax-100;
        lm.player.setMaxHealth(newMax);
        //If player deactivates, do not let player die. Instead reduce health to 1.
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth-100;
        if (newHealth <= 0){
            newHealth = 1;
        }
        lm.player.setCurrHealth(newHealth);

        int oldLifeSteal = lm.player.getLifeSteal();
        int newLifeSteal = oldLifeSteal-5;
        lm.player.setLifeSteal(newLifeSteal);
    }


}
