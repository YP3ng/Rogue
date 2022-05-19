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
    public static final int DEFAULT_MAP_WIDTH = 6; // Final static since the size of map and location of monster won't change
	public static final int DEFAULT_MAP_HEIGHT = 4;
	public static final int DEFAULT_MONSTER_X = 4;
	public static final int DEFAULT_MONSTER_Y = 2;
    public static final int DEFAULT_PLAYER_X = 1;
    public static final int DEFAULT_PLAYER_Y = 1;
    private int mapWidth; 
	private int mapHeight;
    private ArrayList<StringBuilder> mapRows;
    private ArrayList<Player> playerList;
    private ArrayList<Monster> monsterList;
    private ArrayList<Item> itemList;
    
    // Constructor
    public Map(int Width, int Height) {
        this.setHeight(Height);
        this.setWidth(Width);
        this.mapRows = new ArrayList<StringBuilder>(mapHeight);
        this.playerList = new ArrayList<Player> (1);
        this.monsterList = new ArrayList<Monster>(4);
        this.itemList = new ArrayList<Item>(3);
    }

    // Default constructor 
    public Map(Player player, Monster monster) {
        this.mapWidth = DEFAULT_MAP_WIDTH;
        this.mapHeight = DEFAULT_MAP_HEIGHT;
        this.mapRows = new ArrayList<StringBuilder>(mapHeight);
        player.setPlayerLocation(DEFAULT_PLAYER_X, DEFAULT_PLAYER_Y);
        monster.setMonsterLocation(DEFAULT_MONSTER_X, DEFAULT_MONSTER_Y);
        StringBuilder line = new StringBuilder("......");
        for (int i = 0; i < DEFAULT_MAP_HEIGHT; i++) {
            this.mapRows.add(line);
        }
    }

    @Override
    // Normal ground can be traversed, others can't
    public boolean canTraverse(int x, int y, String direction) {

        char type = extracTerrain(x, y, direction);
        if (type == '#') {return false;}
        if (type == '~') {return false;}
        return true;

    }

    private char extracTerrain (int x, int y, String direction) {

        StringBuilder row;
        char type;

        if (direction.equals("left")) {
            // left
            row = mapRows.get(y);
            type = row.charAt(x - 1);
            return type;

        } else if (direction.equals("right")) {
            // right
            row = mapRows.get(y);
            type = row.charAt(x + 1);
            return type;

        } else if (direction.equals("up")) {
            // up
            row = mapRows.get(y - 1);
            type = row.charAt(x);
            return type;

        } else {
            // down
            row = mapRows.get(y + 1);
            type = row.charAt(x);
            return type;
        }
        
    }
    
    // Setter for map width
    public void setWidth (int wid) {
        this.mapWidth = wid;
    }
    // Getter for map width
    public int getWidth () {return this.mapWidth;}
    
    // Setter for map Height
    public void setHeight (int hei) {
        this.mapHeight = hei;
    }
    // Getter for map Height
    public int getHeight () {return this.mapHeight;}

    // Setter for map rows
    public void setRows (String line) {
        mapRows.add(new StringBuilder(line)); // Use StringBuilder to make sure the line is mutable
    }

    // Playerlist getter
    public ArrayList<Player> getPlayerList () {return this.playerList;}
    // Monsterlist getter
    public ArrayList<Monster> getMonsterList () {return this.monsterList;}
    // Itemlist getter
    public ArrayList<Item> getItemList () {return this.itemList;}

    // Entity remover
    public void removeEntity (ArrayList<Entity> e) {
        for (Entity ele : e) {

            if (ele instanceof Monster) {
                monsterList.remove(ele);
            } else if (ele instanceof Item) {
                itemList.remove(ele);
            }
        }
        
    }


    // Update entities' location and then print out, row by row
    public void fileMapping () {

        int index = 0;
        // Print rows from maprRows
        // Player > monster > item, if their locations are the same
        for (StringBuilder row : mapRows) {

            // Check item location and update if needed
            StringBuilder selectRow = this.updateRow(index, row);

            System.out.println(selectRow);
            index += 1;
        }
        System.out.println();
    }

    // Update
    public void defaultMapping (Player player, Monster monster) {

        for (int i = 0; i < DEFAULT_MAP_HEIGHT; i++) {
            for (int j = 0; j < DEFAULT_MAP_WIDTH; j++) {
                if (player.getPlayerPosX() == j && player.getPlayerPosY() == i) { 		  
                    System.out.printf ("%c", player.getNameChar());	  
                } else if (monster.getMonsterPosX() == j && monster.getMonsterPosY() == i) {
                    System.out.printf ("%c", monster.getNameChar());
                } else {
                    System.out.print (".");
                }
            }
            System.out.println ();
        }
        System.out.println ();
    
    }


    // Unit file input info
    // Gather player location from file input
    public void gatherPlayerLoc (String line, Player player) {

        String[] playerData = this.extractFileData(line, 3);
        player.setPlayerLocation(Integer.parseInt(playerData[1]), Integer.parseInt(playerData[2]));
        playerList.add(player);
    }

    // Gather monster Information and create a new monster
    public void makeNewMonster (String line) {

        String[] monsterData = this.extractFileData(line, 6);
        Monster monster = new Monster(monsterData);
        monsterList.add(monster);
        
    }

    // Gather Item Information and create new item
    public void makeNewItem (String line) {

        String[] itemData = extractFileData(line, 4);

        switch (itemData[3]) {
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
        for (int i = 0; i < sepLine.length; i++) {
            infoSet[i] = sepLine[i];
        }
        return infoSet;
    }

    // Update mapRows based on item location
    private StringBuilder updateRow (int index, StringBuilder row) {
        
        // loop through item list
        for (Item item : itemList) {

            // Update row if item on that line
            if (index == item.getItemPosY()) {
                row.setCharAt(item.getItemPosX(), item.getItemName());
            }
        }
        // loop through monster list
        for (Monster monster : monsterList) {
            // Update row if monster on that line
            if (index == monster.getMonsterPosY()) {
                row.setCharAt(monster.getMonsterPosX(), monster.getNameChar());
            }
        }
        // loop through player list
        for (Player player : playerList) {
            // Update row if player on that line
            if (index == player.getPlayerPosY()) {
                row.setCharAt(player.getPlayerPosX(), player.getNameChar());
            }
        }

        return row;
    }

    // Reset player's previous location
    public void resetRow (int x, int y) {

            StringBuilder selectRow = mapRows.get(y);
            selectRow.setCharAt(x, '.');
            mapRows.set(y, selectRow);
        
    }

}
