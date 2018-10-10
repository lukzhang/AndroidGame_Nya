package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Creates a scrolling background for the scene for each level
 * The Background allows each instance to set the dimensions and bitmap as well as the scrolling speed.
 * Uses the BackgroundData class to retrieve the paramaters of the background
 */


public class Background {

    Bitmap bitmap;
    Bitmap bitmapReversed;
    int width;
    int height;
    boolean reversedFirst;
    int xClip;// controls where we clip the bitmaps each frame
    float y;
    float endY;
    int z;
    float speed;
    boolean isParallax;

    Background(Context context, int yPixelsPerMetre,
               int screenWidth, BackgroundData data){
        int resID = context.getResources().getIdentifier
                (data.bitmapName, "drawable",
                        context.getPackageName());
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), resID);

        // Which version of background (reversed or regular) is
        // currently drawn first (on left)
        reversedFirst = false;

        //Initialize animation variables.
        xClip = 0; //always start at zero
        y = data.startY;
        endY = data.endY;
        z = data.layer;
        isParallax = data.isParallax;
        speed = data.speed; //Scrolling background speed

        //Scale background to fit the screen.
        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth,
                data.height * yPixelsPerMetre
                , true);
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        // Create a mirror image of the background
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1); //Horizontal mirror effect.
        bitmapReversed = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, true);
    }
}
