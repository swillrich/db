package fu.db.readin;

import java.util.ArrayList;
import java.util.Formatter;

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