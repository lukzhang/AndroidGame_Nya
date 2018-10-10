package com.example.assignment1_comp486;

/**
 * Barmaid NPC that sells items
 */

public class Barmaid extends GameObject {

    Barmaid(float worldStartX, float worldStartY, char type) {


        final String BITMAP_NAME = "barmaid";

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
        addDialogue("I've got some magic items that you may want");

    }

    public void update(long fps) {
    }
}
