package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.PointF;


/**
 * First basic enemy the player deals with. This enemy tracks the player at a certain distance
 * and disappears (effectively exploding) if touches the player and gives some damage. In order to
 * respawn, the drone's worldposition is moved off to the distance and dissapears before spawning
 * in one of the 3 designated spawning points (randomly).
 * If enemy dies, has a chance of rewarding player with bone.
 */

public class Drone extends GameObject {

    final float MAX_X_VELOCITY = 5;
    final float MAX_Y_VELOCITY = 5;
    private PointF waypoint;



    //****************************In the constructor, store the starting coordinates. When
    //killed, can respawn at that location
    //Perhaps can pick a random direction to move until close enough to player


    Drone(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {
        final float HEIGHT = 1;
        final float WIDTH = 1;
        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        final int ANIMATION_FPS = 5;
        final int ANIMATION_FRAME_COUNT = 4;
        final String BITMAP_NAME = "redslime";
        setBitmapName(BITMAP_NAME);

        setMoves(true);
        setActive(true);
        setVisible(true);

        // Set this object up to be animated
        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setBitmapName(BITMAP_NAME);
        setAnimated(context, pixelsPerMetre, true);

        currentWaypoint = new PointF();

        // Where does the drone start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
        setFacing(RIGHT);

        //Stats
        setDamage(3);
        setMaxHealth(2);
        setCurrHealth(getMaxHealth());
        setIsEnemy(true);

        setExperienceToGive(3);

        //Respawn time
        setRespawnTime(4000);

        //Does this enemy drop an item?
        int randomNum = 1 + (int)(Math.random() * ((3 - 1) + 1));
        if(randomNum==1){
            setDropItem(true);
        }

    }

    public void update(long fps) {


            //Tracks player with a distance of 22
            moveAI(22, MAX_X_VELOCITY, MAX_Y_VELOCITY);

            //Randomly rewards a bone. Has a 1 in 3 chance of dropping a bone
            int randomNum = 1 + (int)(Math.random() * ((3 - 1) + 1));
            if(randomNum == 3){
                setBonesToGive(1);
            }
            else{
                setBonesToGive(0);
                //To drop the item, set the bitmap of item to that of enemy
            }


            //respawns enemy after elapsed time
            respawnEnemy();

            // update the drone hitbox
            setRectHitbox();

            move(fps);

    }


}