package com.yedam;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dao {
	// jdbc driver 체크
	static String url = "jdbc:oracle:thin:@192.168.0.5:1521/xe";
	static String user = "proj";
	static String pass = "proj";
	static Connection conn;
	public static Connection getConnect() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection(url,user,pass);
		} catch(Exception e ) {
			e.printStackTrace();
		}
		return conn;
	}
}
