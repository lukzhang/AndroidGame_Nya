package com.example.assignment1_comp486;

/**
 * NPC of pink dog that tells player to eat chickens to gain exp. Has a 2nd part of dialogue, but
 * not used in PlatformView
 */

public class Pink extends GameObject {

    Pink(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 2 metre wide

        setType(type);

        // Choose a Bitmap
        setBitmapName("pinkdog");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        setRectHitbox();

        //Dialogue
        addDialogue("I suggest you eat the chickens");
        addDialogue("They give you 1 experience");
        addDialogue("Not to mention soo tasty");

        addDialoguePart2("I like this farm");
        addDialoguePart2("I also like the color pink!");

    }

    public void update(long fps) {
    }
}
