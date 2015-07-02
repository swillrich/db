package fu.db.cli;

import java.util.Scanner;

import fu.db.Log;

/**
 *This class realizes the user interaction. 
 *It holds an association to a collection of CLICommands which are 
 *showing to user via console where the user can choice between them.
 */
public class CLIUserInteraction {
	/**
	 * The commands the user can choice between
	 */
	private CLICommand[] cliCommands = new CLICommand[] { new ExitCommand(),
			new Request20RatedMoviesCommand(),
			new RequestMoreThan6PointsInLastYearCommand(),
			new RequestCommonDebutCommand(), new MostMoviesCommand(),
			new RegisseurActorsPreferences(),
			new LesserThan6PointRatingCommand() };
	Scanner scanner = new Scanner(System.in);
	String userInfo = "";
	{
		userInfo = "Nur Ziffern von 1 - " + cliCommands.length
				+ " oder - zur Beendigung - 'exit' und mit 'Enter' bestätigen.";
	}

	/**
	 * the user console starts
	 */
	public void start() {
		Log.info(userInfo);
		while (true) {
			showOptions();
			int userInput = getUserInput() - 1;
			if (userInput < 0) {
				Log.info("Falsche Eingabe. " + userInfo);
			} else {
				instructByUserInput(userInput);
				if (cliCommands[userInput] instanceof ExitCommand) {
					break;
				}

			}
		}
	}

	private int getUserInput() {
		try {
			return Integer.valueOf(scanner.nextLine());
		} catch (Exception e) {
			return -1;
		}
	}

	private void showOptions() {
		Log.info("\nOption wählen:");
		for (int i = 0; i < cliCommands.length; i++) {
			Log.info(i + 1 + "\t" + cliCommands[i].getDescription());
		}
	}

	private void instructByUserInput(int userChoice) {
		CLICommand cliCommand = cliCommands[userChoice];
		Log.info("=> " + cliCommand.getDescription());
		try {
			cliCommand.execute();
		} catch (Exception e) {
			Log.error(e);
		}
	}
}
