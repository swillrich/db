package fu.db.inputres.csv2db;

import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;

public class MovieHasDirectorAndActorDBDataImporter extends DBDataImporter {

	@Override
	public ValueTransformer<?> getValueTransformer(CSVIterator csvIterator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onEachCSVRow(CSVRowList list) {
		String[] directorArr = list.get(6).split("\\|");
		String[] actorsArr = list.get(7).split("\\|");
		String[] genreArr = list.get(8).split("\\|");
		// for (String director :
		// directorArr) {
		// int sep = director.indexOf(" ");
		// String firstName = sep ?
		// String lastName = "";
		// new PersonDAO().findActorByFirstAndLastName(firstName, lastName);
		// }
	}

	@Override
	public void onEachCSVRowElement(int rowId, int columnId, String value) {

	}

	@Override
	public void forEachInSet(String s) {

	}
}
