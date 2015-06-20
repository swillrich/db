package fu.db.cmi;

public abstract class CLICommand {
	public abstract String getDescription();
	public abstract void execute() throws Exception;
}
