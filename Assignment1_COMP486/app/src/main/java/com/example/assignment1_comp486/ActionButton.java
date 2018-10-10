package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Created by Luke on 5/1/2018.
 *
 * Item that is used to get bitmap so that the action button may be drawn
 */

public class ActionButton extends Item {

    ActionButton(Context context, int pixelsPerMetre) {
        final float HEIGHT = 3;
        final float WIDTH = 4;

        //Set dimensions
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("actionbutton");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }
}


