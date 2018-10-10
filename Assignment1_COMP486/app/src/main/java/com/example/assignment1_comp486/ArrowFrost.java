package com.example.assignment1_comp486;

import android.content.Context;


//Item that increases fire rate by 3. Note, that gun is an instance of player.
public class ArrowFrost extends Item {

    ArrowFrost(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //Set dimensions
        setWidth(WIDTH);
        setHeight(HEIGHT);

        //How much the item costs to buy
        setCost(25);

        //Resell value for item
        setResell(18);

        // Choose a Bitmap
        setBitmapName("arrowfrost");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

        //set Description
        setDescription("Increases projectile fire rate by 3");

        //Set Title
        setTitle("Frost Arrow");

    }

    //Activates ability when bought by increasing fire rate by 3
    public void ability(LevelManager lm){
        int prevFireRate = lm.player.gun.getRateOfFire();
        int newFireRate = prevFireRate+3;
        lm.player.gun.setFireRate(newFireRate);
    }

    //Deactivates ability when discarded by decreasing fire rate by 3
    public void deactivateAbility(LevelManager lm){
        int prevFireRate = lm.player.gun.getRateOfFire();
        int newFireRate = prevFireRate-3;
        lm.player.gun.setFireRate(newFireRate);
    }


}
