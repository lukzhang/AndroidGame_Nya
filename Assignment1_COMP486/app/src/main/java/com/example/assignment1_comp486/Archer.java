package com.example.assignment1_comp486;

/**
 * NPC that sells items
 */

//The archer who sells items
public class Archer extends GameObject {

    Archer(float worldStartX, float worldStartY, char type) {

        final String BITMAP_NAME = "archer";

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
        addDialogue("Want some better projectiles?");

    }

    public void update(long fps) {
    }
}
