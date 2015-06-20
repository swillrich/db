package fu.db.inputres;

import fu.db.connection.DBConnection;
import fu.db.inputres.csv2db.ActorDBDataImporter;
import fu.db.inputres.csv2db.DBDataImporter;
import fu.db.inputres.csv2db.DirectorDBDataImporter;
import fu.db.inputres.csv2db.GenresDBDataImporter;
import fu.db.inputres.csv2db.KindOfPersonDBDataImporter;
import fu.db.inputres.csv2db.MovieDBDataImporter;
import fu.db.inputres.csv2db.MovieHasDirectorAndActorAndGenreDBDataImporter;

public class CSV2DB {

	public void transform(ImbdCSVImporter data, DBConnection connection) {
		for (int i = data.getList().size() - 1; i >= 500; i--) {
			data.getList().remove(i);
		}
		DBDataImporter[] importerArray = new DBDataImporter[] {
				new KindOfPersonDBDataImporter(), new GenresDBDataImporter(),
				new ActorDBDataImporter(), new DirectorDBDataImporter(),
				new MovieDBDataImporter(),
				new MovieHasDirectorAndActorAndGenreDBDataImporter() };
		for (DBDataImporter importer : importerArray) {
			importer.start(data, connection);
		}
	}

}
