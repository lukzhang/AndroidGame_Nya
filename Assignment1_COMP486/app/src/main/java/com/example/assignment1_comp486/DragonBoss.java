package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.PointF;

/**
 * Boss dragon with animation. After player kills the boss, they may interact with the tree to
 * move onto the next level.
 */

public class DragonBoss extends GameObject {


    final float MAX_X_VELOCITY = 3;
    final float MAX_Y_VELOCITY = 3;
    private PointF waypoint;

    DragonBoss(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 3;
        final String BITMAP_NAME = "dragonboss";

        final float HEIGHT = 6;
        final float WIDTH = 6;

        setHeight(HEIGHT); // 6 metre tall
        setWidth(WIDTH); // 6 metre wide

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
        setDamage(7);
        setMaxHealth(50);
        setCurrHealth(getMaxHealth());
        setExperienceToGive(15);
        setIsEnemy(true);
        setIsBoss(true);


        //Rewards 20 bones
        setBonesToGive(20);
    }

    public void update(long fps) {

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


        // update the drone hitbox
        setRectHitbox();

        move(fps);

    }



}
