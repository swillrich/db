package fu.db.inputres.csv2db;

import fu.db.connection.AbstractStat;
import fu.db.domain.Genre;
import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;

public class GenresDBDataImporter extends DBDataImporter {

	@Override
	public ValueTransformer<?> getValueTransformer(CSVIterator csvIterator) {
		return new ValueTransformer<Genre>() {

			@Override
			public void transform(String value) {
				for (String genreAsString : value.split("\\|")) {
					getUniqueSet().add(genreAsString);
				}
			}

			@Override
			public int getColumn() {
				return 8;
			}
		};
	}

	@Override
	public void onEachCSVRow(CSVRowList list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEachCSVRowElement(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void forEachInSet(String s) {
		System.out.println(s);
	}
}
