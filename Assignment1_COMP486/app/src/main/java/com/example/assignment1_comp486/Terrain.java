package com.example.assignment1_comp486;

/**
 * Used to set spawn points. The 'grass2' tile is not actually used as it is set to non-active and
 * non-visible. Only the coordinates are used to initialize the spawn points on each map.
 *
 */

public class Terrain extends GameObject {

    Terrain(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 1 metre wide

        setType(type);
        setVisible(false);
        setActive(false);



        // Choose a Bitmap
        setBitmapName("grass2");

        // Where does the tile start
        // X and y locations from constructor parameters
        //Set z to -1 as we want it behind the player****************
        setWorldLocation(worldStartX, worldStartY, -1);


        //******NO HIT BOX!!!!!!!!!**************
        //setRectHitbox();
    }

    public void update(long fps) {
    }

}
