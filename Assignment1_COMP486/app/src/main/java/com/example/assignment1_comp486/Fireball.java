package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 6/25/2018.
 */

public class Fireball extends Item {

    Fireball(Context context, int pixelsPerMetre) {
        final float HEIGHT = 2;
        final float WIDTH = 2;

        //Set Dimension
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("fireball");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }




}