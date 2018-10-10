package com.example.assignment1_comp486;

import android.content.Context;

import java.util.ArrayList;

/**
 * Main player in the game (who is a dog). Player is of type GameObject but also has stats including
 * armor, shield, player level, experience, lifeseteal, damage.
 * Damage is the damage dealt to enemies. Armor is the damage absorbed when enemies attack player.
 * Shield is the chance to block the attack completeley. Lifesteal is the life given to player when
 * hitting an enemy. Player also has experience, and experience to go to the next level.
 * In addition, player makes use of the Gun class, which spawns bullets when pullTrigger() is
 * activated by pressing the action button in the Input Controller. Other stats such as thorn damage,
 * armour penetration are left alone for now.
 *
 *
 *
 */

//Player is a dog, which is represented by a spritesheet
public class Player extends GameObject {
    RectHitbox rectHitboxFeet;
    RectHitbox rectHitboxHead;
    RectHitbox rectHitboxLeft;
    RectHitbox rectHitboxRight;

    final float MAX_X_VELOCITY = 10;

    //Let's add another velocity
    final float MAX_Y_VELOCITY = 10;

    boolean isPressingRight = false;
    boolean isPressingLeft = false;
    boolean isPressingUp = false;
    boolean isPressingDown = false;

    //Player's Action
    private boolean action = false;

    //Number of bones the player has. This acts as in game currency
    private int numBones;

    //Player's gun
    public Gun gun;

    //Player's experience
    private int experience;
    private int playerLevel;
    private int toNextLevel;
    private final int MAX_LEVEL = 50;       //Max level the player can get to

    //Other player combat stats
    //******At least for now, only let player use these attributes (rather than enemies as well)
    private int lifeSteal;           //When player hits enemy, steals some life
    private int armour;              //Armour reduces damage taken
    private int shield;              //shield has a chance of blocking attack
    private int magicDamage;         //Additional damage depending on enemy's magic resistance
    private int armourPenetration;   //Extent of which damage penetrates armour
    private int magicResist;         //Resistance to magic damage
    private int thornDamage;         //Damage taken is reflected upon enemy

    //The slots to add abilities to from the inventory panel
    ArrayList<Item> abilitySlots = new ArrayList<>();



    Player(Context context, float worldStartX, float worldStartY, int pixelsPerMetre) {

        final float HEIGHT = 1;
        final float WIDTH = 2;

        setHeight(HEIGHT); // 1 metre tall
        setWidth(WIDTH); // 2 metre wide

        // Standing still to start with
        setxVelocity(0);
        setyVelocity(0);
        setFacing(RIGHT);


        // Now for the player's other attributes
        // Our game engine will use these
        setMoves(true);
        setActive(true);
        setVisible(true);

        //Tile type
        setType('p');

        // Choose a Bitmap
        // This is a sprite sheet with multiple frames
        setBitmapName("dogwalk");

        final int ANIMATION_FPS = 16;
        final int ANIMATION_FRAME_COUNT = 5;

        // Set this object up to be animated
        setAnimFps(ANIMATION_FPS);
        setAnimFrameCount(ANIMATION_FRAME_COUNT);
        setAnimated(context, pixelsPerMetre, true);

        //Player's gun
        gun = new Gun();


        // X and y locations from constructor parameters
        setWorldLocation(worldStartX, worldStartY, 0);

        rectHitboxFeet = new RectHitbox();
        rectHitboxHead = new RectHitbox();
        rectHitboxLeft = new RectHitbox();
        rectHitboxRight = new RectHitbox();

        //Player starts with 0 bones
        numBones = 0;

        //Stats
        setMaxHealth(20);
        setCurrHealth(getMaxHealth());
        setDamage(1);
        setExperience(0);
        setToNextLevel(10);
        setPlayerLevel(1);

    }

