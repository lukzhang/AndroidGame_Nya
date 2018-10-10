package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.PointF;

/**
 * Boss of 3rd level. Tracks player and rewards player with exp and bones.
 */

public class SkeletonBoss extends GameObject {


    final float MAX_X_VELOCITY = 6;
    final float MAX_Y_VELOCITY = 6;
    private PointF waypoint;

    SkeletonBoss(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 3;
        final String BITMAP_NAME = "skeletonking";

        final float HEIGHT = 5;
        final float WIDTH = 3;

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
        setDamage(80);
        setMaxHealth(3000);
        setCurrHealth(getMaxHealth());
        setExperienceToGive(120);
        setIsEnemy(true);
        setIsBoss(true);


        //Rewards bones
        setBonesToGive(220);
    }

    public void update(long fps) {

        //Tracks player through the hypotenuse distnace on x and y
        moveAI(25, MAX_X_VELOCITY, MAX_Y_VELOCITY);


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
