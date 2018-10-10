package com.example.assignment1_comp486;

/**
 * Creates the store, panel for item description, inventory, as well as buttons to toggle inventory,
 * close store, and buy/sell items.
 * Buttons trigger booleans in the level manager so that items can be traded or viewed.
 *
 * Each store and player inventory has 9 slots, so 9 rects are made for each. The Level Manager is
 * designed so that items from each store and inventory are placed from top left to bottom right. If
 * a selected slot's index (which is also goes from top left to bottom right) exceeds that of the
 * inventory/store array list size, that means there is no item there and no action takes place.
 * On the other hand, if an item is there, the title and description of it is shown on the panel above
 * as well as the price of it in the transcation button (if the store is open). The player may press
 * the transaction button (or buy/sell button) and if the player's bones is at least equal to the item's
 * price, a transaction occurs.
 *
 */

import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;


public class ArtifactsController {

    //Inventory
    Rect[] inventories;
    Rect page;
    Rect toggleInventory;

    //Store
    Rect[] stores;
    Rect stock;
    Rect closeStore;

    //For buying and selling
    Rect transaction;
    //Description of item
    Rect description;

    //Abilities
    Rect firstAbility;

    //Add items to belt button
    Rect addItem;

    /*
    //Slots
    Rect firstSlot;
    Rect secondSlot;
    Rect thirdSlot;
    Rect fourthSlot;
    */


