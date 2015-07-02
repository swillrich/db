package fu.db.cli;

import fu.db.connection.sql.SelectStat;

public class MostMoviesCommand extends CLICommand {

	@Override
	public String getDescription() {
		return "Welcher Regisseur hat in einem bestimmten Jahr die meisten Filme gedreht?";
	}

	@Override
	public void execute() throws Exception {
		String sql = "SELECT year, director, count(*), p.lastname FROM moviedb.movie m INNER JOIN moviedb.moviehasdirector d ON d.movie = m.id INNER JOIN moviedb.person p ON p.id = d.director GROUP BY m.year, d.director, p.lastname ORDER BY count(*) DESC LIMIT 30;";
		new SelectStat().setSql(sql).execute().printResultSet().done();
	}
}
