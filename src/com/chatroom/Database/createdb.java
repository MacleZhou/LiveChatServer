package com.chatroom.Database;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.chatroom.configuration.Config;
import com.chatroom.others.LogFileWriter;


public class createdb {
	public static void main(String[] args) {
		Connection connection = null;
		java.sql.Statement statement= null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			//creating database
			connection = DriverManager.getConnection(Config.DATABASE_URL,Config.USER_NAME,Config.USER_PWD);
			String Query = "CREATE DATABASE IF NOT EXISTS "+ Config.DATABASE_NAME;
			statement = connection.createStatement();
			statement.executeUpdate(Query);
			connection.close();
			
			//for execute multiple queries separate queries by semicolon
			connection = DriverManager.getConnection(Config.DATABASE_URL+'/'+Config.DATABASE_NAME+"?allowMultiQueries=true",Config.USER_NAME,Config.USER_PWD);
			String Queries = "CREATE TABLE IF NOT EXISTS " + Config.TABLE_NAME + "(" + Config.CLIENT_ID + " int auto_increment," + Config.CLIENT_NAME + " VARCHAR(50) not null, "+ Config.CLIENT_PWD + " VARCHAR(150), " +"primary key(" +Config.CLIENT_ID+ "))";
			
			statement = connection.createStatement();
			statement.executeUpdate(Queries);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace(new PrintWriter(Config.errors));
			LogFileWriter.Log(Config.errors.toString());
		} catch (SQLException e) {
			e.printStackTrace(new PrintWriter(Config.errors));
			LogFileWriter.Log(Config.errors.toString());
		}
		finally {
			try {
				connection.close(); //close the database connection
			} catch (SQLException e) {
				e.printStackTrace(new PrintWriter(Config.errors));
				LogFileWriter.Log(Config.errors.toString());
			}
		}
	}
}
