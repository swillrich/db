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

	private void setGenre(CSVRowList list, String movieId) throws SQLException {
		for (String element : list.get(8).split("\\|")) {
			Genre genre = new GenreDao().findGenreByName(element);
			new InsertStat().setTable("moviedb.moviehasgenre")
					.setColumns("movie", "genre")
					.setValues(movieId, genre.getId()).executeAndDone();
		}
	}

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

				Person person = new PersonDAO().findPersonByFirstAndLastName(
						firstName, lastName, clazzOfPerson);

				new InsertStat()
						.setTable(
								"moviedb.moviehas"
										+ clazzOfPerson.getSimpleName()
												.toLowerCase())
						.setColumns("movie",
								clazzOfPerson.getSimpleName().toLowerCase())
						.setValues(movieId, person.getId()).insertIfNotExists()
						.done();
			}
	}

	@Override
	public void onEachCSVRowElement(int rowId, int columnId, String value) {

	}

	@Override
	public void forEachInSet(String s) {

	}
}
