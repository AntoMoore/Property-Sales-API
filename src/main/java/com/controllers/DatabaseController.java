package com.controllers;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.resources.Agent;
import com.resources.Property;
import com.resources.Sale;

public class DatabaseController {
	//constants
	private static final String CLASS_NAME = "org.sqlite.JDBC";
	private static final String PRODUCTION_DATABASE_URL = "jdbc:sqlite:property_sales_data.db";
	private static final String TEST_DATABASE_URL = "jdbc:sqlite:test_data.db";

	//member variables
	private static DatabaseController database_controller_instance = null;
	private ConnectionSource connectionSource = null;
	private Dao<Agent, String> agentDao = null;
	private Dao<Property, String> propertyDao = null;
	private Dao<Sale, String> saleDao = null;

	private DatabaseController() {
		// no-arg constructor
	}

	// singleton getInstance()
	public static DatabaseController getInstance() {
		if(database_controller_instance == null)
			database_controller_instance = new DatabaseController();

		return database_controller_instance;
	}

	public void connect(String connectionType) {
		try {
			Class.forName(CLASS_NAME);
			
			if(connectionType.equalsIgnoreCase("TEST")) {
				connectionSource = new JdbcConnectionSource(TEST_DATABASE_URL);
			} 
			else if(connectionType.equalsIgnoreCase("PRODUCTION")){
				connectionSource = new JdbcConnectionSource(PRODUCTION_DATABASE_URL);
			}
			else {
				System.out.println("Invalid Connection Type, must be TEST or PRODUCTION.");
				return;
			}
			
			//create database tables if empty
			TableUtils.createTableIfNotExists(connectionSource, Agent.class);
			TableUtils.createTableIfNotExists(connectionSource, Property.class);
			TableUtils.createTableIfNotExists(connectionSource, Sale.class);

			//data access objects
			agentDao = DaoManager.createDao(connectionSource, Agent.class);
			propertyDao = DaoManager.createDao(connectionSource, Property.class);
			saleDao = DaoManager.createDao(connectionSource, Sale.class);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Dao<Agent, String> getAgentDao() {
		return agentDao;
	}
	
	public Dao<Property, String> getPropertyDao() {
		return propertyDao;
	}
	
	public Dao<Sale, String> getSaleDao() {
		return saleDao;
	}
	
	public void clearTables() throws SQLException {
		TableUtils.dropTable(connectionSource, Agent.class, true);
		TableUtils.dropTable(connectionSource, Property.class, true);
		TableUtils.dropTable(connectionSource, Sale.class, true);
	}
}
