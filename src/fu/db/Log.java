package fu.db;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * @author Sven Willrich
 *
 *         This class is responsible for logging. All logging messages (e.g.
 *         info, errors) should take the way about this singleton instance.
 */
public class Log {
	/**
	 * The log4j root logger instance
	 */
	private Logger log = Logger.getRootLogger();

	/**
	 * The singleton instance
	 */
	private static Log INSTANCE = new Log();

	public Log() {
		log.setLevel(Level.DEBUG);
		PatternLayout layout = new PatternLayout("%m%n");
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		log.addAppender(consoleAppender);
	}

	/**
	 * Produces an info output
	 * 
	 * @param info
	 *            the message
	 */
	public static void info(String info) {
		INSTANCE.log.log(Level.INFO, info);
	}

	/**
	 * Produces an error output
	 * 
	 * @param info
	 *            the message
	 */
	public static void error(String error) {
		INSTANCE.log.log(Level.ERROR, error);
	}

	/**
	 * Produces an error output by a given exception
	 * 
	 * @param info
	 *            the message
	 */
	public static void error(Exception e) {
		INSTANCE.log.log(Level.ERROR, e.getMessage(), e);
	}

}
