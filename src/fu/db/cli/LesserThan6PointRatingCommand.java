package fu.db.cli;

import fu.db.connection.sql.SelectStat;

public class LesserThan6PointRatingCommand extends CLICommand {

	@Override
	public String getDescription() {
		return "Welcher Schauspieler spielt nur in Filmen mit, die eine Bewertung von weniger als 6 haben?";
	}

	@Override
	public void execute() throws Exception {
		String sql = "SELECT p.lastname, p.firstname, string_agg(m.title, ', ') FROM moviedb.movie m INNER JOIN moviedb.moviehasactor a ON a.movie = m.id INNER JOIN moviedb.person p ON p.id = a.actor WHERE m.rating < 6 GROUP BY p.lastname, p.firstname LIMIT 30;";
		new SelectStat().setSql(sql).execute().printResultSet().done();
	}

}
