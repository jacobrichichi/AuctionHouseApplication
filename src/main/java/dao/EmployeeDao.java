package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;

public class EmployeeDao {
	/*
	 * This class handles all the database operations related to the employee table
	 */
	
	public String addEmployee(Employee employee) {

		/*
		 * All the values of the add employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the employee details and return "success" or "failure" based on result of the database insertion.
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			int i = st.executeUpdate("INSERT INTO Employees (SSN,"
					+ "                LastName,"
					+ "                FirstName,"
					+ "                Address,"
					+ "                City,"
					+ "                State,"
					+ "                ZipCode,"
					+ "                Telephone,"
					+ "                StartDate, "
					+ "				   HourlyRate, Email) "
					+ "VALUES (" + employee.getEmployeeID() + "," + employee.getLastName() + "," + 
					employee.getFirstName() + "," + employee.getAddress() + ", " + employee.getCity() + "," + employee.getState() + "," +
					employee.getZipCode() + ","  + employee.getTelephone() + ", " + employee.getStartDate() + "," + employee.getHourlyRate() + "," + employee.getEmail());
			
			if(i != 1) {
				return "failure";
			}
			
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		
		return "success";
		/*Sample data ends*/

	}

	public String editEmployee(Employee employee) {
		/*
		 * All the values of the edit employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			
			int i = st.executeUpdate("UPDATE Employees SET LastName = " + employee.getLastName() + 
					" FirstName = " + employee.getFirstName() + " Address = " + employee.getAddress() + " City = " + employee.getCity() +
					" State = " + employee.getState() + " ZipCode = " + employee.getZipCode() + " Telephone = " + employee.getTelephone() +
					" StartDate = " + employee.getStartDate() + " HourlyRate = " + employee.getHourlyRate() + " Email = " + employee.getEmail()
					+ " WHERE EmployeeID = " + employee.getEmployeeID());
			
			if(i!=0) {
				return "failure";
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return "success";
		/*Sample data ends*/

	}

	public String deleteEmployee(String employeeID) {
		/*
		 * employeeID, which is the Employee's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		
		/*Sample data begins*/
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			int deleted = st.executeUpdate("DELETE " +
											"FROM Employees E " +
											"WHERE E.SSN = " + employeeID);
			if(deleted == 0) {
				return "failure";
			}
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return "success";
		/*Sample data ends*/

	}

	
	public List<Employee> getEmployees() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to return details about all the employees must be implemented
		 * Each record is required to be encapsulated as a "Employee" class object and added to the "employees" List
		 */

		List<Employee> employees = new ArrayList<Employee>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * " +
											"FROM Employees ");
			ResultSet rs2;
			while(rs.next()) {
				Employee employee = new Employee();
				employee.setEmployeeID(rs.getString("EmployeeId"));
				employee.setAddress(rs.getString("Address"));
				employee.setLastName(rs.getString("LastName"));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setCity(rs.getString("City"));
				employee.setState(rs.getString("State"));
				employee.setEmail(rs.getString("Email"));
				employee.setZipCode(rs.getInt("ZipCode"));
				employee.setTelephone(rs.getString("Telephone"));
				employee.setStartDate(rs.getString("CreditCard"));
				employee.setHourlyRate(rs.getInt("Rating"));
				
				rs2 = st.executeQuery("SELECT * FROM CustomerRepresentatives WHERE SSN = " + employee.getEmployeeID());
				if(rs2.next()) {
					employee.setLevel("CustomerRepresentative");
				}
				else {
					employee.setLevel("Manager");
				}
				
				
				employees.add(employee);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return employees;
	}

	public Employee getEmployee(String employeeID) {

		/*
		 * The students code to fetch data from the database based on "employeeID" will be written here
		 * employeeID, which is the Employee's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Employee" class object
		 */

		Employee employee = new Employee();
		ResultSet rs2;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * " +
											"FROM Employees E " +
											"WHERE E.EmployeeId = " + employeeID);
			
			employee.setEmployeeID(rs.getString("SSN"));
			employee.setAddress(rs.getString("Address"));
			employee.setLastName(rs.getString("LastName"));
			employee.setFirstName(rs.getString("FirstName"));
			employee.setCity(rs.getString("City"));
			employee.setState(rs.getString("State"));
			employee.setEmail(rs.getString("Email"));
			employee.setZipCode(rs.getInt("ZipCode"));
			employee.setTelephone(rs.getString("Telephone"));
			employee.setStartDate(rs.getString("StartDate"));
			employee.setHourlyRate(rs.getInt("HourlyRate"));
			
			rs2 = st.executeQuery("SELECT * FROM CustomerRepresentatives WHERE SSN = " + employee.getEmployeeID());
			if(rs2.next()) {
				employee.setLevel("CustomerRepresentative");
			}
			else {
				employee.setLevel("Manager");
			}
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return employee;
	}
	
	public Employee getHighestRevenueEmployee() {
		
		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */
		
		Employee employee = new Employee();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT E.*, R.Revenue FROM Employees C, RevenueByEmployee R "
											+ "WHERE E.SSN = R.SSN "
											+ "AND R.Revenue = (SELECT MAX(B.Revenue) FROM RevenueByEmployee B)");
			
			employee.setEmployeeID(rs.getString("SSN"));
			employee.setAddress(rs.getString("Address"));
			employee.setLastName(rs.getString("LastName"));
			employee.setFirstName(rs.getString("FirstName"));
			employee.setCity(rs.getString("City"));
			employee.setState(rs.getString("State"));
			employee.setEmail(rs.getString("Email"));
			employee.setZipCode(rs.getInt("ZipCode"));
			employee.setTelephone(rs.getString("Telephone"));
			employee.setStartDate(rs.getString("StartDate"));
			employee.setHourlyRate(rs.getInt("HourlyRate")); 
			employee.setLevel("CustomerRepresentative");
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return employee;
	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */
		String id = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT SSN " +
											"FROM Employee E " +
											"WHERE E.email = " + username);
			id = rs.getString("SSN");
		}
		catch(Exception e) {
			System.out.println(e);
		}

		return "111-11-1111";
	}

}
