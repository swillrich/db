package fu.db.inputres.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sven Willrich
 * 
 *         This class is responsible for transforming a CSV file to java by
 *         using own data structures
 */
public class CSVImport {

	/**
	 * Specifies the separator being used while parsing the CSV
	 */
	public static String SEPARATOR = ";";

	public static Charset charset = Charset.forName("utf-8");

	private CSVContainerList list = new CSVContainerList();
	private CSVContainerList invalideEntries = new CSVContainerList();

	public CSVContainerList getList() {
		return list;
	}

	public CSVContainerList getInvalideEntries() {
		return invalideEntries;
	}

	/**
	 * Used to convert a given CSV file to own data structure, here
	 * CSVContainerList
	 * 
	 * @param file
	 *            the CSV file
	 * @param firstRowIsHeader
	 *            whether the first line within the CSV file is a header with
	 *            titles or not
	 * @return the CSVContainreList inflated by CSV file content
	 * @throws IOException
	 */
	public void importCSV(File file, boolean firstRowIsHeader)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "utf-8"));
		String currentLine;
		if (firstRowIsHeader) {
			currentLine = reader.readLine();
			list.setHeaderData(currentLine.split(SEPARATOR));
		}
		while ((currentLine = reader.readLine()) != null) {
			boolean successfully = fixIfInvalidate(currentLine, reader);
			if (successfully) {
				list.addNewRow(currentLine.split(SEPARATOR));
			} else {
				invalideEntries.addNewRow(currentLine.split(SEPARATOR));
			}
		}
	}

	public boolean fixIfInvalidate(String currentLine, BufferedReader reader) {
		return true;
	}

	public static interface ValueTransformer<T> {
		public int getColumn();

		public void transform(String value);
	}

	public abstract class CSVIterator {
		public abstract void onNextRow(CSVRowList element);

		public abstract void defaultProcessorForEachElement(int rowId, int columnId, String value);

		private List<ValueTransformer<?>> valueTransformerList = new ArrayList<ValueTransformer<?>>();

		public List<ValueTransformer<?>> getValueTransformerList() {
			return valueTransformerList;
		}

		private ValueTransformer<?> getById(int id) {
			for (ValueTransformer<?> valueTransformerList : valueTransformerList) {
				if (valueTransformerList.getColumn() == id) {
					return valueTransformerList;
				}
			}
			return null;
		}

		public void start() {
			CSVContainerList list = CSVImport.this.getList();
			for (int i = 0; i < list.size(); i++) {
				onNextRow(list.get(i));
				for (int j = 0; j < list.get(i).size(); j++) {
					ValueTransformer<?> transformer = getById(j);
					if (transformer == null) {
						defaultProcessorForEachElement(i, j, list.get(i).get(j));
					} else {
						transformer.transform(list.get(i).get(j));
					}
				}
			}
		}
	}

	/**
	 * This method merely makes sure that the insertion works as expected
	 */
	public static void main(String[] args) {
		try {
			CSVImport csvImport = new CSVImport();
			csvImport.importCSV(new File("res/sample.csv"), true);

			System.out.println(csvImport.getList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
