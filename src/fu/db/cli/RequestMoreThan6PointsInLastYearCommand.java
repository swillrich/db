package fu.db.cli;

import fu.db.connection.sql.SelectStat;

public class RequestMoreThan6PointsInLastYearCommand extends CLICommand {

	@Override
	public String getDescription() {
		return "Geben Sie alle Filme, die in den letzten Jahren mehr als 6 Bewertungspunkte bekommen haben, in absteigender Reihenfolge aus.";
	}

	@Override
	public void execute() throws Exception {
		new SelectStat()
				.setSql("SELECT * FROM moviedb.movie WHERE rating > 6 AND year > EXTRACT(year FROM CURRENT_DATE) - 1 ORDER BY title DESC;")
				.execute().printResultSet().done();
	}

}