    public void update(long fps) {

        if (isPressingRight) {
            this.setxVelocity(MAX_X_VELOCITY);
        } else if (isPressingLeft) {
            this.setxVelocity(-MAX_X_VELOCITY);
        }
        else if(isPressingUp){
            this.setyVelocity(-MAX_Y_VELOCITY);
        }
        else if(isPressingDown){
            this.setyVelocity((MAX_Y_VELOCITY));
        }
        else {
            this.setxVelocity(0);
            this.setyVelocity(0);
        }

        //Current health never exceeds max health or is below zero
        if(getCurrHealth() > getMaxHealth()){
            setCurrHealth(getMaxHealth());
        }
        if(getCurrHealth() < 0){
            setCurrHealth(0);
        }

        //If we level up
        //In the future, cap the level with MAX_LEVEL
        if(getExperience() >= getToNextLevel()){
            int updateExp = getExperience() - getToNextLevel();
            int prevLvl = getPlayerLevel();
            setPlayerLevel(prevLvl+1);
            setExperience(updateExp);
            //Set the required experience for next level up. Use this formula: Lvl = playerLvl^2 + playerlvl*8
            int nextLvl = getPlayerLevel() * getPlayerLevel() + getPlayerLevel()*8;
            //Update the player's attack
            int prevAttack = getDamage();
            int lvl = getPlayerLevel();
            int attackToAdd = lvl / 2;
            int updatedAttack = prevAttack+attackToAdd;
            setDamage(updatedAttack);
            //Update the player's health
            int prevHealth = getCurrHealth();
            int prevMaxHealth = getMaxHealth();
            int healthToAdd = getPlayerLevel() * 3;
            setMaxHealth(prevMaxHealth + healthToAdd);
            setCurrHealth(prevHealth+healthToAdd);

            setToNextLevel(nextLvl);
        }



        //what way is player facing?
        if (this.getxVelocity() > 0) {
            //facing right
            setFacing(RIGHT);
        } else if (this.getxVelocity() < 0) {
            //facing left
            setFacing(LEFT);
        }//if 0 then unchanged




        // Let's go!  UPDATES THE X AND Y COORDS
        this.move(fps);



        // Update all the hitboxes to the new location
        // Get the current world location of the player
        // and save them as local variables we will use next
        Vector2Point5D location = getWorldLocation();
        float lx = location.x;
        float ly = location.y;



        //update the player feet hitbox
        rectHitboxFeet.top = ly + (getHeight() * .95f);
        rectHitboxFeet.left = lx + getWidth() * .1f;
        rectHitboxFeet.bottom = ly + getHeight() * .98f;
        rectHitboxFeet.right = lx + getWidth() * .9f;

        // Update player head hitbox
        rectHitboxHead.top = ly;
        rectHitboxHead.left = lx + (getWidth() * .1f);
        rectHitboxHead.bottom = ly + getHeight() * .2f;
        rectHitboxHead.right = lx + (getWidth() * .9f);


        // Update player left hitbox
        rectHitboxLeft.top = ly + getHeight() * .1f;
        rectHitboxLeft.left = lx + getWidth() * .2f;
        rectHitboxLeft.bottom = ly + getHeight() * .9f;
        rectHitboxLeft.right = lx + (getWidth() * .3f);

        // Update player right hitbox
        rectHitboxRight.top = ly + getHeight() * .1f;
        rectHitboxRight.left = lx + (getWidth() * .8f);
        rectHitboxRight.bottom = ly + getHeight() * .9f;
        rectHitboxRight.right = lx + getWidth() * .7f;

        //Constrain player's movement to defined area
        //Confine everything past x=0 and x=100
        if(getWorldLocation().x < 0){
            setWorldLocationX(0);
            setxVelocity(0);
        }
        if(getWorldLocation().x > 200){
            getWorldLocation().x = 200;
            setxVelocity(0);
        }
        //Confine everything between y=8 and y=22
        if(getWorldLocation().y > 22){
            setWorldLocationY(22);
            setyVelocity(0);
        }
        if(getWorldLocation().y < 8){
            setWorldLocationY(8);
            setyVelocity(0);
        }



    }

