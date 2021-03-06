package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Bid;
import model.Customer;

public class BidDao {
	
	private final String DB_URL = "jdbc:mysql://localhost:3306/sys";
	private final String DB_ROOT_USR = "root";
	private final String DB_ROOT_PW = "MyNewPass";

	public List<Bid> getBidHistory(String auctionID) {
		
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * auctionID, which is the Auction's ID, is given as method parameter
		 * Query to get the bid history of an auction, indicated by auctionID, must be implemented
		 */

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM AuctionTransactions T WHERE T.AuctionID = " + auctionID);
			 
			while(rs.next()) {
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID("" + rs.getInt("BidderID"));
				bid.setBidPrice(rs.getInt("BidAmt"));
				bid.setBidTime(rs.getString("BidTime"));
				bids.add(bid);
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data ends*/
		
		return bids;
	}

	public List<Bid> getAuctionHistory(String customerID) {
		
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * customerID, which is the Customer's ID, is given as method parameter
		 * Query to get the bid history of all the auctions in which a customer participated, indicated by customerID, must be implemented
		 */

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);
			Statement st = con.createStatement();
			String s = "SELECT * FROM AuctionTransactions T WHERE T.BidderID = " + customerID;
			ResultSet rs = st.executeQuery(s);
			
			while(rs.next()) {
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID("" + rs.getInt("BidderID"));
				bid.setBidPrice(rs.getInt("BidAmt"));
				bid.setBidTime(rs.getString("BidTime"));
				bids.add(bid);
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return bids;
	}

	public Bid submitBid(String auctionID, String itemID, Float currentBid, Float maxBid, String customerID) {
		
		Bid bid = new Bid();

		/*
		 * The students code to insert data in the database
		 * auctionID, which is the Auction's ID for which the bid is submitted, is given as method parameter
		 * itemID, which is the Item's ID for which the bid is submitted, is given as method parameter
		 * currentBid, which is the Customer's current bid, is given as method parameter
		 * maxBid, which is the Customer's maximum bid for the item, is given as method parameter
		 * customerID, which is the Customer's ID, is given as method parameter
		 * Query to submit a bid by a customer, indicated by customerID, must be implemented
		 * After inserting the bid data, return the bid details encapsulated in "bid" object
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);
			Statement st = con.createStatement();
			
			String s = "INSERT INTO AuctionTransactions(AuctionID, BidderID, BidTime, BidAmt) VALUES(" + auctionID + ", " + customerID + ", CURRENT_TIMESTAMP, " + maxBid + ")";
			int i = st.executeUpdate(s);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
		return bid;
	}

	public List<Bid> getSalesListing(String searchKeyword) {
		
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * searchKeyword, which is the search parameter, is given as method parameter
		 * Query to  produce a list of sales by item name or by customer name must be implemented
		 * The item name or the customer name can be searched with the provided searchKeyword
		 */

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);
			Statement st = con.createStatement();
			String s = "SELECT T.* FROM AuctionTransactions T, Auctions A, Items I WHERE T.AuctionID = A.AuctionID AND A.ItemID = I.ItemID AND I.ItemName like\'%" + searchKeyword + "%\'";
			ResultSet rs = st.executeQuery(s);
			
			while(rs.next()) {
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID("" + rs.getInt("BidderID"));
				bid.setBidPrice(rs.getInt("BidAmt"));
				bid.setBidTime(rs.getString("BidTime"));
				bids.add(bid);
			}
			
			s = "SELECT T.* FROM AuctionTransactions T, Auctions A, Customers C WHERE "
					+ "T.AuctionID = A.AuctionID AND T.BidderID = C.CustomerID "
					+ "AND C.FirstName like\'%" + searchKeyword + "%\' OR C.LastName like\'%" + searchKeyword + "%\'";
			rs = st.executeQuery(s);
			
			while(rs.next()) {
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID("" + rs.getInt("BidderID"));
				bid.setBidPrice(rs.getInt("BidAmt"));
				bid.setBidTime(rs.getString("BidTime"));
				bids.add(bid);
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		/*Sample data ends*/
		
		return bids;
	}

}
