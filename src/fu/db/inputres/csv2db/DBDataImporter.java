package fu.db.inputres.csv2db;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import fu.db.connection.DBConnection;
import fu.db.inputres.ImbdCSVImporter;
import fu.db.inputres.csv.CSVImport.CSVIterator;
import fu.db.inputres.csv.CSVImport.ValueTransformer;
import fu.db.inputres.csv.CSVRowList;

public abstract class DBDataImporter {

	private ImbdCSVImporter data;
	private DBConnection connection;
	private CSVIterator csvIterator;
	private boolean skipCSVIteration = false;

	public DBConnection getConnection() {
		return connection;
	}

	public void setSkipCSVIteration(boolean skipCSVIteration) {
		this.skipCSVIteration = skipCSVIteration;
	}

	Set<String> uniqueSet = new TreeSet<String>(new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	});

	public Set<String> getUniqueSet() {
		return uniqueSet;
	}

	public ImbdCSVImporter getData() {
		return data;
	}

	public void start(ImbdCSVImporter data, DBConnection connection) {
		this.data = data;
		this.connection = connection;
		if (!skipCSVIteration) {
			this.csvIterator = data.new CSVIterator() {

				@Override
				public void onNextRow(CSVRowList element) {
					onEachCSVRow(element);
				}

				@Override
				public void defaultProcessorForEachElement(int rowId,
						int columnId, String value) {
					onEachCSVRowElement(rowId, columnId, value);
				}

			};

			ValueTransformer<?> valueTransformer = getValueTransformer(this.csvIterator);
			if (valueTransformer != null) {
				this.csvIterator.getValueTransformerList()
						.add(valueTransformer);
			}
			this.csvIterator.start();
		}

		for (String s : uniqueSet) {
			forEachInSet(s);
		}
	}

	public abstract ValueTransformer<?> getValueTransformer(
			CSVIterator csvIterator);

	public abstract void onEachCSVRow(CSVRowList list);

	public abstract void onEachCSVRowElement(int rowId, int columnId,
			String value);

	public abstract void forEachInSet(String s);

	public <T> T getAs(String o, Class<T> clazz) {
		try {
			if (clazz.equals(Integer.class)) {
				return (T) Integer.valueOf(o);
			} else if (clazz.equals(Double.class)) {
				return (T) Double.valueOf(o);
			} else {
				return (T) o;
			}
		} catch (ClassCastException e) {
			return null;
		}
	}
}
