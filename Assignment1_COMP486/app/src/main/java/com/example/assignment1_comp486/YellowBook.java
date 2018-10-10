package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Ability item
 */

public class YellowBook extends Item {

    YellowBook(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(15);

        //Resell value for item
        setResell(9);

        // Choose a Bitmap
        setBitmapName("yellowbook");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Heals player by 5");

        //Set Title
        setTitle("Book of Healing");

        //Make ability item
        setAbilityItem(true);

        //Set cooldown
        setCoolDown(10);

    }

    //Perhaps should reference this is PlatformView or Player class w.r.t. Index
    public void ability(LevelManager lm) {
        //NO ability as of yet
    }

    public void deactivateAbility(LevelManager lm) {
        //NO ability as of yet
    }

    public void abilityBelt(LevelManager lm){

        if(lm.player.getCurrHealth() + 5 > lm.player.getMaxHealth()){
            lm.player.setCurrHealth(lm.player.getMaxHealth());
        }
        else{
            lm.player.setCurrHealth(lm.player.getCurrHealth()+5);
        }

    }
    public void deactivateAbilityBelt(LevelManager lm){

    }
}
