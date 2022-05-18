
/**
 * The overall class for each  Holds map data and entities of the  
 * It would make sense for this class to manages interactions between entities (such as triggering battles, 
 * or detecting the pickup of items by the player). 
 * Manages the overall rendering of the world, perhaps delegating to some rendering to other classes.


 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
import java.util.Set;
import java.util.Scanner;
import java.util.ArrayList;
//This class should control movement, battlecheck, and item interactions.
 public class World {
    
    public static final int MAP_WIDTH = 6; // Final static since the size of map and location of monster won't change
	public static final int MAP_HEIGHT = 4;
	public static final int MONSTER_X = 4;
	public static final int MONSTER_Y = 2;
    public static final Set<String> ALLOWED_MOVEMENT = Set.of ("w", "a", "s", "d", "home");
    private Player player;
	private Monster monster;

    private ArrayList<Player> playerList;
    private ArrayList<Monster> monsterList;
    private ArrayList<Item> itemList;
    private Map map;

    // Default constructor
    public World (Player player, Monster monster, Map map) {
        this.map = map;
        this.player = player;
        this.monster = monster;
    }

    // Fileinput constructor
    public World (Map map) {
        this.map = map;
        this.playerList = map.getPlayerList();
        this.monsterList = map.getMonsterList();
        this.itemList = map.getItemList();
    }

    // Dummy constructor
    public World () {}

    // TODO: need to add filemapping process
    public void gameWorld (Scanner movement, Commands commands, String fileOrDefault) {
        
        // Start the advanture
        boolean isEnd = false;
        if (fileOrDefault == "default") {
        // Start the game world loop
            while (!isEnd) {

                // Print out default map
                map.defaultMapping(player, monster);
                prompt();
                
                // Validating input directions and player won't be out of boundary after moving
                String direction = inputForMap (movement);
                if (validateDirection (direction)) {continue;}
                if (validateMove (direction)) {continue;}

                // If home is typed, return to menu
                if (direction == "home") {
                    home (isEnd);
                    break;
                }
                
                // Player move
                player.movement(direction);

                // Check if player meets monster
                if (encounterCheck (player, monster)) {
                    Battle defaultBattle = new Battle(player, monster);
                    defaultBattle.battleLoop(commands);

                }
            }
            
        } else if (fileOrDefault == "file") {

            while (!isEnd) {

                // Print out file input map
                map.fileMapping();
                prompt();

                // Validating input directions and player won't be out of boundary after moving
                String direction = inputForMap (movement);
                if (validateDirection (direction)) {continue;}
                if (filevalidateMove (direction)) {continue;}

                // If home is typed, return to menu
                if (direction == "home") {
                    home (isEnd);
                    break;
                }

                // Monster move
                for (Monster monster : monsterList) {
                    monster.movement(map, playerList.get(0));
                }

                // Player move
                for (Player player : playerList) {
                    player.movement(direction, map);
                }
                
                // Battle occurs before pick up item
                for (Monster mon : monsterList) {
                    if (encounterCheck(playerList.get(0), mon)) {
                        Battle battle = new Battle(playerList.get(0), mon);
                        // if player wins, continue. Lost monster removed
                        // if player lose, return to menu
                        String result = battle.battleLoop(commands);
                        if (result == "monster") {
                            home(isEnd);
                            break;
                        }

                        if (result == "player") {
                            map.removeMonster(mon);
                            this.monsterList.remove(mon);
                        }
                        
                    };
                }

                // Check if items need to be picked
                for (Item ite :itemList) {
                    if(itemPickCheck(playerList.get(0), ite)) {
                        String afterEffect = ite.effect(playerList.get(0));
                        map.removeItem(ite);
                        this.itemList.remove(ite);
                        if (afterEffect == "warp") {
                            home(isEnd);
                            break;
                        }

                    }
                }
            }
        }
    }

    // Reminder for user to type
    private void prompt () {
        System.out.print("> ");
    }
    // Check if the direction is allowed
    private boolean validateDirection (String input) {
        if (!ALLOWED_MOVEMENT.contains (input)) {
            System.out.println ("You type something wrong, please try again!!");
            return true;
        }
        return false;
    }
    // Check if player is still inside the (6,4) map after moving
    private boolean validateMove (String input) {
        switch (input) {
            case "w":
                if (this.player.getPlayerPosY() - 1 < 0) {return true;}
                break;
            case "a":
                if (this.player.getPlayerPosX() - 1 < 0) {return true;}
                break;
            case "s":
                if (this.player.getPlayerPosY() + 1 > map.getHeight() - 1) {return true;}
                break;
            case "d":
                if (this.player.getPlayerPosX() + 1 > map.getWidth() - 1) {return true;}
                break;
            case "home": break;
        }
        return false;
    }

    // Check if player is still inside the custimized map after moving
    private boolean filevalidateMove (String input) {
        switch (input) {
            case "w":
                if (playerList.get(0).getPlayerPosY() - 1 < 0) {return true;}
                break;
            case "a":
                if (playerList.get(0).getPlayerPosX() - 1 < 0) {return true;}
                break;
            case "s":
                if (playerList.get(0).getPlayerPosY() + 1 > map.getHeight() - 1) {return true;}
                break;
            case "d":
                if (playerList.get(0).getPlayerPosX() + 1 > map.getWidth() - 1) {return true;}
                break;
            case "home": break;
        }
        return false;
    }
    // Input cutting for map
	private String inputForMap (Scanner movement) {
		String order = movement.nextLine ();
		String [] commandArgs = order.split (" ");
		String direction = commandArgs[0];
		return direction;
	}

    // Battle encounter check
    private boolean encounterCheck (Player player, Monster monster) {
        if (player.getPlayerPosX() == monster.getMonsterPosX() && player.getPlayerPosY() == monster.getMonsterPosY()) {return true;}
        return false;
    }
    
    // Method for users to break loop
    private boolean home (boolean isEnd) {
        System.out.println ("Returning home...\n");
        return isEnd = true;
    }

    // Item location check
    private boolean itemPickCheck (Player player, Item item) {
        if (player.getPlayerPosX() == item.getItemPosX() && player.getPlayerPosY() == item.getItemPosY()) {return true;}
        return false;
    }
}
