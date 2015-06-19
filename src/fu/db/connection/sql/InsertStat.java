package fu.db.connection.sql;

import java.sql.SQLException;

import fu.db.Log;

public class InsertStat extends UpdateStat<InsertStat> {

	@Override
	public String getPostMessage() {
		return getAmountOfChanges()
				+ " insertions done by the following statement: " + getSql();
	}

	@Override
	public String getPreMessage() {
		return null;
	}

	public InsertStat insertIfNotExists() throws SQLException {
		SelectStat stat = new SelectStat().setColumns(getColumns())
				.setTable(getTable()).setValues(getValues()).execute();
		boolean isEmpty = stat.isResultSetEmpty();
		stat.done();
		if (!isEmpty) {
			Log.error("Insertion would lead to violation, the statement performs to a non-empty result: "
					+ stat.getSql());
		} else {
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
