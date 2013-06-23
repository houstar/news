package com.houstar;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.houstar.DatabaseOperator;

public class User {
	private String userName;
	private String userPasswd;
	private int userAge;

	public User() {
	}

	public User(String userName, String userPasswd, int userAge) {
		super();
		this.userName = userName;
		this.userPasswd = userPasswd;
		this.userAge = userAge;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public  List<User> getUsers(String userName, int userAge) {
		List<User> usrList = new ArrayList<User>();
		DatabaseOperator db = new DatabaseOperator();
		try {
			userName = new String(userName.getBytes("ISO8859_1"), "UTF-8");
			System.out.println("um=" + userName);
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

}
