package fu.db.inputres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

import fu.db.inputres.csv.CSVImport;

public class ImbdCSVImporter extends CSVImport {

	private String[] titles = new String[] { "imdbID", "name", "year",
			"rating", "votes", "runtime", "directors", "actors", "genres" };

	public ImbdCSVImporter(Path path) {
		try {
			SEPARATOR = "\t";
			importCSV(path.toFile(), false);
			getList().setHeaderData(titles);
			System.out.println(getList().size() + " entries imported");
			System.out
					.println(getInvalideEntries().size()
							+ " entries do not meet format requirements, these remain unused");
			printSnipped(3);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printSnipped(int j) {
		System.out.println("\n=> Snipped (top " + j + "):");
		System.out.println(getList().getHeader());
		for (int i = 0; i < j; i++) {
			System.out.println(getList().get(i));
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
