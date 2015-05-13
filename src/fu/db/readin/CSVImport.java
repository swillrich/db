package fu.db.readin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVImport {

	public static String SEPARATOR = ";";

	public static List<List<String>> convert(File file) throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		for (String currentLine; (currentLine = reader.readLine()) != null;) {
			List<String> currentLineList = new ArrayList<String>();
			currentLineList.addAll(Arrays.asList(currentLine.split(SEPARATOR)));
			list.add(currentLineList);
		}
		return list;
	}

	public static void main(String[] args) {

		try {
			List<List<String>> convert = convert(new File("res/sample.csv"));
			System.out.println(convert);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
