package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that gives 50 damage and increases fire rate by 3
 */

public class PotionRage extends Item {

    PotionRage(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(185);

        //Resell value for item
        setResell(74);

        // Choose a Bitmap
        setBitmapName("potionrage");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases damage by 50 and fire rate by 3");

        //Set Title
        setTitle("Goblin's Potion of Might");

    }

    //When bought, increases fire rate by 3 and damage by 50
    public void ability(LevelManager lm){

        int prevFireRate = lm.player.gun.getRateOfFire();
        int newFireRate = prevFireRate+3;
        lm.player.gun.setFireRate(newFireRate);

        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage+50;
        lm.player.setDamage(newDamage);
    }

    //When discarded, decreases by 3 and damage by 50
    public void deactivateAbility(LevelManager lm){

        int prevFireRate = lm.player.gun.getRateOfFire();
        int newFireRate = prevFireRate-3;
        lm.player.gun.setFireRate(newFireRate);

        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage-50;
        lm.player.setDamage(newDamage);
    }


}
