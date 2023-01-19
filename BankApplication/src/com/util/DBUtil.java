package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	//this class used for making connection to DB
		//static Connection:singleton only one object
	
	private static Connection con;
	
	public static void openConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver Loaded....");
		
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/suraj","root","root123");
		System.out.println("Connected To DB.....");
	}

	public static Connection getCon() {
		return con;
	}
	
	public static void closeXConnection() throws SQLException {
		
		if(con!=null) {
			con.close();
		}
		
	}
}
