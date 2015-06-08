package fu.db.inputres.csv;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import fu.db.domain.Genre;

public class IMDBImporter extends CSVImport {

	private CSVContainerList list;

	public static void main(String[] args) {
		new IMDBImporter(Paths.get("res", "imdb_top100.txt"));
	}

	public IMDBImporter(Path path) {
		try {
			SEPARATOR = "\t";
			this.list = convert(path.toFile(), false);
			this.list.setHeaderData(new String[] { "imdbID", "name", "year",
					"rating", "votes", "runtime", "directors", "actors",
					"genres" });
			String string = list.toString();
			System.out.println(string);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<Genre> genres = getGenres();
		for (Genre g : genres) {
			System.out.println(g.getName());
		}
	}

	private Set<Genre> getGenres() {
		Set<Genre> genres = new TreeSet<Genre>(new Comparator<Genre>() {

			@Override
			public int compare(Genre arg0, Genre arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}

		});
		for (CSVRowList row : list) {
			for (String genreAsString : row.get(8).split("\\|")) {
				Genre genre = new Genre();
				genre.setName(genreAsString);
				genres.add(genre);
			}
		}
		return genres;
	}
}
