package com.example.assignment1_comp486;

/**
 * NPC that asks player for time by making them go to clocktree and interacting with it. Rewards 5
 * bones
 */

public class ShopKeeper extends GameObject {

    ShopKeeper(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 2 metre wide

        setType(type);

        // Choose a Bitmap
        setBitmapName("shopkeeper");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        setRectHitbox();

        //Dialogue
        addDialogue("Hi, I'm Kevin");
        addDialogue("I've seemed to lost my clock");
        addDialogue("One of the red monsters came in and stole it");
        addDialogue("Could you return it for me?");

        addDialoguePart2("Ah, my precious clock!");
        addDialoguePart2("Thanks for your help, here's a reward");
        addDialoguePart2("You received 25 bones");

        addDialoguePart3("I understand you're on your way home");
        addDialoguePart3("It's kind of confusing how you landed here..");
    }

    public void update(long fps) {
    }
}
