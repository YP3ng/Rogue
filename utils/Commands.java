package utils;


/**
 * This class is for generally used methods and commands.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import Entity.Unit.Monster;
import Entity.Unit.Player;
import Exception.GameLevelNotFoundException;
import Terrain.World;
import Terrain.Map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;

public class Commands {
    
    public static final Set<String> ALLOWED_COMMANDS = Set.of ("help", "player", "monster", "start", "exit", "commands", "save", "load");
    // Show users what commands are allowed
    public void commands () {
        System.out.println ("help");
        System.out.println ("player");
        System.out.println ("monster");
        System.out.println ("start");
        System.out.println ("exit");
        System.out.println ("save");
        System.out.println ("load");
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

        // FileInputure player name
        String input = scan.nextLine ();
        // Validate input. If invalid, redo the process
        if (validateInputText (input)) {player (player, scan);}
        // Set player name
        if (player.getName() == null) {
            player.setName (cleanInputText (input)[0]);
            System.out.printf ("Player '%s' created.\n\n", player.getName());
        }

    }

    // Set up monster infomation
    // If users' input invalid for any reason, users need to redo the monster creation process
    public void monster (Monster monster, Scanner scan) {

        // Loop for input validation
        boolean isDone = false;
        while (!isDone) {
            // FileInputure name
            System.out.print ("Monster name: ");
            String input = scan.nextLine();
            if (validateInputText (input)) {continue;};
            // Set monster name
            String[] nameInput = cleanInputText(input);
            monster.setName (nameInput[0]);

            // FileInputure health
            System.out.print ("Monster health: ");
            input = scan.nextLine ();
            if (validateInputInt(input)) {continue;};
            // Set monster health
            String[] healthVal = cleanInputText(input);
            monster.setMaxHealth (parseStrToInt(healthVal).get(0));
            monster.setCurHealth (parseStrToInt(healthVal).get(0));

            // FileInputure damage
            System.out.print ("Monster damage: ");
            input = scan.nextLine();
            if (validateInputInt (input)) {continue;}
            // Set monster damage
            String[] damageVal = cleanInputText(input);
            monster.setDamage (parseStrToInt (damageVal).get(0));

            if (monster.getName() != null) {
                System.out.printf ("Monster '%s' created.\n\n", monster.getName ());
                isDone = true;
            }
        }
    }

    // Start the game
    // Should allow file input
    public void start (
        Player player, Monster monster, 
        Scanner scan, Commands commands,
        String fileName
        ) {
        // Check if player is set up
        if (player.getName () == null) {
            System.out.println("No player found, please create a player with 'player' first.\n");
            return;
        }

        // Heal both the player and monster to full health if a new game is triggered
        // Not sure if this still hold in A2
        if (
            player.getCurHealth () != player.getMaxHealth () || 
            monster.getCurHealth () != monster.getMaxHealth ()
        ) {
            player.setCurHealth (player.getMaxHealth ());
            monster.setCurHealth (monster.getMaxHealth ());
        }

        
        // Initialise the file reader
        if (fileName != null) {
            Scanner gameFileRead = null;

            try {
                gameFileRead =gameFileRead(fileName);
            } catch (GameLevelNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println();
                return;
            }

            // Extracting the information of the map
            if (gameFileRead.hasNextLine()) {
                
                // Extract map size
                String mapSize = gameFileRead.nextLine();
                String [] mapInfo = mapInfo(mapSize);
                ArrayList<Integer> mapInfoList = parseStrToInt(mapInfo);
                Map fileMap = new Map(mapInfoList.get(0), mapInfoList.get(1));
                
                // Extracting other information
                while (gameFileRead.hasNextLine()) {
                    String line = gameFileRead.nextLine();
                    this.charClassifier(line, fileMap, player);

                }

                World world = new World(fileMap);
                world.gameWorld(scan, commands, "file");
    
            }
            
        } else {

            // Check if monster is created
            if (monster.getName() == null) {
                System.out.println("No monster found, please create a monster with 'monster' first.\n");
                return;
            }
            // No file input, set a default map
            Map defaultMap = new Map(player, monster);
            World world = new World(player, monster, defaultMap);
            world.gameWorld (scan,commands, "default"); // Game world with no input file
        }

        
    }

    public void save (Player player) {

        String filename = "player.dat";

        // Create printWriter
        PrintWriter outStream = newFileOrOverwrite(filename);

        // Check if player exist
        if (player.getName() == null) {
            
            System.out.println("No player data to save.");
        } else {
            // Player information
            String name = player.getName();
            int level = player.getLevel();

            outStream.println(name + " " + level);
            outStream.close();

            System.out.println("Player data saved.");
        }
        System.out.println();

    };
    // Loading player name and level
    public Player load (Player player) {

        String fileName = "player.dat";
        String[] sepLine = new String[2];
        // Open reader
        Scanner inStream = playerFileRead(fileName);
        if (inStream != null) {
            while (inStream.hasNextLine()) {
                sepLine = inStream.nextLine().split(" ");
            }
        } else {
            return player;
        }
        // Initialise new player
        player = new Player(sepLine[0], Integer.parseInt(sepLine[1]));

        System.out.println("Player data loaded.");
        System.out.println();

        return player;
    };

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

    // Concat infomation from both player and monster
	public void battlePlayerMonster (String pInfo, String mInfo) {
		System.out.printf ("%s", pInfo);
		System.out.print (" | ");
		System.out.printf ("%s\n", mInfo);
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
	private String[] cleanInputText (String input) {
		String[] commandArgs = input.split (" ");
		return commandArgs;
	}

    // Parse String to Integer
	private ArrayList<Integer> parseStrToInt (String[] input) {
        ArrayList<Integer> intList = new ArrayList<Integer> (2);
        for (String ele : input) {
            try {
                int number = Integer.parseInt (ele);
                intList.add(number);
            } catch (InputMismatchException err) {
                return null;
            }
        }
		return intList;
	}

    // Read game file
    private Scanner gameFileRead (String fileName) throws GameLevelNotFoundException {

        Scanner gameFileRead = null;
        try {
            gameFileRead = new Scanner (new FileInputStream(fileName));
            return gameFileRead;
        } catch (FileNotFoundException e) {
            throw new GameLevelNotFoundException ("Map not found.");
        }
    }

    // Read player file
    private Scanner playerFileRead (String fileName) {

        Scanner playerFileRead = null;
        try {
            playerFileRead = new Scanner (new FileInputStream(fileName));
            return playerFileRead;
        } catch (FileNotFoundException e) {
            System.out.println("No player data found.");
            System.out.println();
        }
        return playerFileRead;
    }
    
    // Classifier to determine where the information belongs to
    private void charClassifier (String line, Map fileMap, Player player) {
        char firstChar = line.charAt(0);
        switch (firstChar) {
            case 'p':
                fileMap.gatherPlayerLoc(line, player);
                break;
            case 'm':
                fileMap.makeNewMonster(line);
                break;
            case 'i':
                fileMap.makeNewItem(line);
                break;
            case '.':
                fileMap.setRows(line);
                break;
            case '~':
                fileMap.setRows(line);
                break;
            case '#':
                fileMap.setRows(line);
                break;
        }
    }

    // Extracting map information from file input
    private String[] mapInfo (String line) {
        try {
            String[] lineArgs = line.split(" ");
            return lineArgs;
        } catch (Exception e) {
            return null;
        }
    }

    // PrintWriter for saving function
    private PrintWriter newFileOrOverwrite (String filename) {

        PrintWriter outStream = null;

        try {
            outStream = new PrintWriter(new FileOutputStream(filename));
        } catch (Exception e) {
            System.out.println("Error opening " + filename + "for writing.");
        }

        return outStream;
    }
}
