package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.ArrayList;


/**
 * The basic class for making NPCs including those that the player talks with, buys/sells items from,
 * enemies, bosses, as enviromental objects. There are a number of methods that handle the dealings
 * with the different types of GameObjects.
 *
 * For enemies, the setWaypoint() method allows the enemy to know the player's position. The moveAI()
 * method moves the enemy to the player depending if they are close enough (which is set by the enemy's
 * class). The enemy may respawn on 1 of 3 locations set by the level manager thorugh PlatformView.
 *
 * Players may interact with NPC's via the getDialogues(), getDialoguePart2, getDialoguePart3, where
 * they return an ArrayList<String> of the dialogue set in the specific NPC class. This is retrieved
 * in the PlatformView.
 *
 * The ArrayList<Items> inventory, represents the inventory of the player as well as the store. At
 * the beginning of each loadLevel() in the PlatformView, items are added to the respective NPCs'
 * stores. Buying/selling occurs as items from one inventory are removed and added to the other one
 * (with the player's bones being adjusted in the process).
 *
 *
 */

public abstract class GameObject {

    // Most objects only have 1 frame
    private Animation anim = null;
    private boolean animated;
    private int animFps = 1;

    private RectHitbox rectHitbox = new RectHitbox();

    private float xVelocity;
    private float yVelocity;
    final int LEFT = -1;
    final int RIGHT = 1;
    private int facing;
    private boolean moves =false;


    private Vector2Point5D worldLocation;
    private float width;
    private float height;

    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;

    private String bitmapName;

    //If GameObject is enemy, then bullet can deal damage to it
    private boolean isEnemy;
    //If GameObject is boss
    private boolean isBoss;

    //Is this the final winning object the player needs to get to?
    private boolean isWinning = false;

    //Quest number. If 0, it is not a quest object.
    private int quest = 0;

    //If it is pickupitem, determine if it has been dropped
    private boolean hasBeenDropped = false;

    //Common item drop
    private boolean dropCommonItem = false;


    //For dialogue
    //A series of them depending on how far the player is into the game
    ArrayList<String> dialogues = new ArrayList<>();
    ArrayList<String> dialoguesPart2 = new ArrayList<>();
    ArrayList<String> dialoguesPart3 = new ArrayList<>();

    //For inventory. This refers the the player's inventory as well as the store owners
    //ArrayList<Item> inventory = new ArrayList<>();
    //Or we should just have an array list of int, that way we can reference to the item w.r.t. index
    ArrayList<Item> inventory = new ArrayList<>();

    //Stats: Damage, health, etc.
    private int maxHealth;
    private int currHealth;
    private int damage;
    private int bonesToGive;
    private int experienceToGive;


    //When enemy dies, wait a set time before respawning
    private boolean enemyDead;
    private long enemyTimeOfDeath;
    private long respawnTime;

    private Vector2Point5D spawnLocation;
    private Vector2Point5D spawnLocation2;
    private Vector2Point5D spawnLocation3;

    //Alert area box
    Rect alertBox;

    //For AI player movement
    long lastWaypointSetTime;
    PointF currentWaypoint;

    //Trigger to drop an item
    private boolean dropItem;


    //Updates the GameObject based on the frames per second
    public abstract void update(long fps);

    public void setAnimFps(int animFps) {
        this.animFps = animFps;
    }

    public void setAnimFrameCount(int animFrameCount) {
        this.animFrameCount = animFrameCount;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(Context context, int pixelsPerMetre, boolean animated){
        this.animated = animated;
        this.anim = new Animation(context, bitmapName,
                height,
                width,
                animFps,
                animFrameCount,
                pixelsPerMetre );
    }

    public Rect getRectToDraw(long deltaTime){
        return anim.getCurrentFrame(deltaTime, xVelocity, yVelocity, isMoves());
    }

    public void setRectHitbox() {
        rectHitbox.setTop(worldLocation.y);
        rectHitbox.setLeft(worldLocation.x);
        rectHitbox.setBottom(worldLocation.y + height);
        rectHitbox.setRight(worldLocation.x + width);
    }


    RectHitbox getHitbox(){
        return rectHitbox;
    }

    public void setWorldLocationY(float y) {
        this.worldLocation.y = y;
    }

    public void setWorldLocationX(float x) {
        this.worldLocation.x = x;
    }

    //If the GameObject can move, adjust its position depending on the fps and the velocity of that
    //GameObject.
    void move(long fps){
        if(xVelocity != 0) {
            this.worldLocation.x += xVelocity / fps;

            //Confine everything past x=0 and x=100
            if(this.worldLocation.x < 0){
                this.worldLocation.x =0;
                xVelocity=0;
            }
            if(this.worldLocation.x > 200){
                this.worldLocation.x = 200;
                xVelocity=0;
            }
        }

        if(yVelocity != 0) {

            this.worldLocation.y += yVelocity / fps;

            //Confine everything between y=8 and y=22
            if(this.worldLocation.y > 22){
                this.worldLocation.y = 22;
                yVelocity = 0;
            }
            if(this.worldLocation.y < 8){
                this.worldLocation.y = 8;
                yVelocity=0;
            }
        }

    }



    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        // Only allow for objects that can move
        if(moves) {
            this.xVelocity = xVelocity;
        }
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        // Only allow for objects that can move
        if(moves) {
            this.yVelocity = yVelocity;
        }
    }