    ArtifactsController(int screenWidth, int screenHeight) {

        //Configure the player buttons
        int buttonWidth = screenHeight / 7;
        int buttonHeight = screenHeight / 7;
        int buttonPadding = screenWidth / 80;

        //The inventory will have 9 slots
        inventories = new Rect[9];
        //The store also has 9 slots
        stores = new Rect[9];

        //Initialize each panel for both store and inventory
        for(int i=0; i<9; i++){

            //j represents each row by dividing the integer
            int j=i/3;
            //k represents each column by taking the remainder
            int k=i%3;

                inventories[i] = new Rect(screenWidth/2 + buttonPadding + (buttonPadding+buttonWidth)*2
                        + (buttonWidth + buttonPadding) * k,
                        buttonPadding*3 + buttonHeight + (buttonHeight+buttonPadding)*j,
                        screenWidth/2 + buttonPadding + (buttonPadding+buttonWidth)*2
                                + (buttonWidth + buttonPadding) * k + buttonWidth,
                        buttonPadding*3 +buttonHeight + (buttonHeight+buttonPadding)*j + buttonHeight );

                stores[i] = new Rect(screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2
                       + (buttonWidth + buttonPadding) * k,
                        buttonPadding*3 + buttonHeight + (buttonHeight+buttonPadding)*j,
                        screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2
                                + (buttonWidth + buttonPadding) * k + buttonWidth,
                        buttonPadding*3 +buttonHeight + (buttonHeight+buttonPadding)*j + buttonHeight);
        }

        //Represents the panel that shows the inventory
        page = new Rect(screenWidth/2 + (buttonPadding+buttonWidth)*2,
                buttonPadding*2 + buttonHeight,
                screenWidth/2 + buttonPadding + (buttonPadding+buttonWidth)*2
                        + (buttonWidth + buttonPadding) * 2 + buttonWidth + buttonPadding,
                buttonPadding*3 +buttonHeight + (buttonHeight+buttonPadding)*2 + buttonHeight + buttonPadding);

        //Represents the panel that shows the store
        stock = new Rect(screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2 - buttonPadding,
                buttonPadding*2 + buttonHeight,
                screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2 - buttonPadding
                        + (buttonWidth + buttonPadding) * 2 + buttonWidth + buttonPadding + buttonPadding,
                buttonPadding*3 +buttonHeight + (buttonHeight+buttonPadding)*2 + buttonHeight + buttonPadding );

        //Button that toggles the inventory on or off. Designed in the level manager so that it cannot be
        //toggled off while in store. Only the Close button for the store does that.
        toggleInventory = new Rect(screenWidth/2 + buttonWidth*3,
                buttonPadding*3 +(buttonHeight+buttonPadding)*4 + buttonPadding,
                screenWidth/2 + buttonWidth*3 + buttonWidth,
                buttonPadding*3 + (buttonHeight+buttonPadding)*4 + buttonHeight - buttonPadding);

        //Button that closes the store while the store is open and both inventory and store are viewed
        closeStore = new Rect(screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*4
                + (buttonWidth + buttonPadding) * 2 ,
                buttonPadding*5,
                screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*4
                        + (buttonWidth + buttonPadding) * 2 + buttonWidth,
                buttonPadding*5 + buttonHeight/2);

        //The buy/sell button when the store is open. This is between the two panels for store/inventory
        transaction = new Rect(screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2
                + (buttonWidth + buttonPadding) * 3 + buttonPadding,
                buttonPadding*3 + buttonHeight,
                screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2
                        + (buttonWidth + buttonPadding) * 3 + buttonPadding + buttonWidth,
                buttonPadding*3 + buttonHeight + buttonHeight );

        //Add item to belt button
        addItem = new Rect(screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2
                + (buttonWidth + buttonPadding) * 3 + buttonPadding,
                buttonPadding + buttonHeight*3,
                screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*2
                        + (buttonWidth + buttonPadding) * 3 + buttonPadding + buttonWidth,
                buttonPadding + buttonHeight*3 + buttonHeight );

        //The panel above invnetory and store panels that show the title and description of the selected
        //item.
        description = new Rect(screenWidth/2 - buttonPadding - (buttonPadding+buttonWidth)*3
                + (buttonWidth + buttonPadding) * 2,
                buttonPadding,
                screenWidth/2 + buttonWidth * 4,
                buttonPadding*5 + buttonHeight/2);

        //First Ability
        firstAbility = new Rect(screenWidth/2 + buttonWidth*3,
                buttonPadding*3 +(buttonHeight+buttonPadding)*4 + buttonPadding + buttonHeight,
                screenWidth/2 + buttonWidth*3 + buttonWidth,
                buttonPadding*3 + (buttonHeight+buttonPadding)*4 + buttonHeight*2 - buttonPadding);

        /*
        //Slots
        firstSlot = new Rect(screenWidth - buttonPadding - buttonWidth,
                buttonPadding*3 + buttonPadding + buttonHeight,
                screenWidth - buttonPadding - buttonWidth + buttonWidth,
                buttonPadding*3 + buttonPadding + buttonHeight + buttonHeight);
        secondSlot = new Rect(screenWidth - buttonPadding - buttonWidth,
                buttonPadding*3 + buttonPadding + buttonHeight + buttonHeight + buttonPadding,
                screenWidth - buttonPadding - buttonWidth + buttonWidth,
                buttonPadding*3 + buttonPadding + buttonHeight + buttonHeight + buttonPadding + buttonHeight);
        thirdSlot = new Rect(screenWidth - buttonPadding - buttonWidth,
                buttonPadding*3 + buttonPadding + buttonHeight + buttonHeight + buttonPadding + buttonHeight + buttonPadding,
                screenWidth - buttonPadding - buttonWidth + buttonWidth,
                buttonPadding*3 + buttonPadding + buttonHeight + buttonHeight + buttonPadding + buttonHeight + buttonPadding + buttonHeight);
*/

    }

    //Gets each panel for the Inventory as an ArryList so that PlatformView can draw them
    public ArrayList getPanel(){
        //create an array of buttons for the draw method
        ArrayList<Rect> currentButtonList = new ArrayList<>();

        //Add Inventory panels
        for(int i=0; i<9; i++){
            currentButtonList.add(inventories[i]);
        }

        return  currentButtonList;
    }

    //Gets each panel for the Store as an ArrayList so that PlatformView can draw them
    public ArrayList getStores(){
        ArrayList<Rect> currentButtonList = new ArrayList<>();

        //Add Inventory panels
        for(int i=0; i<9; i++){
            currentButtonList.add(stores[i]);
        }

        return  currentButtonList;
    }



