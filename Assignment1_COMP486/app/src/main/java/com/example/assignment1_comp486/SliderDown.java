package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item used for bitmap for button
 */

public class SliderDown extends Item {

    SliderDown(Context context, int pixelsPerMetre) {
        final float HEIGHT = 4;
        final float WIDTH = 3;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("bluesliderdown");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }


}
