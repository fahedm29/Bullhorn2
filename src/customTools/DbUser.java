package customTools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;


import util.MD5Util;
import model.BhUser;

public class DbUser {

	public static BhUser getUser(long BhUserID)
	{

		String sql = "Select BhUserid,username,useremail,userpassword,joindate,motto " + 
				"from BhUser where BhUserid = ?";
		BhUser user = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1,BhUserID);
			rs = pstmt.executeQuery();
			if (rs.next()){
				user = new BhUser();
				user.setBhuserid(rs.getLong(1));
				user.setUsername(rs.getString(2));
				user.setUseremail(rs.getString(3));
				user.setUserpassword(rs.getString(4));
				//user.setJoindate(java.util.Date(rs.getString(5)));
				user.setMotto(rs.getString(6));
			}




		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return user;		
	}

	public static int insert(BhUser BhUser) {
		String sql = "insert into BhUser (username,useremail,userpassword,joindate,motto) " + 
				"values(?,?,?,?,?)";
		int recordsAffected = 0;
		BhUser user = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,BhUser.getUsername());
			pstmt.setString(2, BhUser.getUseremail());
			pstmt.setString(3, BhUser.getUserpassword());
			pstmt.setDate(4, null);
			pstmt.setString(5, BhUser.getMotto());
			recordsAffected = pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return recordsAffected;

	}
	/**
	 * Gets a Gravatar URL given the email and size
	 * In accordance with Gravatar's requirements the email will be hashed
	 * with the MD5 hash and returned as part of the url
	 * The url will also include the s=xx attribute to request a Gravatar of a
	 * particular size.
	 * References: <a href="http://www.gravatar.com">http://www.gravatar.com</>
	 * @param email - email of the user who's gravatar you want
	 * @param size - indicates pixel height of the image to be returned. Height and Width are same.
	 * @return - the gravatar URL. You can test it in a browser.
	 */
	public static String getGravatarURL(String email, Integer size){
		String url = "http://www.gravatar.com/avatar/" +
				MD5Util.md5Hex(email) + "?s=" + size.toString();
		return url;
	}
	/**
	 * Updates the data in a BhUser
	 * Pass the method a BhUser with all the values set to your liking and 
	 * this method will update the database with these values.
	 * This method doesn't actually return anything but the good feeling
	 * that your update has been completed. If it can't be completed then 
	 * it won't tell you. Sounds like something needs to be added in the future. Hmmm.
	 * @param BhUser
	 */
	public static int update(BhUser BhUser) {

		Connection con = null;
		PreparedStatement pstmt = null;

		int nmbrUpdated = 0;

		String sql = "update BhUser set username=?, userpassword=?," + 
				"useremail=?,motto=? where BhUserid=?";

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, BhUser.getUsername());
			pstmt.setString(2, BhUser.getUserpassword());
			pstmt.setString(3, BhUser.getUseremail());
			pstmt.setString(4, BhUser.getMotto());
			//java.sql.Date datejoined = new java.sql.Date(BhUser.getJoindate().getTime());
			//pstmt.setString(5, datejoined.toString());
			pstmt.setLong(5, BhUser.getBhuserid());
			nmbrUpdated = pstmt.executeUpdate();


		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return nmbrUpdated;
	}

	/**
	 * Gets a user given their email address.
	 * You've got the email when they log in but you really need the 
	 * user and all its related information This method will find the user
	 * matching that email. The database should ensure that you can't have two users
	 * with the same email. Otherwise there's no telling what you'd get.
	 * @param email
	 * @return BhUser with that unique email address
	 */
	public static BhUser getUserByEmail(String useremail)
	{
		String sql = "Select BhUserid,username,useremail,userpassword,joindate,motto " + 
				"from BhUser where useremail = ?";
		BhUser user = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,useremail);
			rs = pstmt.executeQuery();
			if (rs.next()){
				user = new BhUser();
				user.setBhuserid(rs.getLong(1));
				user.setUsername(rs.getString(2));
				user.setUseremail(rs.getString(3));
				user.setUserpassword(rs.getString(4));
				//user.setJoindate(java.util.Date(rs.getString(5)));
				user.setMotto(rs.getString(6));
			}




		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return user;
	}

	/**
	 * Is this user valid? This method has the answer for you.
	 * Checks the database and counts the number of users with this
	 * username and password. If it returns 0 then either the username
	 * or password don't exist in the database. If it returns 1 then you have found 
	 * the user with that username and password. If it returns >1 then you need to 
	 * fix your database first.
	 * @param user of type BhUser
	 * @return true or false indicating the user exists or doesn't
	 */
	public static boolean isValidUser(String useremail, String userpassword)
	{
		boolean isValid = false;
		int recordsAffected = 0;
		String sql = "Select count(BhUserid) from BhUser "
				+ "where useremail = ? and userpassword = ?";

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,useremail);
			pstmt.setString(2, userpassword);
			rs = pstmt.executeQuery();
			if (rs.next()){
				recordsAffected = rs.getInt(1);

			}




		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		if (recordsAffected==1){
			isValid = true;
		}else{
			isValid = false;
		}

		return isValid;

	}

}
