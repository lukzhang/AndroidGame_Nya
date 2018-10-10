package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 6/21/2018.
 */

public class Angel extends Item {

    Angel(Context context, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        //Set Dimension
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("angel");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }




}
