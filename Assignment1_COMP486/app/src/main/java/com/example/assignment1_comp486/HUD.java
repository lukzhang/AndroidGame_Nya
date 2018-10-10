package com.example.assignment1_comp486;

/**
 * This creates the hud for the bottom portion where the dialogue occurs, as well as allowing the player
 * to tap on it to continue the converssation. Furthermore, the health and experience bars are displayed
 */

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

public class HUD {

    //HUD
    Rect bottom;
    //Filler for HUD that doesn't have any action. All it does is make the HUD full at the bottom
    Rect leftFiller;
    Rect rightFiller;
    //Buttons if player needs to answer a question
    //Rect yes;
    //Rect no;

    //Displays player's health
    Rect maxHealth;

    //Display's player's experience bar
    Rect nextLevel;


    //button padding
    int padding;

    //Abilities slots
    Rect abilitySlots[];
    Rect abilityHolder;




    HUD(int screenWidth, int screenHeight) {

        //Configure the dimensions to build the rects
        int buttonWidth = screenWidth / 10;
        int buttonHeight = screenHeight / 7;
        int buttonPadding = screenWidth / 120;

        padding=buttonPadding;

        //HUD
        bottom = new Rect(buttonWidth + buttonPadding + buttonWidth,
                buttonHeight*5,
                screenWidth - (buttonWidth + buttonPadding + buttonWidth),
                screenHeight);
        //Left
        leftFiller = new Rect(0,
                buttonHeight*5,
                buttonWidth + buttonPadding + buttonWidth,
                screenHeight);

        //Right
        rightFiller = new Rect(screenWidth - (buttonWidth + buttonPadding + buttonWidth),
                buttonHeight*5,
                screenWidth,
                screenHeight);

        /*
        //Yes
        yes = new Rect(screenWidth - (buttonWidth + buttonPadding + buttonWidth),
                buttonHeight*5,
                screenWidth - buttonWidth - 2*buttonPadding,
                screenHeight - buttonHeight);

        //No
        no = new Rect(screenWidth - (buttonWidth + buttonPadding + buttonWidth),
                screenHeight - buttonHeight,
                screenWidth - buttonWidth - 2*buttonPadding,
                screenHeight);
                */

        maxHealth = new Rect(buttonPadding, buttonPadding, buttonPadding+buttonWidth*2,
                buttonPadding*2);

        nextLevel = new Rect(buttonPadding, buttonPadding*2, buttonPadding + buttonWidth*2,
                buttonPadding*2 + buttonPadding/2);

        //Ability slots
        abilitySlots = new Rect[4];

        int xOffset = buttonWidth + buttonPadding;
        int yOffset = -buttonHeight/2;

        for(int i=0; i<4; i++){
            abilitySlots[i] = new Rect(buttonWidth*2 + buttonPadding + buttonWidth + xOffset*i, buttonHeight*5 + yOffset,
                    buttonWidth*2 + buttonPadding + buttonWidth + buttonWidth + xOffset*i,
                    buttonHeight*5 + buttonHeight + yOffset);
        }

        abilityHolder = new Rect(buttonWidth*2 + buttonPadding + buttonWidth - buttonPadding,
                buttonHeight*5 + yOffset - buttonPadding,
                buttonWidth*2 + buttonPadding + buttonWidth + buttonWidth + buttonPadding + xOffset*3,
                buttonHeight*5 + buttonHeight + yOffset + buttonPadding);


    }

    //Returns the components of the HUD as an arrayList so that the platformView draw() method
    //can use them.
    public ArrayList getHUD(){
        //create an array of buttons for the draw method
        ArrayList<Rect> currentButtonList = new ArrayList<>();

        //Add HUD
        currentButtonList.add(bottom);
        currentButtonList.add(leftFiller);
        currentButtonList.add(rightFiller);
        //currentButtonList.add(yes);
        //currentButtonList.add(no);
        return  currentButtonList;
    }


    public Rect getMaxHealth(){
        return maxHealth;
    }

    public Rect getNextLevel(){
        return nextLevel;
    }

    public Rect[] getAbilitySlots() {
        return abilitySlots;
    }

    public Rect getAbilityHolder() {
        return abilityHolder;
    }

    public ArrayList getBelt(){
        ArrayList<Rect> currentButtonList = new ArrayList<>();

        //Add Inventory panels
        for(int i=0; i<4; i++){
            currentButtonList.add(abilitySlots[i]);
        }

        return  currentButtonList;
    }

    public void handleInput(MotionEvent motionEvent, LevelManager l, SoundManager sound, Viewport vp){
        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {

            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);

            if(l.isPlaying()) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        if (bottom.contains(x, y)){
                            //If the player is in a dialogue, move to the next dialogue
                            if(l.getDialogueActive()){
                                l.setNextDialogue(true);
                            }
                        }

                        for(int k = 0; k<abilitySlots.length; k++){

                            if(abilitySlots[k].contains(x,y) && l.player.abilitySlots.size() > k){
                                //If inventory is open, select item, otherwise activate ability
                                //Make sure to let level manager know item is from belt
                                if(l.getDisplayInventory()){
                                    l.currItem = l.player.abilitySlots.get(k);
                                    l.setCurrItemIsOnBelt(true);
                                }
                                else{
                                    //Set on cooldown and activate ability on belt
                                    if(!l.getBeltAbilityOnCooldown(k)){
                                        l.setBeltAbilityOnCooldown(k, true);
                                        l.player.getAbilitySlots().get(k).abilityBelt(l);
                                    }
                                }
                            }
                        }


                        /*
                        else if(yes.contains(x,y)){
                            if(l.getQuestioning()){
                                l.setTheAnswer(true);
                                l.setHasAnswered(true);
                                l.setQuestioning(false);
                            }
                        }
                        else if(no.contains(x,y)){
                            if(l.getQuestioning()){
                                l.setTheAnswer(false);
                                l.setHasAnswered(true);
                                l.setQuestioning(false);
                            }
                        }
                        */


                        break;


                    case MotionEvent.ACTION_UP:


                        break;


                    case MotionEvent.ACTION_POINTER_DOWN:

                        if (bottom.contains(x, y)){
                            //If player is in dialogue, move to next dialogue
                            if(l.getDialogueActive()){
                                l.setNextDialogue(true);
                            }

                        }
                        /*
                        else if(yes.contains(x,y)){

                            if(l.getQuestioning()){
                                l.setTheAnswer(true);
                                l.setHasAnswered(true);
                                l.setQuestioning(false);
                            }

                        }
                        else if(no.contains(x,y)){

                            if(l.getQuestioning()){
                                l.setTheAnswer(false);
                                l.setHasAnswered(true);
                                l.setQuestioning(false);
                            }
                        }
                        */


                        break;


                    case MotionEvent.ACTION_POINTER_UP:

                        break;
                }
            }else {// Not playing

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:



                        break;


                }



            }
        }

    }
}
