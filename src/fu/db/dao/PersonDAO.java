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
			String lastName) {
		if (firstName != null) {
			firstName = firstName.replace("'", "''");
		}
		if (lastName != null) {
			lastName = lastName.replace("'", "''");
		}
		String whereClause = "firstname = '" + firstName + "' AND lastname = '"
				+ lastName + "'";
		if (firstName == null && lastName != null) {
			whereClause = "lastname = '" + lastName + "';";
		} else if (lastName == null && firstName != null) {
			whereClause = "firstname = '" + firstName + "';";
		} else {
			whereClause = "firstname = '" + firstName + "' AND lastname = '"
					+ lastName + "';";
		}
		SelectStat select = new SelectStat();
		try {
			select.setSql(
					"SELECT moviedb.person.id FROM moviedb.person WHERE "
							+ whereClause).execute();
			System.out.println(select.getSql());
			ResultSet resultSet = select.getResultSet();

			if (resultSet.next()) {
				Person p = new Person();
				p.setId(resultSet.getInt(1));
				p.setForename(firstName);
				p.setLastname(lastName);
				select.done();
				return (T) p;
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
