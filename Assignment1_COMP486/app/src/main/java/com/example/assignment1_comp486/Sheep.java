package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.PointF;

/**
 * Sheep that can be herded if player buys GreenBook item from The Teacher NPC. Drops 25 bones.
 */

//Sheep which moves when player bumps into it. Object for Quest 3.
public class Sheep extends GameObject {

    Sheep(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 4;
        final String BITMAP_NAME = "sheep";

        final float HEIGHT = 2;
        final float WIDTH = 5;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 5 metre wide

        setType(type);

        // Now for the player's other attributes
        // Our game engine will use these
        setMoves(true);
        setActive(true);
        setVisible(true);

        // Choose a Bitmap
        setBitmapName(BITMAP_NAME);
        // Set this object up to be animated
        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setBitmapName(BITMAP_NAME);
        setAnimated(context, pixelsPerMetre, true);

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();

        setFacing(LEFT);

        //Sheep does not move until player bumps into it
        setxVelocity(0);

        //Quest Number 3
        setQuest(3);

        //Dialogue
        addDialogue("I do not know how to herd this sheep");
        addDialogue("Perhaps I can find a fellow dog that can help me");

        addDialoguePart2("Bah!");
        addDialoguePart2("You herded the sheep");
        addDialoguePart2("The sheep dropped 25 bones!");
    }

    public void update(long fps) {

        move(fps);

        setRectHitbox();

    }



}
