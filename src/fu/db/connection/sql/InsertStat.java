package fu.db.connection.sql;

import java.sql.SQLException;

import fu.db.Log;

public class InsertStat extends UpdateStat<InsertStat> {

	public InsertStat() {
		super("");
	}

	@Override
	public String getPostMessage() {
		return getAmountOfChanges()
				+ " insertions done by the following statement: " + getSql();
	}

	public InsertStat insertIfNotExists() throws SQLException {
		Log.info("**********Existence check*********");
		SelectStat stat = new SelectStat().setColumns(getColumns())
				.setTable(getTable()).setValues(getValues()).execute();
		Log.info("**********Existence check finished*********");
		boolean isEmpty = stat.isResultSetEmpty();
		stat.done();
		if (!isEmpty) {
			Log.error("Execution inpossible, existence check is positive for "
					+ generateSQLString());
		} else {
			Log.info("Execution possible, existence check is negative for "
					+ generateSQLString());
			return execute();
		}
		return this;
	}

	@Override
	public String generateSQLString() {
		return "INSERT INTO " + getTable() + " ("
				+ commaSeperate(getColumns(), false) + ") VALUES ("
				+ commaSeperate(getValues(), true) + ")";
	}
}
