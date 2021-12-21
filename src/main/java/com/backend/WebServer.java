package com.backend;

import com.data.DatabaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resources.Agent;

import static spark.Spark.get;
import spark.Spark;

public class WebServer {
	// connection type and reference
	public static final String CONNECTION_TYPE = "PRODUCTION"; // (production/test)
	private static DatabaseController databaseController = DatabaseController.getInstance();

	public void startWebServer() {
		//connect to database
		databaseController.connect(CONNECTION_TYPE);

		// === HTTP ROUTES ====

		// GET status 
		get("/status", (req, res) -> {
			res.status(200);
			//String serverStatus = "status: " + res.status();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + res.status());
		});

		// GET Sensor by ID
		get("/agent/:id", (req, res) -> {
			// search by primary key
			Agent agent = databaseController.getAgentDao().queryForId("1");

			if (agent != null) {
				res.status(200);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				return gson.toJson(agent);
			} else {
				res.status(400);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				return gson.toJson("error: Agent not found");
			}	
		});	

	}
	
	public void stopWebServer() {
		System.out.println("Stopping Server...");
		Spark.stop();	}

}
