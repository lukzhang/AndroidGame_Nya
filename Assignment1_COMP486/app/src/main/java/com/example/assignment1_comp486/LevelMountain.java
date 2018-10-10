package com.example.assignment1_comp486;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * 3rd level of the game. Contains Drone, KelverDrone, MosquitoDrone, and Skeleton boss.
 */

public class LevelMountain extends LevelData{
    LevelMountain() {
        tiles = new ArrayList<String>();
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("p..................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("..............T.....T.....T........................m...m.................................................................");
        this.tiles.add("......G............................................................................................................");
        this.tiles.add(".............................m...........................m...................Q...........K............D...Q..........................D.");
        this.tiles.add("...................................................................m..............................Q........Q..D.......m........K.");
        this.tiles.add("...............C.....V.....E..........m...........m..l........................D......D...Q.......D.......K.........t...Q.............");
        this.tiles.add(".........................................a.............................z...........................Q....Q.......D....m.........");
        this.tiles.add("......................................m.....................................K.........Q.......D....................");
        this.tiles.add("......................m........................................m.........................Q..........K......Q....Q..................m.........i............S......................");
        this.tiles.add("...........................w............................................m..........................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");
        this.tiles.add("...................................................................................................................");

        //Scrolling parralax backgrounds
        // note that speeds less than 2 cause problems
        backgroundDataList = new ArrayList<BackgroundData>();

        //Bottom parralax
        this.backgroundDataList.add(
                new BackgroundData("valley", true, 1, 19, 26, 10, 10 ));

        //Upper parralax
        this.backgroundDataList.add(
                new BackgroundData("mountains", true, 1, -7, 7, 24, 6 ));

        //Map color
        int theColor = Color.argb(255, 120, 120, 120);
        setMapColor(theColor);

        //Set map name to the exact name of the class
        setMapName("LevelMountain");


    }
}