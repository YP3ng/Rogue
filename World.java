
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
//TODO: This default version of Rogue map need to be merged with Map class.
//This class should control movement, battlecheck, and item interactions.
 public class World {
    
    public static final int MAP_WIDTH = 6; // Final static since the size of map and location of monster won't change
	public static final int MAP_HEIGHT = 4;
	public static final int MONSTER_X = 4;
	public static final int MONSTER_Y = 2;
    public static final Set<String> ALLOWED_MOVEMENT = Set.of ("w", "a", "s", "d", "home");
    private int playerX; // Initial location of player is (1, 1), change by user I/O
	private int playerY;

    // Default constructor
    public World () {}

    public void gameWorld (
        Player player, Monster monster, 
        Scanner movement, Battle battle, Commands commands
        ) {
        
        // Start the advanture
        boolean isEnd = false;

        // Start the game world loop
        while (!isEnd) {

            // Print out the map
            printMap (
                MAP_WIDTH, MAP_HEIGHT, 
                playerX, playerY,
                MONSTER_X, MONSTER_Y,
                nameOnMap (player.getName ().toUpperCase ()),
                nameOnMap (monster.getName ().toLowerCase ())
            );
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
            
            // Update mosnter location information

            // Update player location information
            player.movement(direction);

            // Check if player meets monster
            if (encounterCheck (playerX, playerY)) {
                battle.battleLoop(player, monster, commands);
                // if player lose, return to menu
                // if monster lose, monster removed
                // if more than one monster encounter player, battle starts one by one.
            }

            // Check if player meets items
            
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
                if (this.playerY - 1 < 0) {return true;}
                break;
            case "a":
                if (this.playerX - 1 < 0) {return true;}
                break;
            case "s":
                if (this.playerY + 1 > MAP_HEIGHT - 1) {return true;}
                break;
            case "d":
                if (this.playerX + 1 > MAP_WIDTH - 1) {return true;}
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
    private boolean encounterCheck (int playerX, int playerY) {
        if (playerX == MONSTER_X && playerY == MONSTER_Y) {return true;}
        return false;
    }
    
    // Method for users to break loop
    private boolean home (boolean isEnd) {
        System.out.println ("Returning home...\n");
        return isEnd = true;
    }

    // Decide names on the map
    private char nameOnMap (String name) {
        char firstChar = name.charAt(0);
        return firstChar;
    }
}
