package fu.db.inputres.csv2db;

import java.sql.ResultSet;
import java.sql.SQLException;

import fu.db.Log;
import fu.db.connection.sql.InsertStat;
import fu.db.connection.sql.SelectStat;
import fu.db.domain.Genre;
import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;

public class DirectorDBDataImporter extends DBDataImporter {

	private int foreignKeyDirector = -1;

	@Override
	public ValueTransformer<?> getValueTransformer(CSVIterator csvIterator) {
		return new ValueTransformer<Genre>() {

			@Override
			public void transform(String value) {
				String[] split = value.split("\\|");
				String director = "";
				for (String e : split) {
					int indexOf = e.indexOf(" ");
					if (indexOf == -1) {
						director = e;
					} else {
						director = e.substring(0, indexOf) + " ";
						director += e.substring(indexOf + 1, e.length());
					}
					getUniqueSet().add(director);
				}
			}

			@Override
			public int getColumn() {
				return 6;
			}
		};
	}

	@Override
	public void onEachCSVRow(CSVRowList list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEachCSVRowElement(int rowId, int columnId, String value) {
		// TODO Auto-generated method stub

	}

	private void setForeignKeyForDirectors() {
		if (foreignKeyDirector < 0) {
			try {
				SelectStat selectStat = new SelectStat();
				ResultSet resultSet = selectStat
						.setSql("SELECT id FROM moviedb.persontype WHERE persontype = 'director';")
						.execute().getResultSet();
				if (resultSet.next()) {
					foreignKeyDirector = resultSet.getInt(1);
				}
				selectStat.done();
				return;
			} catch (SQLException e) {
				Log.error(e);
			}
		}
	}

	@Override
	public void forEachInSet(String s) {

		setForeignKeyForDirectors();

		InsertStat stat = new InsertStat().setTable("moviedb.person");
		int indexOf = s.indexOf(" ");
		if (indexOf == -1) {
			stat.setColumns("lastname", "persontype").setValues(s,
					foreignKeyDirector);
		} else {
			String firstName = s.substring(0, indexOf);
			String lastName = s.substring(indexOf + 1, s.length());
			stat.setColumns("firstname", "lastname", "persontype").setValues(
					firstName, lastName, foreignKeyDirector);
		}
		try {
			stat.insertIfNotExists().done();
		} catch (SQLException e) {
			Log.error(e);
		}
	}
}
