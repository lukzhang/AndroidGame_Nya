package com.example.assignment1_comp486;

/**
 * NPC that sells GreenBook to player so that they can herd sheep.
 *
 */

public class Teacher extends GameObject {

    Teacher(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 2;
        final float WIDTH = 2;

        setHeight(HEIGHT); // 2 metre tall
        setWidth(WIDTH); // 2 metre wide

        setType(type);

        // Choose a Bitmap
        setBitmapName("teacher");

        // Where does the tile start
        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, -1);

        setRectHitbox();

        //Dialogue
        addDialogue("Hello fellow canine");
        addDialogue("I am the one they call The Teacher");
        addDialogue("Now go away, I'm busy meditating");

        addDialoguePart2("You need the special knowledge in order to herd sheep");
        addDialoguePart2("Are you willing to learn the secrets of the herd?");
        addDialoguePart2("Good. Then just buy my ancient text book to know the way");

        addDialoguePart3("The tree above me teleports you to the next level");
        addDialoguePart3("But, you must defeat the boss first");
        addDialoguePart3("Look past the stone landmark to the east");


    }

    public void update(long fps) {
    }
}
