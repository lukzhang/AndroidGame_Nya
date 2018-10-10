package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.PointF;

/**
 * Boss of 2nd level that track the player
 */

public class MinotaurBoss extends GameObject {


    final float MAX_X_VELOCITY = 5;
    final float MAX_Y_VELOCITY = 5;
    private PointF waypoint;

    MinotaurBoss(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 3;
        final String BITMAP_NAME = "minotaur";

        final float HEIGHT = 6;
        final float WIDTH = 4;

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
        setDamage(20);
        setMaxHealth(500);
        setCurrHealth(getMaxHealth());
        setExperienceToGive(45);
        setIsEnemy(true);
        setIsBoss(true);


        //Rewards bones
        setBonesToGive(60);
    }

    public void update(long fps) {

        //Tracks the player based on the preset hypotenuse distance based on x and y
        moveAI(27, MAX_X_VELOCITY, MAX_Y_VELOCITY);


        //Change facing depending on direction. If not moving use last direction.
        if(getxVelocity() > 0){
            setFacing(LEFT);
        }
        else if(getxVelocity() < 0){
            setFacing(RIGHT);
        }

        //Make boss permantly dead
        if(getCurrHealth() <= 0){
            setVisible(false);
            setActive(false);
        }


        // update the hitbox
        setRectHitbox();

        move(fps);

    }



}
