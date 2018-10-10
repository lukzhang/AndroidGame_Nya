package com.example.assignment1_comp486;

/**
 * Acts as the game's currency in game. Player may walk over it to pick it up.
 * Note: player may recieve bones when killing enemies, but this GameObject is not used. Instead
 * it tallies it through it level manager
 */

public class Bone extends GameObject {

    Bone(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metre wide

        setType(type);


        // Choose a Bitmap
        setBitmapName("bone");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        //If player collides, increase player's bone count by 1 and make disappear
        setRectHitbox();
    }

    public void update(long fps) {
    }
}
