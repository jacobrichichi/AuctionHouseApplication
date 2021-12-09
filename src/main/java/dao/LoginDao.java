package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import model.Login;

public class LoginDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */

	private final String DB_URL = "jdbc:mysql://localhost:3306/sys";
	private final String DB_ROOT_USR = "root";
	private final String DB_ROOT_PW = "MyNewPass";

	private final String QUERY_GET_ALL_USERS = "SELECT user FROM mysql.user";

	public Login login(String username, String password) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or
		 * "customer" if successful login Else, return null The role depends on the type
		 * of the user, which has to be handled in the database username, which is the
		 * email address of the user, is given as method parameter password, which is
		 * the password of the user, is given as method parameter Query to verify the
		 * username and password and fetch the role of the user, must be implemented
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			Login login = new Login();
			login.setUsername(username);
			login.setPassword(password);

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT Email FROM Customers WHERE Email LIKE \'%" + username + "%\'");
			while(rs.next()) {
				login.setRole("customer");
				return login;
			}

			rs = st.executeQuery("SELECT E.Email FROM Employees E, CustomerRepresentative R where E.Email LIKE \'%" + username + "%\' AND E.SSN = R.SSN");
			while(rs.next()) {
				login.setRole("customerRepresentative");
				return login;
			}

			rs = st.executeQuery("SELECT E.Email FROM Employees E, Manager R where E.Email LIKE \'%" + username + "%\' AND E.SSN = R.SSN");
			while(rs.next()) {
				login.setRole("manager");
				return login;
			}

			return null;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public String addUser(Login login) {
	    //TODO: Requires validation testing
		/*
		 * Query to insert a new record for user login must be implemented login, which
		 * is the "Login" Class object containing username and password for the new
		 * user, is given as method parameter The username and password from login can
		 * get accessed using getter methods in the "Login" model e.g. getUsername()
		 * method will return the username encapsulated in login object Return "success"
		 * on successful insertion of a new user Return "failure" for an unsuccessful
		 * database operation
		 */

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			Statement st = con.createStatement();
			ResultSet rs = null;

			if(login.getRole().equals("customer"))
				rs = st.executeQuery("SELECT Email FROM Customers where Email LIKE \'%" + login.getUsername() + "%\'");
			else if(login.getRole().equals("customerRepresentative"))
				rs = st.executeQuery("SELECT E.Email FROM Employees E, CustomerRepresentative R where E.Email LIKE \'%" + login.getUsername() + "%\' AND E.SSN = R.SSN");
			else if (login.getRole().equals("manager"))
				rs = st.executeQuery("SELECT E.Email FROM Employees E, Manager R where E.Email LIKE \'%" + login.getUsername() + "%\' AND E.SSN = R.SSN");

			if(rs != null && rs.next()) {
				con.setAutoCommit(false);

				rs = st.executeQuery("CREATE USER \'%" + login.getUsername() + "%\' IDENTIFIED BY \'%" + login.getPassword() + "%\'");

				if(login.getRole().equals("customer"))
					rs = st.executeQuery("GRANT \'customer\' TO \'%" + login.getUsername() + "%\'");
				else if(login.getRole().equals("customerRepresentative"))
					rs = st.executeQuery("GRANT \'customerrep\' TO \'%" + login.getUsername() + "%\'");
				else if (login.getRole().equals("manager"))
					rs = st.executeQuery("GRANT \'manager\' TO \'%" + login.getUsername() + "%\'");

				con.commit();

				return "success";

			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return "failure";
	}

}