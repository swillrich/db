package fu.db.connection.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.Locale;

import fu.db.Log;
import fu.db.connection.DBConnection;

public abstract class AbstractStat<T extends AbstractStat> {
	private Statement statement;
	private String sql;
	private ResultSet resultSet;

	private String table;
	private String[] columns;
	private Object[] values;

	public String getSql() {
		return sql;
	}

	public abstract String getPreMessage();

	public abstract String getPostMessage();

	public abstract String generateSQLString();

	public String[] getColumns() {
		return columns;
	}

	public String getTable() {
		return table;
	}

	public Object[] getValues() {
		return values;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public T setColumns(String... columns) {
		this.columns = columns;
		return (T) this;
	}

	public T setTable(String table) {
		this.table = table;
		return (T) this;
	}

	public T setValues(Object... values) {
		this.values = values;
		return (T) this;
	}

	public Statement getStatement() {
		return statement;
	}

	public T setSql(String sql) {
		this.sql = sql;
		return (T) this;
	}

	public void done() {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}
	}

	public void doneWithConnection() {
		done();
		try {
			DBConnection.getINSTANCE().getConnection().close();
		} catch (SQLException e) {
			Log.error(e);
		}
	}

	public void treatColumnValuesForInsertion() {
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof String) {
				values[i] = ((String) values[i]).replace("'", "''");
			}
		}
	}

	public T execute() throws SQLException {
		if (sql == null) {
			treatColumnValuesForInsertion();
			sql = generateSQLString();
		}

		if (getPreMessage() != null) {
			Log.info(getPreMessage());
		}
		statement = DBConnection.getINSTANCE().getConnection()
				.createStatement();
		try {
			if (this instanceof UpdateStat) {
				int executeUpdate = statement.executeUpdate(sql);
				((UpdateStat) this).setAmountOfChanges(executeUpdate);
			} else {
				if (!statement.execute(sql)) {
					throw new SQLException("Result of execution is: false");
				}
			}
			resultSet = statement.getResultSet();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			if (getPostMessage() != null) {
				Log.info(getPostMessage() + "\n");
			}
		}
		return (T) this;
	}

	public T executeAndDone() throws SQLException {
		execute();
		done();
		return (T) this;
	}

	private String format(StringBuilder sb, boolean underline) {
		int maxLengthPerCell = 20;
		String[] split = sb.toString().split("\\|");
		for (int i = 0; i < split.length; i++) {
			split[i] = split[i].length() > maxLengthPerCell ? split[i]
					.substring(0, maxLengthPerCell - 3).concat("...") : split[i];
		}
		StringBuilder format = new StringBuilder();
		for (int i = 0; i < split.length; i++) {
			format.append("%-" + (maxLengthPerCell + 2) + "s");
		}
		String result = String.format(format.toString(), split);
		if (underline) {
			result += "\n";
			int length = result.length();
			for (int i = 0; i < length; i++) {
				result += "=";
			}
		}
		return result;
	}

	public T printResultSet() {
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String tableName = rsmd.getTableName(1);
			Log.info("\n*** " + tableName + " ***");

			int numberOfColumns = rsmd.getColumnCount();

			StringBuilder b = new StringBuilder();
			for (int i = 1; i <= numberOfColumns; i++) {
				if (i > 1)
					b.append("|");
				String columnName = rsmd.getColumnName(i);
				b.append(columnName);
			}
			Log.info(format(b, true));

			while (resultSet.next()) {
				b = new StringBuilder();
				for (int i = 1; i <= numberOfColumns; i++) {
					if (i > 1)
						b.append("|");
					String columnValue = resultSet.getString(i);
					b.append(columnValue);
				}
				Log.info(format(b, false));
			}
		} catch (SQLException ex) {
			Log.error(ex);
		}
		return (T) this;
	}

	String commaSeperate(Object[] arr, boolean isValue) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (isValue) {
				if (arr[i] instanceof String) {
					b.append("'" + arr[i] + "'");
				} else {
					b.append(arr[i]);
				}
			} else {
				b.append(arr[i]);
			}

			if (i + 1 < arr.length) {
				b.append(", ");
			}
		}
		return b.toString();
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
