package com.example.assignment1_comp486;

/**
 * This handles all the data and events of each level. Each GameObject (especially the Player), item,
 * is stored and intialized here. Each GameObject has an index and can be instansiated when loading
 * the level depending on the level's char arrays (each char corresponds to a GameObject).
 *
 * Each item is added to an items ArrayList. The items corresponding to this ArrayList can be added
 * to the NPCs in PlatformView.
 *
 * In addition, various methods and booleans are used and tracked here. These include:
 *
 * Pausing the game
 * If player has won the overall game
 * If level's boss is alive
 * pauses the game by switchingPlayingStatus()
 * Loading the backgrounds
 * Checking if dialogueIsActive, and then going to next dialogue (if player taps on bottom HUD to
 *    indicate that they want to continue the conversation)
 * Buying/selling items by checking the reciever's inventory and ensuring adding an item won't exceed
 *    the given slots
 * Getting and Setting the current store so that the player may interact with the chosen NPC
 * Getting and Setting the current item so that the selected item may be viewed
 * Gets and Sets the level's 3 spawn points, so drone enemies can respawn to 1 of them at random
 *
 *
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;

public class LevelManager {

    private String level;
    int mapWidth;
    int mapHeight;

    Player player;
    int playerIndex;

    private boolean playing;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;
    ArrayList<Background> backgrounds;

    //******************
    //Array list for items for inventory and store
    ArrayList<Item> items;

    ArrayList<Rect> currentButtons;
    Bitmap[] bitmapsArray;

    //If we won
    private boolean won;

    //First Quest Counter. Counts number of chickens eaten
    private int chickensEaten;

    //Dialogue
    private boolean dialogueActive = false; //If dialogue is active, freeze the player
    private boolean nextDialogue = false;

    //Display Inventory
    private boolean displayInventory;
    //Display Store
    private boolean displayStore;

    //Current item
    Item currItem;
    //Curent store owner
    GameObject currStore;
    //If item is selected from store
    private boolean storeSelect;

    //Player can herd sheep
    boolean canHerdSheep;

    //Check if player is in dialogue
    private boolean playerHasCollided;

    //Items used for their bitmaps
    DownDog downDog;
    Bag bag;
    Angel angel;
    SliderLeft sliderLeft;
    SliderRight sliderRight;
    SliderUp sliderUp;
    SliderDown sliderDown;
    ActionButton actionButton;
    PauseButton pauseButton;
    Fireball fireball;
    FireballFlipped fireballFlipped;

    //Enemy spawn location
    //1st spawn point
    private float spawnX;
    private float spawnY;
    //2nd spawn point
    private float spawn2X;
    private float spawn2Y;
    //3rd spawn point
    private float spawn3X;
    private float spawn3Y;

    //Status if the boss is alive
    private boolean bossIsDead;

    private boolean abilityOn = false;

    private boolean abilityOnCooldown = false;

    private boolean dropTheClock = false;
    private Vector2Point5D dropClockLocation;
    private boolean droppedOnce = false;

    private boolean dropTheDragTrophy = false;
    private Vector2Point5D dropDragTrophyLocation;

    private boolean droppedNugget = false;
    private Vector2Point5D dropNuggetLocation;

    private boolean currItemIsOnBelt = false;

    private boolean beltAbilityOnCooldown[] = {false, false, false, false};

    private float startLocationX;
    private float startLocationY;

    //When guardian angel protects player
    private boolean guardianAngelOn = false;
    private int protectionSeconds;




    public LevelManager(Context context, int pixelsPerMetre, int screenWidth, InputController ic,
                        String level, float px, float py) {
        this.level = level;

        //The various levels to be loaded
        switch (level) {
            case "LevelFarm":
                levelData = new LevelFarm();
                break;

            case "LevelDesert":
                levelData = new LevelDesert();
                break;

            case "LevelMountain":
                levelData = new LevelMountain();
                break;

            case "LevelSnow":
                levelData = new LevelSnow();
                break;

            // We can add extra levels here

        }

        // To hold all our GameObjects
        gameObjects = new ArrayList<>();

        //To hold all our Items
        items = new ArrayList<>();

        // To hold 1 of every Bitmap
        bitmapsArray = new Bitmap[50];

        // Load all the GameObjects and Bitmaps
        loadMapData(context, pixelsPerMetre, px, py);
        loadBackgrounds(context, pixelsPerMetre, screenWidth);

        //Add each item to ArrayList
        loadItemData(context, pixelsPerMetre);

        //When starting, player has not won
        won = false;

        //Initializes number of chickens eaten
        chickensEaten = 0;

        //Bitmap for when dog is down
        downDog = new DownDog(context, pixelsPerMetre);
        //Bitmap for inventory icon
        bag = new Bag(context, pixelsPerMetre);
        //Bitmap for angel icon
        angel = new Angel(context, pixelsPerMetre);
        //Fireball for projectiles
        fireball = new Fireball(context, pixelsPerMetre);
        fireballFlipped = new FireballFlipped(context, pixelsPerMetre);
        //Buttons
        sliderLeft = new SliderLeft(context, pixelsPerMetre);
        sliderRight = new SliderRight(context, pixelsPerMetre);
        sliderUp = new SliderUp(context, pixelsPerMetre);
        sliderDown = new SliderDown(context, pixelsPerMetre);
        actionButton = new ActionButton(context, pixelsPerMetre);
        pauseButton = new PauseButton(context, pixelsPerMetre);



    }

    //Pauses/unpauses the game
    public void switchPlayingStatus() {
        playing = !playing;
    }

    //checks if the game is playing
    public boolean isPlaying() {
        return playing;
    }

    //If player has won the game
    public boolean hasWon(){
        return won;
    }


    // Each index Corresponds to a bitmap
    public Bitmap getBitmap(char blockType) {

        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;

            case '1':
                index = 1;
                break;

            case 'p':
                index = 2;
                break;

            case 't':
                index = 3;
                break;

            case 'l':
                index = 4;
                break;

            case 'a':
                index = 5;
                break;

            case 'o':
                index = 6;
                break;

            case 'c':
                index = 7;
                break;

            case 'w':
                index = 8;
                break;

            case 'h':
                index = 9;
                break;

            case 'b':
                index = 10;
                break;

            case 's':
                index = 11;
                break;

            case 'm':
                index = 12;
                break;

            case 'd':
                index = 13;
                break;

            case 'k':
                index = 14;
                break;

            case 'u':
                index = 15;
                break;

            case 'y':
                index = 16;
                break;

            case 'i':
                index = 17;
                break;

            case 'M':
                index = 18;
                break;

            case 'T':
                index = 19;
                break;

            case 'D':
                index = 20;
                break;

            case 'R':
                index = 21;
                break;

            case 'A':
                index = 22;
                break;

            case 'W':
                index = 23;
                break;

            case 'K':
                index = 24;
                break;

            case 'O':
                index = 25;
                break;

            case 'B':
                index = 26;
                break;

            case 'C':
                index = 27;
                break;

            case 'V':
                index = 28;
                break;

            case 'E':
                index = 29;
                break;

            case 'Q':
                index = 30;
                break;

            case 'S':
                index = 31;
                break;

            case 'z':
                index = 32;
                break;

            case 'U':
                index = 33;
                break;

            case 'P':
                index = 34;
                break;

            case 'L':
                index = 35;
                break;

            case 'H':
                index = 36;
                break;

            case 'G':
                index = 37;
                break;

            case 'N':
                index = 38;
                break;



            default:
                index = 0;
                break;
        }

        return bitmapsArray[index];
    }

    // This method allows each GameObject which 'knows'
    // its type to get the correct index to its Bitmap
    // in the Bitmap array.
    public int getBitmapIndex(char blockType) {

        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;

            case '1':
                index = 1;
                break;

            case 'p':
                index = 2;
                break;

            case 't':
                index = 3;
                break;

            case 'l':
                index = 4;
                break;

            case 'a':
                index = 5;
                break;

            case 'o':
                index = 6;
                break;

            case 'c':
                index = 7;
                break;

            case 'w':
                index = 8;
                break;

            case 'h':
                index = 9;
                break;

            case 'b':
                index = 10;
                break;

            case 's':
                index = 11;
                break;

            case 'm':
                index = 12;
                break;

            case 'd':
                index = 13;
                break;

            case 'k':
                index = 14;
                break;

            case 'u':
                index = 15;
                break;

            case 'y':
                index = 16;
                break;

            case 'i':
                index = 17;
                break;

            case 'M':
                index = 18;
                break;

            case 'T':
                index = 19;
                break;

            case 'D':
                index = 20;
                break;

            case 'R':
                index = 21;
                break;

            case 'A':
                index = 22;
                break;

            case 'W':
                index = 23;
                break;

            case 'K':
                index = 24;
                break;

            case 'O':
                index = 25;
                break;

            case 'B':
                index = 26;
                break;

            case 'C':
                index = 27;
                break;

            case 'V':
                index = 28;
                break;

            case 'E':
                index = 29;
                break;

            case 'Q':
                index = 30;
                break;

            case 'S':
                index = 31;
                break;

            case 'z':
                index = 32;
                break;

            case 'U':
                index = 33;
                break;

            case 'P':
                index = 34;
                break;

            case 'L':
                index = 35;
                break;

            case 'H':
                index = 36;
                break;

            case 'G':
                index = 37;
                break;

            case 'N':
                index = 38;
                break;



            default:
                index = 0;
                break;
        }

        return index;
    }

    // Load all the tiles in the map from top left to bottom right. Each tile corresponds to the
    // specific 'char'
    void loadMapData(Context context, int pixelsPerMetre, float px, float py) {

        char c;

        //Keep track of where we load our game objects
        int currentIndex = -1;

        // how wide and high is the map? Viewport needs to know
        mapHeight = levelData.tiles.size();
        mapWidth = levelData.tiles.get(0).length();

        for (int i = 0; i < levelData.tiles.size(); i++) {
            for (int j = 0; j < levelData.tiles.get(i).length(); j++) {

                c = levelData.tiles.get(i).charAt(j);
                if (c != '.') {// Don't want to load the empty spaces
                    currentIndex++;
                    switch (c) {

                        case '1':
                            // Add a tile to the gameObjects
                            gameObjects.add(new Grass(j, i, c));
                            break;

                        case 'p':// a player
                            // Add a player to the gameObjects
                            gameObjects.add(new Player
                                    (context, px, py, pixelsPerMetre));

                            // We want the index of the player
                            playerIndex = currentIndex;
                            // We want a reference to the player object
                            player = (Player) gameObjects.get(playerIndex);

                            break;

                        case 't': //Spawn point
                            // Add a grass to the gameObjects
                            gameObjects.add(new Terrain(j, i, c));

                            break;


                        case 'l':
                            //Add a landmark
                            gameObjects.add(new Landmark(j, i, c));
                            break;

                        case 'a':
                            //Add a tree
                            gameObjects.add(new Tree(j, i, c));
                            break;

                        case 'o':
                            //Add pumpkins
                            gameObjects.add(new Pumpkin(j,i,c));
                            break;

                        case 'c':
                            //Add clock tree
                            gameObjects.add(new clocktree(j,i,c));
                            break;

                        case 'w':
                            //Add well
                            gameObjects.add(new Well(j,i,c));
                            break;

                        case 'h':
                            //Add house
                            gameObjects.add(new House(j,i,c));
                            break;

                        case 'b':
                            //Add chicken
                            gameObjects.add(new Chicken(context, j,i,c, pixelsPerMetre));
                            break;

                        case 's':
                            //Add sheep
                            gameObjects.add(new Sheep(context, j,i,c, pixelsPerMetre));
                            break;

                        case 'm':
                            //Add bone
                            gameObjects.add(new Bone(j,i,c));
                            break;

                        case 'd':
                            //Add dog house
                            gameObjects.add(new Store(j,i,c));
                            break;

                        case 'k':
                            //The store's shop keeper, which the player can interact with
                            gameObjects.add(new ShopKeeper(j,i,c));
                            break;

                        case 'u':
                            //The Teacher, who let's the player buy a potion to herd the sheep
                            gameObjects.add(new Teacher(j,i,c));
                            break;

                        case 'y':
                            //Pink dog who gives you a hint to eat the chickens
                            gameObjects.add(new Pink(j,i,c));
                            break;


                        case 'i':
                            //second spawn point
                            // Add a grass to the gameObjects
                            gameObjects.add(new Terrain(j, i, c));
                            break;


                        case 'M':
                            //Merchant dog
                            gameObjects.add(new Merchant(context, j,i,c, pixelsPerMetre));
                            break;

                        case 'T':
                            //Market tent behind merchant dog
                            gameObjects.add(new Tent(j,i,c));
                            break;

                        case 'D':
                            //Drone that tracks and attacks player
                            gameObjects.add(new Drone(context, j,i,c, pixelsPerMetre));
                            break;


                        case 'R':
                            //Red dragon boss
                            gameObjects.add(new DragonBoss(context, j,i,c, pixelsPerMetre));
                            break;

                        case 'A':
                            //Armourer
                            gameObjects.add(new Armourer(j,i,c));
                            break;

                        case 'W':
                            //Wizard
                            gameObjects.add(new Wizard(j,i,c));
                            break;

                        case 'K':
                            //Kilver drone
                            gameObjects.add(new KilverDrone(context, j,i,c, pixelsPerMetre));
                            break;

                        case 'O':
                            //Minotaur boss
                            gameObjects.add(new MinotaurBoss(context, j,i,c, pixelsPerMetre));
                            break;

                        case 'B':
                            //Archer
                            gameObjects.add(new Archer(j,i,c));
                            break;

                        case 'C':
                            //Warrior
                            gameObjects.add(new Warrior(j,i,c));
                            break;

                        case 'V':
                            //Veteran
                            gameObjects.add(new Veteran(j,i,c));
                            break;

                        case 'E':
                            //Barmaid
                            gameObjects.add(new Barmaid(j,i,c));
                            break;

                        case 'Q':
                            //Mosquito drone
                            gameObjects.add(new MosquitoDrone(context, j,i,c, pixelsPerMetre));
                            break;

                        case 'S':
                            //Skeleton Boss
                            gameObjects.add(new SkeletonBoss(context, j,i,c, pixelsPerMetre));
                            break;

                        case 'z':
                            //3rd spawn point
                            gameObjects.add(new Terrain(j, i, c));
                            break;

                        case 'U':
                            //Snowman for decoration
                            gameObjects.add(new SnowMan(j,i,c));
                            break;

                        case 'P':
                            //Clock for pickup
                            gameObjects.add(new PickupClock(j,i,c));
                            break;

                        case 'L':
                            //Dragon trophy for pickup after dragon boss
                            gameObjects.add(new pickupDragonTrophy(j,i,c));
                            break;

                        case 'H':
                            //Mintaur trophy for pickup after minotaur boss
                            gameObjects.add(new pickupMinotaurTrophy(j,i,c));
                            break;

                        case 'G':
                            //Crown for pickup after skeleton king
                            gameObjects.add(new pickupCrown(j,i,c));
                            break;

                        case 'N':
                            //Nugget for pickup
                            gameObjects.add(new pickupNugget(j,i,c));
                            break;


                    }

                    // If the bitmap isn't prepared yet
                    if (bitmapsArray[getBitmapIndex(c)] == null) {
                        // Prepare it now and put it in the bitmapsArrayList
                        bitmapsArray[getBitmapIndex(c)] =
                                gameObjects.get(currentIndex).
                                        prepareBitmap(context,
                                                gameObjects.get(currentIndex).
                                                        getBitmapName(),
                                                pixelsPerMetre);

                    }
                }
            }
        }
    }


    //Loads the background from each level
    private void loadBackgrounds(Context context,
                                 int pixelsPerMetre, int screenWidth) {
        backgrounds = new ArrayList<Background>();
        //load the background data into the Background objects and
        // place them in our GameObject arraylist
        for (BackgroundData bgData : levelData.backgroundDataList) {
            backgrounds.add(new Background(context,
                    pixelsPerMetre, screenWidth, bgData));
        }
    }

    //If the player has won the game, set this to true
    public void setWon(boolean hasWon){
        won = hasWon;
    }


    //Increments chickens eaten for Quest 1
    public void incrementChickensEaten(){
        chickensEaten++;
    }


    public void setDialogueActive(boolean dialogueActive){
        this.dialogueActive=dialogueActive;
    }

    public boolean getDialogueActive(){
        return  dialogueActive;
    }

    public void setNextDialogue(boolean nextDialogue){
        this.nextDialogue=nextDialogue;
    }

    public boolean getNextDialogue(){
        return  nextDialogue;
    }

    public void setDisplayInventory(boolean displayInventory){
        this.displayInventory=displayInventory;
    }

    public boolean getDisplayInventory(){
        return displayInventory;
    }

    public void setDisplayStore(boolean displayStore){
        this.displayStore=displayStore;
    }

    public boolean getDisplayStore(){
        return displayStore;
    }

    //**********************Load items*********************************
    //ArrayList index can easily by tracked by the PlatformView so that the NPCs can recieve the right
    //items. This is called in the loadLevel() method in PlatformView when starting each level.
    public void loadItemData(Context context, int pixelsPerMetre){
        items.add(new BookGreen(context, pixelsPerMetre));
        items.add(new Clover(context, pixelsPerMetre));
        items.add(new Paw(context, pixelsPerMetre));
        items.add(new Clock(context, pixelsPerMetre));
        items.add(new Arrow(context, pixelsPerMetre));
        items.add(new Knife(context, pixelsPerMetre));
        items.add(new Enchant(context, pixelsPerMetre));
        items.add(new Shield(context, pixelsPerMetre));
        items.add(new ShieldBlood(context, pixelsPerMetre));
        items.add(new ShieldDiamond(context, pixelsPerMetre));
        items.add(new BreastPlate(context, pixelsPerMetre));
        items.add(new RingNurture(context, pixelsPerMetre));
        items.add(new RingMight(context, pixelsPerMetre));
        items.add(new ArrowFrost(context, pixelsPerMetre));
        items.add(new Bow(context, pixelsPerMetre));
        items.add(new BattleSet(context, pixelsPerMetre));
        items.add(new Sword(context, pixelsPerMetre));
        items.add(new PotionRed(context, pixelsPerMetre));
        items.add(new PotionLuck(context, pixelsPerMetre));
        items.add(new PotionRage(context, pixelsPerMetre));
        items.add(new BookFlame(context, pixelsPerMetre));
        items.add(new dragontrophy(context, pixelsPerMetre));
        items.add(new minotaurtrophy(context, pixelsPerMetre));
        items.add(new Crown(context, pixelsPerMetre));
        items.add(new Nugget(context, pixelsPerMetre));
        items.add(new YellowBook(context, pixelsPerMetre));
        items.add(new PurpleBook(context, pixelsPerMetre));
        items.add(new HeartBook(context, pixelsPerMetre));
        items.add(new BrownBook(context, pixelsPerMetre));
        items.add(new GreyBook(context, pixelsPerMetre));
        items.add(new RedBook(context, pixelsPerMetre));
        items.add(new SkullBook(context, pixelsPerMetre));

        //Perhaps can add accordding to index and refer to integer as name (eg. ITEM_CLOVER = 2)
    }


    //Set current store owner
    public void setCurrStore(GameObject currStore){
        this.currStore=currStore;
    }
    //Transaction
    public void setCurrItem(int i){
        if(currStore.inventory.size() >= i+1){
            currItem = currStore.inventory.get(i);
        }
    }
    //Player curr item
    public void setPlayerCurrItem(int i){
        if(player.inventory.size() >= i+1){
            currItem = player.inventory.get(i);
        }
    }

    public void setCurrItemToNull(){
        currItem=null;
    }

    public Item getCurrItem(){
        return currItem;
    }

    //Buys an item by checking the player's inventory size and that adding an item doesn't exceed
    //the given player's slot size
    public void buyItem(){

        if(player.inventory.size() < 9 && currItem.getCost() <= player.getNumBones()){
            currStore.inventory.remove(currItem);
            player.inventory.add(currItem);
            int prev = player.getNumBones();
            int updated = prev - currItem.getCost();
            player.setNumBones(updated);

            currItem=null;
        }
    }

    //Sells an item to the NPC by checking the NPC's inventory size and that adding 1 to it won't
    //exceed the slot size
    public void sellItem(){
        if(currStore.inventory.size() < 9){
            player.inventory.remove(currItem);
            currStore.inventory.add(currItem);
            int prev = player.getNumBones();
            int updated = prev + currItem.getResell();
            player.setNumBones(updated);

            currItem=null;
        }
    }

    //Adds item to slot of player for ability
    public void addAbilityItemToSlot(){
        if(player.abilitySlots.size() < 4){
            player.inventory.remove(currItem);
            player.abilitySlots.add(currItem);

            //Activate ability
            //currItem.abilityBelt(this);

            currItem=null;
        }
    }

    //Remove item from belt to inventory
    public void removeAbilityItemFromSlot(){
        if(player.inventory.size() < 9){
            //***Need to define what the currItem is when selecting item and not activating ability
            player.abilitySlots.remove(currItem);
            player.inventory.add(currItem);

            //Deactivate ability
            //currItem.deactivateAbilityBelt(this);

            setCurrItemIsOnBelt(false);

            currItem=null;
        }
    }

    //Ability: Makes the player invisible for a period of time
    public void setAbilityOn(boolean abilityOn) {

        this.abilityOn = abilityOn;
    }

    public boolean isAbilityOn() {
        return abilityOn;
    }




    public void setStoreSelect(boolean storeSelect){
        this.storeSelect=storeSelect;
    }

    public boolean getStoreSelect(){
        return storeSelect;
    }

    public void setPlayerHasCollided(boolean playerHasCollided){
        this.playerHasCollided=playerHasCollided;
    }

    public boolean getPlayerHasCollided(){
        return playerHasCollided;
    }

    public void setSpawnX(float spawnX){
        this.spawnX=spawnX;
    }

    public float getSpawnX(){
        return spawnX;
    }

    public void setSpawnY(float spawnY){
        this.spawnY = spawnY;
    }

    public float getSpawnY(){
        return spawnY;
    }

    public void setSpawn2X(float spawnX){
        this.spawnX=spawnX;
    }

    public float getSpawn2X(){
        return spawnX;
    }

    public void setSpawn2Y(float spawnY){
        this.spawnY = spawnY;
    }

    public float getSpawn2Y(){
        return spawnY;
    }

    public boolean getBossIsDead() {
        return bossIsDead;
    }

    public void setBossIsDead(boolean bossIsDead) {
        this.bossIsDead = bossIsDead;
    }

    public void setSpawn3X(float spawnX){
        this.spawnX=spawnX;
    }

    public float getSpawn3X(){
        return spawnX;
    }

    public void setSpawn3Y(float spawnY){
        this.spawnY = spawnY;
    }

    public float getSpawn3Y(){
        return spawnY;
    }

    public boolean isAbilityOnCooldown() {
        return abilityOnCooldown;
    }

    public void setAbilityOnCooldown(boolean abilityOnCooldown) {
        this.abilityOnCooldown = abilityOnCooldown;
    }

    public boolean isDropTheClock() {
        return dropTheClock;
    }

    public void setDropTheClock(boolean dropTheClock) {
        this.dropTheClock = dropTheClock;
    }

    public Vector2Point5D getDropClockLocation() {
        return dropClockLocation;
    }

    public void setDropClockLocation(Vector2Point5D dropClockLocation) {
        this.dropClockLocation = dropClockLocation;
    }

    public boolean isDroppedOnce() {
        return droppedOnce;
    }

    public void setDroppedOnce(boolean droppedOnce) {
        this.droppedOnce = droppedOnce;
    }

    public boolean isDropTheDragTrophy() {
        return dropTheDragTrophy;
    }

    public void setDropTheDragTrophy(boolean dropTheDragTrophy) {
        this.dropTheDragTrophy = dropTheDragTrophy;
    }

    public Vector2Point5D getDropDragTrophyLocation() {
        return dropDragTrophyLocation;
    }

    public void setDropDragTrophyLocation(Vector2Point5D dropDragTrophyLocation) {
        this.dropDragTrophyLocation = dropDragTrophyLocation;
    }

    public boolean isDroppedNugget() {
        return droppedNugget;
    }

    public void setDroppedNugget(boolean droppedNugget) {
        this.droppedNugget = droppedNugget;
    }

    public Vector2Point5D getDropNuggetLocation() {
        return dropNuggetLocation;
    }

    public void setDropNuggetLocation(Vector2Point5D dropNuggetLocation) {
        this.dropNuggetLocation = dropNuggetLocation;
    }

    public boolean isCurrItemIsOnBelt() {
        return currItemIsOnBelt;
    }

    public void setCurrItemIsOnBelt(boolean currItemIsOnBelt) {
        this.currItemIsOnBelt = currItemIsOnBelt;
    }

    public void setBeltAbilityOnCooldown(int i, boolean onCoolDown){
        beltAbilityOnCooldown[i] = onCoolDown;
    }

    public boolean getBeltAbilityOnCooldown(int i){
        return beltAbilityOnCooldown[i];
    }

    public float getStartLocationX() {
        return startLocationX;
    }

    public void setStartLocationX(float startLocationX) {
        this.startLocationX = startLocationX;
    }

    public float getStartLocationY() {
        return startLocationY;
    }

    public void setStartLocationY(float startLocationY) {
        this.startLocationY = startLocationY;
    }

    public boolean isGuardianAngelOn() {
        return guardianAngelOn;
    }

    public void setGuardianAngelOn(boolean guardianAngelOn) {
        this.guardianAngelOn = guardianAngelOn;
    }

    public int getProtectionSeconds() {
        return protectionSeconds;
    }

    public void setProtectionSeconds(int protectionSeconds) {
        this.protectionSeconds = protectionSeconds;
    }
}
