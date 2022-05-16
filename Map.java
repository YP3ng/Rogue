/**
 * There is a lot of functionality related with managing the "map" for the world (the look of the terrain, and whether or not it is traversable). 
 * It would make sense to group this functionality out into a separate class. 
 * The map can manage the rendering of the terrain, and allow functionality for checking whether a particular location on the map is traversable or not. 
 * It would also make sense for the map to hold the dimensions of the map / world.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */

import java.util.ArrayList;
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

    // for default map 
    public Map() {}

    @Override
    // Normal ground can be traversed, others can't
    public boolean canTraverse(String type) {
        if (type == ".") {return true;}
        return false;
    }

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

    // Update entities' location and then print out, row by row
    public void fileMapping () {

        int index = 0;
        // Print rows from maprRows
        // Player > monster > item, if their locations are the same
        for (StringBuilder row : mapRows) {

            // Check item location and update if needed
            this.updateItemRow(index, row);
            // Check monster location
            this.updateMonsterRow(index, row);
            // Check player location 
            this.updatePlayerRow(index, row);   

            System.out.println(row);
            index += 1;
        }
    }

    // Update
    public void defaultMappint () {

        

    }


    // Unit file input info
    // Gather player location from file input
    public void gatherPlayerLoc (String line, Player player) {

        String[] playerData = this.extractFileData(line, 2);
        player.setPlayerLocation(Integer.parseInt(playerData[0]), Integer.parseInt(playerData[1]));
        playerList.add(player);
    }

    // Gather monster Information and create a new monster
    public void makeNewMonster (String line) {

        String[] monsterData = this.extractFileData(line, 4);
        Monster monster = new Monster(monsterData);
        monsterList.add(monster);
        
    }

    // Gather Item Information and create new item
    public void makeNewItem (String line) {

        String[] itemData = extractFileData(line, 3);

        switch (itemData[2]) {
            case "+":
                HealingItem healItem = new HealingItem("+", itemData);
                itemList.add(healItem);
                break;
            case "^":
                DamagePerk damageItem = new DamagePerk("^", itemData);
                itemList.add(damageItem);
                break;
            case "@":
                WarpStone stoneItem = new WarpStone("@", itemData);
                itemList.add(stoneItem);
                break;
        }
    }

    private String[] extractFileData (String line, int size) {

        String[] infoSet = new String[size];
        String[] sepLine = line.split(" ");
        int loopIndex = 0;
        for (String ele : sepLine) {

            if (loopIndex == 0) {continue;} // Don't need first word
            infoSet[loopIndex] = ele;
            loopIndex += 1;
        }
        return infoSet;
    }

    // Update mapRows based on item location
    private void updateItemRow (int index, StringBuilder row) {

        // loop through item list
        for (Item item : itemList) {
            // Update row if item on that line
            if (index == item.getItemPosY()) {
                row.setCharAt(item.getItemPosX(), item.getItemName());
                mapRows.set(index, row);
            }

        }
    }

    // Update mapRows based on monster location
    private void updateMonsterRow (int index, StringBuilder row) {

        // loop through monster list
        for (Monster monster : monsterList) {
            // Update row if monster on that line
            if (index == monster.getMonsterPosY()) {
                row.setCharAt(monster.getMonsterPosX(), monster.getNameChar());
                mapRows.set(index, row);
            }

        }
    }

    // Update mapRows based on player location
    private void updatePlayerRow (int index, StringBuilder row) {

        // loop through player list
        for (Player player : playerList) {
            // Update row if player on that line
            if (index == player.getPlayerPosY()) {
                row.setCharAt(player.getPlayerPosX(), player.getNameChar());
                mapRows.set(index, row);
            }

        }
    }

    // Print map function
    private void printMap (
        int mapWidth, int mapHeight, 
        int playerX, int playerY, 
        int monsterX, int monsterY,
        char pFirstChar, char mFirstChar
    ) {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if (playerX == j && playerY == i) { 		  
                    System.out.printf ("%c", pFirstChar);	  
                } else if (monsterX == j && monsterY == i) {
                    System.out.printf ("%c", mFirstChar);
                } else {
                    System.out.print (".");
                }
            }
            System.out.println ();
        }
        System.out.println ();
        
    }
}
