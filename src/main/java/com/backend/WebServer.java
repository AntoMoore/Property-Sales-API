package com.backend;

import com.data.DatabaseController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.resources.ResourceFactory;

import static spark.Spark.get;

import java.util.Map;
import java.util.TreeMap;

import spark.Spark;

public class WebServer {
	// connection type and reference
	public static final String CONNECTION_TYPE = "PRODUCTION"; // (production/test)
	private static DatabaseController databaseController = DatabaseController.getInstance();
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

		// GET request using wildcard value
		get("/openproperty/api/*", (request, res) -> {
			Map<String, String[]> map = new TreeMap<>();
			map = request.queryMap().toMap();
			System.out.println("Number of Params: " + map.size());
			
			String resValue = request.queryMap().get("resource").value();
			String resId = request.queryMap().get("id").value();
			
			return "param-1: " + resValue + "param-2:" + resId;
		});	

	}
	
	public void stopWebServer() {
		System.out.println("Stopping Server...");
		Spark.stop();	}

}
