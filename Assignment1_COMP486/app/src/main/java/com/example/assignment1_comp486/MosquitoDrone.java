package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.PointF;

/**
 * Enemy of drone type that track player and moves to him if close enough. On impact, respawns after
 * a set time. Chance to reward with bones.
 */

public class MosquitoDrone extends GameObject {


    final float MAX_X_VELOCITY = 7;
    final float MAX_Y_VELOCITY = 7;
    private PointF waypoint;

    MosquitoDrone(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 7;
        final String BITMAP_NAME = "mosquito";

        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT);
        setWidth(WIDTH);

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

        currentWaypoint = new PointF();

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();

        setFacing(LEFT);


        //Stats
        setDamage(55);
        setMaxHealth(60);
        setCurrHealth(getMaxHealth());
        setIsEnemy(true);

        setExperienceToGive(8);

        //Takes 10 seconds to respawn
        setRespawnTime(10000);

    }

    public void update(long fps) {

        //Tracks player with set hypotenuse distance based on x and y
        moveAI(20, MAX_X_VELOCITY, MAX_Y_VELOCITY);

        //Randomly rewards a bone. Has a 1 in 3 chance of dropping 12 bones
        int randomNum = 1 + (int)(Math.random() * ((3 - 1) + 1));
        if(randomNum == 3){
            setBonesToGive(15);
        }
        else{
            setBonesToGive(0);
        }


        //respawns enemy after elapsed time
        respawnEnemy();


        // update the drone hitbox
        setRectHitbox();

        move(fps);

    }



}
