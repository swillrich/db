package fu.db.inputres;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Sven Willrich
 *
 *         This class represents the bridge between postgresql.properties and
 *         java. Use this class to access the properties being specified in the
 *         properties file. This class is given as singleton.
 */
public class DBProperties extends Properties {

	/**
	 * Holds the singleton instance
	 */
	public static DBProperties INSTANCE = new DBProperties();

	private DBProperties() {
		try {
			read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void read() throws IOException {
		load(new FileInputStream(new File("postgresql.properties")));
	}
}
