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
	
	
	public Login login(String username, String password) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */
		
		Login login = new Login();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			
			System.out.println("hey");
			ResultSet rs = st.executeQuery("SELECT * FROM Customers WHERE Email = \'" + username + "\' AND Passwrd = \'" + password + "\'");
			if(rs.next()) {
				System.out.println("here");
				login.setRole("customer");
				System.out.println(username + " " + password);
				login.setUsername(username);
				login.setPassword(password);
			}
			
			else {
				System.out.println("here");
				rs = st.executeQuery("SELECT * FROM Employees WHERE Email = " + username + " AND Paswrd = " + password);
				
				if(rs.next()) {
					login.setUsername(username);
					login.setPassword(password);
					
					int id = rs.getInt("SSN");
					rs = st.executeQuery("SELECT * FROM CustomerRepresentative WHERE SSN = " + id);
					
					if(rs.next()) {
						login.setRole("customerRepresentative");
					}
					else {
						login.setRole("manager");
					}
				}
				else {
					return null;
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		/*Sample data begins*/
		return login;
		/*Sample data ends*/
		
	}
	
	public String addUser(Login login) {
		/*
		 * Query to insert a new record for user login must be implemented
		 * login, which is the "Login" Class object containing username and password for the new user, is given as method parameter
		 * The username and password from login can get accessed using getter methods in the "Login" model
		 * e.g. getUsername() method will return the username encapsulated in login object
		 * Return "success" on successful insertion of a new user
		 * Return "failure" for an unsuccessful database operation
		 */
		
		/*Sample data begins*/
		return "success";
		/*Sample data ends*/
	}

}