    public boolean pullTrigger() {
        //Try and fire a shot

        float xOffset = this.getWorldLocation().x;

        //If player facing Right, start the bullet at the player's head by adding player's width
        if(getFacing() == RIGHT){
            xOffset = this.getWorldLocation().x + this.getWidth();
        }
        return gun.shoot(xOffset, this.getWorldLocation().y, getFacing(), getHeight());
    }

    public int checkCollisions(RectHitbox rectHitbox) {
        int collided = 0;//no collision

        //the left
        if (this.rectHitboxLeft.intersects(rectHitbox)) {
            //left has collided
            setxVelocity(0);
            setPressingLeft(false);
            collided = 1;
        }

        //the right
        if (this.rectHitboxRight.intersects(rectHitbox)) {
            //right has collided
            setxVelocity(0);
            setPressingRight(false);
            collided = 1;
        }


        //the feet
        if (this.rectHitboxFeet.intersects(rectHitbox)) {
            //feet have collided
            setyVelocity(0);
            setPressingDown(false);
            collided = 2;
        }

        //Now the head
        if (this.rectHitboxHead.intersects(rectHitbox)) {
            //head has collided
            setyVelocity(0);
            setPressingUp(false);
            collided = 3;
        }

        return collided;

    }


    public void setPressingRight(boolean isPressingRight) {
        this.isPressingRight = isPressingRight;
    }

    public void setPressingLeft(boolean isPressingLeft) {
        this.isPressingLeft = isPressingLeft;
    }

    public void setPressingUp(boolean isPressingUp) {
        this.isPressingUp = isPressingUp;
    }

    public void setPressingDown(boolean isPressingDown) {
        this.isPressingDown = isPressingDown;
    }


    public void setAction(boolean action){
        this.action=action;
    }

    public boolean getAction(){
        return action;
    }

    public void setNumBones(int numBones){
        this.numBones=numBones;
    }

    public int getNumBones(){
        return  numBones;
    }

    //Increment number of bones
    public void incrementBones(){
        numBones++;
    }
    //Decrement number of bones
    public void decrementBones(){numBones--;}

    public void setExperience(int experience){
        this.experience=experience;
    }

    public int getExperience(){
        return experience;
    }

    public void setPlayerLevel(int playerLevel){
        this.playerLevel = playerLevel;
    }

    public int getPlayerLevel(){
        return playerLevel;
    }

    public void setToNextLevel(int toNextLevel){
        this.toNextLevel=toNextLevel;
    }

    public int getToNextLevel(){
        return toNextLevel;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(int lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    //For damaging the player w.r.t. armour and shield
    public void damagePlayer(int damageToGive){

        //Get armour, get shield, get health
        int theArmour = getArmour();
        int theShield = getShield();
        int theHealth = getCurrHealth();

        //Armour absorbs some damage
        damageToGive = damageToGive - theArmour;

        //Do not give player health if they take damage
        if(damageToGive < 0){
            damageToGive=0;
        }

        //Random number between 1 to 100
        int randomNum = 1 + (int)(Math.random() * ((100 - 1) + 1));

        //Chance that the shield blocks the attack
        if(randomNum < theShield){
            damageToGive=0;
        }

        //The health is updated based on the modified damageToGive
        theHealth = theHealth - damageToGive;

        //Update the current health
        setCurrHealth(theHealth);

    }

    //Gives the player health depending on how much lifesteal they have
    public void lifeStealTaken(){
        int oldHealth = getCurrHealth();
        int newHealth = oldHealth+getLifeSteal();

        if(newHealth > getMaxHealth()){
            setCurrHealth(getMaxHealth());
        }
        else{
            setCurrHealth(newHealth);
        }
    }

    public ArrayList<Item> getAbilitySlots() {
        return abilitySlots;
    }

    public void setAbilitySlots(ArrayList<Item> abilitySlots) {
        this.abilitySlots = abilitySlots;
    }
}
