package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases damage by 25 and armour by 5
 */

public class BattleSet extends Item {

    BattleSet(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(85);

        //Resell value for item
        setResell(59);

        // Choose a Bitmap
        setBitmapName("battleset");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases damage by 25 and armour by 5");

        //Set Title
        setTitle("Warrior's Battle Set");

    }

    //Increases damage by 25 when bought
    //Increases armour by 5 when bought
    public void ability(LevelManager lm){

        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage+25;
        lm.player.setDamage(newDamage);

        int prevArmour = lm.player.getArmour();
        int newArmour = prevArmour+5;
        lm.player.setArmour(newArmour);
    }

    //Decreases damage by 25 when discarded
    //Decreases armour by 5 when discarded
    public void deactivateAbility(LevelManager lm){
        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage-25;
        lm.player.setDamage(newDamage);

        int prevArmour = lm.player.getArmour();
        int newArmour = prevArmour-5;
        lm.player.setArmour(newArmour);
    }


}
