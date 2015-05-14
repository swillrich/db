package fu.db.inputres;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties extends Properties {

	public static DBProperties INSTANCE = new DBProperties();

	private DBProperties() {
		try {
			read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void read() throws IOException {
		load(new FileInputStream(new File("postgresql.properties")));
	}
}
