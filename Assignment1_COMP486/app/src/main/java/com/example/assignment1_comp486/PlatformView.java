package com.example.assignment1_comp486;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * This is where the main game runs.
 *
 * The level is first loaded. If it's any level other than the 1st level, the player's stat's such
 * as damage, health, experience, are carried over from the previous level.
 *
 * There are two main methods that are called in the run() method; update() and draw().
 * Update() keeps track of the player, inputs from input controller, HUD, and artifacts controller,
 * which are called in the onTouchEvent() method. Checks if Player has collided with any objects. If
 * so, checks on the type of GameObject and the correct functions are carried out. These include
 * talking to NPCs if the action button is also pressed, taking damage, or walking over bones to pick
 * them up.
 *
 * Draw() draws each object, the HUD, the buttons, the items, from the worldLocation onto the viewport.
 * Objects non in the viewport are clipped off. Bitmaps and text are drawn onto Rects of the input
 * controller, HUD, and artifacts controller by using them as a coordinate reference.
 *
 *
 */

public class PlatformView extends SurfaceView implements Runnable {

    private boolean debugging = false;  //If debugging, will draw the coordinates and velocities
    private volatile boolean running;   //Pauses/unpauses game
    private Thread gameThread = null;

    // For drawing
    private Paint paint;
    // Canvas could initially be local.
    // But later we will use it outside of the draw() method
    private Canvas canvas;
    private SurfaceHolder ourHolder;


    Context context;

    // Our new engine classes
    private LevelManager lm;
    private Viewport vp;
    InputController ic;
    SoundManager sm;

    long startFrameTime;
    long timeThisFrame;
    long fps;

    //For ability time check
    long lastTime;

    //Abilities for belt slots
    long[] lastBeltTIme = new long[4];

    //For dialogue
    private String theDialogue;
    int counter;

    //HUD
    HUD hud;
    //Store and Inventory
    ArtifactsController ac;

    //Flags that determine where the player has progressed and can move forward
    //first flag (with index 0) does nothing and is meaningless
    boolean[] questFlags;

    //Current Store Inventory
    ArrayList<Item> currentStore = new ArrayList<>();

    //Starting level player location. When player dies, restarts at that location
    float oldX;
    float oldY;

    //Checks if dog is down
    boolean dogIsDown;
    //When player dies, restrict player's movement by freezing them at their last location
    float deadX;
    float deadY;
    //Checks if it is time to respawn after fallen animation
    boolean timeToRespawn = false;
    //Keeps track of the time dog was down before respawning
    long timeOfDeath;

    //The current level player is on
    private LevelData currLevel;
    //ArrayList of levels
    ArrayList<LevelData> mapLevels;

    //The levels
    //1.
    LevelFarm levelFarm;
    //2.
    LevelDesert levelDesert;
    //3.
    LevelMountain levelMountain;
    //4.
    LevelSnow levelSnow;
    //
    //WINNING LEVEL - when player reaches this level, set hasWon() to true
    int winningLevel = 3;       //Index of 3 in mapLevels

    //Spawn location for enemies
    Vector2Point5D spawnMapLocation;
    //2nd spawn area
    Vector2Point5D spawnMapLocation2;
    //3rd spawn area
    Vector2Point5D spawnMapLocation3;

    //If boss is unable to attack
    boolean bossCantAttack;
    long timeOfContact;


    PlatformView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        // Initialize the viewport
        vp = new Viewport(screenWidth, screenHeight);

        sm = new SoundManager();
        sm.loadSound(context);

        //Initialize the levels
        currLevel=new LevelData();
        mapLevels=new ArrayList<>();
        levelFarm = new LevelFarm();
        levelDesert = new LevelDesert();
        levelMountain = new LevelMountain();
        levelSnow = new LevelSnow();

        //Add levels to arraylist
        mapLevels.add(levelFarm);
        mapLevels.add(levelDesert);
        mapLevels.add(levelMountain);
        mapLevels.add(levelSnow);

        //Set current level to level 1
        setCurrLevel(mapLevels.get(0));

        //Loads the starting level. In the future, this can be the saved level.
        loadLevel(getCurrLevel().getMapName(), 10, 16);

        setCounter(0);

