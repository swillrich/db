package fu.db.inputres.csv;

import java.util.ArrayList;
import java.util.Formatter;

/**
 * @author Sven Willrich
 *
 *         This class is an own or custom structure to save one CSV line. Put
 *         differently, this class represents one CSV line.
 */
public class CSVRowList extends ArrayList<String> {

	private static final int MAX_LENGTH = 15;
	private static final String SUFFIX = ".. ";

	@Override
	public String toString() {
		Formatter formatter = new Formatter();
		String format = new String();
		for (int i = 0; i < this.size(); i++) {
			format = format + "%-" + MAX_LENGTH + "s";
		}

		Object[] arr = new Object[size()];
		for (int i = 0; i < arr.length; i++) {
			if (get(i).length() >= MAX_LENGTH) {
				arr[i] = get(i).substring(0, MAX_LENGTH - SUFFIX.length() - 1)
						.concat(SUFFIX);
			} else {
				arr[i] = get(i);
			}
		}

		return formatter.format(format + "%n", arr).toString();
	}
}