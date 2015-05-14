package fu.db;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Log {
	private Logger log = Logger.getRootLogger();

	private static Log INSTANCE = new Log();

	public Log() {
		log.setLevel(Level.DEBUG);
		PatternLayout layout = new PatternLayout("%m%n");
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		log.addAppender(consoleAppender);
	}

	public static void info(String info) {
		INSTANCE.log.log(Level.INFO, info);
	}

	public static void error(String error) {
		INSTANCE.log.log(Level.ERROR, error);
	}

	public static void error(Exception e) {
		INSTANCE.log.log(Level.ERROR, e.getMessage(), e);
	}

}
