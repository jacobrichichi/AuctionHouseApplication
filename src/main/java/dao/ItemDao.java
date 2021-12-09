package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.sql.CallableStatement;

import model.Auction;
import model.Bid;
import model.Item;

public class ItemDao {
	private final String DB_URL = "jdbc:mysql://localhost:3306/cse305db";
	private final String DB_ROOT_USR = "root";
	private final String DB_ROOT_PW = "cse305";

	public List<Item> getItems() {

		/*
		 * The students code to fetch data from the database will be written here Query
		 * to fetch details of all the items has to be implemented Each record is
		 * required to be encapsulated as a "Item" class object and added to the "items"
		 * List
		 */

		List<Item> items = new ArrayList<Item>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Items");

			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setName(rs.getString("ItemName"));
				item.setType(rs.getString("ItemType"));
				item.setDescription(rs.getString("Description"));
				item.setNumCopies(rs.getInt("NumInStock"));
				items.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;

	}

	public List<Item> getBestsellerItems() {

		/*
		 * The students code to fetch data from the database will be written here Query
		 * to fetch details of the bestseller items has to be implemented Each record is
		 * required to be encapsulated as a "Item" class object and added to the "items"
		 * List
		 */

		List<Item> items = new ArrayList<Item>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM bestsellers");

			while (rs.next()) {
				Item item = new Item();
				item.setItemID(rs.getInt("ItemID"));
				item.setName(rs.getString("ItemName"));
				item.setType(rs.getString("ItemType"));
				item.setDescription(rs.getString("Description"));
				item.setNumCopies(rs.getInt("NumInStock"));
				items.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;

	}

	public List<Item> getSummaryListing(String searchKeyword) {
		// TODO: Might need the customer search keyword as well. Needs validation
		/*
		 * The students code to fetch data from the database will be written here Query
		 * to fetch details of summary listing of revenue generated by a particular item
		 * or item type must be implemented Each record is required to be encapsulated
		 * as a "Item" class object and added to the "items" ArrayList Store the revenue
		 * generated by an item in the soldPrice attribute, using setSoldPrice method of
		 * each "item" object
		 */

		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);
			
			Statement salesByItemsST = con.createStatement();
			ResultSet salesByItems = salesByItemsST.executeQuery("SELECT * FROM salesbyitem WHERE ItemName REGEXP \'"
					+ searchKeyword + "\' OR ItemType REGEXP \'" + searchKeyword + "\'");
			
			while(salesByItems.next()) {
				Statement getItemST = con.createStatement();
				ResultSet itemRS = getItemST.executeQuery("SELECT * FROM Items WHERE ItemID=" + salesByItems.getInt("ItemID"));
				itemRS.next();
				
				Item item = new Item();
				item.setItemID(salesByItems.getInt("ItemID"));
				item.setDescription(itemRS.getString("Description"));
				item.setType(salesByItems.getString("ItemType"));
				item.setName(salesByItems.getString("ItemName"));
				item.setSoldPrice(salesByItems.getInt("ClosingBid"));
				items.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;

	}

	public List<Item> getItemSuggestions(String customerID) {
		// TODO: Needs more validation testing
		/*
		 * The students code to fetch data from the database will be written here Query
		 * to fetch item suggestions for a customer, indicated by customerID, must be
		 * implemented customerID, which is the Customer's ID for whom the item
		 * suggestions are fetched, is given as method parameter Each record is required
		 * to be encapsulated as a "Item" class object and added to the "items"
		 * ArrayList
		 */

		List<Item> items = new ArrayList<Item>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			CallableStatement cs = con.prepareCall("{CALL PersonalizedItemSuggestion(?)}");
			cs.setInt("CustomerID", Integer.parseInt(customerID));
//			cs.setInt("CustomerID", 5);

			boolean hadResults = cs.execute();
			while (hadResults) {

				ResultSet rs = cs.getResultSet();
				List<String> favoriteTypes = new ArrayList<String>();
				while (rs.next()) {
					favoriteTypes.add(rs.getString("ItemType"));
				}

				Statement st = con.createStatement();
				for (String itemType : favoriteTypes) {
					rs = st.executeQuery("SELECT * FROM Items WHERE ItemType=\'" + itemType + "\'");

					while (rs.next()) {
						Item item = new Item();
						item.setItemID(rs.getInt("ItemID"));
						item.setName(rs.getString("ItemName"));
						item.setType(rs.getString("ItemType"));
						item.setDescription(rs.getString("Description"));
						item.setNumCopies(rs.getInt("NumInStock"));
						items.add(item);
					}
				}

				hadResults = cs.getMoreResults();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;

	}

	public List getItemsBySeller(String sellerID) {
		// TODO: Needs more validation testing
		/*
		 * The students code to fetch data from the database will be written here Query
		 * to fetch items sold by a given seller, indicated by sellerID, must be
		 * implemented sellerID, which is the Sellers's ID who's items are fetched, is
		 * given as method parameter The bid and auction details of each of the items
		 * should also be fetched The bid details must have the highest current bid for
		 * the item The auction details must have the details about the auction in which
		 * the item is sold Each item record is required to be encapsulated as a "Item"
		 * class object and added to the "items" List Each bid record is required to be
		 * encapsulated as a "Bid" class object and added to the "bids" List Each
		 * auction record is required to be encapsulated as a "Auction" class object and
		 * added to the "auctions" List The items, bids and auctions Lists have to be
		 * added to the "output" List and returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Bid> bids = new ArrayList<Bid>();
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			CallableStatement itemsBeingSoldProc = con.prepareCall("{CALL ItemsBeingSoldBySeller(?)}");
			Statement getAuctionsST = con.createStatement();
			Statement getItemsST = con.createStatement();
			CallableStatement auctionBidHistoryProc = con.prepareCall("{CALL AuctionBidHistory(?)}");

			itemsBeingSoldProc.setInt("SellerID", Integer.parseInt(sellerID));
			boolean sellerHasMoreItems = itemsBeingSoldProc.execute();

			while (sellerHasMoreItems) {
				ResultSet auctionShortInfo = itemsBeingSoldProc.getResultSet();

				while (auctionShortInfo.next()) {

					ResultSet auctionRS = getAuctionsST.executeQuery(
							"SELECT * FROM Auctions WHERE AuctionID=" + auctionShortInfo.getInt("AuctionID"));
					auctionRS.next();
					ResultSet itemRS = getItemsST
							.executeQuery("SELECT * FROM Items WHERE ItemID=" + auctionRS.getInt("ItemID"));
					itemRS.next();

					Item item = new Item();
					item.setItemID(itemRS.getInt("ItemID"));
					item.setName(itemRS.getString("ItemName"));
					item.setType(itemRS.getString("ItemType"));
					item.setDescription(itemRS.getString("Description"));
					item.setNumCopies(itemRS.getInt("NumInStock"));
					items.add(item);

					Auction auction = new Auction();
					auction.setAuctionID(auctionRS.getInt("AuctionID"));
					auction.setMinimumBid(auctionRS.getFloat("MinimumBid"));
					auction.setBidIncrement(auctionRS.getFloat("Increment"));
					auctions.add(auction);

					auctionBidHistoryProc.setInt("AuctionID", auctionShortInfo.getInt("AuctionID"));
					boolean auctionHasMoreHistory = auctionBidHistoryProc.execute();

					String maxBidderID = "";
					int maxBid = 0;

					while (auctionHasMoreHistory) {
						ResultSet auctionHistory = auctionBidHistoryProc.getResultSet();

						while (auctionHistory.next()) {
							String bidderID = auctionHistory.getString("BidderID");
							int bidAmt = auctionHistory.getInt("BidAmt");

							if (bidAmt > maxBid) {
								maxBidderID = bidderID;
								maxBid = bidAmt;
							}
						}

						auctionHasMoreHistory = auctionBidHistoryProc.getMoreResults();
					}
					Bid bid = new Bid();
					bid.setCustomerID(maxBidderID);
					bid.setBidPrice(maxBid);
					bids.add(bid);
				}

				sellerHasMoreItems = itemsBeingSoldProc.getMoreResults();
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		output.add(items);
		output.add(bids);
		output.add(auctions);

		return output;
	}

	public List<Item> getItemTypes() {

		/*
		 * The students code to fetch data from the database will be written here Each
		 * record is required to be encapsulated as a "Item" class object and added to
		 * the "items" ArrayList A query to fetch the unique item types has to be
		 * implemented Each item type is to be added to the "item" object using setType
		 * method
		 */

		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT DISTINCT ItemType FROM Items");

			while (rs.next()) {
				Item item = new Item();
				item.setType(rs.getString("ItemType"));
				items.add(item);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return items;
	}

	public List getItemsByName(String itemName) {
		// TODO: Needs more validation testing
		/*
		 * The students code to fetch data from the database will be written here The
		 * itemName, which is the item's name on which the query has to be implemented,
		 * is given as method parameter Query to fetch items containing itemName in
		 * their name has to be implemented Each item's corresponding auction data also
		 * has to be fetched Each item record is required to be encapsulated as a "Item"
		 * class object and added to the "items" List Each auction record is required to
		 * be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and
		 * returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			CallableStatement availableAuctionsProc = con.prepareCall("{CALL AuctionsAvailableByKeywords(?)}");
			availableAuctionsProc.setString("keywords", itemName);

			boolean hadResults = availableAuctionsProc.execute();
			while (hadResults) {

				ResultSet availableAuctions = availableAuctionsProc.getResultSet();
				while (availableAuctions.next()) {
					Statement getAuctionST = con.createStatement();
					ResultSet auctionRS = getAuctionST.executeQuery(
							"SELECT * FROM Auctions WHERE AuctionID=" + availableAuctions.getString("AuctionID"));
					auctionRS.next();

					Statement getItemST = con.createStatement();
					ResultSet itemRS = getItemST
							.executeQuery("SELECT * from Items WHERE ItemID=" + auctionRS.getString("ItemID"));
					itemRS.next();

					Item item = new Item();
					item.setItemID(itemRS.getInt("ItemID"));
					item.setName(itemRS.getString("ItemName"));
					item.setType(itemRS.getString("ItemType"));
					item.setDescription(itemRS.getString("Description"));
					item.setNumCopies(itemRS.getInt("NumInStock"));
					items.add(item);

					Auction auction = new Auction();
					auction.setAuctionID(auctionRS.getInt("AuctionID"));
					auction.setMinimumBid(auctionRS.getFloat("MinimumBid"));
					auction.setBidIncrement(auctionRS.getFloat("Increment"));
					auctions.add(auction);

				}

				hadResults = availableAuctionsProc.getMoreResults();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		output.add(items);
		output.add(auctions);

		return output;
	}

	public List getItemsByType(String itemType) {
		// TODO: Needs more validation testing
		/*
		 * The students code to fetch data from the database will be written here The
		 * itemType, which is the item's type on which the query has to be implemented,
		 * is given as method parameter Query to fetch items containing itemType as
		 * their type has to be implemented Each item's corresponding auction data also
		 * has to be fetched Each item record is required to be encapsulated as a "Item"
		 * class object and added to the "items" List Each auction record is required to
		 * be encapsulated as a "Auction" class object and added to the "auctions" List
		 * The items and auctions Lists are to be added to the "output" List and
		 * returned
		 */

		List output = new ArrayList();
		List<Item> items = new ArrayList<Item>();
		List<Auction> auctions = new ArrayList<Auction>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			CallableStatement availableAuctionsProc = con.prepareCall("{CALL AuctionsAvailableByType(?)}");
			availableAuctionsProc.setString("type", itemType);

			boolean hadResults = availableAuctionsProc.execute();
			while (hadResults) {

				ResultSet availableAuctions = availableAuctionsProc.getResultSet();
				while (availableAuctions.next()) {
					Statement getAuctionST = con.createStatement();
					ResultSet auctionRS = getAuctionST.executeQuery(
							"SELECT * FROM Auctions WHERE AuctionID=" + availableAuctions.getString("AuctionID"));
					auctionRS.next();

					Statement getItemST = con.createStatement();
					ResultSet itemRS = getItemST
							.executeQuery("SELECT * from Items WHERE ItemID=" + auctionRS.getString("ItemID"));
					itemRS.next();

					Item item = new Item();
					item.setItemID(itemRS.getInt("ItemID"));
					item.setName(itemRS.getString("ItemName"));
					item.setType(itemRS.getString("ItemType"));
					item.setDescription(itemRS.getString("Description"));
					item.setNumCopies(itemRS.getInt("NumInStock"));
					items.add(item);

					Auction auction = new Auction();
					auction.setAuctionID(auctionRS.getInt("AuctionID"));
					auction.setMinimumBid(auctionRS.getFloat("MinimumBid"));
					auction.setBidIncrement(auctionRS.getFloat("Increment"));
					auctions.add(auction);

				}

				hadResults = availableAuctionsProc.getMoreResults();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		output.add(items);
		output.add(auctions);

		return output;
	}

	public List<Item> getBestsellersForCustomer(String customerID) {
		// TODO: Needs validation testing
		/*
		 * The students code to fetch data from the database will be written here. Each
		 * record is required to be encapsulated as a "Item" class object and added to
		 * the "items" ArrayList. Query to get the Best-seller list of items for a
		 * particular customer, indicated by the customerID, has to be implemented The
		 * customerID, which is the customer's ID for whom the Best-seller items have to
		 * be fetched, is given as method parameter
		 */

		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_ROOT_USR, DB_ROOT_PW);

			CallableStatement bestSellersProc = con.prepareCall("{CALL PersonalBestSellers(?)}");
			Statement getItemsST = con.createStatement();

			bestSellersProc.setInt("CustomerID", Integer.parseInt(customerID));
			boolean sellerHasMoreItems = bestSellersProc.execute();

			while (sellerHasMoreItems) {
				ResultSet bestSellersInfo = bestSellersProc.getResultSet();
				while (bestSellersInfo.next()) {
					ResultSet itemRS = getItemsST
							.executeQuery("SELECT * FROM Items WHERE ItemID=" + bestSellersInfo.getInt("ItemID"));

					while (itemRS.next()) {
						Item item = new Item();
						item.setItemID(itemRS.getInt("ItemID"));
						item.setName(itemRS.getString("ItemName"));
						item.setType(itemRS.getString("ItemType"));
						item.setDescription(itemRS.getString("Description"));
						item.setNumCopies(bestSellersInfo.getInt("NumAuctions"));
						items.add(item);

					}

				}

				sellerHasMoreItems = bestSellersProc.getMoreResults();
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return items;

	}

}