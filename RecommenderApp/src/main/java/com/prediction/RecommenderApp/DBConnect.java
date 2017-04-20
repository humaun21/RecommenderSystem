package com.prediction.RecommenderApp;

import java.sql.*;
public class DBConnect {
	
	final String driver="com.mysql.cj.jdbc.Driver";
	final String dbPath="jdbc:mysql://localhost:3306/recommender_app";
	String userName=null;
	String password=null;
	Connection conn=null;
	Statement stm=null;
	
	//Constructor will accept username and password from MySql.
	public DBConnect(String name, String pass){
		userName=name;
		password=pass;
	}
	//Define the connection method
	public void connect() throws SQLException,Exception{
		Class.forName(driver);
		conn=DriverManager.getConnection(dbPath,userName,password);
		stm=conn.createStatement();
	}
	//Method to close connection
	public void closeConnection()throws SQLException,Exception{
		stm.close();
		conn.close();
	}
	//Getters and setters
	public Connection getConnection(){
		return conn;
	}
	public Statement getStatement(){
		return stm;
	}

}
