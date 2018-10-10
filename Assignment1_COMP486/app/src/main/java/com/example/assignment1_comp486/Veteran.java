package com.example.assignment1_comp486;

/**
 * NPC that sells items
 */

public class Veteran extends GameObject {

    Veteran(float worldStartX, float worldStartY, char type) {


        final String BITMAP_NAME = "veteran";

        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 2 metre wide

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
        addDialogue("I'm a veteran of many years..");

    }

    public void update(long fps) {
    }
}
