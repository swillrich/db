package fu.db.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import fu.db.Log;

public class SQLFileExecuter {
	private Connection connection;

	public SQLFileExecuter(Connection connection) {
		this.connection = connection;
	}

	public void dropSchema() {
		try {
			String url = connection.getMetaData().getURL();
			String schemaName = url.substring(url.lastIndexOf("/") + 1,
					url.length());
			connection.createStatement().executeUpdate(
					"drop schema " + schemaName + " CASCADE;");
			Log.info("schema with name " + schemaName + " dropped");
		} catch (SQLException e) {
			Log.error(e);
		}
	}

	public void executeSQLFile(File file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		int currentChar;
		StringBuilder b = new StringBuilder();
		while ((currentChar = bufferedReader.read()) != -1) {
			b.append((char) currentChar);
			if (currentChar == ';') {
				Log.info(b.toString());
				boolean isSuccessfully = executeSQL(b.toString());
				if (!isSuccessfully) {
					Log.error("> SQL execution finished with errors");
				} else {
					Log.info("> SQL executed successfully");
				}
				b = new StringBuilder();
			}
		}
	}

	private boolean executeSQL(String sql) {
		try {
			int executeUpdate = connection.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			Log.error(e);
			return false;
		}
	}
}
