package fu.db.inputres.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import fu.db.domain.Genre;

public class IMDBImporter extends CSVImport {

	private String[] titles = new String[] { "imdbID", "name", "year",
			"rating", "votes", "runtime", "directors", "actors", "genres" };

	public static void main(String[] args) {
		new IMDBImporter(Paths.get("res", "imdb_top100t.txt"));
	}

	public IMDBImporter(Path path) {
		try {
			SEPARATOR = "\t";
			importCSV(path.toFile(), false);
			getList().setHeaderData(titles);
			System.out.println(getList().size() + " entries imported");
			System.out
					.println(getInvalideEntries().size()
							+ " entries do not meet format conditions, these remain unused");
			System.out.println("=> Snipped:");
			System.out.println(getList().getHeader());
			for (int i = 0; i < 5; i++) {
				System.out.println(getList().get(i));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Genre> genres = getGenres();
		for (Genre g : genres) {
		}
	}

	@Override
	boolean fixIfInvalidate(String currentLine, BufferedReader reader) {
		String[] split = currentLine.split(SEPARATOR);
		if (split.length != this.titles.length) {
			return false;
		}
		return true;
	}

	private Set<Genre> getGenres() {
		Set<Genre> genres = new TreeSet<Genre>(new Comparator<Genre>() {

			@Override
			public int compare(Genre arg0, Genre arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}

		});
		for (CSVRowList row : getList()) {
			for (String genreAsString : row.get(8).split("\\|")) {
				Genre genre = new Genre();
				genre.setName(genreAsString);
				genres.add(genre);
			}
		}
		return genres;
	}
}
