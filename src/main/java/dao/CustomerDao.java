package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Customer;

import java.util.stream.IntStream;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */
	
	public static void main(String [] args) {
		System.out.print(getCustomerMailingList());
	}
	
	/**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers(String searchKeyword) {
		/*
		 * This method fetches one or more customers based on the searchKeyword and returns it as an ArrayList
		 */
		
		List<Customer> customers = new ArrayList<Customer>();
		
		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		/*Sample data begins*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			
			String s = "SELECT * " +
					"FROM Customers " +
					"WHERE FirstName like\'%" + searchKeyword + "%\'"+
					" or LastName like \'%" + searchKeyword + "%\'";
			
			if(searchKeyword == null) {
				s = "SELECT * FROM Customers";
			}
			
			ResultSet rs = st.executeQuery(s);
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerId"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("Email"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		/*for (int i = 0; i < 10; i++) {
			Customer customer = new Customer();
			customer.setCustomerID("111-11-1111");
			customer.setAddress("123 Success Street");
			customer.setLastName("Lu");
			customer.setFirstName("Shiyong");
			customer.setCity("Stony Brook");
			customer.setState("NY");
			customer.setEmail("shiyong@cs.sunysb.edu");
			customer.setZipCode(11790);
			customer.setTelephone("5166328959");
			customer.setCreditCard("1234567812345678");
			customer.setRating(1);
			customers.add(customer);			
		}*/
		/*Sample data ends*/
		
		return customers;
	}


	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */


		/*Sample data begins*/
		
		Customer customer = new Customer();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT C.*, R.Revenue FROM Customers C, RevenueByCustomer R "
											+ "WHERE C.CustomerID = R.SellerID "
											+ "AND R.Revenue = (SELECT MAX(B.Revenue) FROM RevenueByCustomer B)");
			
			customer.setCustomerID(rs.getString("CustomerId"));
			customer.setAddress(rs.getString("Address"));
			customer.setLastName(rs.getString("LastName"));
			customer.setFirstName(rs.getString("FirstName"));
			customer.setCity(rs.getString("City"));
			customer.setState(rs.getString("State"));
			customer.setEmail(rs.getString("Email"));
			customer.setZipCode(rs.getInt("ZipCode"));
			customer.setTelephone(rs.getString("Telephone"));
			customer.setCreditCard(rs.getString("CreditCardNum"));
			customer.setRating(rs.getInt("Rating"));
			
			return customer;
			
		}
		
		catch(Exception e) {
			System.out.println(e);
		}
		
		
		/*Customer customer = new Customer();
		customer.setCustomerID("111-11-1111");
		customer.setLastName("Lu");
		customer.setFirstName("Shiyong");
		customer.setEmail("shiyong@cs.sunysb.edu");*/
		/*Sample data ends*/
	
		return customer;
		
	}

	public static List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 * Each customer record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * " +
											"FROM Customers");
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerId"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("Email"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		/*Sample data begins*/
		/*for (int i = 0; i < 10; i++) {
			Customer customer = new Customer();
			customer.setCustomerID("111-11-1111");
			customer.setAddress("123 Success Street");
			customer.setLastName("Lu");
			customer.setFirstName("Shiyong");
			customer.setCity("Stony Brook");
			customer.setState("NY");
			customer.setEmail("shiyong@cs.sunysb.edu");
			customer.setZipCode(11790);
			customers.add(customer);			
		}*/
		/*Sample data ends*/
		
		return customers;
	}

	public Customer getCustomer(String customerID) {

		Customer customer = new Customer();
		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		
		/*Sample data begins*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * " +
											"FROM Customers C " +
											"WHERE C.CustomerId = " + customerID);
			
			customer.setCustomerID(rs.getString("CustomerId"));
			customer.setAddress(rs.getString("Address"));
			customer.setLastName(rs.getString("LastName"));
			customer.setFirstName(rs.getString("FirstName"));
			customer.setCity(rs.getString("City"));
			customer.setState(rs.getString("State"));
			customer.setEmail(rs.getString("Email"));
			customer.setZipCode(rs.getInt("ZipCode"));
			customer.setTelephone(rs.getString("Telephone"));
			customer.setCreditCard(rs.getString("CreditCardNum"));
			customer.setRating(rs.getInt("Rating"));
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		/*Customer customer = new Customer();
		customer.setCustomerID("111-11-1111");
		customer.setAddress("123 Success Street");
		customer.setLastName("Lu");
		customer.setFirstName("Shiyong");
		customer.setCity("Stony Brook");
		customer.setState("NY");
		customer.setEmail("shiyong@cs.sunysb.edu");
		customer.setZipCode(11790);
		customer.setTelephone("5166328959");
		customer.setCreditCard("1234567812345678");
		customer.setRating(1);*/
		/*Sample data ends*/
		
		return customer;
	}
	
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			int deleted = st.executeUpdate("DELETE " +
											"FROM Customers C " +
											"WHERE C.CustomerId = " + customerID);
			if(deleted == 0) {
				return "failure";
			}
			
			
		}
		catch(Exception e) {
			System.out.println(e);
		}

		/*Sample data begins*/
		return "success";
		/*Sample data ends*/
		
	}


	public String getCustomerID(String username) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */
		
		String id = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT C.CustomerID " +
											"FROM Customers C " +
											"WHERE C.Email = \'" + username + "\'");
			rs.next();
			id = rs.getString("CustomerID");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		

		return id;
	}


	public List<Customer> getSellers() {
		
		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINCT C.* FROM Customers C, Auctions A "
					+ "WHERE C.CustomerID = A.SellerID");
			
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerId"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("Email"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return customers;

	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		
		/*Sample data begins*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			
			String s = "INSERT INTO Customers (CustomerID,Address,LastName,FirstName,City,State,ZipCode,Email,Passwrd,Rating)\n\r "
					+ "VALUES (" + customer.getCustomerID() + ",\'" + customer.getAddress() + "\',\'" + customer.getLastName() + "\',\'" + 
					customer.getFirstName() + "\',\'" + customer.getCity() + "\',\'" + customer.getState() + "\',\'" +
					customer.getZipCode() + "\',\'" + customer.getEmail() + "\',\'\',"  + customer.getRating();
			System.out.println(s);
			int i = st.executeUpdate("INSERT INTO Customers (CustomerID,Address,LastName,FirstName,City,State,ZipCode,Email,Passwrd,Rating) "
					+ "VALUES (" + customer.getCustomerID() + ",\'" + customer.getAddress() + "\',\'" + customer.getLastName() + "\',\'" + 
					customer.getFirstName() + "\',\'" + customer.getCity() + "\',\'" + customer.getState() + "\',\'" +
					customer.getZipCode() + "\',\'" + customer.getEmail() + "\',\'\',"  + customer.getRating() + ")");
			
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

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			
			int i = st.executeUpdate("UPDATE Customers SET LastName = " + customer.getLastName() +
					" FirstName = " + customer.getFirstName() + " Address = " + customer.getAddress() + " City = " + customer.getCity() +
					" State = " + customer.getState() + " ZipCode = " + customer.getZipCode() + " Telephone = " + customer.getTelephone() +
					" Email = " + customer.getEmail() + " CreditCardNum = " + customer.getCreditCard() + " Rating = " + customer.getRating()
					+ " WHERE CustomerID = " + customer.getCustomerID());
			
			if(i!=0) {
				return "failure";
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		/*Sample data begins*/
		return "success";
		/*Sample data ends*/

	}

}
