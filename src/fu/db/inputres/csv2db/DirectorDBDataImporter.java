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
import fu.db.inputres.performance.Cache;

public class DirectorDBDataImporter extends DBDataImporter {
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

	@Override
	public void forEachInSet(String s) {

		int indexOf = s.indexOf(" ");
		if (indexOf == -1) {
			cache.add(s);
		} else {
			String firstName = s.substring(0, indexOf);
			String lastName = s.substring(indexOf + 1, s.length());
			cache.add(firstName, lastName);
		}
	}

	Cache cache = new Cache() {

		private static final long serialVersionUID = 1L;

		@Override
		public String getIdentifier(String... elements) {
			if (elements.length == 1) {
				return elements[0];
			} else {
				return elements[0] + " " + elements[1];
			}
		}

		@Override
		public String[] onIsNotInCacheContaining(String... elements) {
			InsertStat stat = new InsertStat().setTable("moviedb.person");
			if (elements.length == 1) {
				stat.setColumns("lastname").setValues(elements[0]);
			} else {
				stat.setColumns("firstname", "lastname").setValues(elements[0],
						elements[1]);
			}
			try {
				stat.insertIfNotExists().done();
			} catch (SQLException e) {
				Log.error(e);
			}
			return elements;
		}

	};
}
