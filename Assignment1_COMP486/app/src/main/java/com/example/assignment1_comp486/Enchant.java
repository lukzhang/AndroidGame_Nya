package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases player health by 10
 */

public class Enchant extends Item {

    Enchant(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(6);

        //Resell value for item
        setResell(4);

        // Choose a Bitmap
        setBitmapName("enchant");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases health by 10");

        //Set Title
        setTitle("Enchanted Necklace of Vitality");

    }

    //Increases health by 10 when bought
    public void ability(LevelManager lm){
        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax+10;
        lm.player.setMaxHealth(newMax);
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth+10;
        lm.player.setCurrHealth(newHealth);
    }

    //Decreases health by 10 when sold
    public void deactivateAbility(LevelManager lm){
        int prevMax = lm.player.getMaxHealth();
        int newMax = prevMax-10;
        lm.player.setMaxHealth(newMax);
        //If player deactivates, do not let player die. Instead reduce health to 1.
        int prevHealth = lm.player.getCurrHealth();
        int newHealth = prevHealth-10;
        if (newHealth <= 0){
            newHealth = 1;
        }
        lm.player.setCurrHealth(newHealth);
    }


}
