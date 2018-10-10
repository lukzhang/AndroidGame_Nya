package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Item which is used for its bitmap so that it can be drawn as the toggle inventory icon
 */

public class Bag extends Item {

    Bag(Context context, int pixelsPerMetre) {
        final float HEIGHT = 2;
        final float WIDTH = 2;

        //Set Dimension
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("inventory");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }




}
