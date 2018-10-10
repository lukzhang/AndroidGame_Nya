package com.example.assignment1_comp486;

/**
 * Tree that telports the player to the next level. In PlatformView, checks if the boss is dead
 * before allowing the teleport.
 */

public class Tree extends GameObject {

    Tree(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 5;
        final float WIDTH = 4;

        setHeight(HEIGHT); // 5 metres tall
        setWidth(WIDTH); // 4 metre wide

        setType(type);


        // Choose a Bitmap
        setBitmapName("tree");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 1);

        setRectHitbox();

        //Dialogue
        addDialogue("You will be teleported to the next level");

        //Dialogue
        addDialoguePart2("You need to defeat the boss..");
        addDialoguePart2("to travel to the next level");
    }

    public void update(long fps) {
    }
}
