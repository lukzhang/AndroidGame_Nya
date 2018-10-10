package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item used for bitmap for button
 */

public class SliderLeft extends Item {

    SliderLeft(Context context, int pixelsPerMetre) {
        final float HEIGHT = 3;
        final float WIDTH = 4;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("bluesliderleft");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }


}
