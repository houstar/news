package com.houstar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperator {
	private Connection conn = null;
	private Statement stmt = null;

	public DatabaseOperator() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://houstar168.xicp.net:3306/newsdb", "newsdb",
					"newsdb");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return stmt.executeQuery(sql);
	}

	public int executeUpdate(String sql) throws SQLException {
		return stmt.executeUpdate(sql);
	}

	public static void main(String args[]) throws SQLException {
		DatabaseOperator dbOperator = new DatabaseOperator();
		ResultSet rs = dbOperator.executeQuery("select * from tbl_user");
		while (rs.next()) {
			System.out.println(rs.getString("userName") + "\t"
					+ rs.getString("userPasswd") + "\t"
					+ rs.getString("userAge"));

		}
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
}
