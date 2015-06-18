package fu.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;

import fu.db.connection.DBConnection;
import fu.db.inputres.ImbdCSVImporter;
import fu.db.inputres.csv2db.CSV2DB;
import fu.db.sql.SQLFileExecuter;

/**
 * @author Sven Willrich
 *
 *         This class is the starting point
 */
public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		resetDatabaseSchema();
		insertCSV();
	}

	private void insertCSV() {
		ImbdCSVImporter importer = new ImbdCSVImporter(Paths.get("res",
				"imdb_top100t_2015-06-18.csv"));
		CSV2DB csv2DomainObject = new CSV2DB();
		csv2DomainObject.transform(importer, DBConnection.getINSTANCE());
	}

	private void resetDatabaseSchema() {
		Connection connection = DBConnection.getINSTANCE().getConnection();
		SQLFileExecuter sqlFileExecuter = new SQLFileExecuter(connection);
		sqlFileExecuter.dropSchema();
		try {
			sqlFileExecuter.executeSQLFile(new File("res/moviedb.sql"));
		} catch (IOException e) {
			Log.error(e);
		}
	}
}
