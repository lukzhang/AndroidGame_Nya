package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 7/1/2018.
 */

public class PurpleBook extends Item {

    PurpleBook(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(12);

        //Resell value for item
        setResell(6);

        // Choose a Bitmap
        setBitmapName("purplebook");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Teleports player back to town");

        //Set Title
        setTitle("Book of Teleportation");

        //Make ability item
        setAbilityItem(true);

        //Set cooldown
        setCoolDown(30);

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
        //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }

    public void abilityBelt(LevelManager lm){

        lm.player.setWorldLocation(lm.getStartLocationX(), lm.getStartLocationY(), 0);

    }
    public void deactivateAbilityBelt(LevelManager lm){

    }
}
