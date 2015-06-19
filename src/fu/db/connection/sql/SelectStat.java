package fu.db.connection.sql;

public class SelectStat extends AbstractStat<SelectStat> {
	public SelectStat(String sql) {
		super(sql);
	}

	public SelectStat() {
		super("");
	}

	@Override
	public String getPreMessage() {
		return "Non-update Statements will be execute: \n" + getSql();
	}

	@Override
	public String getPostMessage() {
		return null;
	}

	@Override
	public String generateSQLString() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ").append(getTable()).append(" WHERE ");
		for (int i = 0; i < getColumns().length; i++) {
			sql.append(getColumns()[i]).append("=")
					.append("'" + getValues()[i] + "'");
			if (i + 1 < getColumns().length) {
				sql.append(" AND ");
			}
		}
		sql.append(";");
		return sql.toString();
	}
}
