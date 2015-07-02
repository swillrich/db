package fu.db.cli;

import fu.db.Log;


public class ExitCommand extends CLICommand {

	@Override
	public String getDescription() {
		return "Programm beenden.";
	}

	@Override
	public void execute() throws Exception {
		Log.info("Programm beendet.");
	}

}
