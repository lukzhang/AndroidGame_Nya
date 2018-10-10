package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 7/1/2018.
 */

public class HeartBook extends Item {

    HeartBook(Context context, int pixelsPerMetre) {
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
        setBitmapName("heartbook");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Heals player by 25");

        //Set Title
        setTitle("Book of St. Valentine");

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

        if(lm.player.getCurrHealth() + 25 > lm.player.getMaxHealth()){
            lm.player.setCurrHealth(lm.player.getMaxHealth());
        }
        else{
            lm.player.setCurrHealth(lm.player.getCurrHealth()+25);
        }

    }
    public void deactivateAbilityBelt(LevelManager lm){

    }
}