        //Keeps track of what has been said and done. Allows the game to maintain basic logic such
        //as interacting with NPCs before opening the store.
        questFlags = new boolean[50];
    }

    //Load the selected level
    public void loadLevel(String level, float px, float py) {

        //Previous level. That way previous stats can be transferred to new level
        LevelManager oldLM = lm;

        // Make the LevelManager null
        // As this method can be called at any time
        // Including when LevelManager is not null.
        lm = null;

        // Create a new LevelManager
        // Passing in a Context, screen details, level name and player location
        lm = new LevelManager(context, vp.getPixelsPerMetreX(), vp.getScreenWidth(), ic, level, px, py);

        ic = new InputController(vp.getScreenWidth(), vp.getScreenHeight());
        //HUD
        hud = new HUD(vp.getScreenWidth(), vp.getScreenHeight());
        //Inventory and store
        ac = new ArtifactsController(vp.getScreenWidth(), vp.getScreenHeight());

        //set the players location as the world centre of the viewport
        vp.setWorldCentre(lm.gameObjects.get(lm.playerIndex)
                        .getWorldLocation().x,
                lm.gameObjects.get(lm.playerIndex)
                        .getWorldLocation().y);

        //Sets starting location of level
        oldX = lm.gameObjects.get(lm.playerIndex)
                .getWorldLocation().x;
        oldY = lm.gameObjects.get(lm.playerIndex)
                .getWorldLocation().y;

        //Set the spawn point for enemies
        for (GameObject go : lm.gameObjects){
            //If grass type (which is visible is set to false) get world location for
            //spawn point

            //1st spawn point
            if(go.getType()=='t'){
                lm.setSpawnX(go.getWorldLocation().x);
                lm.setSpawnY(go.getWorldLocation().y);

                spawnMapLocation=go.getWorldLocation();
            }
            //2nd spawn point
            if(go.getType()=='i'){
                lm.setSpawn2X(go.getWorldLocation().x);
                lm.setSpawn2Y(go.getWorldLocation().y);

                spawnMapLocation2=go.getWorldLocation();
            }
            //3rd spawn point
            if(go.getType()=='z'){
                lm.setSpawn3X(go.getWorldLocation().x);
                lm.setSpawn3Y(go.getWorldLocation().y);

                spawnMapLocation3=go.getWorldLocation();
            }

        }

        //Set old stats to new level manager
        if(oldLM != null){
            lm.player.setPlayerLevel(oldLM.player.getPlayerLevel());
            lm.player.setExperience(oldLM.player.getExperience());
            lm.player.setToNextLevel(oldLM.player.getToNextLevel());
            lm.player.inventory = oldLM.player.inventory;
            lm.player.setDamage(oldLM.player.getDamage());
            lm.player.setCurrHealth(oldLM.player.getCurrHealth());
            lm.player.setMaxHealth(oldLM.player.getMaxHealth());
            lm.player.setLifeSteal(oldLM.player.getLifeSteal());
            lm.player.setShield(oldLM.player.getShield());
            lm.player.setArmour(oldLM.player.getArmour());
            lm.player.setNumBones(oldLM.player.getNumBones());
            lm.player.gun.setFireRate(oldLM.player.gun.getRateOfFire());
            lm.player.abilitySlots = oldLM.player.getAbilitySlots();
        }

        //******************************************************
        //***Give NPC's their items at the start of each level***
        //*******************************************************
        for (GameObject go : lm.gameObjects){

            //*************************
            //***Level 1: LevelFarm****
            //*************************
            //Teacher has the green book
            if(go.getType()=='u'){
                int itemIndex = 0;
                go.inventory.add(lm.items.get(itemIndex));
            }
            //Merchant
            if(go.getType()=='M'){
                int itemsIndex = 1;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 2;
                go.inventory.add(lm.items.get(itemsIndex));
                /*
                itemsIndex = 3;
                go.inventory.add(lm.items.get(itemsIndex));
                */
                itemsIndex = 4;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 5;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 6;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 25;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 26;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 27;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 28;
                go.inventory.add(lm.items.get(itemsIndex));
            }
            //***************************
            //***Level 2: LevelDesert****
            //***************************
            //Armourer
            if(go.getType()=='A'){
                int itemsIndex = 7;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 8;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 9;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 10;
                go.inventory.add(lm.items.get(itemsIndex));
            }
            //Wizard
            if(go.getType()=='W'){
                int itemsIndex = 11;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 12;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 25;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 26;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 27;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 28;
                go.inventory.add(lm.items.get(itemsIndex));
            }
            //Archer
            if(go.getType()=='B'){
                int itemsIndex = 4;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 13;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 14;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 29;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 30;
                go.inventory.add(lm.items.get(itemsIndex));
            }
            //*****************************
            //***Level 3: LevelMountain****
            //*****************************
            //Warrior
            if(go.getType()=='C'){
                int itemsIndex = 15;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 16;
                go.inventory.add(lm.items.get(itemsIndex));
            }
            //Barmaid
            if(go.getType()=='E'){
                int itemsIndex = 17;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 18;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 19;
                go.inventory.add(lm.items.get(itemsIndex));
            }
            //Veteran
            if(go.getType()=='V'){
                int itemsIndex = 20;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 31;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 29;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 30;
                go.inventory.add(lm.items.get(itemsIndex));
                itemsIndex = 26;
                go.inventory.add(lm.items.get(itemsIndex));
            }
        }

        //Set boss is dead to false as it's a new level
        lm.setBossIsDead(false);

        //Game intiallly paused, so unpase
        lm.switchPlayingStatus();
    }

    //set the current level
    public void setCurrLevel(LevelData currLevel){
        this.currLevel = currLevel;
    }
    //get the current level
    public LevelData getCurrLevel(){
        return currLevel;
    }


    //Main loop of the game. Updates the game, draw the relavent tiles and gameobjects and items,
    //then calculates the FPS so that it may update accordingly
    @Override
    public void run() {

        while (running) {
            startFrameTime = System.currentTimeMillis();

            update();

            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                //Set a higher frame rate so that bullets can detect enemies consistently
                fps = 1000 / timeThisFrame;
            }
        }
    }


    /*
    * UPDATE:
    * Checks if player has collided with any objects (with a hitbox)
    * if so, checks if it is of type (char) of a certain object so it may interact with it
    * One of the interactions include dialogue, so player may press action to interact with the
    * NPC and then tap on the bottom HUD to continue conversation via dialogueSequence() method
    * Has enemies track the player by getting the player's world position
    * Checks if each bullet has collided with an object, if it is an enemy, adjusts damage
    * accordingly
    * Checks if the player has died (by 0 or less health) so that it may respawn later
    *
    * */
    private void update() {

        //Set it false before the loop, if any object collides with the player, do not allow player to shoot
        lm.setPlayerHasCollided(false);


        /*
        //For going through the level quickly
        lm.player.setDamage(1000);
        lm.player.setMaxHealth(10000);
        lm.player.setCurrHealth(lm.player.getMaxHealth());
        lm.player.setNumBones(1000);
        */


        //Set the location in level manager as well
        lm.setStartLocationX(oldX);
        lm.setStartLocationY(oldY);


        //Goes through all the LevelManager's GameObjects and see if player collided with them
        for (GameObject go : lm.gameObjects) {

            //Teleport object to enemy location upon death if triggered
            if(go.getType() == 'P'){
                if(lm.isDropTheClock()){
                            /*
                            go.setWorldLocation(lm.getDropClockLocation().x, lm.getDropClockLocation().y,
                                    lm.getDropClockLocation().z);
                                    */

                    go.setWorldLocationX(lm.getDropClockLocation().x);
                    go.setWorldLocationY(lm.getDropClockLocation().y);

                    //Now that the item is dropped, make visible and active
                    go.setActive(true);
                    go.setVisible(true);

                    //RESET the hitbox with new location
                    go.setRectHitbox();

                    lm.setDropTheClock(false);
                }
            }
            //Nugget
            if(go.getType() == 'N'){
                if(lm.isDroppedNugget() && !go.isHasBeenDropped()){

                    go.setWorldLocationX(lm.getDropNuggetLocation().x);
                    go.setWorldLocationY(lm.getDropNuggetLocation().y);

                    //Now that the item is dropped, make visible and active
                    go.setActive(true);
                    go.setVisible(true);

                    //RESET the hitbox with new location
                    go.setRectHitbox();

                    //Set to has been dropped
                    go.setHasBeenDropped(true);
                }
            }

            //Teleport Boss Trophy after defeating boss
            //Check which level is on so can use a different trophy
            if(go.getType() == 'L' && !lm.isDropTheDragTrophy()){
                if(lm.getBossIsDead()){
                    go.setWorldLocationX(lm.getDropDragTrophyLocation().x);
                    go.setWorldLocationY(lm.getDropDragTrophyLocation().y);
                    go.setActive(true);
                    go.setVisible(true);
                    go.setRectHitbox();
                    lm.setDropTheDragTrophy(true);
                }
            }
            if(go.getType() == 'H' && !lm.isDropTheDragTrophy()){
                if(lm.getBossIsDead()){
                    go.setWorldLocationX(lm.getDropDragTrophyLocation().x);
                    go.setWorldLocationY(lm.getDropDragTrophyLocation().y);
                    go.setActive(true);
                    go.setVisible(true);
                    go.setRectHitbox();
                    lm.setDropTheDragTrophy(true);
                }
            }
            if(go.getType() == 'G' && !lm.isDropTheDragTrophy()){
                if(lm.getBossIsDead()){
                    go.setWorldLocationX(lm.getDropDragTrophyLocation().x);
                    go.setWorldLocationY(lm.getDropDragTrophyLocation().y);
                    go.setActive(true);
                    go.setVisible(true);
                    go.setRectHitbox();
                    lm.setDropTheDragTrophy(true);
                }
            }


            if (go.isActive()) {
                // Clip anything off-screen
                if (!vp.clipObjects(go.getWorldLocation().x, go.getWorldLocation().y, go.getWidth(), go.getHeight())) {

                    // Set visible flag to true
                    go.setVisible(true);

                    // check collisions with player
                    int hit = lm.player.checkCollisions(go.getHitbox());

                    //If there's a collison, deal with different types
                    if (hit > 0) {
                        //Since, theres a hit, don't allow player to shoot
                        lm.setPlayerHasCollided(true);

                        //If bone, make disappear by walking over it and increment bone count by 1
                        if(go.getType()=='m'){
                            lm.player.incrementBones();
                            go.setVisible(false);
                            go.setActive(false);
                        }

                        //If it is item to be picked up, add to inventory if not full
                        if(go.getType() == 'P'){
                            if(lm.player.inventory.size() < 9){
                                Item clock = lm.items.get(3);
                                lm.player.inventory.add(clock);
                                //then make item disappear
                                go.setVisible(false);
                                go.setActive(false);
                            }
                        }
                        //Nugget to pickup
                        if(go.getType() == 'N'){
                            if(lm.player.inventory.size() < 9){
                                Item clock = lm.items.get(24);
                                lm.player.inventory.add(clock);
                                //then make item disappear
                                go.setVisible(false);
                                go.setActive(false);
                            }
                        }

                        //Pickup dragon trophy
                        if(go.getType() == 'L'){
                            if(lm.player.inventory.size() < 9){
                                Item dragTrophy = lm.items.get(21);
                                lm.player.inventory.add(dragTrophy);
                                //then make item disappear
                                go.setVisible(false);
                                go.setActive(false);
                            }
                        }
                        //Pickup minotaur trophy
                        if(go.getType() == 'H'){
                            if(lm.player.inventory.size() < 9){
                                Item dragTrophy = lm.items.get(22);
                                lm.player.inventory.add(dragTrophy);
                                //then make item disappear
                                go.setVisible(false);
                                go.setActive(false);
                            }
                        }
                        //Pickup skeleton crown
                        if(go.getType() == 'G'){
                            if(lm.player.inventory.size() < 9){
                                Item dragTrophy = lm.items.get(23);
                                lm.player.inventory.add(dragTrophy);
                                //then make item disappear
                                go.setVisible(false);
                                go.setActive(false);
                            }
                        }


                        //If player interacts with shop keeper, print dialogue
                        if(go.getType()=='k'){

                            if(!questFlags[2]){
                                boolean hasClock = false;

                                //Check player's inventory for clock
                                for(int i=0; i<lm.player.inventory.size(); i++){
                                    if(lm.player.inventory.get(i).getTitle()=="Clock"){
                                        hasClock = true;
                                    }
                                }

                                if(hasClock){
                                    dialogueSequence(go.getDialoguesPart2(), 2, true);

                                    //Reward player with bones and remove clock from inventory
                                    if(questFlags[2]){
                                        for(int i=0; i<25; i++){
                                            lm.player.incrementBones();
                                        }

                                        for(int i=0; i<lm.player.inventory.size(); i++){
                                            if(lm.player.inventory.get(i).getTitle()=="Clock"){
                                                lm.player.inventory.remove(i);
                                            }
                                        }
                                    }
                                }
                                else{
                                    dialogueSequence(go.getDialogues(), 0, true);
                                }
                            }
                            else{
                                dialogueSequence(go.getDialoguesPart3(), 0, true);
                            }



                            //Old quest
                            /*
                            if(questFlags[1] && !questFlags[2]){
                                dialogueSequence(go.getDialoguesPart2(), 2, true);

                                //Give the player 5 bones for completing the quest
                                if(questFlags[2]){
                                    for(int i=0; i<5; i++){
                                        lm.player.incrementBones();
                                    }
                                }
                            }
                            //Some random banter
                            else if(questFlags[2]){
                                dialogueSequence(go.getDialoguesPart3(), 0, true);
                            }
                            else{
                                dialogueSequence(go.getDialogues(), 3, true);
                            }
                            */

                        }


                        //Quest 2. Player looks at clock tree to tell time for the shop keeper
                        //to complete the quest. Note: only the 1st level has quests, and they are
                        //not mandatory, only bonuses
                        //NOTE: This is the old quest and doesn't trigger any quest flags anymore
                        if(go.getType()=='c'){

                            /*
                            if(!questFlags[2] && questFlags[3]){
                                dialogueSequence(go.getDialogues(), 1, true);
                            }
                            else{
                                dialogueSequence(go.getDialoguesPart2(), 0, true);
                            }
                            */
                            dialogueSequence(go.getDialoguesPart2(), 0, true);
                        }

                        //Well, replenishes health
                        if(go.getType()=='w'){
                            dialogueSequence(go.getDialogues(), 9, true);
                            if(getQuestFlag(9)){
                                lm.player.setCurrHealth(lm.player.getMaxHealth());
                                setQuestFlags(9, false);
                            }
                        }

                        //If player finds The Teacher, print dialogue
                        if(go.getType()=='u'){
                            if(!questFlags[4]){
                                dialogueSequence(go.getDialogues(), 1, true);
                            }
                            else if(questFlags[4] && !lm.canHerdSheep){
                                dialogueSequence(go.getDialoguesPart2(), 7, true);

                                if(getQuestFlag(7)){
                                    //Display store, then set flag to false so player can exit
                                    //if they want
                                    lm.setDisplayStore(true);
                                    setQuestFlags(7, false);
                                    //Set the current store items
                                    setCurrentStore(go.inventory);
                                    //Set current Store owner
                                    lm.setCurrStore(go);
                                }
                            }
                            else{
                                dialogueSequence(go.getDialoguesPart3(), 1, true);
                            }
                        }


                        //Quest 3, the sheep. Again, this is just mandatory, but can help the player
                        //recieve a fair number of bones
                        if(go.getType()=='s'){

                            if(!questFlags[6] && !lm.canHerdSheep){
                                dialogueSequence(go.getDialogues(),4,true);
                            }
                            else if(!questFlags[6] && lm.canHerdSheep){
                                dialogueSequence(go.getDialoguesPart2(), 6, true);

                                //Reward player with 25 bones after he is done
                                setQuestFlags(11, true);
                                if(getQuestFlag(11) && questFlags[6]){
                                    int oldBones = lm.player.getNumBones();
                                    int newBones = oldBones+25;
                                    lm.player.setNumBones(newBones);
                                    setQuestFlags(11, false);
                                }
                            }
                            else if(questFlags[6]){
                                go.setxVelocity(3);
                            }
                        }

                        //Merchant
                        if(go.getType() == 'M'){
                            shopKeeper(go);
                        }

                        //The pink dog
                        if(go.getType()=='y'){
                            if(getQuestFlag(8)){
                                dialogueSequence(go.getDialoguesPart2(), 1, true);
                            }
                            else{
                                dialogueSequence(go.getDialogues(), 1, true);
                            }
                        }

                        //Chicken
                        if(go.getType()=='b' && lm.player.getAction()){
                            //Gives player health for eating it
                            lm.player.changeHealth(go.getDamage());
                            go.setVisible(false);
                            go.setActive(false);
                            lm.incrementChickensEaten();

                            //Give 1 exp
                            int oldExp = lm.player.getExperience();
                            int newExp = oldExp + go.getExperienceToGive();
                            lm.player.setExperience(newExp);
                        }

                        if(!lm.isAbilityOn()){
                            //Drone
                            if(go.getType()=='D'){
                                //Damage player
                                lm.player.damagePlayer(go.getDamage());

                                //Set to dead and respawn when time has elapsed
                                go.setEnemyDead(true);

                                //Set enemy's respawn location
                                go.setSpawnLocation(spawnMapLocation);
                                go.setSpawnLocation2(spawnMapLocation2);
                                go.setSpawnLocation3(spawnMapLocation3);

                            }

                            //Kilver drone
                            if(go.getType()=='K'){
                                //Damage player
                                lm.player.damagePlayer(go.getDamage());

                                //Set to dead and respawn when time has elapsed
                                go.setEnemyDead(true);

                                //Set enemy's respawn location
                                go.setSpawnLocation(spawnMapLocation);
                                go.setSpawnLocation2(spawnMapLocation2);
                                go.setSpawnLocation3(spawnMapLocation3);
                            }
                            //Mosquito drone
                            if(go.getType() == 'Q'){
                                //Damage player
                                lm.player.damagePlayer(go.getDamage());

                                //Set to dead and respawn when time has elapsed
                                go.setEnemyDead(true);

                                //Set enemy's respawn location
                                go.setSpawnLocation(spawnMapLocation);
                                go.setSpawnLocation2(spawnMapLocation2);
                                go.setSpawnLocation3(spawnMapLocation3);
                            }


                        }


                        //***Bosses are not effected by ability
                        //Dragon boss
                        if(go.getType()=='R'){
                            bossAttack(go);
                        }
                        //Minotaur boss
                        if(go.getType()=='O'){
                            bossAttack(go);
                        }
                        //Skeleton boss
                        if(go.getType()=='S'){
                            bossAttack(go);
                        }




                        //Tree that teleports player to next level
                        if(go.getType()=='a'){

                            if(lm.getBossIsDead()){

                                dialogueSequence(go.getDialogues(), 14, true);

                                if(getQuestFlag(14)){
                                    int index = mapLevels.indexOf(getCurrLevel());
                                    index++;

                                    if(mapLevels.get(index) != null && index != winningLevel){
                                        setCurrLevel(mapLevels.get(index));
                                        loadLevel(getCurrLevel().getMapName(), 10, 16);
                                    }
                                    else if (index == winningLevel){
                                        //Player has won the game
                                        lm.setWon(true);
                                    }

                                    setQuestFlags(14, false);
                                }
                            }
                            else{
                                dialogueSequence(go.getDialoguesPart2(), 1, true);
                            }
                        }

                        //Armourer
                        if(go.getType() == 'A'){
                            shopKeeper(go);
                        }
                        //Wizard
                        if(go.getType() == 'W'){
                            shopKeeper(go);
                        }
                        //Archer
                        if(go.getType() == 'B'){
                            shopKeeper(go);
                        }
                        //Warrior
                        if(go.getType()=='C'){
                            shopKeeper(go);
                        }
                        //Barmaid
                        if(go.getType()=='E'){
                            shopKeeper(go);
                        }
                        //veteran
                        if(go.getType()=='V'){
                            shopKeeper(go);
                        }

                    }


                    //Check bullet collisions
                    for (int i = 0; i < lm.player.gun.getNumBullets(); i++) {

                        lm.player.gun.update(fps);

                        //Make a hitbox out of the the current bullet
                        RectHitbox r = new RectHitbox();
                        r.setLeft(lm.player.gun.getBulletX(i));
                        r.setTop(lm.player.gun.getBulletY(i));
                        r.setRight(lm.player.gun.getBulletX(i) + lm.player.gun.getBulletWidth());
                        r.setBottom(lm.player.gun.getBulletY(i) + lm.player.gun.getBulletHeight());


                        //*****************
                        //Make a method to ask if isEnemy(), then reduce health of the enemy
                        if (go.getHitbox().intersects(r)) {
                            //collision detected
                            //make bullet disappear until it is respawned as a new bullet
                            lm.player.gun.hideBullet(i);

                            //If game object is of enemy type, then allow bullet to interact with it
                            if(go.getIsEnemy()){

                                //Player's lifesteal
                                lm.player.lifeStealTaken();

                                if(go.getType() == 'b'){
                                    go.setCurrHealth(0);
                                    go.setActive(false);
                                    go.setVisible(false);
                                }

                                //Drone enemies
                                if(go.getType() == 'D' || go.getType() == 'K' || go.getType() == 'Q'){
                                    int damageToGive = lm.player.getDamage();
                                    int prevHealth = go.getCurrHealth();
                                    int updatedHealth = prevHealth - damageToGive;
                                    go.setCurrHealth(updatedHealth);
                                }

                                //Dragon boss
                                if(go.getType() == 'R'){
                                    int damageToGive = lm.player.getDamage();
                                    int prevHealth = go.getCurrHealth();
                                    int updatedHealth = prevHealth - damageToGive;
                                    go.setCurrHealth(updatedHealth);
                                }
                                //Minotaur boss
                                if(go.getType()=='O'){
                                    int damageToGive = lm.player.getDamage();
                                    int prevHealth = go.getCurrHealth();
                                    int updatedHealth = prevHealth - damageToGive;
                                    go.setCurrHealth(updatedHealth);
                                }
                                //Skeleton boss
                                if(go.getType()=='S'){
                                    int damageToGive = lm.player.getDamage();
                                    int prevHealth = go.getCurrHealth();
                                    int updatedHealth = prevHealth - damageToGive;
                                    go.setCurrHealth(updatedHealth);
                                }


                                //If enemy has 0 or less health move object away so it can respawn******
                                if(go.getCurrHealth() <= 0){
                                    //go.setVisible(false);
                                    //go.setActive(false);

                                    //Check to see if drone will drop item
                                    if(go.getType() == 'D'){
                                        if(go.isDropItem() && !lm.isDroppedOnce()){
                                            lm.setDropTheClock(true);
                                            lm.setDropClockLocation(go.getWorldLocation());
                                            lm.setDroppedOnce(true);
                                        }
                                    }

                                    //Check to see if kilver drone will drop item
                                    if(go.getType() == 'K'){
                                        if(go.isDropItem() && !lm.isDroppedNugget()){
                                            lm.setDroppedNugget(true);
                                            lm.setDropNuggetLocation(go.getWorldLocation());
                                        }
                                    }

                                    //Rewards with bones if any
                                    int bonesToGive = go.getBonesToGive();
                                    int prevPlayerBones = lm.player.getNumBones();
                                    int updatePlayerBones = bonesToGive+prevPlayerBones;
                                    lm.player.setNumBones(updatePlayerBones);

                                    //Reward players with experience
                                    int expToGive = go.getExperienceToGive();
                                    int prevPlayerExp = lm.player.getExperience();
                                    int updatePlayerExp = prevPlayerExp + expToGive;
                                    lm.player.setExperience(updatePlayerExp);

                                    //Set to dead and respawn when time has elapsed
                                    go.setEnemyDead(true);

                                    //Set enemy's respawn location
                                    go.setSpawnLocation(spawnMapLocation);
                                    go.setSpawnLocation2(spawnMapLocation2);
                                    go.setSpawnLocation3(spawnMapLocation3);

                                    //It enemy is boss, set bossIsDead to true
                                    if(go.getIsBoss() && !lm.getBossIsDead()){

                                        Vector2Point5D temp = new Vector2Point5D();
                                        temp.x = go.getWorldLocation().x + go.getWidth()/2;
                                        temp.y = go.getWorldLocation().y + go.getHeight()/2;
                                        temp.z = go.getWorldLocation().z;

                                        lm.setDropDragTrophyLocation(temp);
                                        lm.setBossIsDead(true);
                                    }


                                }
                            }

                        }

                    }

                    }


                if (lm.isPlaying()) {
                        // Run any un-clipped updates
                        go.update(fps);

                    if (go.getType() == 'D') {// Let any near by drones know where the player is
                        Drone d = (Drone) go;
                        d.setWaypoint(lm.player.getWorldLocation());
                    }
                    //Let Dragon boss know where player is
                    else if(go.getType() == 'R'){
                        DragonBoss r = (DragonBoss) go;
                        r.setWaypoint(lm.player.getWorldLocation());
                    }
                    //Let Minotaur boss know where player is
                    else if(go.getType() == 'O'){
                        MinotaurBoss r = (MinotaurBoss) go;
                        r.setWaypoint(lm.player.getWorldLocation());
                    }
                    //Let Skeleton boss know where player is
                    else if(go.getType() == 'S'){
                        SkeletonBoss s = (SkeletonBoss) go;
                        s.setWaypoint(lm.player.getWorldLocation());
                    }
                    //Let kilver drone know
                    else if(go.getType() == 'K'){
                        KilverDrone k = (KilverDrone) go;
                        k.setWaypoint(lm.player.getWorldLocation());
                    }
                    else if(go.getType() == 'Q'){
                        MosquitoDrone M = (MosquitoDrone) go;
                        M.setWaypoint(lm.player.getWorldLocation());
                    }

                }
                } else {
                    // Set visible flag to false
                    go.setVisible(false);
                    // Now draw() can ignore them
                }




        }




        //If player is in dialogue, wait until finished before player can move
        if(lm.getDialogueActive()){
            lm.player.setxVelocity(0);
            lm.player.setyVelocity(0);
            lm.player.setPressingDown(false);
            lm.player.setPressingUp(false);
            lm.player.setPressingLeft(false);
            lm.player.setPressingRight(false);
        }

        //If player dies, reset to beginning location of level, restore health, and charge 5 bones
        if(lm.player.getCurrHealth() <= 0){

            //Record time of death and player coordinates
            if(!dogIsDown){
                timeOfDeath = System.currentTimeMillis();
                deadX = lm.player.getWorldLocation().x;
                deadY = lm.player.getWorldLocation().y;

            }

            //Add a bitmap of a fallen dog for a few seconds before restarting
            dogIsDown=true;
            lm.player.setVisible(false);

            if(timeToRespawn){
                lm.player.setWorldLocation(oldX, oldY, 0);          //resets to starting location
                lm.player.setCurrHealth(lm.player.getMaxHealth());    //restores health

                //Charges 5 bones
                int oldBones = lm.player.getNumBones();
                int newBones = oldBones - 5;
                if(newBones < 0){
                    newBones=0;
                }
                lm.player.setNumBones(newBones);

                //Reset the death and spawn flags
                dogIsDown=false;
                timeToRespawn=false;
            }


        }


        if (lm.isPlaying()) {
            //Reset the players location as the world centre of the viewport
            //if game is playing
            vp.setWorldCentre(lm.gameObjects.get(lm.playerIndex)
                            .getWorldLocation().x,
                    lm.gameObjects.get(lm.playerIndex)
                            .getWorldLocation().y);

        }
    }

    private void draw() {

        if (ourHolder.getSurface().isValid()) {
            //First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();

            // Rub out the last frame with arbitrary color
            paint.setColor(Color.argb(255, 0, 60, 0));
            canvas.drawColor(getCurrLevel().getMapColor());

            // Draw parallax backgrounds from -1 to -3
            drawBackground(0, -3);

            // Draw all the GameObjects
            Rect toScreen2d = new Rect();

            // Draw a layer at a time
            for (int layer = -1; layer <= 1; layer++) {

                for (GameObject go : lm.gameObjects) {
                    if (go.isVisible() && go.getWorldLocation().z == layer) { //Only draw if visible and this layer

                        toScreen2d.set(vp.worldToScreen
                                (go.getWorldLocation().x,
                                        go.getWorldLocation().y,
                                        go.getWidth(),
                                        go.getHeight()));

                        if (go.isAnimated()) {
                            // Get the next frame of the bitmap
                            // Rotate if necessary
                            if (go.getFacing() == 1) {
                                // Rotate
                                // We could pre-compute this during load level
                                Matrix flipper = new Matrix();
                                flipper.preScale(-1, 1);
                                Rect r = go.getRectToDraw(System.currentTimeMillis());
                                Bitmap b = Bitmap.createBitmap(lm.bitmapsArray[lm.getBitmapIndex(go.getType())],
                                        r.left,
                                        r.top,
                                        r.width(),
                                        r.height(),
                                        flipper,
                                        true);

                                canvas.drawBitmap(b,
                                        toScreen2d.left,
                                        toScreen2d.top, paint);

                            } else {

                                // draw it the regular way round
                                canvas.drawBitmap(lm.bitmapsArray[lm.getBitmapIndex(go.getType())],
                                        go.getRectToDraw(System.currentTimeMillis()),
                                        toScreen2d, paint);
                            }


                        } else { // Just draw the whole bitmap
                            canvas.drawBitmap(lm.bitmapsArray[lm.getBitmapIndex(go.getType())],
                                    toScreen2d.left,
                                    toScreen2d.top, paint);
                        }

                        //Draw the health box for each enemy
                        if(go.getIsEnemy()){

                            //Draw max health in red
                            paint.setColor(Color.argb(255, 255,0,0));

                            if(go.getCurrHealth() > 0){
                                toScreen2d.set(vp.worldToScreen
                                        (go.getWorldLocation().x,
                                                go.getWorldLocation().y + go.getHeight(),
                                                go.getWidth(),
                                                go.getWidth()/8));

                                canvas.drawRect(toScreen2d, paint);

                                //Draw curr health in green
                                paint.setColor(Color.argb(255, 0,255,0));

                                float currHealth = go.getWidth() * (float)go.getCurrHealth()/ (float)go.getMaxHealth();

                                toScreen2d.set(vp.worldToScreen
                                        (go.getWorldLocation().x,
                                                go.getWorldLocation().y + go.getHeight(),
                                                currHealth,
                                                go.getWidth()/8));

                                canvas.drawRect(toScreen2d, paint);
                            }


                        }
                    }
                }
            }

            //draw the bullets
            paint.setColor(Color.argb(255, 255, 255, 255));
            for (int i = 0; i < lm.player.gun.getNumBullets(); i++) {

                // Pass in the x and y coords as usual. Note: Height and width are on x and y
                //axis respectively (as height of bullet is horizontal)
                toScreen2d.set(vp.worldToScreen
                        (lm.player.gun.getBulletX(i),
                                lm.player.gun.getBulletY(i),
                                lm.player.gun.getBulletWidth(),
                                lm.player.gun.getBulletHeight()));


                if(lm.player.gun.getDirection(i) == 1){
                    canvas.drawBitmap(lm.fireball.getBitmap(), toScreen2d.left + lm.player.gun.getBulletWidth(),
                            toScreen2d.top - lm.fireball.getBitmap().getHeight()/2, paint);
                }
                else{
                    canvas.drawBitmap(lm.fireballFlipped.getBitmap(), toScreen2d.left - lm.player.gun.getBulletWidth(),
                            toScreen2d.top - lm.fireball.getBitmap().getHeight()/2, paint);
                }

                //DO not draw the actual bullet that uses the hitbox to deal damage
                //canvas.drawRect(toScreen2d, paint);
            }

            // Draw parallax backgrounds from layer 1 to 3
            drawBackground(4, 0);

            // Text for debugging
            if (debugging) {
                paint.setTextSize(16);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawText("fps:" + fps, 10, 60, paint);
                canvas.drawText("num objects:" + lm.gameObjects.size(), 10, 80, paint);
                canvas.drawText("num clipped:" + vp.getNumClipped(), 10, 100, paint);
                canvas.drawText("playerX:" + lm.gameObjects.get(lm.playerIndex).getWorldLocation().x,
                        vp.getScreenWidth()/2, vp.getScreenHeight()/2, paint);
                canvas.drawText("playerY:" + lm.gameObjects.get(lm.playerIndex).getWorldLocation().y,
                        vp.getScreenWidth()/2, vp.getScreenHeight()/2 + 60, paint);

                canvas.drawText("X velocity:" + lm.gameObjects.get(lm.playerIndex).getxVelocity(), 10, 180, paint);
                canvas.drawText("Y velocity:" + lm.gameObjects.get(lm.playerIndex).getyVelocity(), 10, 200, paint);

                //for reset the number of clipped objects each frame
                vp.resetNumClipped();
            }


            // Text if We have Won
            if(this.lm.hasWon()){
                paint.setTextSize(120);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawText("You're a winner!", vp.getScreenWidth() / 2, vp.getScreenHeight() / 2, paint);

                //Stop running the game
                lm.switchPlayingStatus();
            }

            //draw HUD
            //Important to draw HUD before buttons, so buttons go over HUD
            paint.setColor(Color.argb(255, 0,0,0));
            ArrayList<Rect> hudToDraw;
            hudToDraw=hud.getHUD();

            for (Rect rect : hudToDraw) {
                RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                canvas.drawRect(rf, paint);
            }

            //Draw the max health in red, then current health in green
            //Max Health
            paint.setColor(Color.argb(255, 255,0,0));
            Rect maxHealth = hud.getMaxHealth();
            RectF toDraw = new RectF(maxHealth.left, maxHealth.top, maxHealth.right, maxHealth.bottom);
            canvas.drawRect(toDraw, paint);

            //Curr Health
            //Note: must declare seperate variables 'currHealth' and 'playerMaxHealth' in order to
            //divide the pair to get the ratio. Dividing directly from the getters results in 0 all
            //the time
            paint.setColor(Color.argb(255, 0,255,0));
            float currHealth = lm.player.getCurrHealth();
            float playerMaxHealth = lm.player.getMaxHealth();
            float ratio = currHealth/playerMaxHealth;
            float updatedCurrHealth = (maxHealth.right - maxHealth.left) * ratio;
            toDraw = new RectF(maxHealth.left, maxHealth.top,
                    updatedCurrHealth + maxHealth.left, maxHealth.bottom);
            canvas.drawRect(toDraw, paint);

            //Health of player
            paint.setTextSize(30);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.argb(255, 255, 255, 255));
            //Put it above action button
            canvas.drawText(""+lm.player.getCurrHealth() + "/" + lm.player.getMaxHealth()
                    + " Health",
                    hud.getMaxHealth().right + hud.padding,
                    hud.getMaxHealth().bottom, paint);



            //Draw to next experience in grey, and current experience in white
            //To next level experience in grey
            paint.setColor(Color.argb(255, 120,120,120));
            Rect toNextLevel = hud.getNextLevel();
            toDraw = new RectF(toNextLevel.left, toNextLevel.top, toNextLevel.right, toNextLevel.bottom);
            canvas.drawRect(toDraw, paint);

            //Current experience in white
            paint.setColor(Color.argb(255, 255, 255, 255));
            float currExp = lm.player.getExperience();
            float toNextlvl = lm.player.getToNextLevel();
            ratio = currExp/toNextlvl;
            float updatedExp = (toNextLevel.right - toNextLevel.left) * ratio;
            toNextLevel = hud.getNextLevel();
            toDraw = new RectF(toNextLevel.left, toNextLevel.top, toNextLevel.left + updatedExp,
                    toNextLevel.bottom);
            canvas.drawRect(toDraw, paint);

            //Put level of player below the experience bar
            canvas.drawText("Level "+lm.player.getPlayerLevel(),
                    hud.getNextLevel().left,
                    hud.getNextLevel().bottom + hud.padding * 2, paint);
            //Put current player's experience and experience to next level below right of experience
            //bar
            String experienceRatio = "("+lm.player.getExperience() + "/" + lm.player.getToNextLevel()
                    + ") exp";
            float expWidth = paint.measureText(experienceRatio);
                    canvas.drawText(experienceRatio,
                    hud.getNextLevel().right - expWidth,
                    hud.getNextLevel().bottom + hud.padding * 2, paint);

            //draw buttons
            paint.setColor(Color.argb(0, 255, 255, 255));
            ArrayList<Rect> buttonsToDraw;
            buttonsToDraw = ic.getButtons();

            for (Rect rect : buttonsToDraw) {
                RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                canvas.drawRoundRect(rf, 15f, 15f, paint);
            }

            //Draw slider images, action image, and pause onto buttons
            paint.setColor(Color.argb(255, 255, 255, 255));
            Bitmap btnBmp = lm.sliderLeft.getBitmap();
            canvas.drawBitmap(btnBmp, ic.left.left, ic.left.top, paint);
            btnBmp = lm.sliderRight.getBitmap();
            canvas.drawBitmap(btnBmp, ic.right.left, ic.left.top, paint);
            btnBmp = lm.sliderUp.getBitmap();
            canvas.drawBitmap(btnBmp, ic.up.left, ic.up.top + hud.padding, paint);
            btnBmp = lm.sliderDown.getBitmap();
            canvas.drawBitmap(btnBmp, ic.down.left, ic.down.top, paint);
            btnBmp = lm.actionButton.getBitmap();
            canvas.drawBitmap(btnBmp, ic.action.left, ic.action.top, paint);
            btnBmp = lm.pauseButton.getBitmap();
            canvas.drawBitmap(btnBmp, ic.pause.left, ic.pause.top, paint);



            //draw paused text
            if (!this.lm.isPlaying() && !this.lm.hasWon()) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));

                paint.setTextSize(120);
                canvas.drawText("Paused", vp.getScreenWidth() / 2, vp.getScreenHeight() / 2, paint);
            }


            //Draw dialogue
            if(lm.getDialogueActive()){
                drawText(theDialogue, vp.getScreenWidth()/2, vp.getScreenHeight() - vp.getScreenHeight()/8);
            }


            //Toggle inventory button
            paint.setColor(Color.argb(120, 255, 255, 255));
            Rect toggleInventory = ac.getToggleInventory();
            RectF myToggle = new RectF(toggleInventory.left, toggleInventory.top, toggleInventory.right,
                    toggleInventory.bottom);
            canvas.drawRoundRect(myToggle, 15f, 15f, paint);

            //Draw the button's icon
            //int icon = lm.items.indexOf('c');
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawBitmap(lm.bag.getBitmap(), ac.toggleInventory.left
                    + (ac.getToggleInventory().width() - lm.bag.getBitmap().getWidth())/2,
                    ac.toggleInventory.top + (ac.toggleInventory.height() - lm.bag.getBitmap().getHeight())/2,
                    paint);

            /*
            //Draw Abilities box
            paint.setColor(Color.argb(80, 255, 255, 255));
            Rect firstAbility = ac.getFirstAbility();
            RectF myFirstAbility = new RectF(firstAbility.left, firstAbility.top, firstAbility.right,
                    firstAbility.bottom);
            canvas.drawRoundRect(myFirstAbility, 15f, 15f, paint);

            //Draw the icon (guardian angel) for ability box
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawBitmap(lm.angel.getBitmap(), firstAbility.left + firstAbility.width()/3,
                    firstAbility.top + firstAbility.height()/3,
                    paint);
                    */

            //Draw holder for abilities
            paint.setColor(Color.argb(255, 120, 120, 120));
            Rect abilityHolder = hud.getAbilityHolder();
            RectF myAbilityHolder = new RectF(abilityHolder.left, abilityHolder.top, abilityHolder.right,
                    abilityHolder.bottom);
            canvas.drawRoundRect(myAbilityHolder, 15f, 15f, paint);

            //Draw ability slots
            paint.setColor(Color.argb(80, 0, 0, 0));
            Rect abilitySlots[] = hud.getAbilitySlots();
            for(int i=0; i<abilitySlots.length; i++){
                RectF test = new RectF(abilitySlots[i].left, abilitySlots[i].top, abilitySlots[i].right,
                        abilitySlots[i].bottom);
                canvas.drawRoundRect(test, 15f, 15f, paint);
            }



            //Only allow ability to activate for certain amount of seconds
            if(!lm.isAbilityOn()){
                lastTime = System.currentTimeMillis();
                /*
                if(!lm.isAbilityOnCooldown()){
                    lastTime = System.currentTimeMillis();
                }
                else{
                    paint.setColor(Color.argb(80, 20, 20, 20));


                    long thisTime = System.currentTimeMillis();
                    long currTime = thisTime - lastTime;
                    int t = (int) currTime;

                    if(t < 20000){
                        myFirstAbility = new RectF(firstAbility.left, firstAbility.top + (firstAbility.top * t/10000)/20,
                                firstAbility.right, firstAbility.bottom);
                        canvas.drawRoundRect(myFirstAbility, 15f, 15f, paint);
                    }
                    else{
                        lm.setAbilityOnCooldown(false);
                    }


                }
                */
            }
            else{
                long thisTime = System.currentTimeMillis();
                //Add this time to the action button
                long currTime = thisTime - lastTime;

                int abilityTime = lm.getProtectionSeconds();


                if(currTime > abilityTime * 1000){
                    lm.setAbilityOn(false);
                }
                else{
                    //Draw the guardian angel
                    paint.setColor(Color.argb(255, 255, 255, 255));
                    canvas.drawBitmap(lm.angel.getBitmap(), vp.getScreenWidth()/2,
                            vp.getScreenHeight()/3,
                            paint);
                }


                int t = (int) currTime;
                int countdown = abilityTime - (t / 1000);

                /*
                if(countdown != 0){
                    paint.setTextSize(40);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setColor(Color.argb(255, 255, 255, 255));


                    canvas.drawText("" + countdown, firstAbility.exactCenterX(),
                            firstAbility.top, paint);


                    paint.setColor(Color.argb(80, 20, 20, 20));
                    myFirstAbility = new RectF(firstAbility.left, firstAbility.top,
                            firstAbility.right, firstAbility.bottom);
                    canvas.drawRoundRect(myFirstAbility, 15f, 15f, paint);


                    //Draw the guardian angel above player during ability
                    paint.setColor(Color.argb(255, 255, 255, 255));
                    canvas.drawBitmap(lm.angel.getBitmap(), vp.getScreenWidth()/2,
                            vp.getScreenHeight()/3,
                            paint);
                }
                else{
                    lm.setAbilityOnCooldown(true);
                    lastTime += 3000;               //Reset the timer so it is in sync
                }
                */

            }

            //********************
            //Display items in belt
            //*******************
            displayPlayerItemAbilitySlots();



            //*********************************
            //Draw the inventory panels
            //*********************************
            if(lm.getDisplayInventory()){
                //Main inventory page
                paint.setColor(Color.argb(255, 120, 120, 0));
                Rect invetoryPage = ac.getPage();
                RectF myInventoryPage = new RectF(invetoryPage.left, invetoryPage.top, invetoryPage.right,
                        invetoryPage.bottom);
                canvas.drawRoundRect(myInventoryPage, 15f, 15f, paint);

                //Individual cells
                paint.setColor(Color.argb(80, 255, 255, 255));
                ArrayList<Rect> panelsToDraw;
                panelsToDraw = ac.getPanel();


                for (Rect rect : panelsToDraw) {
                    RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                    canvas.drawRoundRect(rf, 15f, 15f, paint);
                }

                //Draw items of player
                paint.setColor(Color.argb(255, 0, 0, 0));
                displayPlayerItems();

                //Draw description box
                paint.setColor(Color.argb(80, 255, 255, 255));
                Rect desc = ac.getDescription();
                RectF myDesc = new RectF(desc.left, desc.top, desc.right, desc.bottom);
                canvas.drawRoundRect(myDesc, 15f, 15f, paint);


                //If it is only player inventory open and not store, then print description
                if(lm.getCurrItem() != null){
                    paint.setTextSize(30);
                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(Color.argb(255, 0, 0, 0));
                    String itemDesc = lm.getCurrItem().getDescription();
                    float textWidth = paint.measureText(itemDesc);
                    canvas.drawText(itemDesc, ac.description.left +
                                    (ac.description.width() - textWidth)/2,
                            ac.description.top + ac.description.height()*2/3, paint);
                    paint.setTextSize(40);

                    String itemTitle = lm.getCurrItem().getTitle();
                    textWidth = paint.measureText(itemTitle);
                    canvas.drawText(itemTitle, ac.description.left +
                                    (ac.description.width() - textWidth)/2,
                            ac.description.top + ac.description.height()/3, paint);
                }


                //Add item to belt button
                paint.setColor(Color.argb(255, 0, 190, 190));
                Rect addItem = ac.getAddItem();
                RectF myAddItem = new RectF(addItem.left, addItem.top, addItem.right, addItem.bottom);
                canvas.drawRoundRect(myAddItem, 15f, 15f, paint);

                if(lm.getCurrItem() != null){
                    paint.setColor(Color.argb(255, 0, 0, 0));
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(30);
                    if(lm.getCurrItem().isAbilityItem() && !lm.getStoreSelect()){

                        if(!lm.isCurrItemIsOnBelt()){
                            canvas.drawText("Add", ac.getAddItem().centerX(), ac.getAddItem().top
                                    + ac.getAddItem().height()/2, paint);
                        }
                        else{
                            canvas.drawText("Remove", ac.getAddItem().centerX(), ac.getAddItem().top
                                    + ac.getAddItem().height()/2, paint);
                        }

                    }

                }


            }

            //*********************************
            //STORE
            //Generates the store's page, 9 cells, and close button. Draws the items of the
            //current inventory
            //***************************
            if(lm.getDisplayStore()){

                //The player's inventory will always be open as long as store is open
                lm.setDisplayInventory(true);

                //The player cannot move while the store is open
                lm.player.setPressingRight(false);
                lm.player.setPressingLeft(false);
                lm.player.setPressingUp(false);
                lm.player.setPressingDown(false);


                //Main store page
                paint.setColor(Color.argb(255, 139, 69, 19));
                Rect invetoryPage = ac.getStock();
                RectF myInventoryPage = new RectF(invetoryPage.left, invetoryPage.top, invetoryPage.right,
                        invetoryPage.bottom);
                canvas.drawRoundRect(myInventoryPage, 15f, 15f, paint);

                //Individual cells
                paint.setColor(Color.argb(80, 255, 255, 255));
                ArrayList<Rect> panelsToDraw;
                panelsToDraw = ac.getStores();

                for (Rect rect : panelsToDraw) {
                    RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                    canvas.drawRoundRect(rf, 15f, 15f, paint);
                }

                //Store's "close store" button
                paint.setColor(Color.argb(120, 255, 255, 255));
                Rect closeStore = ac.getCloseStore();
                RectF myCloseStore = new RectF(closeStore.left, closeStore.top, closeStore.right,
                        closeStore.bottom);
                canvas.drawRoundRect(myCloseStore, 15f, 15f, paint);

                //Draw text for "close store" button
                paint.setTextSize(40);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 0, 0, 0));
                //Put it above action button
                canvas.drawText("Close", ac.closeStore.exactCenterX(),
                        ac.closeStore.exactCenterY(), paint);

                //Draw Items
                displayItems();

                //Draw buy/sell button
                paint.setColor(Color.argb(120, 0, 255, 0));
                Rect buysell = ac.getTransaction();
                RectF myTransaction = new RectF(buysell.left, buysell.top, buysell.right,
                        buysell.bottom);
                canvas.drawRoundRect(myTransaction, 15f, 15f, paint);






                //Print description
                if(lm.getCurrItem() != null){

                    paint.setTextSize(30);
                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(Color.argb(255, 0, 0, 0));
                    String itemDesc = lm.getCurrItem().getDescription();
                    float textWidth = paint.measureText(itemDesc);
                    canvas.drawText(itemDesc, ac.description.left +
                                    (ac.description.width() - textWidth)/2,
                            ac.description.top + ac.description.height()*2/3, paint);
                    paint.setTextSize(40);

                    String itemTitle = lm.getCurrItem().getTitle();
                    textWidth = paint.measureText(itemTitle);
                    canvas.drawText(itemTitle, ac.description.left +
                                    (ac.description.width() - textWidth)/2,
                            ac.description.top + ac.description.height()/3, paint);

                    //If item selected from store, buy
                    if(lm.getStoreSelect()){
                        textWidth=paint.measureText("Buy");
                        canvas.drawText("Buy", ac.transaction.left +
                                        (ac.transaction.width() - textWidth)/2,
                                ac.transaction.top + ac.transaction.height()/3, paint);

                        int itemCost = lm.getCurrItem().getCost();
                        String priceTag = "" + itemCost + " bones";
                        textWidth=paint.measureText(priceTag);


                        paint.setTextSize(30);
                        canvas.drawText(priceTag, ac.transaction.left
                                        + hud.padding/2,  //TextWidth here is the wider one
                                ac.transaction.top + ac.transaction.height()*2/3, paint);
                    }
                    //Otherwise, from the player's end, sell
                    else{
                        textWidth=paint.measureText("Sell");
                        canvas.drawText("Sell", ac.transaction.left +
                                        Math.abs((textWidth - ac.transaction.width())/2),
                                ac.transaction.top + ac.transaction.height()/3, paint);

                        int itemCost = lm.getCurrItem().getResell();
                        String priceTag = "(" + itemCost + " bones)";
                        textWidth=paint.measureText(priceTag);

                        paint.setTextSize(30);
                        canvas.drawText(priceTag, ac.transaction.left +
                                        (textWidth - ac.transaction.width())/2,  //TextWidth here is the wider one
                                ac.transaction.top + ac.transaction.height()*2/3, paint);
                    }


                }
            }


            /*
            //**********************************************
            //**************Special Abilities***************
            //**********************************************
            int xPos = (int) vp.getScreenWidth()/2;
            int yPos = (int) vp.getScreenHeight()/2;
            int xOffset = 500;
            int yOffset = 500;


            RectHitbox r = new RectHitbox();
            r.setLeft(vp.getViewportWorldCentreX());
            r.setRight(vp.getViewportWorldCentreX() + 50);
            r.setTop(vp.getViewportWorldCentreY());
            r.setBottom(vp.getViewportWorldCentreY() + 50);

            Rect killZone = new Rect((int)r.left + xPos, (int)r.top + yPos, (int)r.right + xPos,
                    (int)r.bottom + yPos);
            canvas.drawRect(killZone, paint);

            for(GameObject go: lm.gameObjects){
                if(go.getIsEnemy() && go.getHitbox().intersects(r)){
                    go.setCurrHealth(1);
                }
            }

            //*************************************************
            //*************************************************
            */

            //If dog is down, draw down dog
            if(dogIsDown){

                //Freeze player to last location
                lm.player.setWorldLocation(deadX, deadY, 0);

                long currTime = System.currentTimeMillis();

                paint.setColor(Color.argb(255, 255, 255, 255));
                drawDownDog(lm.player.getWorldLocation().x + vp.getScreenWidth()/2,
                        lm.player.getWorldLocation().y + vp.getScreenHeight()/2);


                //After a certain amount of time, allow respawn
                long dTime = currTime - timeOfDeath;
                if(dTime > 3000){
                    timeToRespawn = true;
                }

                //Counts the time to respawn
                int convertedTime = (int) dTime;
                int roundedTime = convertedTime/1000;
                int countdown = 3 - roundedTime;

                paint.setTextSize(60);
                String deathDesc = "You have fallen";
                String deathStatus = "" + countdown;

                float descWidth =  paint.measureText(deathDesc);
                float statusWidth = paint.measureText(deathStatus);

                paint.setTextAlign(Paint.Align.LEFT);

                canvas.drawText(deathDesc, (vp.getScreenWidth()-descWidth)/2,
                        vp.getScreenHeight()/2 - hud.padding*7, paint);
                //paint.setTextSize(40);
                canvas.drawText(deathStatus, (vp.getScreenWidth()-statusWidth)/2,
                        vp.getScreenHeight()/2 - hud.padding*3, paint);

            }


            // Text for stats
            // Number of bones player has
            paint.setTextSize(40);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.argb(255, 255, 255, 255));
            //Put it above action button
            canvas.drawText("Bones: " + lm.player.getNumBones(), ic.action.exactCenterX(),
                    ic.action.exactCenterY() - ic.action.height(), paint);
            //Damage of player
            //Put it right of movement button
            int yOffset = hud.padding*8;
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Damage: " + lm.player.getDamage()
                    ,hud.padding, hud.padding*10, paint);
            canvas.drawText("Lifesteal: " + lm.player.getLifeSteal()
                    ,hud.padding, hud.padding*10 + yOffset, paint);
            canvas.drawText("Armour: " + lm.player.getArmour()
                    ,hud.padding, hud.padding*10 + yOffset*2, paint);
            canvas.drawText("Shield: " + lm.player.getShield()
                    ,hud.padding, hud.padding*10 + yOffset*3, paint);


            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    //If player touches screen, allows the InputController, HUD, and Artifacts Controller to
    //take action
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (lm != null) {
            ic.handleInput(motionEvent, lm, sm, vp);
            //Also add HUD
            hud.handleInput(motionEvent, lm,sm,vp);
            //Also add toggle Inventory button
            ac.handleInput(motionEvent, lm, sm, vp);
        }
        //invalidate();
        return true;
    }

    // Clean up our thread if the game is interrupted or the player quits
    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("error", "failed to pause thread");
        }
    }

    // Make a new thread and start it
    // Execution moves to our run method
    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void drawBackground(int start, int stop) {
        Rect fromRect1 = new Rect();
        Rect toRect1 = new Rect();
        Rect fromRect2 = new Rect();
        Rect toRect2 = new Rect();

        for (Background bg : lm.backgrounds) {
            if (bg.z < start && bg.z > stop) {
                // Is this layer in the viewport?
                // Clip anything off-screen
                if (!vp.clipObjects(-1, bg.y, 1000, bg.height)) {
                    float floatstartY = ((vp.getyCentre() -
                            ((vp.getViewportWorldCentreY() - bg.y) *
                                    vp.getPixelsPerMetreY())));
                    int startY = (int) floatstartY;
                    float floatendY = ((vp.getyCentre() -
                            ((vp.getViewportWorldCentreY() - bg.endY) *
                                    vp.getPixelsPerMetreY())));
                    int endY = (int) floatendY;
                    // Define what portion of bitmaps to capture
                    // and what coordinates to draw them at
                    fromRect1 = new Rect(0, 0, bg.width - bg.xClip,
                            bg.height);
                    toRect1 = new Rect(bg.xClip, startY, bg.width, endY);
                    fromRect2 = new Rect(bg.width - bg.xClip, 0,
                            bg.width, bg.height);
                    toRect2 = new Rect(0, startY, bg.xClip, endY);
                }// End if (!vp.clipObjects...
                //draw backgrounds
                if (!bg.reversedFirst) {
                    canvas.drawBitmap(bg.bitmap,
                            fromRect1, toRect1, paint);
                    canvas.drawBitmap(bg.bitmapReversed,
                            fromRect2, toRect2, paint);
                } else {
                    canvas.drawBitmap(bg.bitmap,
                            fromRect2, toRect2, paint);
                    canvas.drawBitmap(bg.bitmapReversed,
                            fromRect1, toRect1, paint);
                }
                // Calculate the next value for the background's
                // clipping position by modifying xClip
                // and switching which background is drawn first,
                // if necessary.
                bg.xClip -= lm.player.getxVelocity() / (20 / bg.speed);
                if (bg.xClip >= bg.width) {
                    bg.xClip = 0;
                    bg.reversedFirst = !bg.reversedFirst;
                }
                else if (bg.xClip <= 0) {
                    bg.xClip = bg.width;
                    bg.reversedFirst = !bg.reversedFirst;
                }
            }
        }
    }

    public void setTheDialogue(String theDialogue){
        this.theDialogue=theDialogue;
    }

    //Sets dialogue and continues conversation as player taps on bottom HUD
    //After dialogue, enables a flag to be set. If no important quest changes needed
    //set i to 0
    public void dialogueSequence(ArrayList<String> dialogues, int i, boolean flag){

        //Initialize the dialogue by having the player press action button
        if (counter == 0 && lm.player.getAction() && !lm.getDialogueActive()) {
            lm.setDialogueActive(true);
            lm.setNextDialogue(true);
        }

        if(counter < dialogues.size() && lm.getDialogueActive()
                && lm.getNextDialogue()){
            setTheDialogue(dialogues.get(counter));
            counter++;
            lm.setNextDialogue(false);
        }
        else if(counter >= dialogues.size() && lm.getNextDialogue()){
            lm.setDialogueActive(false);
            lm.setNextDialogue(false);
            counter=0;

            //Set flag
            setQuestFlags(i, flag);
        }
    }


    private void drawText(String dialogue, float x, float y){

        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.argb(255, 255, 255, 0));

        canvas.drawText(dialogue, x, y, paint);

        paint.setTextSize(30);
        canvas.drawText("(Tap to continue)", x, y+60, paint);
    }

    public void setCounter(int counter){
        this.counter=counter;
    }
    public int getCounter(){
        return  counter;
    }

    public void setQuestFlags(int i, boolean flag){
        this.questFlags[i] = flag;
    }
    public boolean getQuestFlag(int i){
        return  questFlags[i];
    }

    //Set's the current store for player to interact with
    public void setCurrentStore(ArrayList<Item> currentStore){
        this.currentStore=currentStore;
    }
    public ArrayList<Item> getCurrentStore(){
        return currentStore;
    }

    //Displays items from the current store
    public void displayItems(){
        ArrayList<Rect> rt = ac.getStores();

        for(int i=0; i<getCurrentStore().size(); i++){
            Rect curr = rt.get(i);
            Bitmap bmp = getCurrentStore().get(i).getBitmap();
            canvas.drawBitmap(bmp,curr.left + (curr.width() - bmp.getWidth())/2,
                    curr.top + (curr.height() - bmp.getHeight())/2, paint);

        }
    }
    public void displayPlayerItems(){

            ArrayList<Rect> rt = ac.getPanel();

            for(int i=0; i<lm.player.inventory.size(); i++){
                Rect curr = rt.get(i);
                Bitmap bmp = lm.player.inventory.get(i).getBitmap();
                canvas.drawBitmap(bmp,curr.left + (curr.width() - bmp.getWidth())/2,
                        curr.top + (curr.height() - bmp.getHeight())/2, paint);

            }
    }
    public void displayPlayerItemAbilitySlots(){

        paint.setColor(Color.argb(255, 0, 0, 0));

        ArrayList<Rect> rt = hud.getBelt();

        for(int i=0; i<lm.player.abilitySlots.size(); i++){
            paint.setColor(Color.argb(255, 0, 0, 0));
            Rect curr = rt.get(i);
            Bitmap bmp = lm.player.abilitySlots.get(i).getBitmap();
            canvas.drawBitmap(bmp,curr.left + (curr.width() - bmp.getWidth())/2,
                    curr.top + (curr.height() - bmp.getHeight())/2, paint);


            //Draw cooldown
            if(!lm.getBeltAbilityOnCooldown(i)){
                lastBeltTIme[i] = System.currentTimeMillis();
            }
            else{
                paint.setColor(Color.argb(80, 20, 20, 20));

                long thisTime = System.currentTimeMillis();
                long currTime = thisTime - lastBeltTIme[i];
                int t = (int) currTime;

                int coolDownThreshold = lm.player.getAbilitySlots().get(i).getCoolDown();
                coolDownThreshold *= 1000;

                int offset = curr.bottom - curr.top;

                if(t < coolDownThreshold){
                    RectF coolDown = new RectF(curr.left, curr.top + offset * ((float)t/(float)coolDownThreshold),
                            curr.right, curr.bottom);
                    canvas.drawRoundRect(coolDown, 15f, 15f, paint);
                }
                else{
                    lm.setBeltAbilityOnCooldown(i, false);
                }
            }

        }
    }


    //Draws the bitmap of the downed dog
    public void drawDownDog(float x, float y){
        Bitmap bmp = lm.downDog.getBitmap();
        canvas.drawBitmap(bmp, x,
                y, paint);
    }

    //Boss needs to wait 1 second before attacking again
    public void bossAttack(GameObject go){
        if(!bossCantAttack){
            lm.player.damagePlayer(go.getDamage());
            timeOfContact = System.currentTimeMillis();
            bossCantAttack=true;
        }
        else{
            long currTime = System.currentTimeMillis();
            long dTime = currTime - timeOfContact;

            //Knocks the boss back so it doesnt pin the player down
            go.setWorldLocationX(go.getWorldLocation().x + 3 * (lm.player.getFacing()));

            if(dTime > 1000){
                bossCantAttack=false;
            }
        }
    }

    //Shop keeper to talk, then show items
    public void shopKeeper(GameObject go){
        if(!questFlags[7]){

            dialogueSequence(go.getDialogues(), 7, true);

            if(getQuestFlag(7)){
                //Display store, then set flag to false so player can exit
                //if they want
                lm.setDisplayStore(true);
                setQuestFlags(7, false);
                //Set the current store items
                setCurrentStore(go.inventory);
                //Set current Store owner
                lm.setCurrStore(go);
            }
        }
    }






}// End of PlatformView
