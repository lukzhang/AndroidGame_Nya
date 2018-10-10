package com.example.assignment1_comp486;

import android.content.Context;



//Armourer who sells items
public class Armourer extends GameObject {

    Armourer(float worldStartX, float worldStartY, char type) {

        final String BITMAP_NAME = "armourer";

        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);


        setMoves(false);
        setActive(true);
        setVisible(true);

        // Choose a Bitmap
        setBitmapName(BITMAP_NAME);


        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();

        //Dialogue
        addDialogue("I am the armourer. Take a look at my wares");

    }

    public void update(long fps) {
    }
}
