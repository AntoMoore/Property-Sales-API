package com.backend;

import com.controllers.RequestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resources.Resource;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import spark.Spark;

import java.util.List;

public class WebServer {

	public void startWebServer() {
		//connect to database
		RequestController requestController = new RequestController();
		requestController.connectDatabase();

		// GET status 
		get("/openproperty/status", (request, response) -> {
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

		// GET agents using wildcard value
		get("/openproperty/agents/*", (request, response) -> {
			List<Resource> list = requestController.getAgentRequest(request);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(list);
		});	

		// GET properties using wildcard value
		get("/openproperty/properties/*", (request, response) -> {
			List<Resource> list = requestController.getPropertyRequest(request);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(list);
		});	

		// GET sales using wildcard value
		get("/openproperty/sales/*", (request, response) -> {
			List<Resource> list = requestController.getSaleRequest(request);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson(list);
		});	

		// POST agent
		post("/openproperty/agents", (request, response) -> {
			requestController.postAgentRequest(request);
			response.status(201);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

		// POST property
		post("/openproperty/properties", (request, response) -> {
			requestController.postPropertyRequest(request);
			response.status(201);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

		// POST property
		post("/openproperty/sales", (request, response) -> {
			requestController.postSaleRequest(request);
			response.status(201);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

		// DELETE agent
		delete("/openproperty/agents/remove/", (request, response) -> {
			requestController.deleteAgentRequest(request);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

		// DELETE property
		delete("/openproperty/properties/remove/", (request, response) -> {
			requestController.deletePropertyRequest(request);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

		// DELETE sale
		delete("/openproperty/sales/remove/", (request, response) -> {
			requestController.deleteSaleRequest(request);
			response.status(200);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			return gson.toJson("status: " + response.status());
		});

	}

	public void stopWebServer() {
		System.out.println("Stopping Server...");
		Spark.stop();	
	}

}
