package fu.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		try {
			Statement st = DBConnection.getINSTANCE().getNewStat();
			ResultSet rs = st.executeQuery("SELECT * FROM member");

			while (rs.next()) {
				String name = rs.getString(1).trim();
				String nick = rs.getString(2).trim();
				System.out.println("name: " + name + ", nick: " + nick);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
