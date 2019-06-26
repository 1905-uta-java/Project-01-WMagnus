package com.revature.projects.wmagnus.project1.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

	private static Connection connection;
	private static Driver d;
	
	public static void initConnections()
	{
		System.out.println("Trying to init connections.");
		try {
			d = new oracle.jdbc.driver.OracleDriver();
			DriverManager.registerDriver(d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void destroyDriver()
	{
		try {
			DriverManager.deregisterDriver(d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConectionFromFile() throws SQLException, IOException {
		Properties properties = new Properties();
		InputStream in = new FileInputStream("C:\\Users\\wsm\\Documents\\workspace-sts-3.9.7.RELEASE\\project-01\\connection.properties");
		properties.load(in);
		
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		if (connection == null || connection.isClosed()) 
		{
			connection = DriverManager.getConnection(url, username, password);
		}
		return connection;
		
	}

	
}
