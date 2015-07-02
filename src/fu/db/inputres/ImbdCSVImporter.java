package fu.db.inputres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

import fu.db.Log;
import fu.db.inputres.csv.CSVImport;

/**
 * This class extends CSVImporter and adds attributes respecting IMDB, e.g. titles.
 * 
 */
public class ImbdCSVImporter extends CSVImport {

	private String[] titles = new String[] { "imdbID", "name", "year",
			"rating", "votes", "runtime", "directors", "actors", "genres" };

	public ImbdCSVImporter(Path path) {
		try {
			SEPARATOR = "\t";
			importCSV(path.toFile(), false);
			getList().setHeaderData(titles);
			Log.info(getList().size() + " entries imported");
			Log.info(getInvalideEntries().size()
					+ " entries do not meet format requirements, these remain unused");
			printSnipped(3);

		} catch (IOException e) {
			Log.error(e);
		}
	}

	private void printSnipped(int j) {
		Log.info("\n=> Snipped (top " + j + "):");
		Log.info(getList().getHeader().toString());
		for (int i = 0; i < j; i++) {
			Log.info(getList().get(i).toString());
		}
	}

	public boolean fixIfInvalidate(String currentLine, BufferedReader reader) {
		String[] split = currentLine.split(SEPARATOR);
		if (split.length != this.titles.length) {
			return false;
		}
		return true;
	}

}
