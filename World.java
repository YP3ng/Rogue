
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
    private Player player; // Initial location of player is (1, 1), change by user I/O
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

        // Start the game world loop
        while (!isEnd) {

            // Print out the map
            if (fileOrDefault == "default") {
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
                
                player.movement(direction);

                // Check if player meets monster
                if (encounterCheck (player)) {
                    Battle defaultBattle = new Battle(player, monster);
                    defaultBattle.battleLoop(commands);

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
    // Input cutting for map
	private String inputForMap (Scanner movement) {
		String order = movement.nextLine ();
		String [] commandArgs = order.split (" ");
		String direction = commandArgs[0];
		return direction;
	}

    // Battle encounter check
    private boolean encounterCheck (Player player) {
        if (player.getPlayerPosX() == MONSTER_X && player.getPlayerPosY() == MONSTER_Y) {return true;}
        return false;
    }
    
    // Method for users to break loop
    private boolean home (boolean isEnd) {
        System.out.println ("Returning home...\n");
        return isEnd = true;
    }

}
