package com.backend;

import com.data.DatabaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resources.Resource;

import static spark.Spark.get;
import spark.Spark;

import java.util.List;

public class WebServer {
	// connection type and reference
	public static final String CONNECTION_TYPE = "PRODUCTION"; // (production/test)
	private static DatabaseController databaseController = DatabaseController.getInstance();
	private RequestController requestController = new RequestController();
	//private static ResourceFactory resourceFactory = new ResourceFactory();

	public void startWebServer() {
		//connect to database
		databaseController.connect(CONNECTION_TYPE);

		// === HTTP ROUTES ====

		// GET status 
		get("/openproperty/status", (request, response) -> {
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

		// GET agents using wildcard value
		get("/openproperty/agents/*", (request, response) -> {
			List<Resource> list = requestController.getAgentRequest(databaseController, request, response);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(list);
		});	

		// GET properties using wildcard value
		get("/openproperty/properties/*", (request, response) -> {
			List<Resource> list = requestController.getPropertyRequest(databaseController, request, response);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(list);
		});	

		// GET sales using wildcard value
		get("/openproperty/sales/*", (request, response) -> {
			List<Resource> list = requestController.getSaleRequest(databaseController, request, response);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(list);
		});	

	}

	public void stopWebServer() {
		System.out.println("Stopping Server...");
		Spark.stop();	}

}
