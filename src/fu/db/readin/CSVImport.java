package fu.db.readin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVImport {

	public static String SEPARATOR = ";";

	public static CSVContainerList convert(File file, boolean firstRowIsHeader)
			throws IOException {
		CSVContainerList list = new CSVContainerList();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String currentLine;
		if (firstRowIsHeader) {
			currentLine = reader.readLine();
			list.setHeaderData(currentLine.split(SEPARATOR));
		}
		while ((currentLine = reader.readLine()) != null) {
			list.addNewRow(currentLine.split(SEPARATOR));
		}
		return list;
	}

	public static void main(String[] args) {
		try {
			CSVContainerList convert = convert(new File("res/sample.csv"), true);
			System.out.println(convert);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
