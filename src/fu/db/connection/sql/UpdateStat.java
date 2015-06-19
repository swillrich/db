package fu.db.connection.sql;

public class UpdateStat<T extends UpdateStat> extends AbstractStat<T> {

	private int amountOfChanges = -1;

	public void setAmountOfChanges(int amountOfChanges) {
		this.amountOfChanges = amountOfChanges;
	}

	public int getAmountOfChanges() {
		return amountOfChanges;
	}

	@Override
	public String getPreMessage() {
		return "Update statements will be execute: " + getSql();
	}

	@Override
	public String getPostMessage() {
		return null;
	}

	@Override
	public String generateSQLString() {
		return "";
	}
}
