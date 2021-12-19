package com.app;

import java.sql.SQLException;

import com.data.DatabaseController;
import com.resources.Agent;

public class Application {

	public static void main(String[] args) throws SQLException {
		//database controller
		DatabaseController databaseController = DatabaseController.getInstance();
		databaseController.connect();

		Agent a1 = new Agent();
		a1.setAgentName("TestAgent");
		databaseController.getAgentDao().create(a1);
		System.out.println("Created Agent: " + a1.toString());

	}

}
