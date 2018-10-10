package com.example.assignment1_comp486;

/**
 * Creates buttons for player to move around, action button to talk to NPCs and fire gun, and
 * pause button to pause the game
 */

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

public class InputController {

    Rect left;
    Rect right;
    Rect up;
    Rect down;

    Rect pause;

    //This button is used to interact with NPCs and objects
    Rect action;


    InputController(int screenWidth, int screenHeight) {

        //Configure the player buttons
        int buttonWidth = screenWidth / 8;
        int buttonHeight = screenHeight / 7;
        int buttonPadding = screenWidth / 80;

        left = new Rect(buttonPadding,
                screenHeight - buttonHeight - 10*buttonPadding,
                buttonWidth + buttonPadding,
                screenHeight - 10*buttonPadding);

        right = new Rect(buttonWidth + buttonPadding,
                screenHeight - buttonHeight - 10*buttonPadding,
                buttonWidth + buttonPadding + buttonWidth,
                screenHeight - 10*buttonPadding);

        //If we want this to be a 2D rpg rather than a platformer
        //Up button
        up = new Rect(buttonWidth + buttonPadding - buttonHeight/2,
                screenHeight - buttonWidth - 17*buttonPadding - buttonPadding/2,
                buttonWidth + buttonPadding + buttonHeight/2,
                screenHeight - 17*buttonPadding);
        //Down button
        down = new Rect(buttonWidth + buttonPadding - buttonHeight/2,
                screenHeight - 10*buttonPadding,
                buttonWidth + buttonPadding + buttonHeight/2,
                screenHeight - 10*buttonPadding + buttonWidth);


        //Make this a new button potentially
        action = new Rect(screenWidth - buttonWidth - buttonPadding,
                screenHeight - buttonHeight - buttonPadding,
                screenWidth - buttonPadding,
                screenHeight - buttonPadding);

        pause = new Rect(screenWidth - buttonPadding - buttonWidth/2,
                buttonPadding,
                screenWidth - buttonPadding,
                buttonPadding + buttonHeight/2);

    }

    //Returns the buttons so that the draw() in PlatformView can use them
    public ArrayList getButtons(){
        //create an array of buttons for the draw method
        ArrayList<Rect> currentButtonList = new ArrayList<>();
        currentButtonList.add(left);
        currentButtonList.add(right);
        currentButtonList.add(action);
        currentButtonList.add(pause);

        //I added this!!!
        currentButtonList.add(up);
        currentButtonList.add(down);

        //Add HUD
        //currentButtonList.add(bottom);
        return  currentButtonList;
    }


    public void handleInput(MotionEvent motionEvent,LevelManager l, SoundManager sound, Viewport vp){
        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {

            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);

            if(l.isPlaying()) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    //Left/Right/Down/Up are triggered as such.
                    case MotionEvent.ACTION_DOWN:
                        if (right.contains(x, y)) {
                            l.player.setPressingDown(false);
                            l.player.setPressingUp(false);
                            l.player.setPressingRight(true);
                            l.player.setPressingLeft(false);
                        } else if (left.contains(x, y)) {
                            l.player.setPressingDown(false);
                            l.player.setPressingUp(false);
                            l.player.setPressingLeft(true);
                            l.player.setPressingRight(false);
                        }
                        else if(up.contains(x,y)){
                            l.player.setPressingDown(false);
                            l.player.setPressingUp(true);
                            l.player.setPressingRight(false);
                            l.player.setPressingLeft(false);
                        }
                        else if(down.contains(x,y)){
                            l.player.setPressingDown(true);
                            l.player.setPressingUp(false);
                            l.player.setPressingRight(false);
                            l.player.setPressingLeft(false);
                        }
                        //If player is near an object, do not fire weapon. That way, they can
                        //talk to NPCs without firing.
                        else if (action.contains(x, y)) {
                            if(l.getPlayerHasCollided()){
                                l.player.setAction(true);
                            }
                            else{
                                l.player.pullTrigger();
                            }

                        } else if (pause.contains(x, y)) {
                            //If we have won the game, no need to pause as the game has ended
                            if(!l.hasWon()){
                                l.switchPlayingStatus();
                            }
                        }


                        break;


                    case MotionEvent.ACTION_UP:
                        if (right.contains(x, y)) {
                            l.player.setPressingRight(false);
                        } else if (left.contains(x, y)) {
                            l.player.setPressingLeft(false);
                        }
                        else if (up.contains(x,y)){
                            l.player.setPressingUp(false);
                        }
                        else if (down.contains(x,y)){
                            l.player.setPressingDown(false);
                        }
                        else if(action.contains(x,y)){
                            l.player.setAction(false);
                        }


                        break;


                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (right.contains(x, y)) {
                            l.player.setPressingDown(false);
                            l.player.setPressingUp(false);
                            l.player.setPressingRight(true);
                            l.player.setPressingLeft(false);
                        } else if (left.contains(x, y)) {
                            l.player.setPressingDown(false);
                            l.player.setPressingUp(false);
                            l.player.setPressingLeft(true);
                            l.player.setPressingRight(false);
                        }
                        else if(up.contains(x,y)){
                            l.player.setPressingDown(false);
                            l.player.setPressingUp(true);
                            l.player.setPressingLeft(false);
                            l.player.setPressingRight(false);
                        }
                        else if(down.contains(x,y)){
                            l.player.setPressingDown(true);
                            l.player.setPressingUp(false);
                            l.player.setPressingLeft(false);
                            l.player.setPressingRight(false);
                        }
                        else if (action.contains(x, y)) {
                            if(l.getPlayerHasCollided()){
                                l.player.setAction(true);
                            }
                            else{
                                l.player.pullTrigger();
                            }

                        } else if (pause.contains(x, y)) {
                            //If we have won the game, no need to pause as the game has ended
                            if(!l.hasWon()){
                                l.switchPlayingStatus();
                            }
                        }
                        break;


                    case MotionEvent.ACTION_POINTER_UP:
                        if (right.contains(x, y)) {
                            l.player.setPressingRight(false);
                        } else if (left.contains(x, y)) {
                            l.player.setPressingLeft(false);
                        }
                        else if(up.contains(x,y)){
                            l.player.setPressingUp(false);
                        }
                        else if(down.contains(x,y)){
                            l.player.setPressingDown(false);
                        }

                        else if (action.contains(x, y)) {
                            l.player.setAction(false);
                        }
                        break;
                }
            }else {// Not playing
                //Move the viewport around to explore the map
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:

                        if (pause.contains(x, y)) {
                            //If we have won the game, no need to unpause as the game has ended
                            if(!l.hasWon()){
                                l.switchPlayingStatus();
                            }
                            //Log.w("pause:", "DOWN" );
                        }

                        break;


                }



            }
        }

    }
}
