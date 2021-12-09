package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Auction;
import model.Bid;
import model.Customer;
import model.Item;

public class AuctionDao {
	
	public List<Auction> getAllAuctions() {
		
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the auctions should be implemented
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			System.out.println(1);
			ResultSet rs = st.executeQuery("SELECT * " +
											"FROM Auctions");
			while(rs.next()) {
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("AuctionID"));
				auction.setItemID(rs.getInt("ItemID"));
				auction.setBidIncrement(rs.getInt("Increment"));
				auction.setMinimumBid(rs.getInt("MinimumBid"));
				auction.setMonitor(rs.getInt("Monitor"));
				auction.setClosingBid(rs.getInt("ClosingBid"));
				auction.setCurrentBid(rs.getInt("CurrentBid"));
				auction.setCurrentHighBid(rs.getInt("CurrentHighBid"));
				auction.setReserve(rs.getInt("Reserve"));
				auctions.add(auction);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return auctions;

	}

	public List<Auction> getAuctions(String customerID) {
		
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the auctions in which a customer participated should be implemented
		 * customerID is the customer's primary key, given as method parameter
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			System.out.println(2);
			ResultSet rs = st.executeQuery("SELECT UNIQUE A.* " +
											"FROM Auctions A, AuctionTransactions T " +
											"WHERE A.AuctionID = T.AuctionID AND T.BidderID = " + customerID);
			while(rs.next()) {
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("AuctionID"));
				auction.setItemID(rs.getInt("ItemID"));
				auction.setBidIncrement(rs.getInt("Increment"));
				auction.setMinimumBid(rs.getInt("MinimumBid"));
				//auction.setCopiesSold(rs.getInt("CopiesSold"));
				auction.setMonitor(rs.getInt("Monitor"));
				auction.setClosingBid(rs.getInt("ClosingBid"));
				auction.setCurrentBid(rs.getInt("CurrentBid"));
				auction.setCurrentHighBid(rs.getInt("CurrentHighBid"));
				auction.setReserve(rs.getInt("Reserve"));
				auctions.add(auction);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
		return auctions;

	}

	public List<Auction> getOpenAuctions(String employeeEmail) {
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the open auctions monitored by a customer representative should be implemented
		 * employeeEmail is the email ID of the customer representative, which is given as method parameter
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println(3);
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT A.* FROM Auctions A, Employees E "
											+ "WHERE A.ClosingDateTime > CURRENT_TIMESTAMP "
											+ "AND A.OpeningDateTime < CURRENT_TIMESTAMP "
											+ "AND E.SSN = A.Monitor "
											+ "AND E.Email = \'" + employeeEmail + "\'");
			System.out.print(employeeEmail);			
			
			while(rs.next()) {
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("AuctionID"));
				auction.setItemID(rs.getInt("ItemID"));
				auction.setBidIncrement(rs.getInt("Increment"));
				auction.setMinimumBid(rs.getInt("MinimumBid"));
				//auction.setCopiesSold(rs.getInt("CopiesSold"));
				auction.setMonitor(rs.getInt("Monitor"));
				auction.setClosingBid(rs.getInt("ClosingBid"));
				auction.setCurrentBid(rs.getInt("CurrentBid"));
				auction.setCurrentHighBid(rs.getInt("CurrentHighBid"));
				auction.setReserve(rs.getInt("Reserve"));
				auctions.add(auction);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println(auctions);
		return auctions;

		
		
	}

	public String recordSale(String auctionID) {
		/*
		 * The students code to update data in the database will be written here
		 * Query to record a sale, indicated by the auction ID, should be implemented
		 * auctionID is the Auction's ID, given as method parameter
		 * The method should return a "success" string if the update is successful, else return "failure"
		 */
		/* Sample data begins */
		
		
		return "success";
		/* Sample data ends */
	}

	public List getAuctionData(String auctionID, String itemID) {
		
		List output = new ArrayList();
		Item item = new Item();
		Bid bid = new Bid();
		Auction auction = new Auction();
		Customer customer = new Customer();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The item details are required to be encapsulated as a "Item" class object
		 * The bid details are required to be encapsulated as a "Bid" class object
		 * The auction details are required to be encapsulated as a "Auction" class object
		 * The customer details are required to be encapsulated as a "Customer" class object
		 * Query to get data about auction indicated by auctionID and itemID should be implemented
		 * auctionID is the Auction's ID, given as method parameter
		 * itemID is the Item's ID, given as method parameter
		 * The customer details must include details about the current winner of the auction
		 * The bid details must include details about the current highest bid
		 * The item details must include details about the item, indicated by itemID
		 * The auction details must include details about the item, indicated by auctionID
		 * All the objects must be added in the "output" list and returned
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "MyNewPass");
			Statement st = con.createStatement();
			System.out.println(4);
			ResultSet rs = st.executeQuery("SELECT * "
											+ "FROM Auctions A "
											+ "WHERE A.AuctionID = " + auctionID);
			
			auction.setAuctionID(rs.getInt("AuctionID"));
			auction.setItemID(rs.getInt("ItemID"));
			auction.setBidIncrement(rs.getInt("Increment"));
			auction.setMinimumBid(rs.getInt("MinimumBid"));
			//auction.setCopiesSold(rs.getInt("CopiesSold"));
			auction.setMonitor(rs.getInt("Monitor"));
			auction.setClosingBid(rs.getInt("ClosingBid"));
			auction.setCurrentBid(rs.getInt("CurrentBid"));
			auction.setCurrentHighBid(rs.getInt("CurrentHighBid"));
			auction.setReserve(rs.getInt("Reserve"));
			
			int highestBidderId = rs.getInt("BuyerID");
			int currentBidAmount = auction.getCurrentBid();
			int maxBidAmount = auction.getCurrentHighBid();
			
			
			rs = st.executeQuery("SELECT * "
								+ "FROM AuctionTransactions T "
								+ "WHERE T.AuctionID = " + auctionID
								+ " AND T.BidAmt = " + currentBidAmount);
			
			bid.setAuctionID(Integer.parseInt(auctionID));
			bid.setBidPrice(currentBidAmount);
			bid.setBidTime(rs.getString("BidTime"));
			bid.setCustomerID(rs.getString("BidderID"));
			bid.setMaxBid(maxBidAmount);
			
			
			
			rs = st.executeQuery("SELECT * "
					+ "FROM Items I "
					+ "WHERE I.ItemID = " + itemID);
			
			/*private int itemID;
			private String description;
			private String type;
			private String name;
			private int numCopies;
			private int yearManufactured;
			private int soldPrice;*/
			
			item.setItemID(rs.getInt("ItemID"));
			item.setDescription(rs.getString("Description"));
			item.setType(rs.getString("ItemType"));
			item.setName(rs.getString("ItemName"));
			item.setNumCopies(rs.getInt("NumInStock"));
			item.setYearManufactured(rs.getInt("YearMade"));
			item.setSoldPrice(rs.getInt("NumInStock"));
			
			rs = st.executeQuery("SELECT * FROM Customers C WHERE C.CustomerID = " + highestBidderId);
			
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
		
		
		
		
		output.add(item);
		output.add(bid);
		output.add(auction);
		output.add(customer);
		
		return output;

	}

	
}
