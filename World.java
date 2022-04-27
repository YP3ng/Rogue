
/**
 * This program print out the map like Rover.java, with more functionalities.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
import java.util.Set;
import java.util.Scanner;
 public class World {
    
    public static final int MAP_WIDTH = 6; // Final static since the size of map and location of monster won't change
	public static final int MAP_HEIGHT = 4;
	public static final int MONSTER_X = 4;
	public static final int MONSTER_Y = 2;
    public static final Set<String> ALLOWED_MOVEMENT = Set.of ("w", "a", "s", "d", "home");
    private int playerX; // Initial location of player is (1, 1), change by user I/O
	private int playerY;

    // Constructor
    public World () {
        this.playerX = 1; // Initialise player location (1, 1)
        this.playerY = 1;
    }

    public void gameWorld (
        World world, Player player, Monster monster, 
        Scanner movement, Battle battle, Commands commands
        ) {
        
        // Reset player location
        this.setPlayer (1, 1);
        // Start the advanture
        boolean isReachMonster = false;

        while (!isReachMonster) {
            world.printMap (
                MAP_WIDTH, MAP_HEIGHT, 
                world.playerX, world.playerY,
                MONSTER_X, MONSTER_Y,
                nameOnMap (player.getName ().toUpperCase ()),
                nameOnMap (monster.getName ().toLowerCase ())
            );
            world.prompt();
			
            // Validating input directions and player won't be out of boundary after moving
            String direction = world.inputForMap (movement);
            if (world.validateDirection (direction)) {continue;}
            if (world.validateMove (direction)) {continue;}

            // Update location information
            switch (direction) {
                case "w":
                    world.setPlayer (world.playerX, world.playerY - 1);
                    break;
                case "a":
                    world.setPlayer (world.playerX - 1, world.playerY);
                    break;
                case "s":
                    world.setPlayer (world.playerX, world.playerY + 1);
                    break;
                case "d":
                    world.setPlayer (world.playerX + 1, world.playerY);
                    break;            
                case "home":
                    isReachMonster = home (isReachMonster);
                    break;
            
            } 
            // Check if the program needs to stop
            if(isReachMonster) {break;}
            else {isReachMonster = encounterCheck (world.playerX, world.playerY);}
        }
        // Battle starts
        if (encounterCheck (world.playerX, world.playerY)) {
            battle.battleLoop (player, monster, commands);
        }
        
    }
    
    // Set player location after user's input
    private void setPlayer (int x, int y) {
        this.playerX = x;
        this.playerY = y;
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
    private boolean home (boolean isReachMonster) {
        System.out.println ("Returning home...\n");
        return true;
    }

    // Decide names on the map
    private char nameOnMap (String name) {
        char firstChar = name.charAt(0);
        return firstChar;
    }
}
