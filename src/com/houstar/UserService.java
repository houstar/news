package com.houstar;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

	public static List<User> getUsers(String userName, int userAge) {
		List<User> usrList = new ArrayList<User>();
		DatabaseOperator db = new DatabaseOperator();
		try {
			userName = new String(userName.getBytes("ISO8859_1"), "UTF-8");
			System.out.println("userName =" + userName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String sql = "select * from tbl_user";
		if (!"".equals(userName) || userAge != 0) {
			sql += " where 1=1";
		}
		if (!"".equals(userName)) {
			sql += " and userName like '%" + userName + "%'";
		}
		if (userAge != 0) {
			sql += " and userAge =" + userAge;
		}
		System.out.println(sql);
		try {
			ResultSet rs = db.executeQuery(sql);

			while (rs.next()) {
				User usr = new User(rs.getString("userName"),
						rs.getString("userPasswd"), rs.getInt("userAge"));
				usrList.add(usr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usrList;
	}

	public User getUser(String userName) {
		DatabaseOperator db = new DatabaseOperator();

		String sql = "select * from tbl_user";
		if (!"".equals(userName)) {
			sql += " where 1=1";
		}
		if (!"".equals(userName)) {
			sql += " and userName = '" + userName + "'";
		}

		System.out.println(sql);
		User usr = new User();
		try {
			ResultSet rs = db.executeQuery(sql);

			while (rs.next()) {
				usr.setUserName(rs.getString("userName"));
				usr.setUserPasswd(rs.getString("userPasswd"));
				usr.setUserAge(rs.getInt("userAge"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usr;
	}

	public void delUser(String userName) {
		DatabaseOperator db = new DatabaseOperator();

		String sql = "delete from tbl_user where userName ='" + userName + "'";
		try {
			db.executeUpdate(sql);
			System.out.println(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updUser(String userName, String userPasswd, int userAge,
			String oldName) {
		DatabaseOperator db = new DatabaseOperator();
		System.out.println("Update user");
		String sql = "update tbl_user set userName=?,userPasswd=?,userAge=? where userName=?";
		PreparedStatement pstmt;
		try {
			pstmt = db.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userPasswd);
			pstmt.setInt(3, userAge);
			pstmt.setString(4, oldName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addUser(String userName, String userPasswd, int userAge) {
		DatabaseOperator db = new DatabaseOperator();
		String sql = "insert into tbl_user (userName, userPasswd, userAge) values(?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = db.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userPasswd);
			pstmt.setInt(3, userAge);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
