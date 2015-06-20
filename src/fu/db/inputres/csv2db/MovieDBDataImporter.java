package fu.db.inputres.csv2db;

import java.sql.SQLException;

import fu.db.connection.sql.InsertStat;
import fu.db.connection.sql.SelectStat;
import fu.db.dao.PersonDAO;
import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;

public class MovieDBDataImporter extends DBDataImporter {

	@Override
	public ValueTransformer<?> getValueTransformer(CSVIterator csvIterator) {
		return null;
	}

	@Override
	public void onEachCSVRow(CSVRowList list) {
		String movieId = list.get(0);
		String movieName = list.get(1);
		Integer year = getAs(list.get(2), Integer.class);
		Double rating = getAs(list.get(3), Double.class);
		Integer votes = getAs(list.get(4), Integer.class);
		Integer runtime = getAs(list.get(5).replace(" mins.", ""),
				Integer.class);

		try {
			new InsertStat()
					.setTable("moviedb.movie")
					.setColumns("id", "title", "year", "rating", "votes",
							"runtime")
					.setValues(movieId, movieName, year, rating, votes, runtime)
					.executeAndDone();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEachCSVRowElement(int rowId, int columnId, String value) {

	}

	@Override
	public void forEachInSet(String s) {
	}

}
