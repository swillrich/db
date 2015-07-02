package fu.db.cli;

import fu.db.connection.sql.SelectStat;

public class RegisseurActorsPreferences extends CLICommand {

	@Override
	public String getDescription() {
		return "Präferieren Regisseure bestimmte Schauspieler?";
	}

	@Override
	public void execute() throws Exception {
		String sql = "SELECT (SELECT lastname FROM moviedb.person WHERE moviedb.person.id = actor), (SELECT lastname FROM moviedb.person WHERE moviedb.person.id = director), count(*) count_movies, string_agg(m.title, ' | ') FROM moviedb.moviehasactor a INNER JOIN moviedb.moviehasdirector d ON d.movie = a.movie INNER JOIN moviedb.movie m ON m.id = d.movie GROUP BY actor, director ORDER BY count(*) DESC LIMIT 30;";
		new SelectStat().setSql(sql).execute().printResultSet().done();
	}

}
