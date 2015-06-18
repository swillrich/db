package fu.db.connection;

import java.sql.SQLException;

import fu.db.Log;

public class InsertStat extends UpdateStat {

	private String table;
	private String[] columns;
	private String[] values;

	public InsertStat() {
		super("");
	}

	@Override
	public String getPostMessage() {
		return getAmountOfChanges()
				+ " insertions done by the following statement: " + getSql();
	}

	public InsertStat setColumns(String... columns) {
		this.columns = columns;
		return this;
	}

	public InsertStat setTable(String table) {
		this.table = table;
		return this;
	}

	public InsertStat setValues(String... values) {
		this.values = values;
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].replace("'", "''");
		}
		return this;
	}

	public AbstractStat insertIfNotExists() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(table).append(" WHERE ");
		for (int i = 0; i < columns.length; i++) {
			sql.append(columns[i]).append("=").append("'" + values[i] + "'");
			if (i + 1 < columns.length) {
				sql.append(" AND ");
			}
		}
		sql.append(";");
		Log.info("**********Existence check*********");
		Stat stat = (Stat) new Stat(sql.toString()).execute();
		Log.info("**********Existence check finished*********");
		boolean isEmpty = stat.isResultSetEmpty();
		stat.done();
		if (!isEmpty) {
			Log.error("Execution inpossible, existence check is positive for "
					+ getSQLAsString());
		} else {
			Log.info("Execution possible, existence check is negative for "
					+ getSQLAsString());
			return execute();
		}
		return this;
	}

	@Override
	public AbstractStat execute() throws SQLException {
		setSql(getSQLAsString());
		return super.execute();
	}

	private String getSQLAsString() {
		return "INSERT INTO " + table + " (" + commaSeperate(columns, false)
				+ ") VALUES (" + commaSeperate(values, true) + ")";
	}

	private String commaSeperate(String[] arr, boolean isValue) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (isValue) {
				b.append("'" + arr[i] + "'");
			} else {
				b.append(arr[i]);
			}

			if (i + 1 < arr.length) {
				b.append(", ");
			}
		}
		return b.toString();
	}

}
