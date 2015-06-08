package fu.db.inputres.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

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

	/**
	 * This method merely makes sure that the insertion works as expected
	 */
	public static void main(String[] args) {
		try {
			CSVContainerList convert = convert(new File("res/sample.csv"), true);
			System.out.println(convert);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
