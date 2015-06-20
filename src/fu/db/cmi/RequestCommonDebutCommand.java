package fu.db.cmi;

import fu.db.connection.sql.SelectStat;

public class RequestCommonDebutCommand extends CLICommand {

	@Override
	public String getDescription() {
		return "In welchem Jahr und in welchem Film hatte ein Schauspieler sein Debüt?";
	}

	@Override
	public void execute() throws Exception {
		new SelectStat()
				.setSql("SELECT main.id, main.year, sub.director, p.lastname "
						+ "FROM moviedb.movie main "
						+ "INNER JOIN "
						+ "(SELECT mha.movie as movieId, mhd.director "
						+ "FROM moviedb.moviehasactor mha "
						+ "INNER JOIN moviedb.moviehasdirector mhd ON mhd.director = mha.actor "
						+ "GROUP BY mha.movie, mhd.director) sub "
						+ "ON movieId = main.id "
						+ "INNER JOIN moviedb.person p ON p.id = director;")
				.execute().printResultSet().done();
	}
}