    public Rect getPage(){
        return page;
    }

    public Rect getStock(){
        return stock;
    }

    public Rect getToggleInventory(){
        return toggleInventory;
    }

    public Rect getCloseStore(){
        return closeStore;
    }

    public Rect getTransaction(){return transaction; }

    public Rect getDescription(){return description;}

    public Rect getFirstAbility(){return firstAbility;}

    public Rect getAddItem() {
        return addItem;
    }

    //Shows action if player clicks on an item, presses buy/sell button, or toggles inventory or
    //closes the store
    public void handleInput(MotionEvent motionEvent,LevelManager l, SoundManager sound, Viewport vp){
        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {

            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);

            if(l.isPlaying()) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        if(toggleInventory.contains(x,y)){
                            if(!l.getDisplayInventory()){
                                l.setDisplayInventory(true);
                            }
                            else{
                                l.setDisplayInventory(false);
                                if(!l.getDisplayStore()){
                                    l.setCurrItemToNull();
                                }
                            }
                        }
                        else if(closeStore.contains(x,y)){
                            if(l.getDisplayStore()){
                                l.setDisplayStore(false);
                                l.setDisplayInventory(false);
                                l.setCurrItemToNull();
                            }
                        }
                        //Store cells
                        for(int k=0; k<stores.length; k++){
                            if(stores[k].contains(x,y)){

                                    //Checks the current store's inventory size. If the panel corresponding
                                    //to the item exceeds the inventory size, that means there is no
                                    //item. So, cannot select the item.
                                    if(l.currStore.inventory.size() >= k+1){
                                        //If item is there, select the corresponding item as the current
                                        //item.
                                        l.setCurrItem(k);
                                        l.setStoreSelect(true);
                                    }
                            }
                        }
                        //Player inventory cells.
                        //Check's the player's inventory size and the selected inventory cell. If the
                        //cell corresponding to the item exceeds the size, that means the player
                        //doesn't have an item there. (This is because items are sequentially placed
                        //from top left to bottome right).
                        for(int k=0; k<inventories.length; k++){
                            if(inventories[k].contains(x,y)){
                                //If there is an item in the cell, select item as current player item
                                l.setPlayerCurrItem(k);
                                l.setStoreSelect(false);
                            }
                        }

                        //Buy/Sell button
                        if(transaction.contains(x,y)){
                            //If selected from store side, buy the item
                            if(l.getCurrItem() != null && l.getDisplayStore()){
                                if(l.getStoreSelect()){
                                    //Activate ability if player has the money
                                    if(l.player.getNumBones() >= l.currItem.getCost()){
                                        l.currItem.ability(l);
                                        l.buyItem();
                                    }

                                }
                                //If selected from player side, sell the item
                                else{
                                    //Deactivate ability
                                    l.currItem.deactivateAbility(l);
                                    //Sell item
                                    l.sellItem();
                                }

                            }
                        }

                        //Add item to belt button
                        if(addItem.contains(x,y)){
                            //If item is selected and that item is an ability item
                            if(l.getCurrItem() != null){
                                if(l.getCurrItem().isAbilityItem()){
                                    if(!l.isCurrItemIsOnBelt() && !l.getStoreSelect()){
                                        l.addAbilityItemToSlot();
                                    }
                                    else if(!l.getStoreSelect()){
                                        l.removeAbilityItemFromSlot();
                                    }

                                }
                            }
                        }

                        /*
                        //Ability Button
                        if(firstAbility.contains(x,y)){
                            if(!l.isAbilityOn() && !l.isAbilityOnCooldown()){
                                l.setAbilityOn(true);
                                l.setAbilityOnCooldown(true);
                            }

                        }
                        */

                        break;


                    case MotionEvent.ACTION_UP:


                        break;


                    case MotionEvent.ACTION_POINTER_DOWN:

                        break;


                    case MotionEvent.ACTION_POINTER_UP:

                        break;
                }
            }
        }

    }
}
