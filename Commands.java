

/**
 * This class is for generally used methods and commands.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
import java.util.Scanner;
import java.util.Set;

public class Commands {
    public static final Set<String> ALLOWED_COMMANDS = Set.of ("help", "player", "monster", "start", "exit", "commands");
    // Show users what commands are allowed
    public void commands () {
        System.out.println ("help");
        System.out.println ("player");
        System.out.println ("monster");
        System.out.println ("start");
        System.out.println ("exit");
        System.out.println ();
    }

    // Show users what to do next
    public void help () {
        System.out.println ("Type 'commands' to list all available commands");
        System.out.println ("Type 'start' to start a new game");
        System.out.println ("Create a character, battle monsters, and find treasure!");
        System.out.println ();
    }

    // Set up player name
    public void player (Player player, Scanner scan) {
        System.out.println ("What is your character's name?");

        // Configure player name
        String input = scan.nextLine ();
        // Validate input. If invalid, redo the process
        if (validateInputText (input)) {player (player, scan);}
        // Set player name
        if (player.getName() == null) {
            player.setName (cleanInputText (input));
            System.out.printf ("Player '%s' created.\n\n", player.getName());
        }

    }

    // Set up monster infomation
    // If users' input invalid for any reason, users need to redo the monster creation process
    public void monster (Monster monster, Scanner scan) {

        // Loop for input validation
        boolean isDone = false;
        while (!isDone) {
            // Configure name
            System.out.print ("Monster name: ");
            String input = scan.nextLine();
            if (validateInputText (input)) {continue;};
            // Set monster name
            monster.setName (cleanInputText (input));

            // Configure health
            System.out.print ("Monster health: ");
            input = scan.nextLine ();
            if (validateInputInt(input)) {continue;};
            // Set monster health
            monster.setMaxHealth (cleanInputInt(input));
            monster.setCurHealth (cleanInputInt(input));

            // Configure damage
            System.out.print ("Monster damage: ");
            input = scan.nextLine();
            if (validateInputInt (input)) {continue;}
            // Set monster name
            monster.setDamage (cleanInputInt (input));

            if (monster.getName() != null) {
                System.out.printf ("Monster '%s' created.\n\n", monster.getName ());
                isDone = true;
            }
        }
    }

    // Start the game
    public void start (
        World world, Player player, Monster monster, 
        Scanner scan, Battle battle, Commands commands
        ) {
        // Check if player and monster are set up
        if (player.getName () == null) {
            System.out.println("No player found, please create a player with 'player' first.\n");
            return;
        }
        if (monster.getName () == null) {
            System.out.println("No monster found, please create a monster with 'monster' first.\n");
            return;
        }
        // Heal both the player and monster to full health if a new game is triggered
        if (
            player.getCurHealth () != player.getMaxHealth () || 
            monster.getCurHealth () != monster.getMaxHealth ()
        ) {
            player.setCurHealth (player.getMaxHealth ());
            monster.setCurHealth (monster.getMaxHealth ());
        }
        world.gameWorld (world, player, monster, scan, battle, commands);
    }

    // Close the program
    public void exitProgram () {
        System.out.println ("Thank you for playing Rogue!");
        System.exit(0);
    }
    
    // Show users how to go back to menu
    public boolean returnToMenu (Scanner scan) {
        System.out.println ("(Press enter key to return to main menu)");
        scan.nextLine ();
        return true;
    }

    // Return right player info
	public String returnP (Player player) {
		if (player.getName () == null) {return "[None]";} // Return None if player isn't created
		else {
			return player.getName () + " " + 
			// Current health / Max health
			player.getCurHealth () + "/" + player.getMaxHealth ();
		}

	}
	// Return right monster info
	public String returnM (Monster monster) {
		if (monster.getName () == null) {return "[None]";} // Return None if monster isn't created
		else {
			return monster.getName () + " " + 
			// Current health / Max health
			monster.getCurHealth () + "/" + monster.getMaxHealth ();
		}
	}

	// Concat infomation from both player and monster
	public void displayPlayerMonster (String pInfo, String mInfo) {
		System.out.printf ("Player: %s", pInfo);
		System.out.print ("  | ");
		System.out.printf ("Monster: %s\n", mInfo);
	}

    /**
     * The following methods are helpers for commands
     * 
     */

    // Validates users' inputs
	private boolean validateInputText (String input) {
		String[] commandArgs = input.split (" ");
		if (commandArgs.length != 1) {
			System.out.println ("Wrong input size, please only type one word\n");
			return true;
		}
		return false;
	}

    // Validates users' inputs
	private boolean validateInputInt (String input) {
		String[] commandArgs = input.split (" ");
		if (commandArgs.length != 1) {
			System.out.println ("Wrong input size, please only type one word\n");
			return true;
		}

        if (!commandArgs[0].matches ("[0-9]+")) {
            System.out.println ("Invalid input type, please only type numeric numbers\n");
            return true;
        }
		return false;
	}

	// Clean input text for further usage
	private String cleanInputText (String input) {
		String[] commandArgs = input.split (" ");
		String word = commandArgs[0];
		return word;
	}

    // Clean input int for further usage
	private int cleanInputInt (String input) {
		String[] commandArgs = input.split (" ");
        int number = Integer.parseInt (commandArgs[0]);
		return number;
	}

    

}
