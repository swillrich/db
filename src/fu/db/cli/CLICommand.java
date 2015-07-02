package fu.db.cli;

/**
 * @author Sven Willrich
 * This interface is used to handle command line interfaces.
 */
public abstract class CLICommand {
	/**
	 * return the description of the command
	 */
	public abstract String getDescription();

	/**
	 * execute the command
	 */
	public abstract void execute() throws Exception;
}
