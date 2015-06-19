package fu.db.inputres.csv2db;

import java.sql.SQLException;

import fu.db.Log;
import fu.db.connection.sql.InsertStat;
import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;

public class KindOfPersonDBDataImporter extends DBDataImporter {
	public KindOfPersonDBDataImporter() {
		setSkipCSVIteration(true);
		getUniqueSet().add("director");
		getUniqueSet().add("actor");
	}

	@Override
	public ValueTransformer<?> getValueTransformer(CSVIterator csvIterator) {
		return null;
	}

	@Override
	public void onEachCSVRow(CSVRowList list) {
	}

	@Override
	public void onEachCSVRowElement(int rowId, int columnId, String value) {
	}

	@Override
	public void forEachInSet(String s) {
		try {
			new InsertStat().setTable("moviedb.persontype")
					.setColumns("persontype").setValues(s).insertIfNotExists()
					.done();
		} catch (SQLException e) {
			Log.error(e);
		}

	}

}
