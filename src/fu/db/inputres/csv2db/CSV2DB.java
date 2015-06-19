package fu.db.inputres.csv2db;

import fu.db.connection.DBConnection;
import fu.db.inputres.ImbdCSVImporter;

public class CSV2DB {

	public void transform(ImbdCSVImporter data, DBConnection connection) {
		DBDataImporter[] importerArray = new DBDataImporter[] {
				new KindOfPersonDBDataImporter(), new GenresDBDataImporter(),
				new ActorDBDataImporter() };
		for (DBDataImporter importer : importerArray) {
			importer.start(data, connection);
		}
	}

}
