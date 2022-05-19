/**
 * This program is for Main menu and Battle loop. Users should be able to configure player and monster for the game.
 * Once player encounter monster on the map, battle loop will be triggered.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
import java.util.Scanner;
public class GameEngine {

	
	public static void main (String [] args) {
		
		// This main method is for running the whole program
		// Creates an instance of the game engine.
		GameEngine gameEngine = new GameEngine ();
		// Runs the main game loop.
		gameEngine.runGameLoop ();
		
	}
	
	
	/*
	 *  Logic for running the main game loop.
	 */
	private void runGameLoop () {
		
		// User I/O
		Scanner scan = new Scanner (System.in);
		//TODO: Might not need to initialise instance here.
		Player player = new Player ();
		Monster monster = new Monster ();
		Commands commands = new Commands ();
		
		// Call menu
		menu (player, monster, commands);

		boolean isLoop = false;
		// Menu methods looping
		while (!isLoop) {
			// Catches users' inputs
			String input = scan.nextLine ();

			// if users' input is wrong, go back to menu (or just have another prompt()?)
			String commandWord = inputForCommand (input);
			if (validateInputText (commandWord)) {continue;};
			


            switch (commandWord) {
                case "help":
					commands.help ();
					prompt ();
                    break;
				case "commands":
					commands.commands ();
					prompt ();
					break;
                case "player":
					if (player.getName () != null) {
						player.displayInfo (
							player.getName ()
						);
						if (commands.returnToMenu (scan)) {
							menu (player, monster, commands);
							continue;
						}
					}
					commands.player (player, scan);
					if (commands.returnToMenu (scan)) {
						menu (player, monster, commands);
						continue;
					}
                    break;
                case "monster":
					commands.monster (monster, scan);
					if (commands.returnToMenu (scan)) {
						menu (player, monster, commands);
						continue;
					}
                    break;
                case "start":
					String fileName = checkFile(input);
					commands.start (player, monster, scan, commands, fileName);
					if (commands.returnToMenu (scan)) {
						menu (player, monster, commands);
						continue;
					}
                    break;
				case "exit":
					commands.exitProgram ();
					break;
				case "save":
					commands.save(player);
					prompt();
					break;
				case "load":
					commands.load();
					prompt();
					break;
            }
		}
	}
	
	// Remind user to type
	private void prompt () {
		System.out.print ("> ");
	}

	// Validates users' inputs
	private boolean validateInputText (String input) {
		if (!Commands.ALLOWED_COMMANDS.contains (inputForCommand(input))) {
			System.out.println ("Invalid commands name");
			prompt ();
			return true;
		}
		return false;
	}


	// Input cutting for menu
	private String inputForCommand (String input) {
		String [] commandArgs = input.split (" ");
		String words = commandArgs[0];
		return words;
	}

	// Input cutting for loading game file
	private String inputFileName (String input) {
		String [] commandArgs = input.split (" ");
		String fileName = commandArgs[1];
		return fileName;
	}

	// Checking input length
	private String checkFile (String input) {
		String [] commandArgs = input.split (" ");
		if (commandArgs.length == 2) {
			return inputFileName(input);
		} else {
			return null;
		}
	}
	
	// Displays the title text.
	private void displayTitleText () {
		
		String titleText = " ____                        \n" + 
				"|  _ \\ ___   __ _ _   _  ___ \n" + 
				"| |_) / _ \\ / _` | | | |/ _ \\\n" + 
				"|  _ < (_) | (_| | |_| |  __/\n" + 
				"|_| \\_\\___/ \\__, |\\__,_|\\___|\n" + 
				"COMP90041   |___/ Assignment ";
		
		System.out.println (titleText);
		System.out.println ();

	}



	// Displays command hint
	private void displayHint () {
		System.out.println ("Please enter a command to continue.");
		System.out.println ("Type 'help' to learn how to get started.");
		System.out.println ();
	}

	private void menu (Player player, Monster monster, Commands commands) {
		displayTitleText ();
		commands.displayPlayerMonster (commands.returnP (player), commands.returnM (monster));
		System.out.println ();
		displayHint ();
		prompt ();
	}
}
