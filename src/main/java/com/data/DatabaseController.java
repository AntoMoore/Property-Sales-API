package com.data;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.resources.Agent;

public class DatabaseController {

	//constants
	private static final String CLASS_NAME = "org.sqlite.JDBC";
	private static final String DATABASE_URL = "jdbc:sqlite:property_sales_data.db";

	//member variables
	private static DatabaseController database_controller_instance = null;
	private ConnectionSource connectionSource = null;
	private Dao<Agent, String> agentDao = null;

	private DatabaseController() {
		// no-arg constructor
	}

	public static DatabaseController getInstance() {
		if(database_controller_instance == null)
			database_controller_instance = new DatabaseController();

		return database_controller_instance;
	}

	public void connect() {
		try {
			Class.forName(CLASS_NAME);
			connectionSource = new JdbcConnectionSource(DATABASE_URL);

			//create database tables if empty
			TableUtils.createTableIfNotExists(connectionSource, Agent.class);

			//data access objects
			agentDao = DaoManager.createDao(connectionSource, Agent.class);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// connected to database
		System.out.println("Connection Successful.....");	
	}

	public Dao<Agent, String> getAgentDao() {
		return agentDao;
	}

}
