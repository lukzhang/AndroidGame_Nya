package com.example.assignment1_comp486;

import android.content.Context;



//Item that increases fire rate of gun. This gun is an instance of the player.
public class Arrow extends Item {

    Arrow(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //Dimensions
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(3);

        //Resell value for item
        setResell(2);

        // Choose a Bitmap
        setBitmapName("arrow");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases projectile fire rate by 1");

        //Set Title
        setTitle("Arrow of Guidance");

    }


    //When bought, activates ability. Increases fire rate of gun by 1.
    public void ability(LevelManager lm){
        int prevFireRate = lm.player.gun.getRateOfFire();
        int newFireRate = prevFireRate+1;
        lm.player.gun.setFireRate(newFireRate);
    }

    //When discarded, deactivates ability. Decreases fire rate of gun by 1.
    public void deactivateAbility(LevelManager lm){
        int prevFireRate = lm.player.gun.getRateOfFire();
        int newFireRate = prevFireRate-1;
        lm.player.gun.setFireRate(newFireRate);
    }


}
