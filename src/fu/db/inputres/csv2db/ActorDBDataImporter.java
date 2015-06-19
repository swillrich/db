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

public class ActorDBDataImporter extends DBDataImporter {

	private int foreignKeyActors = -1;

	@Override
	public ValueTransformer<?> getValueTransformer(CSVIterator csvIterator) {
		return new ValueTransformer<Genre>() {

			@Override
			public void transform(String value) {
				String[] split = value.split("\\|");
				String actor = "";
				for (String e : split) {
					int indexOf = e.indexOf(" ");
					if (indexOf == -1) {
						actor = e;
					} else {
						actor = e.substring(0, indexOf) + " ";
						actor += e.substring(indexOf + 1, e.length());
					}
					getUniqueSet().add(actor);
				}
			}

			@Override
			public int getColumn() {
				return 7;
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

	private void setForeignKeyForActors() {
		if (foreignKeyActors < 0) {
			try {
				SelectStat selectStat = new SelectStat();
				ResultSet resultSet = selectStat
						.setSql("SELECT id FROM moviedb.persontype WHERE persontype = 'actor';")
						.execute().getResultSet();
				if (resultSet.next()) {
					foreignKeyActors = resultSet.getInt(1);
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

		setForeignKeyForActors();

		InsertStat stat = new InsertStat().setTable("moviedb.person");
		int indexOf = s.indexOf(" ");
		if (indexOf == -1) {
			stat.setColumns("lastname", "persontype").setValues(s,
					foreignKeyActors);
		} else {
			String firstName = s.substring(0, indexOf);
			String lastName = s.substring(indexOf + 1, s.length());
			stat.setColumns("firstname", "lastname", "persontype").setValues(
					firstName, lastName, foreignKeyActors);
		}
		try {
			stat.executeAndDone();
		} catch (SQLException e) {
			Log.error(e);
		}
	}
}
