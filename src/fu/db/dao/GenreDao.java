package fu.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import fu.db.connection.sql.SelectStat;
import fu.db.domain.Genre;

public class GenreDao {
	public Genre findGenreByName(String name) {

		try {
			SelectStat selectStat = new SelectStat().setTable("moviedb.genre")
					.setColumns("genre").setValues(name).execute();
			ResultSet resultSet = selectStat.getResultSet();
			if (resultSet.next()) {
				Genre g = new Genre();
				g.setName(name);
				g.setId(resultSet.getInt(1));
				return g;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
