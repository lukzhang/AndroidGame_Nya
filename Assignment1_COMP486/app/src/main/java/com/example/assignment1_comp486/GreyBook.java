package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 7/1/2018.
 */

public class GreyBook extends Item {

    GreyBook(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(40);

        //Resell value for item
        setResell(30);

        // Choose a Bitmap
        setBitmapName("greybook");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Guardian angel protects you for 7 seconds");

        //Set Title
        setTitle("Book of the Archangel");

        //Make ability item
        setAbilityItem(true);

        //Set cooldown
        setCoolDown(15);

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
        //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }

    public void abilityBelt(LevelManager lm){
        lm.setProtectionSeconds(7);
        lm.setAbilityOn(true);
    }
    public void deactivateAbilityBelt(LevelManager lm){

    }
}
