package com.app;

import java.sql.SQLException;
import java.util.Date;

import com.data.DatabaseController;
import com.resources.Agent;
import com.resources.Property;
import com.resources.Sale;

public class Application {
	//constants
	public static final String CONNECTION_TYPE = "PRODUCTION";

	public static void main(String[] args) throws SQLException {
		//database controller
		DatabaseController databaseController = DatabaseController.getInstance();
		databaseController.connect(CONNECTION_TYPE);

		//create Agent
		Agent a1 = new Agent();
		a1.setAgentName("TestAgent");
		a1.setAgentCommission(0.5f);
		databaseController.getAgentDao().create(a1);
		System.out.println("Created Agent: " + a1.toString());
		
		//create Property
		Agent a2 = databaseController.getAgentDao().queryForId("1");
		Property p1 = new Property();
		p1.setPropertyType("House");
		p1.setPropertyAddress("123 Fake Street");
		p1.setPropertyValue(100000.00f);
		p1.setPropertyAgent(a2);
		databaseController.getPropertyDao().create(p1);
		System.out.println("Created Property: " + p1.toString());
		
		//create Sale
		Property p2 = databaseController.getPropertyDao().queryForId("1");
		Sale s1 = new Sale();
		s1.setSaleDate(new Date());
		s1.setSaleProperty(p2);
		databaseController.getSaleDao().create(s1);
		System.out.println("Created Sale: " + s1.toString());
		
	}
}
