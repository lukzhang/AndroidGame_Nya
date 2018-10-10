package com.example.assignment1_comp486;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Contains the data for each level by containing the tiles, the background, the level number,
 * the ground color, and the name of the map. The getMapName() method is used so that PlatformView
 * can load the map by taking the String from it.
 *
 */

public class LevelData {
    ArrayList<String> tiles;

    ArrayList<BackgroundData> backgroundDataList;

    //The level number of the map
    private int levelNumber;

    //Ground color
    private int mapColor = Color.argb(255, 0, 0, 0);

    //Map name (name of the class so it can be used by the loadLevel() method in PlatformView)
    private String mapName;

    // Tile types
    // . = no tile
    // 1 = Grass
    // 2 = Player
    // 3 = Terrain
    // 4 = Landmark
    // 5 = tree
    // 6 = pumpkin patch
    // 7 = clock tree
    // 8 = house
    // 9 = chicken
    // 10 = sheep

    public void setLevelNumber(int levelNumber){
        this.levelNumber= levelNumber;
    }

    public int getLevelNumber(){
        return levelNumber;
    }

    public void setMapColor(int mapColor){
        this.mapColor=mapColor;
    }

    public int getMapColor(){
        return mapColor;
    }

    public void setMapName(String mapName){
        this.mapName = mapName;
    }

    //Should be exactly the same as the class of the map (i.e. LevelFarm should be "LevelFarm")
    public String getMapName(){
        return mapName;
    }



}