    public boolean isMoves() {
        return moves;
    }

    public void setMoves(boolean moves) {
        this.moves = moves;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {

        // Make a resource id from a String that is the same name as the .png
        int resID = context.getResources().getIdentifier(bitmapName,
                "drawable", context.getPackageName());

        // Create the bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resID);

        // Scale the bitmapSheet based on the number of pixels per metre
        // Multiply by the number of frames contained in the image file
        // Default 1 frame
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (width * animFrameCount * pixelsPerMetre),
                (int) (height * pixelsPerMetre),
                false);

        return bitmap;
    }

    public Vector2Point5D getWorldLocation() {
        return worldLocation;
    }

    public void setWorldLocation(float x, float y, int z) {
        this.worldLocation = new Vector2Point5D();
        this.worldLocation.x = x;
        this.worldLocation.y = y;
        this.worldLocation.z = z;
    }

    public void setBitmapName(String bitmapName){
        this.bitmapName = bitmapName;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setWinning(boolean win){
        isWinning=win;
    }

    public boolean getWinning(){
        return isWinning;
    }

    public void setQuest(int i){
        quest = i;
    }

    public int getQuest(){
        return quest;
    }


    public void addDialogue(String dialogue){
        dialogues.add(dialogue);
    }

    public ArrayList<String> getDialogues(){
        return  dialogues;
    }

    public void addDialoguePart2(String dialogue){dialoguesPart2.add(dialogue);}

    public ArrayList<String> getDialoguesPart2(){
        return  dialoguesPart2;
    }

    public void addDialoguePart3(String dialogue){dialoguesPart3.add(dialogue);}

    public ArrayList<String> getDialoguesPart3(){
        return  dialoguesPart3;
    }

    public void addDialoguePart4(String dialogue){dialoguesPart3.add(dialogue);}

    public ArrayList<String> getDialoguesPart4(){
        return  dialoguesPart3;
    }

    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setDamage(int damage){
        this.damage=damage;
    }

    public int getDamage(){
        return damage;
    }

    public void setCurrHealth(int currHealth){
        this.currHealth=currHealth;
    }

    public int getCurrHealth(){
        return currHealth;
    }

    public void changeHealth(int damageToGive){
        int updatedHealth = getCurrHealth() - damageToGive;

        if(updatedHealth < 0){
            updatedHealth=0;
        }
        else if(updatedHealth > getMaxHealth()){
            updatedHealth=getMaxHealth();
        }

        setCurrHealth(updatedHealth);
    }

    public void setIsEnemy(boolean isEnemy){
        this.isEnemy=isEnemy;
    }

    public boolean getIsEnemy(){
        return isEnemy;
    }

    public void setBonesToGive(int bonesToGive){
        this.bonesToGive=bonesToGive;
    }

    public int getBonesToGive(){
        return bonesToGive;
    }

    public void setExperienceToGive(int experienceToGive){
        this.experienceToGive=experienceToGive;
    }

    public int getExperienceToGive(){
        return experienceToGive;
    }

    public void setEnemyDead(boolean enemyDead){
        this.enemyDead=enemyDead;
    }

    public boolean getEnemyDead(){
        return enemyDead;
    }

    public void setEnemyTimeOfDeath(long enemyTimeOfDeath){
        this.enemyTimeOfDeath = enemyTimeOfDeath;
    }

    public long getEnemyTimeOfDeath(){
        return enemyTimeOfDeath;
    }


    public void setRespawnTime(long respawnTime){
        this.respawnTime = respawnTime;
    }

    public long getRespawnTime(){
        return respawnTime;
    }

    public Vector2Point5D getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Vector2Point5D spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Vector2Point5D getSpawnLocation2() {
        return spawnLocation2;
    }

    public Vector2Point5D getSpawnLocation3() {
        return spawnLocation3;
    }

    public void setSpawnLocation2(Vector2Point5D spawnLocation2) {
        this.spawnLocation2 = spawnLocation2;
    }

    public void setSpawnLocation3(Vector2Point5D spawnLocation3){
        this.spawnLocation3=spawnLocation3;
    }

    //Respawns enemies at 1 of the 3 spawnpoints by choosing randomly.
    public void respawnEnemy(){
        if(getEnemyDead()){

            setVisible(false);
            setWorldLocation(0, 0, 0);

            long dTime =  System.currentTimeMillis();
            long currTime = dTime-getEnemyTimeOfDeath();


            //After the elapsed time, set enemyDead to false so that it may respawn
            if(currTime > getRespawnTime()){

                Vector2Point5D theSpawnPoint = getSpawnLocation(); //1st location be default
                int randomNum = 1 + (int)(Math.random() * ((3 - 1) + 1));
                if(randomNum == 1){
                    theSpawnPoint = getSpawnLocation();
                }

                else if(randomNum == 2){
                    theSpawnPoint = getSpawnLocation2();
                }
                else if(randomNum == 3){
                    theSpawnPoint = getSpawnLocation3();
                }

                setWorldLocation(theSpawnPoint.x, theSpawnPoint.y, 0);
                setEnemyDead(false);

                //Does this enemy drop an item?
                randomNum = 1 + (int)(Math.random() * ((3 - 1) + 1));
                if(randomNum==1){
                    setDropItem(true);
                }
                setCurrHealth(getMaxHealth());
            }
        }
        else{
            long timeOfDeath =  System.currentTimeMillis();
            setEnemyTimeOfDeath(timeOfDeath);
            setVisible(true);
        }
    }

    //Moves the enemy to the player by getting the player's position (in PlatformView). The extent to
    //which they notice the player is based on the hypotenuse variable that calculates the hyptoneuse between
    //the x and y distances between the two gameobjects.
    public void moveAI(int hypotenuse, float velocityx, float velocityy){
        double distance = Math.sqrt((currentWaypoint.x - getWorldLocation().x)*(currentWaypoint.x - getWorldLocation().x)
                +(currentWaypoint.y - getWorldLocation().y)*(currentWaypoint.y - getWorldLocation().y));

        //If enemy is left of player, move enemy right
        if(distance < hypotenuse){
            if (currentWaypoint.x> getWorldLocation().x) {
                setxVelocity(velocityx);
            } else if (currentWaypoint.x < getWorldLocation().x) {
                setxVelocity(-velocityx);
            } else {
                setxVelocity(0);
            }

            //If enemy is right of player, move enemy left
            if (currentWaypoint.y - getHeight()/2 >= getWorldLocation().y) {
                setyVelocity(velocityy);
            } else if (currentWaypoint.y - getHeight()/2 < getWorldLocation().y) {
                setyVelocity(-velocityy);
            } else {
                setyVelocity(0);
            }
        }
        //If player far enough away, stop chasing
        else{
            setxVelocity(0);
            setyVelocity(0);
        }

        //Change facing depending on direction. If not moving use last direction.
        if(getxVelocity() > 0){
            setFacing(LEFT);
        }
        else if(getxVelocity() < 0){
            setFacing(RIGHT);
        }
    }

    //Sets the location of where the player is so that the enemy may track them.
    public void setWaypoint(Vector2Point5D playerLocation) {
        if (System.currentTimeMillis() > lastWaypointSetTime + 1000) {//Has 1 second passed
            lastWaypointSetTime = System.currentTimeMillis();
            currentWaypoint.x = playerLocation.x;
            currentWaypoint.y = playerLocation.y;
        }

    }

    public void setIsBoss(boolean isBoss){
        this.isBoss=isBoss;
    }

    public boolean getIsBoss(){
        return isBoss;
    }

    public boolean isDropItem() {
        return dropItem;
    }

    public void setDropItem(boolean dropItem) {
        this.dropItem = dropItem;
    }

    public boolean isHasBeenDropped() {
        return hasBeenDropped;
    }

    public void setHasBeenDropped(boolean hasBeenDropped) {
        this.hasBeenDropped = hasBeenDropped;
    }

    public boolean isDropCommonItem() {
        return dropCommonItem;
    }

    public void setDropCommonItem(boolean dropCommonItem) {
        this.dropCommonItem = dropCommonItem;
    }
}
