package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.PointF;

/**
 * Enemy that tracks the player and deals damage before respawning. Much like the first 'Drone',
 * but deals more damage and has more health.
 */

public class KilverDrone extends GameObject {


    final float MAX_X_VELOCITY = 6;
    final float MAX_Y_VELOCITY = 6;
    private PointF waypoint;

    KilverDrone(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 8;
        final String BITMAP_NAME = "kilver";

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
        setDamage(26);
        setMaxHealth(25);
        setCurrHealth(getMaxHealth());
        setIsEnemy(true);

        setExperienceToGive(10);

        //Does this enemy drop an item?
        int randomNum = 1 + (int)(Math.random() * ((10 - 1) + 1));
        if(randomNum==1){
            setDropItem(true);
        }

        //Takes 8 seconds to respawn
        setRespawnTime(8000);

    }

    public void update(long fps) {

        moveAI(16, MAX_X_VELOCITY, MAX_Y_VELOCITY);

        //Randomly rewards a bone. Has a 1 in 3 chance of dropping 7 bones
        int randomNum = 1 + (int)(Math.random() * ((3 - 1) + 1));
        if(randomNum == 3){
            setBonesToGive(7);
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
