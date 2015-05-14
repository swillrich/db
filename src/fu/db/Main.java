package fu.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import fu.db.connection.DBConnection;
import fu.db.sql.SQLFileExecuter;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		Connection connection = DBConnection.getINSTANCE().getConnection();

		SQLFileExecuter sqlFileExecuter = new SQLFileExecuter(connection);
		sqlFileExecuter.dropSchema();
		try {
			sqlFileExecuter.executeSQLFile(new File("res/filmdb.sql"));
		} catch (IOException e) {
			Log.error(e);
		}
	}
}
