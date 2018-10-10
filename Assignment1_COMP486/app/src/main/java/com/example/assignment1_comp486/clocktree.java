package com.example.assignment1_comp486;

/**
 * The player can interact with the GameObject so that he can tell the ShopKeeper GameObject NPC
 * the time.
 */

//When player bumps into this, completes the second quest
public class clocktree extends GameObject {

    clocktree(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 6;
        final float WIDTH = 3;

        setHeight(HEIGHT); // 6 metre tall
        setWidth(WIDTH); // 3 metre wide

        setType(type);


        // Choose a Bitmap
        setBitmapName("clocktree");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 1);

        setRectHitbox();

        //Quest Number 2
        setQuest(2);

        addDialogue("You look up at the tree");
        addDialogue("It is 2:15pm");

        addDialoguePart2("What a peculiar tree");


    }

    public void update(long fps) {
    }
}
