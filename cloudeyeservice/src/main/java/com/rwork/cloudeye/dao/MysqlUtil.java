package com.rwork.cloudeye.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class MysqlUtil {

	/*public static void main(String[] args) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("class loaded");
			Connection con=DriverManager.getConnection("jdbc:mysql://192.168.99.100:32771/cloudeye","cloud","cloud");
			System.out.println("got connection");
			Statement stmt= con.createStatement();
			//stmt.executeUpdate("create table temptable(id integer not null, name varchar(25), primary key (id))");
			//stmt.executeUpdate("insert into temptable values(101, 'balram')");
			ResultSet rst= stmt.executeQuery("select * from temptable");
			while(rst.next())
			{
				int id= rst.getInt("id");
				String name= rst.getString("name");
				System.out.println("id="+id+", name="+name);
			}
		}
		catch(ClassNotFoundException e){
			System.out.println("failed to load driver class");
		} catch (SQLException e) {
			System.out.println("failed to get connection");
			e.printStackTrace();
		}

	}*/
	
	

}
