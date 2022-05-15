/**
 * There is a lot of functionality related with managing the "map" for the world (the look of the terrain, and whether or not it is traversable). 
 * It would make sense to group this functionality out into a separate class. 
 * The map can manage the rendering of the terrain, and allow functionality for checking whether a particular location on the map is traversable or not. 
 * It would also make sense for the map to hold the dimensions of the map / world.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;


public class Map implements Traversable {

    // Map size in assignment one, set as default map size if no file input exist
    public static final int DEFAULT_MAP_WIDTH = 6; 
	public static final int DEFAULT_MAP_HEIGHT = 4;
    private int mapWidth; 
	private int mapHeight;
    private ArrayList<StringBuilder> mapRows;
    private ArrayList<Player> playerList;
    private ArrayList<Monster> monsterList;
    private ArrayList<Item> itemList;
    private int playerPosX;
    private int playerPosY;
    private int monsterPosX;
    private int monsterPosY;
    
    // Constructor
    public Map(int Width, int Height) {
        this.setHeight(Height);
        this.setWidth(Width);
        this.mapRows = new ArrayList<StringBuilder>(mapHeight);
    }

    public Map() {}

    @Override
    // Normal ground can be traversed, others can't
    public boolean canTraverse(String type) {
        if (type == ".") {return true;}
        return false;
    }

    public void mapping () {}


    // Three different terrain
    public void normalGround() {
        System.out.print(".");
    }
    public void mountains() {
        System.out.print("#");
    }
    public void water() {
        System.out.print("~");
    }
    
    // Setter for map width
    public void setWidth (int wid) {
        this.mapWidth = wid;
    }

    // Setter for map Height
    public void setHeight (int hei) {
        this.mapHeight = hei;
    }

    // Setter for map rows
    public void setRows (String line) {
        mapRows.add(new StringBuilder(line)); // Use StringBuilder to make sure the line is mutable
    }

    // Print map function
    public void printMap (ArrayList<String> mapRows) {}

    // Unit file input info
    // Gather player location from file input
    public void gatherPlayerLoc (String line, Player player) {

        int[] locSet = new int[2];
        String[] sepLine = line.split (" ");
        int loopIndex = sepLine.length;
        while (loopIndex > 0) {

            if (loopIndex == 0) {continue;}
            int number = Integer.parseInt (sepLine[loopIndex]);
            locSet[loopIndex - 1] = number;
            loopIndex -= 1;
        }
        player.setPlayerLocation(locSet[0], locSet[1]);
        playerList.add(player);
    }

    // Gather monster Information and create a new monster
    public void makeNewMonster (String line) {

        String[] infoSet = new String[4];
        String[] sepLine = line.split(" ");
        int loopIndex = sepLine.length;
        while (loopIndex > 0) {

            if (loopIndex == 0) {continue;}
            infoSet[loopIndex - 1] = sepLine[loopIndex];
            loopIndex -= 1;
        }
        Monster monster = new Monster(infoSet);
        monsterList.add(monster);
        
    }

    // Gather Item Information and create new item
    public void makeNewItem (String line) {

        String[] infoSet = new String[3];
        String[] sepLine = line.split(" ");
        int loopIndex = sepLine.length;
        while (loopIndex > 0) {

            if (loopIndex == 0) {continue;}
            infoSet[loopIndex - 1] = sepLine[loopIndex];
            loopIndex -= 1;
        }

        switch (infoSet[2]) {
            case "+":
            case "^":
            case "@":
        }
    }

}
