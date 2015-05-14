package fu.db.inputres.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVContainerList extends ArrayList<CSVRowList> {
	private CSVRowList header;

	public CSVRowList getHeader() {
		return header;
	}

	public boolean addNewRow(String[] data) {
		List<String> List = Arrays.asList(data);
		CSVRowList csvRowList = new CSVRowList();
		add(csvRowList);
		csvRowList.addAll(List);
		return super.add(csvRowList);
	}

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
