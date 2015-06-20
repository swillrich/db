package fu.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import fu.db.Log;
import fu.db.connection.sql.SelectStat;
import fu.db.domain.Actor;
import fu.db.domain.Director;
import fu.db.domain.Person;

public class PersonDAO {
	public <T extends Person> T findPersonByFirstAndLastName(String firstName,
			String lastName, Class<T> clazz) {
		if (firstName != null) {
			firstName = firstName.replace("'", "''");
		}
		if (lastName != null) {
			lastName = lastName.replace("'", "''");
		}
		String kindOfPerson = "actor";
		if (clazz.equals(Director.class)) {
			kindOfPerson = "director";
		}
		String whereClause = "firstname = '" + firstName + "' AND lastname = '"
				+ lastName + "' AND moviedb.persontype.persontype = 'actor';";
		if (firstName == null && lastName != null) {
			whereClause = "lastname = '" + lastName
					+ "' AND moviedb.persontype.persontype = '" + kindOfPerson
					+ "';";
		} else if (lastName == null && firstName != null) {
			whereClause = "firstname = '" + firstName
					+ "' AND moviedb.persontype.persontype = '" + kindOfPerson
					+ "';";
		} else {
			whereClause = "firstname = '" + firstName + "' AND lastname = '"
					+ lastName + "' AND moviedb.persontype.persontype = '"
					+ kindOfPerson + "';";
		}
		SelectStat select = new SelectStat();
		try {
			select.setSql(
					"SELECT moviedb.person.id FROM moviedb.person INNER JOIN moviedb.persontype ON moviedb.persontype.id = moviedb.person.persontype WHERE "
							+ whereClause).execute();
			System.out.println(select.getSql());
			ResultSet resultSet = select.getResultSet();

			if (resultSet.next()) {
				if (clazz.equals(Director.class)) {
					Director p = new Director();
					p.setId(resultSet.getInt(1));
					p.setForename(firstName);
					p.setLastname(lastName);
					select.done();
					return (T) p;
				} else {
					Actor p = new Actor();
					p.setId(resultSet.getInt(1));
					p.setForename(firstName);
					p.setLastname(lastName);
					select.done();
					return (T) p;
				}

			} else {
				select.done();
			}
		} catch (SQLException e) {
			select.done();
			Log.error(e);
		}
		return null;
	}
}
