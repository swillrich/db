package fu.db.inputres.csv2db;

import fu.db.connection.AbstractStat;
import fu.db.domain.Genre;
import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;

public class ActorDBDataImporter extends DBDataImporter {

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
						actor = e.substring(0, indexOf);
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
	public void onEachCSVRowElement(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void forEachInSet(String s) {
		System.out.println(s);
	}

}
