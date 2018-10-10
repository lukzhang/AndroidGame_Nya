package com.example.assignment1_comp486;

import android.content.Context;

/**
 * Used for bitmap to draw pause icon in top right of screen
 */

public class PauseButton extends Item {

    PauseButton(Context context, int pixelsPerMetre) {
        final float HEIGHT = 2;
        final float WIDTH = 2;

        //If we want to scale the item???
        setWidth(WIDTH);
        setHeight(HEIGHT);

        // Choose a Bitmap
        setBitmapName("pause");

        prepareBitmap(context, getBitmapName(), pixelsPerMetre);

    }


}
