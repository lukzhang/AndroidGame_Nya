package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item that increases damage by 65
 */

public class Sword extends Item {

    Sword(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(135);

        //Resell value for item
        setResell(69);

        // Choose a Bitmap
        setBitmapName("sword");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases damage by 65");

        //Set Title
        setTitle("Jagged Sword of Relentlessness");

    }

    //When bought, increases damage by 65
    public void ability(LevelManager lm){
        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage+65;
        lm.player.setDamage(newDamage);
    }

    //When discarded, decreases damage by 65
    public void deactivateAbility(LevelManager lm){
        int prevDamage = lm.player.getDamage();
        int newDamage = prevDamage-65;
        lm.player.setDamage(newDamage);
    }


}
