package fu.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import fu.db.Log;
import fu.db.inputres.DBProperties;

/**
 * @author Sven Willrich
 * 
 *         This class is used to maintain the database connection and is
 *         competed to return Statements for example.
 */
public class DBConnection {

	/**
	 * This class is built as singleton, so this variable is provided for
	 * holding the object reference
	 */
	private static DBConnection INSTANCE = new DBConnection();

	private Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public static DBConnection getINSTANCE() {
		return INSTANCE;
	}

	/**
	 * Returns a new instantiated statement object for update and selection
	 * statements
	 * 
	 * @return the new generated statement
	 */
	public Statement getNewStat() {
		try {
			return this.connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private DBConnection() {
		try {
			initializeConnection();
		} catch (SQLException e) {
			Log.error(e);
		}
	}

	private void initializeConnection() throws SQLException {
		String url = DBProperties.INSTANCE.getProperty("url");
		String user = DBProperties.INSTANCE.getProperty("user");
		String password = DBProperties.INSTANCE.getProperty("password");

		this.connection = DriverManager.getConnection(url, user, password);
	}
}
