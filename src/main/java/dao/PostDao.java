package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Post;

public class PostDao {
	private final String DB_URL = "jdbc:mysql://localhost:3306/cse305db";

	public List<Item> getSalesReport(Post post) {

		/*
		 * The students code to fetch data from the database will be written here Each
		 * record is required to be encapsulated as a "Item" class object and added to
		 * the "items" List Query to get sales report for a particular month must be
		 * implemented post, which has details about the month and year for which the
		 * sales report is to be generated, is given as method parameter The month and
		 * year are in the format "month-year", e.g. "10-2018" and stored in the
		 * expireDate attribute of post object The month and year can be accessed by
		 * getter method, i.e., post.getExpireDate()
		 */
		List<Item> items = new ArrayList<Item>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, "root", "cse305");

			String month = post.getExpireDate().split("-")[0];
			String year = post.getExpireDate().split("-")[1];

			Statement st = con.createStatement();
			ResultSet auctionsRS = st.executeQuery("SELECT * FROM Auctions " + "WHERE (YEAR(ClosingDateTime) <= " + year
					+ " AND MONTH(ClosingDateTime) <=" + month + " AND ClosingBid > 0)");

			while (auctionsRS.next()) {
				int itemID = auctionsRS.getInt("ItemID");
				ResultSet itemsRS = st.executeQuery("SELECT * FROM Items WHERE ItemID=" + itemID);

				Item item = new Item();
				item.setName(itemsRS.getString("ItemName"));
				item.setSoldPrice((int) auctionsRS.getFloat("ClosingBid"));
				items.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

//		List<Item> items = new ArrayList<Item>();

		/* Sample data begins */
//		for (int i = 0; i < 10; i++) {
//			Item item = new Item();
//			item.setName("Sample item");
//			item.setSoldPrice(100);
//			items.add(item);
//		}
		/* Sample data ends */

		return items;

	}
}
