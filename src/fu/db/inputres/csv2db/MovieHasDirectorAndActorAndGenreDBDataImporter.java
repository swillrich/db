package fu.db.inputres.csv2db;

import java.sql.SQLException;

import fu.db.Log;
import fu.db.connection.sql.InsertStat;
import fu.db.dao.GenreDao;
import fu.db.dao.PersonDAO;
import fu.db.domain.Actor;
import fu.db.domain.Director;
import fu.db.domain.Genre;
import fu.db.domain.Person;
import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;
import fu.db.inputres.performance.Cache;

public class MovieHasDirectorAndActorAndGenreDBDataImporter extends
		DBDataImporter {

	@Override
	public ValueTransformer<?> getValueTransformer(CSVIterator csvIterator) {
		return null;
	}

	@Override
	public void onEachCSVRow(CSVRowList list) {
		try {
			String movieId = list.get(0);
			setActorAndDirector(list, movieId);
			setGenre(list, movieId);
		} catch (SQLException e) {
			Log.error(e);
		}
	}

	Cache movieHasGenre = new Cache() {

		@Override
		public String getIdentifier(String... elements) {
			return elements[0];
		}

		@Override
		public String[] onIsNotInCacheContaining(String... elements) {
			Genre genre = new GenreDao().findGenreByName(elements[0]);
			return new String[] { String.valueOf(genre.getId()) };
		}

	};

	private void setGenre(CSVRowList list, String movieId) throws SQLException {
		for (String element : list.get(8).split("\\|")) {

			String[] data = movieHasGenre.get(element);

			if (data == null) {
				data = movieHasGenre.add(element);
			}

			new InsertStat().setTable("moviedb.moviehasgenre")
					.setColumns("movie", "genre")
					.setValues(movieId, Integer.valueOf(data[0]))
					.executeAndDone();
		}
	}

	Cache movieHasDirectorOrActorCache = new Cache() {

		@Override
		public String getIdentifier(String... elements) {
			if (elements.length == 1) {
				return elements[0] + elements[1];
			} else {
				return elements[0] + elements[1];
			}
		}

		@Override
		public String[] onIsNotInCacheContaining(String... elements) {
			Person person = new PersonDAO().findPersonByFirstAndLastName(
					elements[0], elements[1]);
			return new String[] { String.valueOf(person.getId()) };
		}

	};

	private void setActorAndDirector(CSVRowList list, String movieId)
			throws SQLException {
		// 6 = directors; 7 = actors
		for (int i : new int[] { 6, 7 })
			for (String element : list.get(i).split("\\|")) {
				int firstSpace = element.indexOf(" ");
				String firstName = firstSpace == -1 ? null : element.substring(
						0, firstSpace);
				String lastName = firstSpace == -1 ? element : element
						.substring(firstSpace + 1, element.length());

				Class<? extends Person> clazzOfPerson = i == 7 ? Actor.class
						: Director.class;

				String[] inserData = new String[] { firstName, lastName };

				String[] data = movieHasDirectorOrActorCache.get(inserData);

				if (data == null) {
					data = movieHasDirectorOrActorCache.add(inserData);
				}

				new InsertStat()
						.setTable(
								"moviedb.moviehas"
										+ clazzOfPerson.getSimpleName()
												.toLowerCase())
						.setColumns("movie",
								clazzOfPerson.getSimpleName().toLowerCase())
						.setValues(movieId, Integer.valueOf(data[0]))
						.insertIfNotExists().done();
			}
	}

	@Override
	public void onEachCSVRowElement(int rowId, int columnId, String value) {

	}

	@Override
	public void forEachInSet(String s) {

	}
}
