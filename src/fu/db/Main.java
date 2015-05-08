package fu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	public static void main(String[] args) {
		new Main();
	}

	public Main() {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String url = "jdbc:postgresql://localhost:5431/db";
		String user = "fu";
		String password = "fu";

		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM member");

			while (rs.next()) {
				String name = rs.getString(1);
				String nick = rs.getString(2);
				System.out.println("name: " + name + ", nick: " + nick);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Main.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(Main.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
	}
}
