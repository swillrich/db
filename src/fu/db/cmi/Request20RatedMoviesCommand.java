package fu.db.cmi;

import fu.db.connection.sql.SelectStat;

public class Request20RatedMoviesCommand extends CLICommand {

	@Override
	public String getDescription() {
		return "Geben Sie die 20 bestbewertesten Filme aus.";
	}

	@Override
	public void execute() throws Exception {
		SelectStat select = new SelectStat().setSql(
				"SELECT * FROM moviedb.movie ORDER BY rating DESC LIMIT 20;")
				.execute();
		select.printResultSet();
		select.done();
	}

}
