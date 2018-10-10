package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 7/1/2018.
 */

public class SkullBook extends Item {

    SkullBook(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(180);

        //Resell value for item
        setResell(120);

        // Choose a Bitmap
        setBitmapName("skullbook");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Guardian angel protects for 10 seconds and heals you by 200");

        //Set Title
        setTitle("Book of the Reaper Angel");

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
        lm.setProtectionSeconds(10);
        lm.setAbilityOn(true);

        if(lm.player.getCurrHealth() + 150 > lm.player.getMaxHealth()){
            lm.player.setCurrHealth(lm.player.getMaxHealth());
        }
        else{
            lm.player.setCurrHealth(lm.player.getCurrHealth()+200);
        }
    }
    public void deactivateAbilityBelt(LevelManager lm){

    }
}
