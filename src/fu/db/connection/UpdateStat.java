package fu.db.connection;

public class UpdateStat extends AbstractStat {

	private int amountOfChanges = -1;

	public void setAmountOfChanges(int amountOfChanges) {
		this.amountOfChanges = amountOfChanges;
	}

	public int getAmountOfChanges() {
		return amountOfChanges;
	}

	public UpdateStat(String sql) {
		super(sql);
	}

	@Override
	public String getPreMessage() {
		return "Update statements will be execute: \n" + getSql();
	}

	@Override
	public String getPostMessage() {
		return null;
	}

}