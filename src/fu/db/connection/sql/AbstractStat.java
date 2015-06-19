package fu.db.connection.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import fu.db.Log;
import fu.db.connection.DBConnection;

public abstract class AbstractStat {
	private Statement statement;
	private String sql;
	private ResultSet resultSet;

	public String getSql() {
		return sql;
	}

	public abstract String getPreMessage();

	public abstract String getPostMessage();

	public ResultSet getResultSet() {
		return resultSet;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public AbstractStat(String sql) {
		this.sql = sql;
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

	public AbstractStat execute() throws SQLException {
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
		return this;
	}

	public void executeAndDone() throws SQLException {
		execute();
		done();
	}

	public AbstractStat printResultSet() {
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String tableName = rsmd.getTableName(1);
			Log.info("*** " + tableName + " ***");

			int numberOfColumns = rsmd.getColumnCount();

			StringBuilder b = new StringBuilder();
			for (int i = 1; i <= numberOfColumns; i++) {
				if (i > 1)
					b.append(",  ");
				String columnName = rsmd.getColumnName(i);
				b.append(columnName);
			}
			Log.info(b.toString());

			while (resultSet.next()) {
				b = new StringBuilder();
				for (int i = 1; i <= numberOfColumns; i++) {
					if (i > 1)
						b.append(",  ");
					String columnValue = resultSet.getString(i);
					b.append(columnValue);
				}
				Log.info(b.toString());
			}
		} catch (SQLException ex) {
			Log.error(ex);
		}
		return this;
	}
}
