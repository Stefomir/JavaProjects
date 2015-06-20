package com.calculator;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class MySql {

	Connection con = null;
	PreparedStatement ps = null;
	
	//method that connects the the database
	public void ConnectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {

		}
		String url = "jdbc:mysql://localhost/calculator";
		String user = "stefko";
		String password = "stefko";

		try {
			con = (Connection) DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//method that closes all connections
	public void closeAllConections() {
		try {
			if (ps != null)
				ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// method that return history for a user by its ID
	public List<String> selectHistoryCalculate(String strIdUser) {

		List<String> mylist = new ArrayList<String>();
		ConnectDB();

		try {
			ps = (PreparedStatement) con
					.prepareStatement("SELECT * FROM history where history.idUser=? order by dateMade desc LIMIT 10");
			ps.setString(1, strIdUser);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				mylist.add(rs.getString("DateMade") + ": "
						+ rs.getString("result"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAllConections();
		}
		return mylist;
	}
	//method that saves the user history in to the database
	public void insertHistoryCalculate(String strUserID, String strEndResult) {
		ConnectDB();
		try {
			ps = (PreparedStatement) con
					.prepareStatement("INSERT INTO history (idUser,result,dateMade) values(?,?,NOW())");
			ps.setInt(1, Integer.parseInt(strUserID));
			ps.setString(2, strEndResult);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAllConections();
		}
	}
	//method that checks if there is a dublicate user by its name and email
	public boolean DublicateUsernameEmail(String strUsername, String strEmail) {
		boolean flag = false;

		ConnectDB();
		try {
			ps = (PreparedStatement) con
					.prepareStatement("SELECT idUser FROM user where Upper(username)=Upper(?) and Upper(email)=Upper(?)");

			ps.setString(1, strUsername);
			ps.setString(2, strEmail);
			ResultSet rs = ps.executeQuery();
			if (rs == null || !rs.first()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAllConections();
		}
		return flag;
	}
	//method that saves the user info in registration page
	public void insertUserInfo(String strUsername, String strPassword,
			String strFullname, String strEmail) {
		ConnectDB();
		try {
			ps = (PreparedStatement) con
					.prepareStatement("INSERT INTO user (username,password,fullname,email) values(?,?,?,?)");

			ps.setString(1, strUsername);
			ps.setString(2, strPassword);
			ps.setString(3, strFullname);
			ps.setString(4, strEmail);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAllConections();
		}
	}
	//method that deletes the user history
	public void delUserHistory(String strIdUser) {
		ConnectDB();
		try {
			ps = (PreparedStatement) con
					.prepareStatement("Delete from history where idUser=?");
			ps.setString(1, strIdUser);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAllConections();
		}
	}
	//method that validates the user info in the login stage
	public int loginValidation(String strUsername, String strPassword) {
		int iIdUser = -1;
		ConnectDB();
		try {
			ps = (PreparedStatement) con
					.prepareStatement("SELECT idUser FROM user where username=? and password=?");
			ps.setString(1, strUsername);
			ps.setString(2, strPassword);
			ResultSet rs = ps.executeQuery();
			if (!(rs == null || !rs.first())) {
				iIdUser = rs.getInt("idUser");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAllConections();
		}
		return iIdUser;
	}
}
