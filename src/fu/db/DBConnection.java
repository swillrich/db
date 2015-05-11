package fu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private static DBConnection INSTANCE = new DBConnection();

	public static DBConnection getINSTANCE() {
		return INSTANCE;
	}
	
	public Statement getNewStat() {
		try {
			return this.connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Connection connection;

	private DBConnection() {
		try {
			initializeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initializeConnection() throws SQLException {
		String url = DBProperties.INSTANCE.getProperty("url");
		String user = DBProperties.INSTANCE.getProperty("user");
		String password = DBProperties.INSTANCE.getProperty("password");

		this.connection = DriverManager.getConnection(url, user, password);
	}
}
