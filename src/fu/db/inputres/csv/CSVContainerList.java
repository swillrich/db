package fu.db.inputres.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sven Willrich
 *
 *         This class is used to hold the whole CSV file in cache
 */
public class CSVContainerList extends ArrayList<CSVRowList> {
	/**
	 * When the CSV file is with header/titles for the specific columns, this
	 * variable is competed for holding this
	 */
	private CSVRowList header;

	public CSVRowList getHeader() {
		return header;
	}

	/**
	 * This method adds an new CSV line
	 * 
	 * @param data
	 *            the data to add
	 * @return whether the insertion was successfully or not
	 */
	public boolean addNewRow(String[] data) {
		List<String> List = Arrays.asList(data);
		CSVRowList csvRowList = new CSVRowList();
		csvRowList.addAll(List);
		return super.add(csvRowList);
	}

	/**
	 * This method adds a header for the given CSV file
	 * 
	 * @param data
	 *            the CSV titles
	 */
	public void setHeaderData(String[] data) {
		List<String> List = Arrays.asList(data);
		CSVRowList newRow = new CSVRowList();
		newRow.addAll(List);
		this.header = newRow;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (header != null) {
			b.append(header.toString());
			b.append(b.toString().replaceAll(".", "="));
		}
		for (CSVRowList element : this) {
			b.append(element);
		}
		return b.toString();
	}
}
