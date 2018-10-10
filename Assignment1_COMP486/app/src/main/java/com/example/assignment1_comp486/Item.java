package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * This is the abstract class for all items that are in store and player inventory. Each item
 * has a bitmap, a title, a description, a cost, a resell value, as well as an ability.
 *
 * The title and description are displayed in the description panel when the player clicks on it.
 * The cost is shown when in store when a player clicks on the item in the transcation button. The
 * resell button is the value when player sells to NPC. This is also shown on the transaction button.
 * The ability is triggered by taking in a LevelManager paramater. When the item is bought, the
 * LevelManager can trigger the player's stats (eg. lm.player.damage) and adjust it accordingly.
 * Likewise, there is a deactivateAbility that adjusts the stat back when the player sells the item
 */

public abstract class Item {
    Bitmap bitmap;  //Use level manager to set the bitmap by referring to the extension of Gameobject
    float width;
    float height;
    int cost;
    int resell;
    //Each item has an index. Can set the ability at the player's end depending if the player owns
    //the item with respect to the index number
    int index;

    private String bitmapName;

    private String description;

    private String title;

    private boolean isAbilityItem;

    private int coolDown;


    public void prepareBitmap(Context context, String bitmapName, int pixelsPerMetre){

        // Make a resource id from a String that is the same name as the .png
        int resID = context.getResources().getIdentifier(bitmapName,
                "drawable", context.getPackageName());

        // Create the bitmap
        Bitmap theBitmap = BitmapFactory.decodeResource(context.getResources(),
                resID);

        // Scale the bitmapSheet based on the number of pixels per metre
        // Multiply by the number of frames contained in the image file
        // Default 1 frame

        theBitmap = Bitmap.createScaledBitmap(theBitmap,
                (int) (width *  pixelsPerMetre),
                (int) (height * pixelsPerMetre),
                false);

        //width = bitmap.getWidth();
        //height = bitmap.getHeight();

        this.bitmap = theBitmap;
    }

    //Item's ability that adjusts the player's stat
    public void ability(LevelManager lm){

    }

    //When sold (or discarded), item adjusts the player's stat back
    public void deactivateAbility(LevelManager lm){

    }

    //***************************************************************************
    //**********Item's activate and deactivate ability when added to belt********
    //***************************************************************************
    public void abilityBelt(LevelManager lm){

    }
    public void deactivateAbilityBelt(LevelManager lm){

    }
    public int getCoolDown() {
        return coolDown;
    }
    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    //********************************************************************************

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmapName(String bitmapName){
        this.bitmapName = bitmapName;
    }

    public String getBitmapName(){
        return bitmapName;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public int getCost(){
        return  cost;
    }

    public void setResell(int resell){
        this.resell=resell;
    }

    public int getResell(){
        return resell;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getDescription(){
        return description;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    public boolean isAbilityItem() {
        return isAbilityItem;
    }

    public void setAbilityItem(boolean abilityItem) {
        isAbilityItem = abilityItem;
    }
}
