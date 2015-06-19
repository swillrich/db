package fu.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import fu.db.connection.sql.SelectStat;
import fu.db.domain.Person;

public class PersonDAO {
	public Person findActorByFirstAndLastName(String firstName, String lastName) {
		SelectStat select = new SelectStat()
				.setSql("SELECT moviedb.person.id FROM moviedb.person INNER JOIN moviedb.persontype ON moviedb.persontype.id = moviedb.person.persontype WHERE firstname = '"
						+ firstName
						+ "' AND lastname = '"
						+ lastName
						+ "' AND moviedb.persontype.persontype = 'actor';");
		ResultSet resultSet = select.getResultSet();
		try {
			if (resultSet.next()) {
				Person p = new Person();
				p.setId(resultSet.getInt(1));
				p.setForename(firstName);
				p.setLastname(lastName);
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
