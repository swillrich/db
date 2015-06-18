package fu.db.connection;

import java.sql.SQLException;

import fu.db.Log;

public class Stat extends AbstractStat {

	public Stat(String sql) {
		super(sql);
	}

	@Override
	public String getPreMessage() {
		return "Non-update Statements will be execute: \n" + getSql();
	}

	@Override
	public String getPostMessage() {
		return null;
	}

	public boolean isResultSetEmpty() {
		try {
			return !getResultSet().next();
		} catch (SQLException e) {
			Log.error(e);
		}
		return false;
	}

}
